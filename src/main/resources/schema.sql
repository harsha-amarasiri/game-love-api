CREATE TABLE IF NOT EXISTS GL_GAMES
(
    ID          UUID                                     not null primary key,
    DESCRIPTION CHARACTER VARYING(1000)                  not null,
    STATUS      ENUM ('ACTIVE', 'INACTIVE', 'SUSPENDED') not null,
    TITLE       CHARACTER VARYING(255)                   not null
        constraint UK_GAME__TITLE unique
);



CREATE TABLE IF NOT EXISTS GL_GAME_GENRES
(
    GAME_ID UUID                                                                                                                                                                                                                                                       not null,
    GENRE   ENUM ('ACTION', 'MUSIC', 'OTHER', 'PLATFORMER', 'PUZZLE', 'RACING', 'ROGUELIKE', 'RPG', 'SANDBOX', 'SHOOTER', 'SIMULATION', 'ADVENTURE', 'SPORTS', 'STRATEGY', 'SURVIVAL', 'TRIVIA', 'BATTLE_ROYALE', 'FIGHTING', 'FPS', 'HORROR', 'INDIE', 'MMO', 'MOBA') not null,
    constraint FK_GAME__GAME_GENRES
        foreign key (GAME_ID) references GL_GAMES
);


CREATE TABLE IF NOT EXISTS GL_PLAYERS
(
    ID         UUID                                     not null
        primary key,
    FIRST_NAME CHARACTER VARYING(255)                   not null,
    LAST_NAME  CHARACTER VARYING(255)                   not null,
    STATUS     ENUM ('ACTIVE', 'INACTIVE', 'SUSPENDED') not null,
    USERNAME   CHARACTER VARYING(64)                    not null
        constraint UK_PLAYER__USERNAME
            unique
);

CREATE TABLE IF NOT EXISTS GL_PLAYER_LOVED_GAMES
(
    PLAYER_ID UUID not null,
    GAME_ID   UUID not null,
    constraint FK_GAME__PLAYER_LOVED_GAMES foreign key (GAME_ID) references GL_GAMES,
    constraint FK_PLAYER__PLAYER_LOVED_GAMES foreign key (PLAYER_ID) references GL_PLAYERS
);




