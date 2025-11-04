# Game Love API :video_game: :heart:

> Game Love API is a simple and lightweight RESTful API designed for recording games loved by players and check out most
> popular games loved by the players.

## :card_index: Table of Contents

1. Overview
2. Features
3. Architecture
4. Technology Stack
5. Database Schema
6. API Endpoints
7. Getting Started
8. Building and running
9. Testing
10. Project Structure

## :mag_right: Overview

This is a RESTful API implemented with Spring Boot that allows users to record games they love and retrieve a list of
the most popular games. The API provides endpoints for CRUD operations on Games Resource and Players Resource.

Requirements for this API are specified [here](https://github.com/comeon-group/comeon-gamelove-assignment)

> :fire: A more complete version of this project with additional features and improvements can be found at
> [GameOn API](https://github.com/harsha-amarasiri/game-on-api).
> This project is implemented loosely following DDD principles to demonstrate technologies and patterns including *Soft
Deletes, JPA
> Auditing, Entity Versioning, Database Migrations, Pagination & Sorting, Request Validation, Global Exception Handling,
Enhanced API error handling
> and more.*

## :bulb: Features

- Love a Game: Players can express love for their favorite games
- Unlove a Game: Remove love for a previously loved game
- Player's Loved Games: Retrieve all games loved by a specific player
- Most Loved Games: Get top N most loved games with love counts
- Game Catalog: Fetch all available games in the system
- Embedded Database: Uses H2 local-file database for easy setup and testing

## :classical_building: Architecture

Application implements a layered architecture with the following components:

```mermaid
graph TB
    subgraph CLIENTS["Clients"]
        Client[Client/API Consumer]
        Swagger[Swagger UI]
    end

    subgraph API["API Layer - Controllers"]
        GC[GameController<br/>/api/games]
        PC[PlayerController<br/>/api/players]
    end

    subgraph MAPPERS["Mapping Layer"]
        GM[GameMapper<br/>MapStruct]
        PM[PlayerMapper<br/>MapStruct]
    end

    subgraph SERVICES["Service Layer"]
        GS[GameService]
        PS[PlayerService]
    end

    subgraph REPOSITORY["Repository Layer"]
        GR[GameRepository<br/>JpaRepository]
        PR[PlayerRepository<br/>JpaRepository]
    end

    subgraph "Database Layer"
        DB[(H2 Database)]
        GT[gl_games]
        PT[gl_players]
        PGT[gl_player_loved_games<br/>Join Table]
        GGT[gl_game_genres]
        DB --> GT
        DB --> PT
        DB --> PGT
        DB --> GGT
    end

    CLIENTS --> API --> MAPPERS --> SERVICES --> REPOSITORY --> DB
    Client ~~~ Swagger
    GC ~~~ PC
    GM ~~~ PM
    GS ~~~ PS
    GR ~~~ PR
```

### :ledger: Architectural Decisions:

| ADR # | Title                                                                      | Decision                                                                                                                  | Reasoning                                                                                                                                      |
|-------|----------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------|
| 0001  | Use layered architecture with domain isolation                             | Using DTOss to isolate domain from API                                                                                    | Prevent exposure of domain and sensitive information through public APIs                                                                       |
| 0002  | Strict persistence and domain layer isolation not enforced                 | Spring Data JPA repositories used to perform persistence operations                                                       | To prevent overengineering with multiple DTO mappings and XML defined persistence contexts, domain layer has to retain persistence annotations |   
| 0003  | Use MapStruct for mapping between domain objects and data transfer objects | Use MapStruct for mapping between domain objects and data transfer objects                                                | Mapstruct provides robust/performant annotations based API for mapping domain to DTOs and vice versa                                           | 
| 0004  | Love/Unlove operations implementation                                      | Loving and unloving games are operations of '/api/players/ resource                                                       | Loving/unloving games are performed by a player, therefore defined as an action upon player resource                                           |
| 0005  | Game Player 'many-to-many' mapping                                         | Players and their loves for the game mapped directly with a mapping table. Unidirectional mapping from Player entity used | To retain the simplicity of the implementation, ownership of the game is not considered.                                                       |
| 0006  | Love/Unlove endpoints implementation                                       | Love/Unlove endpoints are implemented as partial updates with 'PATCH' method                                              | Loving and unloving does not update the properties of the player domain, but modifies an associated collection                                 |

## :nut_and_bolt: Technology Stack

- Java 21
- Spring Boot 3.5.7
- Spring Data JPA
- MapStruct
- H2 Database (local-file)
- Swagger UI
- Lombok
- JUnit 5
- Mockito

## :floppy_disk: Database Schema

```mermaid
erDiagram
    GL_GAMES ||--o{ GL_GAME_GENRES: has
    GL_GAMES ||--o{ GL_PLAYER_LOVED_GAMES: "loved by"
    GL_PLAYERS ||--o{ GL_PLAYER_LOVED_GAMES: loves

    GL_GAMES {
        UUID id PK "Primary Key"
        varchar(255) title UK "Unique - Game Title"
        varchar(1000) description "Game Description"
        enum status "ACTIVE, INACTIVE, SUSPENDED"
    }

    GL_GAME_GENRES {
        UUID game_id FK "Foreign Key to GL_GAMES"
        enum genre "ACTION, ADVENTURE, RPG, SHOOTER, etc."
    }

    GL_PLAYERS {
        UUID id PK "Primary Key"
        varchar(64) username UK "Unique - Player Username"
        varchar(255) first_name "Player First Name"
        varchar(255) last_name "Player Last Name"
        enum status "ACTIVE, INACTIVE, SUSPENDED"
    }

    GL_PLAYER_LOVED_GAMES {
        UUID player_id FK "Foreign Key to GL_PLAYERS"
        UUID game_id FK "Foreign Key to GL_GAMES"
    }
```

## :link: API Endpoints

> API endpoints are documented using [Swagger UI](http://localhost:8080/swagger-ui.html).

### Player Endpoints

| Method | Endpoint                                |
|--------|-----------------------------------------|
| GET    | /api/players/                           |
| GET    | /api/players/{id}                       |
| POST   | /api/players/                           |
| PUT    | /api/players/{id}                       |
| PATCH  | /api/players/{id}/games/{gameId}/love   |
| PATCH  | /api/players/{id}/games/{gameId}/unlove |
| PATCH  | /api/players/{id}/status                |
| DELETE | /api/players/{id}                       |

### Game Endpoints

| Method | Endpoint                 |
|--------|--------------------------|
| GET    | /api/games/              |
| GET    | /api/games/popular       |
| GET    | /api/games/stats/popular |
| GET    | /api/games/{id}          |
| POST   | /api/games/              |
| PUT    | /api/games/{id}          |
| PATCH  | /api/games/{id}/status   |
| DELETE | /api/games/{id}          |
| GET    | /api/games/genres        |

## :rocket: Getting Started

### :arrow_forward: Prerequisites

- Java 21
- Maven 3.8.5
- Git 2.36.2
- IDE of your choice (e.g. IntelliJ IDEA, Eclipse, etc.)

### :keyboard: Running the application (Console)

1. Clone the repository
2. Run `mvn spring-boot:run -Dspring-boot.run.profiles=dev`
3. Open [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) to view the API documentation
4. Explore the API endpoints

> :warning: If maven compiler errors occur due to compiler version mimatch, try following:
>
> Run `$ JAVA_HOME=<your_java_21_home> mvn spring-boot:run -Dspring-boot.run.profiles=dev`.

### :desktop_computer: Running the application (IDE)

1. Clone the repository
2. Import the project into your IDE
4. Run `GameLoveApiApplication`
   ![Project Startup](docs/project-start.png)
5. Open [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) to view the API documentation
6. Explore the API endpoints

### :bomb: Rebuilding the database

In case you want to rebuild the database, follow the steps below:

1. Stop the application if it's running
2. Backup database files

```bash
cp -r ./data ./data-backup-$(date +%Y%m%d%H%M%S)
```

3. Delete the existing database files

```bash
rm -rf ./data/*
```

5. Run the application
6. Go to [H2 console](http://localhost:8080/h2-console/login.jsp) and check data exists

![H2 Data](docs/h2-data.png)

## :test_tube: Testing

> :white_check_mark: Tested on `Fedora 42`, `Temurin Java 21.0.8` and `Maven 3.9.9`

### :arrow_forward: Running tests

```bash
mvn clean test jacoco:report -Dspring.profiles.active=test
```

### :arrow_forward: Viewing test coverage report

- Open `target/site/jacoco/index.html` in your browser.

![Test Coverage](docs/test-coverage.png)

## :card_index_dividers: Project Structure

### :file_folder: High-level Project Overview

```
/ (repo root)
├─ mvnw                 # Maven wrapper script
├─ mvnw.cmd             # Maven wrapper for Windows
├─ pom.xml              # Maven project definition and dependencies
├─ README.md            # Project overview and instructions
├─ data/                # Local H2 database files used for development
│  └─ gameondb.mv.db
├─ data-backup/         # Backups of the H2 DB
│  ├─ gameondb.mv.db
│  └─ gameondb.trace.db
├─ docs/                # Project documentation and images
│  ├─ h2-data.png
│  └─ test-coverage.png
├─ src/                 # Source code and tests
│  ├─ main/             # Application source and resources
│  │  ├─ java/          # Java source (package root: com.gamelove.api)
│  │  └─ resources/     # Configs, SQL scripts, static assets
│  └─ test/             # Tests and test utilities
├─ target/              # Build outputs (generated, not committed)
└─ .gitignore (if present)
```

### :open_file_folder: API Implementation - src/main/java/com/gamelove/api/

```
src/main/java/com/gamelove/api/
├─ GameLoveApiApplication.java
├─ ConditionalDataLoader.java
├─ controller/
│  ├─ GameController.java
│  └─ PlayerController.java
├─ service/
│  ├─ GameService.java
│  ├─ PlayerService.java
│  └─ impl/
│     ├─ GameServiceImpl.java
│     └─ PlayerServiceImpl.java
├─ repository/
│  ├─ GameRepository.java
│  └─ PlayerRepository.java
├─ mapper/
│  ├─ GameMapper.java
│  └─ PlayerMapper.java
├─ model/
│  ├─ Game.java
│  ├─ Player.java
│  ├─ Genre.java
│  └─ Status.java
├─ dto/
│  ├─ CreateGameRequest.java
│  ├─ UpdateGameRequest.java
│  ├─ GameResponse.java
│  ├─ GameStats.java
│  ├─ GameStatsProjection.java
│  ├─ CreatePlayerRequest.java
│  ├─ UpdatePlayerRequest.java
│  ├─ PlayerResponse.java
│  └─ PlayerLovedGamesResponse.java
└─ exception/
   ├─ GameLoveException.java
   ├─ ResourceNotFoundException.java
   ├─ ResourceAlreadyExistsException.java
   ├─ InvalidOperationException.java
   └─ GlobalExceptionHandler.java
```



