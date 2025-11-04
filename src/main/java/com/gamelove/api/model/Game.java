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
@Table(name = "gl_games",
        uniqueConstraints = @UniqueConstraint(name = "uk_game__title", columnNames = {"title"})
)
public class Game {

    @Id
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private UUID id;

    @Column(name = "title", length = 255, unique = true, nullable = false)
    private String title;

    @Column(name = "description", length = 1000, nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 10, nullable = false)
    private Status status;

    // didn't add fields related to publishing / developer to retain brevity

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "gl_game_genres",
            joinColumns = @JoinColumn(name = "game_id", foreignKey = @ForeignKey(name = "fk_game__game_genres")))
    @Column(name = "genre", nullable = false)
    private List<Genre> genres = new ArrayList<>();


    @PrePersist
    public void prePersist() {
        if (this.id == null) {
            this.id = UUID.randomUUID();
        }
    }

    @Override
    public String toString() {
        return "Game{" + "Id=" + id + ", title='" + title + '\'' + ", description='" + description + '\'' + ", status=" + status + ", genres=" + genres + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return id.equals(game.id) && title.equalsIgnoreCase(game.title);
    }

    @Override
    public int hashCode() {
        return id.hashCode() + title.toLowerCase().hashCode();
    }
}
