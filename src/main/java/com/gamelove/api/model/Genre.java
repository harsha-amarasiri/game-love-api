package com.gamelove.api.model;

public enum Genre {

    ACTION("Action"),
    ADVENTURE("Adventure"),
    FIGHTING("Fighting"),
    RPG("Role-Playing Game"),
    FPS("First-Person Shooter"),
    MMO("Massively Multiplayer Online"),
    HORROR("Horror"),
    MUSIC("Music"),
    PUZZLE("Puzzle"),
    RACING("Racing"),
    ROGUELIKE("Roguelike"),
    SIMULATION("Simulation"),
    SPORTS("Sports"),
    STRATEGY("Strategy"),
    TRIVIA("Trivia"),
    OTHER("Other");

    private final String name;

    Genre(String name) {
        this.name = name;
    }

}
