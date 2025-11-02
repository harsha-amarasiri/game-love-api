package com.gamelove.api.model;

public enum Genre {

    ACTION("Action"),
    ADVENTURE("Adventure"),
    BATTLE_ROYALE("Battle Royale"),
    FIGHTING("Fighting"),
    FPS("First-Person Shooter"),
    HORROR("Horror"),
    INDIE("Indie"),
    MMO("Massively Multiplayer Online"),
    MOBA("Multiplayer Online Battle Arena"),
    MUSIC("Music"),
    PLATFORMER("Platformer"),
    PUZZLE("Puzzle"),
    RACING("Racing"),
    ROGUELIKE("Roguelike"),
    RPG("Role-Playing Game"),
    SANDBOX("Sandbox"),
    SHOOTER("Shooter"),
    SIMULATION("Simulation"),
    SPORTS("Sports"),
    STRATEGY("Strategy"),
    SURVIVAL("Survival"),
    TRIVIA("Trivia"),
    OTHER("Other");

    private final String name;

    Genre(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}