package com.gamelove.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamelove.api.dto.CreateGameRequest;
import com.gamelove.api.model.Game;
import com.gamelove.api.model.Genre;
import com.gamelove.api.model.Status;
import com.gamelove.api.repository.GameRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GameRepository gameRepository;

    private Game testGame;

    @BeforeEach
    void setUp() {
        testGame = new Game();

        testGame.setTitle("The Witcher 3");
        testGame.setId(UUID.randomUUID());
        testGame.setDescription("The Witcher 3 is a 2021 action-adventure game developed by Bethesda Softworks.");
        testGame.setStatus(Status.ACTIVE);
        testGame.setGenres(List.of(Genre.ACTION, Genre.ADVENTURE, Genre.RPG));

    }

    @AfterEach
    void tearDown() {
        gameRepository.deleteAll();
    }

    @Test
    void createGame_WithValidRequest_ShouldReturnCreated() throws Exception {
        CreateGameRequest request = CreateGameRequest.builder()
                .title(testGame.getTitle())
                .description(testGame.getDescription())
                .genres(testGame.getGenres())
                .build();

        mockMvc.perform(
                        post("/api/games/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value(testGame.getTitle()))
                .andExpect(jsonPath("$.description").value(testGame.getDescription()))
                .andExpect(jsonPath("$.genres").isArray())
                .andExpect(jsonPath("$.genres").isNotEmpty())
                .andExpect(jsonPath("$.genres[0]").value(Genre.ACTION.name()))
                .andExpect(jsonPath("$.genres[1]").value(Genre.ADVENTURE.name()))
                .andExpect(jsonPath("$.genres[2]").value(Genre.RPG.name()))
                .andExpect(jsonPath("$.status").value(Status.ACTIVE.name()));
    }


    @Test
    void createGame_WithInvalidRequest_ShouldReturnBadRequest() throws Exception {
        CreateGameRequest request = CreateGameRequest.builder()
                .title("")
                .description("")
                .genres(List.of())
                .build();

        mockMvc.perform(
                        post("/api/games/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void createGame_WithDuplicateTitle_ShouldReturnConflict() throws Exception {
        CreateGameRequest request = CreateGameRequest.builder()
                .title(testGame.getTitle())
                .description(testGame.getDescription())
                .genres(testGame.getGenres())
                .build();

        this.mockMvc.perform(post("/api/games/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        this.mockMvc.perform(post("/api/games/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());

    }


    // update game details
    @Test
    void updateGameDetails_WithValidRequest_ShouldReturnUpdated() throws Exception {
        // create game
        CreateGameRequest request = CreateGameRequest.builder()
                .title(testGame.getTitle())
                .description(testGame.getDescription())
                .genres(testGame.getGenres())
                .build();

        this.mockMvc.perform(post("/api/games/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(result -> {
                    var uuid = objectMapper.readValue(result.getResponse().getContentAsString(), Game.class).getId();
                    testGame.setId(uuid);
                })
        ;

        // update game details
        var updateRequest = CreateGameRequest.builder()
                .title("The Witcher 3: Wild Hunt")
                .description("The Witcher 3: Wild Hunt is a 2022 action-adventure game developed by Bethesda Softworks.")
                .genres(List.of(Genre.ADVENTURE, Genre.RPG)).build();

        this.mockMvc.perform(put("/api/games/{id}", testGame.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("The Witcher 3: Wild Hunt"))
                .andExpect(jsonPath("$.description").value("The Witcher 3: Wild Hunt is a 2022 action-adventure game developed by Bethesda Softworks."))
                .andExpect(jsonPath("$.genres").isArray())
                .andExpect(jsonPath("$.genres").isNotEmpty())
                .andExpect(jsonPath("$.genres[0]").value(Genre.ADVENTURE.name()))
                .andExpect(jsonPath("$.genres[1]").value(Genre.RPG.name()))
                .andExpect(jsonPath("$.status").value(Status.ACTIVE.name()));
    }


    // update game status
    @Test
    void updateGameStatus_WithValidRequest_ShouldReturnUpdated() throws Exception {
        // create game
        CreateGameRequest request = CreateGameRequest.builder()
                .title(testGame.getTitle())
                .description(testGame.getDescription())
                .genres(testGame.getGenres())
                .build();

        this.mockMvc.perform(post("/api/games/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(result -> {
                    var uuid = objectMapper.readValue(result.getResponse().getContentAsString(), Game.class).getId();
                    testGame.setId(uuid);
                })
        ;

        // update game status
        this.mockMvc.perform(patch("/api/games/{id}/status?value={status}", testGame.getId(), Status.INACTIVE.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Status.INACTIVE)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value(testGame.getTitle()))
                .andExpect(jsonPath("$.description").value(testGame.getDescription()))
                .andExpect(jsonPath("$.genres").isArray())
                .andExpect(jsonPath("$.genres").isNotEmpty())
                .andExpect(jsonPath("$.genres[0]").value(Genre.ACTION.name()))
                .andExpect(jsonPath("$.genres[1]").value(Genre.ADVENTURE.name()))
                .andExpect(jsonPath("$.genres[2]").value(Genre.RPG.name()))
                .andExpect(jsonPath("$.status").value(Status.INACTIVE.name()));
    }


    // delete game
    @Test
    void deleteGame_WithValidId_ShouldReturnNoContent() throws Exception {
        // create game
        CreateGameRequest request = CreateGameRequest.builder()
                .title(testGame.getTitle())
                .description(testGame.getDescription())
                .genres(testGame.getGenres())
                .build();

        this.mockMvc.perform(post("/api/games/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(result -> {
                    var uuid = objectMapper.readValue(result.getResponse().getContentAsString(), Game.class).getId();
                    testGame.setId(uuid);
                })
        ;

        // delete game
        this.mockMvc.perform(delete("/api/games/{id}", testGame.getId()))
                .andExpect(status().isNoContent());
    }

}