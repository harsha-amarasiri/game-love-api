package com.gamelove.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamelove.api.dto.CreateGameRequest;
import com.gamelove.api.model.Game;
import com.gamelove.api.model.Genre;
import com.gamelove.api.model.Status;
import com.gamelove.api.repository.GameRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("GameController Integration Tests")
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

    // ==================== GET /api/games/ ====================

    @Test
    @DisplayName("GET /api/games/ - Should return all games")
    void getAllGames_ShouldReturnAllGames() throws Exception {
        // Arrange
        CreateGameRequest request1 = CreateGameRequest.builder()
                .title("The Witcher 3")
                .description("RPG Game")
                .genres(List.of(Genre.RPG))
                .build();

        CreateGameRequest request2 = CreateGameRequest.builder()
                .title("Call of Duty")
                .description("FPS Game")
                .genres(List.of(Genre.SHOOTER))
                .build();

        mockMvc.perform(post("/api/games/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request1)));

        mockMvc.perform(post("/api/games/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request2)));

        // Act & Assert
        mockMvc.perform(get("/api/games/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title").exists())
                .andExpect(jsonPath("$[1].title").exists());
    }

    @Test
    @DisplayName("GET /api/games/ - Should return empty array when no games exist")
    void getAllGames_WhenNoGamesExist_ShouldReturnEmptyArray() throws Exception {
        // Arrange => no setup needed

        // Act & Assert
        mockMvc.perform(get("/api/games/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    // ==================== GET /api/games/{id} ====================

    @Test
    @DisplayName("GET /api/games/{id} - Should return game when found")
    void getGameById_WhenGameExists_ShouldReturnGame() throws Exception {
        // Arrange
        CreateGameRequest request = CreateGameRequest.builder()
                .title(testGame.getTitle())
                .description(testGame.getDescription())
                .genres(testGame.getGenres())
                .build();

        String response = mockMvc.perform(post("/api/games/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn().getResponse().getContentAsString();

        UUID gameId = objectMapper.readValue(response, Game.class).getId();

        // Act & Assert
        mockMvc.perform(get("/api/games/{id}", gameId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(gameId.toString()))
                .andExpect(jsonPath("$.title").value(testGame.getTitle()))
                .andExpect(jsonPath("$.description").value(testGame.getDescription()))
                .andExpect(jsonPath("$.status").value(Status.ACTIVE.name()));
    }

    @Test
    @DisplayName("GET /api/games/{id} - Should return 404 when game not found")
    void getGameById_WhenGameNotFound_ShouldReturnNotFound() throws Exception {
        // Arrange
        UUID nonExistentId = UUID.randomUUID();

        // Act & Assert
        mockMvc.perform(get("/api/games/{id}", nonExistentId))
                .andExpect(status().isNotFound());
    }

    // ==================== GET /api/games/popular ====================

    @Test
    @DisplayName("GET /api/games/popular - Should return popular games with default size")
    void getMostPopularGames_WithDefaultSize_ShouldReturnGames() throws Exception {
        // Arrange
        CreateGameRequest request = CreateGameRequest.builder()
                .title(testGame.getTitle())
                .description(testGame.getDescription())
                .genres(testGame.getGenres())
                .build();

        mockMvc.perform(post("/api/games/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // Act & Assert
        mockMvc.perform(get("/api/games/popular"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @DisplayName("GET /api/games/popular - Should return popular games with custom size")
    void getMostPopularGames_WithCustomSize_ShouldReturnGames() throws Exception {
        // Arrange - no setup needed

        // Act & Assert
        mockMvc.perform(get("/api/games/popular")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    // ==================== GET /api/games/stats/popular ====================

    @Test
    @DisplayName("GET /api/games/stats/popular - Should return game stats with default size")
    void getMostPopularGameStats_WithDefaultSize_ShouldReturnStats() throws Exception {
        // Arrange - no setup needed

        // Act & Assert
        mockMvc.perform(get("/api/games/stats/popular"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @DisplayName("GET /api/games/stats/popular - Should return game stats with custom size")
    void getMostPopularGameStats_WithCustomSize_ShouldReturnStats() throws Exception {
        // Arrange - no setup needed

        // Act & Assert
        mockMvc.perform(get("/api/games/stats/popular")
                        .param("size", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    // ==================== GET /api/games/genres ====================

    @Test
    @DisplayName("GET /api/games/genres - Should return all game genres")
    void getAllGameGenres_ShouldReturnAllGenres() throws Exception {
        // Arrange - no setup needed

        // Act & Assert
        mockMvc.perform(get("/api/games/genres"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(greaterThan(0))))
                .andExpect(jsonPath("$", hasItem(Genre.ACTION.toString())))
                .andExpect(jsonPath("$", hasItem(Genre.RPG.toString())))
                .andExpect(jsonPath("$", hasItem(Genre.ADVENTURE.toString())));
    }

    // ==================== POST /api/games/ ====================

    @Test
    @DisplayName("POST /api/games/ - Should create game with valid request")
    void createGame_WithValidRequest_ShouldReturnCreated() throws Exception {
        // Arrange
        CreateGameRequest request = CreateGameRequest.builder()
                .title(testGame.getTitle())
                .description(testGame.getDescription())
                .genres(testGame.getGenres())
                .build();

        // Act & Assert
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
    @DisplayName("POST /api/games/ - Should return 400 with invalid request")
    void createGame_WithInvalidRequest_ShouldReturnBadRequest() throws Exception {
        // Arrange
        CreateGameRequest request = CreateGameRequest.builder()
                .title("")
                .description("")
                .genres(List.of())
                .build();

        // Act & Assert
        mockMvc.perform(
                        post("/api/games/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/games/ - Should return 409 with duplicate title")
    void createGame_WithDuplicateTitle_ShouldReturnConflict() throws Exception {
        // Arrange
        CreateGameRequest request = CreateGameRequest.builder()
                .title(testGame.getTitle())
                .description(testGame.getDescription())
                .genres(testGame.getGenres())
                .build();

        mockMvc.perform(post("/api/games/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        // Act & Assert
        mockMvc.perform(post("/api/games/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }

    // ==================== PUT /api/games/{id} ====================

    @Test
    @DisplayName("PUT /api/games/{id} - Should update game with valid request")
    void updateGameDetails_WithValidRequest_ShouldReturnUpdated() throws Exception {
        // Arrange
        CreateGameRequest request = CreateGameRequest.builder()
                .title(testGame.getTitle())
                .description(testGame.getDescription())
                .genres(testGame.getGenres())
                .build();

        String response = mockMvc.perform(post("/api/games/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn().getResponse().getContentAsString();

        UUID gameId = objectMapper.readValue(response, Game.class).getId();

        CreateGameRequest updateRequest = CreateGameRequest.builder()
                .title("The Witcher 3: Wild Hunt")
                .description("The Witcher 3: Wild Hunt is a 2022 action-adventure game developed by Bethesda Softworks.")
                .genres(List.of(Genre.ADVENTURE, Genre.RPG))
                .build();

        // Act & Assert
        mockMvc.perform(put("/api/games/{id}", gameId)
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

    @Test
    @DisplayName("PUT /api/games/{id} - Should return 404 when game not found")
    void updateGameDetails_WhenGameNotFound_ShouldReturnNotFound() throws Exception {
        // Arrange
        UUID nonExistentId = UUID.randomUUID();

        CreateGameRequest updateRequest = CreateGameRequest.builder()
                .title("Updated Title")
                .description("Updated Description")
                .genres(List.of(Genre.RPG))
                .build();

        // Act & Assert
        mockMvc.perform(put("/api/games/{id}", nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT /api/games/{id} - Should return 400 with invalid request")
    void updateGameDetails_WithInvalidRequest_ShouldReturnBadRequest() throws Exception {
        // Arrange
        CreateGameRequest request = CreateGameRequest.builder()
                .title(testGame.getTitle())
                .description(testGame.getDescription())
                .genres(testGame.getGenres())
                .build();

        String response = mockMvc.perform(post("/api/games/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn().getResponse().getContentAsString();

        UUID gameId = objectMapper.readValue(response, Game.class).getId();

        CreateGameRequest invalidRequest = CreateGameRequest.builder()
                .title("")
                .description("")
                .genres(List.of())
                .build();

        // Act & Assert
        mockMvc.perform(put("/api/games/{id}", gameId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    // ==================== PATCH /api/games/{id}/status ====================

    @Test
    @DisplayName("PATCH /api/games/{id}/status - Should update status with valid request")
    void updateGameStatus_WithValidRequest_ShouldReturnUpdated() throws Exception {
        // Arrange
        CreateGameRequest request = CreateGameRequest.builder()
                .title(testGame.getTitle())
                .description(testGame.getDescription())
                .genres(testGame.getGenres())
                .build();

        String response = mockMvc.perform(post("/api/games/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn().getResponse().getContentAsString();

        UUID gameId = objectMapper.readValue(response, Game.class).getId();

        // Act & Assert
        mockMvc.perform(patch("/api/games/{id}/status", gameId)
                        .param("value", Status.INACTIVE.toString())
                        .contentType(MediaType.APPLICATION_JSON))
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

    @Test
    @DisplayName("PATCH /api/games/{id}/status - Should return 404 when game not found")
    void updateGameStatus_WhenGameNotFound_ShouldReturnNotFound() throws Exception {
        // Arrange
        UUID nonExistentId = UUID.randomUUID();

        // Act & Assert
        mockMvc.perform(patch("/api/games/{id}/status", nonExistentId)
                        .param("value", Status.INACTIVE.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PATCH /api/games/{id}/status - Should update to SUSPENDED status")
    void updateGameStatus_ToSuspended_ShouldReturnUpdated() throws Exception {
        // Arrange
        CreateGameRequest request = CreateGameRequest.builder()
                .title(testGame.getTitle())
                .description(testGame.getDescription())
                .genres(testGame.getGenres())
                .build();

        String response = mockMvc.perform(post("/api/games/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn().getResponse().getContentAsString();

        UUID gameId = objectMapper.readValue(response, Game.class).getId();

        // Act & Assert
        mockMvc.perform(patch("/api/games/{id}/status", gameId)
                        .param("value", Status.SUSPENDED.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(Status.SUSPENDED.name()));
    }

    // ==================== DELETE /api/games/{id} ====================

    @Test
    @DisplayName("DELETE /api/games/{id} - Should delete game with valid ID")
    void deleteGame_WithValidId_ShouldReturnNoContent() throws Exception {
        // Arrange
        CreateGameRequest request = CreateGameRequest.builder()
                .title(testGame.getTitle())
                .description(testGame.getDescription())
                .genres(testGame.getGenres())
                .build();

        String response = mockMvc.perform(post("/api/games/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn().getResponse().getContentAsString();

        UUID gameId = objectMapper.readValue(response, Game.class).getId();

        // Act
        mockMvc.perform(delete("/api/games/{id}", gameId))
                .andExpect(status().isNoContent());

        // Assert - Verify game is deleted
        mockMvc.perform(get("/api/games/{id}", gameId))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /api/games/{id} - Should return 404 when game not found")
    void deleteGame_WhenGameNotFound_ShouldReturnNotFound() throws Exception {
        // Arrange
        UUID nonExistentId = UUID.randomUUID();

        // Act & Assert
        mockMvc.perform(delete("/api/games/{id}", nonExistentId))
                .andExpect(status().isNotFound());
    }
}