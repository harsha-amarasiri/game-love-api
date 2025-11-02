package com.gamelove.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "gl_games")
public class Game {

    @Id
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private UUID Id;

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
    @CollectionTable(name = "gl_game_genres", joinColumns = @JoinColumn(name = "game_id"))
    @Column(name = "genre", nullable = false)
    private List<Genre> genres;


    @PrePersist
    public void prePersist() {
        if (this.Id == null) {
            this.Id = UUID.randomUUID();
        }
    }

    @Override
    public String toString() {
        return "Game{" + "Id=" + Id + ", title='" + title + '\'' + ", description='" + description + '\'' + ", status=" + status + ", genres=" + genres + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Id.equals(game.Id) && title.equalsIgnoreCase(game.title);
    }
}
