package com.gamelove.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "gl_players",
        uniqueConstraints = @UniqueConstraint(name = "uk_player__username", columnNames = {"username"})
)
public class Player {

    @Id
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private UUID id;

    @Column(name = "username", length = 64, nullable = false, unique = true)
    private String username;

    @Column(name = "first_name", length = 255, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 255, nullable = false)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 10, nullable = false)
    private Status status;


    // loved games - many-to-many relationship
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "gl_player_loved_games",
            joinColumns = @JoinColumn(name = "player_id", foreignKey = @ForeignKey(name = "fk_player__player_loved_games")),
            inverseJoinColumns = @JoinColumn(name = "game_id", foreignKey = @ForeignKey(name = "fk_game__player_loved_games"))
    )
    private List<Game> lovedGames = new ArrayList<>();

    public void loveGame(Game game) {
        this.lovedGames.add(game);
    }

    public void unloveGame(Game game) {
        this.lovedGames.remove(game);
    }


    @Override
    public String toString() {
        return "Player{" + "id=" + id + ", username='" + username + '\'' + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", status=" + status + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return id.equals(player.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @PrePersist
    public void prePersist() {
        if (this.id == null) {
            this.id = UUID.randomUUID();
        }
    }


}
