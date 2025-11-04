package com.gamelove.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamelove.api.dto.CreateGameRequest;
import com.gamelove.api.dto.CreatePlayerRequest;
import com.gamelove.api.dto.UpdatePlayerRequest;
import com.gamelove.api.model.Game;
import com.gamelove.api.model.Genre;
import com.gamelove.api.model.Player;
import com.gamelove.api.model.Status;
import com.gamelove.api.repository.GameRepository;
import com.gamelove.api.repository.PlayerRepository;
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
@DisplayName("PlayerController Integration Tests")
class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private GameRepository gameRepository;

    private Player testPlayer;
    private Game testGame;

    @BeforeEach
    void setUp() {
        testPlayer = new Player();
        testPlayer.setUsername("testPlayer");
        testPlayer.setFirstName("Test");
        testPlayer.setLastName("Player");
        testPlayer.setStatus(Status.ACTIVE);

        testGame = new Game();
        testGame.setTitle("Test Game");
        testGame.setDescription("Test Description");
        testGame.setGenres(List.of(Genre.ACTION));
        testGame.setStatus(Status.ACTIVE);
    }

    @AfterEach
    void tearDown() {
        playerRepository.deleteAll();
        gameRepository.deleteAll();
    }

    // ==================== GET /api/players/ ====================

    @Test
    @DisplayName("GET /api/players/ - Should return all players")
    void getAllPlayers_ShouldReturnAllPlayers() throws Exception {
        // Arrange
        CreatePlayerRequest request1 = CreatePlayerRequest.builder()
                .username("player1")
                .firstName("First")
                .lastName("Player")
                .build();

        CreatePlayerRequest request2 = CreatePlayerRequest.builder()
                .username("player2")
                .firstName("Second")
                .lastName("Player")
                .build();

        mockMvc.perform(post("/api/players/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request1)));

        mockMvc.perform(post("/api/players/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request2)));

        // Act & Assert
        mockMvc.perform(get("/api/players/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].username").exists())
                .andExpect(jsonPath("$[1].username").exists());
    }

    @Test
    @DisplayName("GET /api/players/ - Should return empty array when no players exist")
    void getAllPlayers_WhenNoPlayersExist_ShouldReturnEmptyArray() throws Exception {
        // Arrange - no setup needed

        // Act & Assert
        mockMvc.perform(get("/api/players/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @DisplayName("GET /api/players/?lovedGames=true - Should return players with loved games")
    void getAllPlayers_WithLovedGamesParam_ShouldReturnPlayersWithLovedGames() throws Exception {
        // Arrange
        CreatePlayerRequest playerRequest = CreatePlayerRequest.builder()
                .username(testPlayer.getUsername())
                .firstName(testPlayer.getFirstName())
                .lastName(testPlayer.getLastName())
                .build();

        String playerResponse = mockMvc.perform(post("/api/players/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(playerRequest)))
                .andReturn().getResponse().getContentAsString();

        UUID playerId = objectMapper.readValue(playerResponse, Player.class).getId();

        CreateGameRequest gameRequest = CreateGameRequest.builder()
                .title(testGame.getTitle())
                .description(testGame.getDescription())
                .genres(testGame.getGenres())
                .build();

        String gameResponse = mockMvc.perform(post("/api/games/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(gameRequest)))
                .andReturn().getResponse().getContentAsString();

        UUID gameId = objectMapper.readValue(gameResponse, Game.class).getId();

        // Player loves the game
        mockMvc.perform(patch("/api/players/{id}/games/{gameId}/love", playerId, gameId));

        // Act & Assert
        mockMvc.perform(get("/api/players/")
                        .param("lovedGames", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].lovedGames").isArray())
                .andExpect(jsonPath("$[0].lovedGames", hasSize(1)));
    }

    // ==================== GET /api/players/{id} ====================

    @Test
    @DisplayName("GET /api/players/{id} - Should return player when found")
    void getPlayerById_WhenPlayerExists_ShouldReturnPlayer() throws Exception {
        // Arrange
        CreatePlayerRequest request = CreatePlayerRequest.builder()
                .username(testPlayer.getUsername())
                .firstName(testPlayer.getFirstName())
                .lastName(testPlayer.getLastName())
                .build();

        String response = mockMvc.perform(post("/api/players/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn().getResponse().getContentAsString();

        UUID playerId = objectMapper.readValue(response, Player.class).getId();

        // Act & Assert
        mockMvc.perform(get("/api/players/{id}", playerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(playerId.toString()))
                .andExpect(jsonPath("$.username").value(testPlayer.getUsername()))
                .andExpect(jsonPath("$.firstName").value(testPlayer.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(testPlayer.getLastName()))
                .andExpect(jsonPath("$.status").value(Status.ACTIVE.name()));
    }

    @Test
    @DisplayName("GET /api/players/{id} - Should return 404 when player not found")
    void getPlayerById_WhenPlayerNotFound_ShouldReturnNotFound() throws Exception {
        // Arrange
        UUID nonExistentId = UUID.randomUUID();

        // Act & Assert
        mockMvc.perform(get("/api/players/{id}", nonExistentId))
                .andExpect(status().isNotFound());
    }

    // ==================== POST /api/players/ ====================

    @Test
    @DisplayName("POST /api/players/ - Should create player with valid request")
    void createPlayer_WithValidRequest_ShouldReturnCreatedPlayer() throws Exception {
        // Arrange
        CreatePlayerRequest request = CreatePlayerRequest.builder()
                .username(testPlayer.getUsername())
                .firstName(testPlayer.getFirstName())
                .lastName(testPlayer.getLastName())
                .build();

        // Act & Assert
        mockMvc.perform(post("/api/players/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.username").value(testPlayer.getUsername()))
                .andExpect(jsonPath("$.firstName").value(testPlayer.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(testPlayer.getLastName()))
                .andExpect(jsonPath("$.status").value(Status.ACTIVE.name()));
    }

    @Test
    @DisplayName("POST /api/players/ - Should return 409 with duplicate username")
    void createPlayer_WithDuplicateUsername_ShouldReturnConflict() throws Exception {
        // Arrange
        CreatePlayerRequest request = CreatePlayerRequest.builder()
                .username(testPlayer.getUsername())
                .firstName(testPlayer.getFirstName())
                .lastName(testPlayer.getLastName())
                .build();

        mockMvc.perform(post("/api/players/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        // Act & Assert
        mockMvc.perform(post("/api/players/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("POST /api/players/ - Should return 400 with invalid request")
    void createPlayer_WithInvalidRequest_ShouldReturnBadRequest() throws Exception {
        // Arrange
        CreatePlayerRequest invalidRequest = CreatePlayerRequest.builder().build();

        // Act & Assert
        mockMvc.perform(post("/api/players/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    // ==================== PUT /api/players/{id} ====================

    @Test
    @DisplayName("PUT /api/players/{id} - Should update player with valid request")
    void updatePlayerDetails_WithValidRequest_ShouldReturnUpdatedPlayer() throws Exception {
        // Arrange
        CreatePlayerRequest createRequest = CreatePlayerRequest.builder()
                .username(testPlayer.getUsername())
                .firstName(testPlayer.getFirstName())
                .lastName(testPlayer.getLastName())
                .build();

        String response = mockMvc.perform(post("/api/players/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andReturn().getResponse().getContentAsString();

        UUID playerId = objectMapper.readValue(response, Player.class).getId();

        UpdatePlayerRequest updateRequest = UpdatePlayerRequest.builder()
                .username(testPlayer.getUsername())
                .firstName("Updated")
                .lastName("Player")
                .build();

        // Act & Assert
        mockMvc.perform(put("/api/players/{id}", playerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(playerId.toString()))
                .andExpect(jsonPath("$.username").value(testPlayer.getUsername()))
                .andExpect(jsonPath("$.firstName").value("Updated"))
                .andExpect(jsonPath("$.lastName").value("Player"));
    }

    @Test
    @DisplayName("PUT /api/players/{id} - Should return 400 when attempting username change")
    void updatePlayer_WithUsernameChange_ShouldReturnBadRequest() throws Exception {
        // Arrange
        CreatePlayerRequest createRequest = CreatePlayerRequest.builder()
                .username(testPlayer.getUsername())
                .firstName(testPlayer.getFirstName())
                .lastName(testPlayer.getLastName())
                .build();

        String response = mockMvc.perform(post("/api/players/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andReturn().getResponse().getContentAsString();

        UUID playerId = objectMapper.readValue(response, Player.class).getId();

        UpdatePlayerRequest updateRequest = UpdatePlayerRequest.builder()
                .username("newUsername")
                .firstName("Updated")
                .lastName("Player")
                .build();

        // Act & Assert
        mockMvc.perform(put("/api/players/{id}", playerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT /api/players/{id} - Should return 400 when updating suspended player")
    void updatePlayer_WhenPlayerSuspended_ShouldReturnBadRequest() throws Exception {
        // Arrange
        CreatePlayerRequest createRequest = CreatePlayerRequest.builder()
                .username(testPlayer.getUsername())
                .firstName(testPlayer.getFirstName())
                .lastName(testPlayer.getLastName())
                .build();

        String response = mockMvc.perform(post("/api/players/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andReturn().getResponse().getContentAsString();

        UUID playerId = objectMapper.readValue(response, Player.class).getId();

        // Suspend the player
        mockMvc.perform(patch("/api/players/{id}/status", playerId)
                .param("value", Status.SUSPENDED.toString()));

        UpdatePlayerRequest updateRequest = UpdatePlayerRequest.builder()
                .username(testPlayer.getUsername())
                .firstName("Updated")
                .lastName("Player")
                .build();

        // Act & Assert
        mockMvc.perform(put("/api/players/{id}", playerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT /api/players/{id} - Should return 404 when player not found")
    void updatePlayer_WhenPlayerNotFound_ShouldReturnNotFound() throws Exception {
        // Arrange
        UUID nonExistentId = UUID.randomUUID();

        UpdatePlayerRequest updateRequest = UpdatePlayerRequest.builder()
                .username("username")
                .firstName("First")
                .lastName("Last")
                .build();

        // Act & Assert
        mockMvc.perform(put("/api/players/{id}", nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT /api/players/{id} - Should return 400 with invalid request")
    void updatePlayer_WithInvalidRequest_ShouldReturnBadRequest() throws Exception {
        // Arrange
        CreatePlayerRequest createRequest = CreatePlayerRequest.builder()
                .username(testPlayer.getUsername())
                .firstName(testPlayer.getFirstName())
                .lastName(testPlayer.getLastName())
                .build();

        String response = mockMvc.perform(post("/api/players/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andReturn().getResponse().getContentAsString();

        UUID playerId = objectMapper.readValue(response, Player.class).getId();

        UpdatePlayerRequest invalidRequest = UpdatePlayerRequest.builder().build();

        // Act & Assert
        mockMvc.perform(put("/api/players/{id}", playerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    // ==================== PATCH /api/players/{id}/games/{gameId}/love ====================

    @Test
    @DisplayName("PATCH /api/players/{id}/games/{gameId}/love - Should love a game")
    void loveGame_WithValidRequest_ShouldReturnPlayerWithLovedGame() throws Exception {
        // Arrange
        CreatePlayerRequest playerRequest = CreatePlayerRequest.builder()
                .username(testPlayer.getUsername())
                .firstName(testPlayer.getFirstName())
                .lastName(testPlayer.getLastName())
                .build();

        String playerResponse = mockMvc.perform(post("/api/players/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(playerRequest)))
                .andReturn().getResponse().getContentAsString();

        UUID playerId = objectMapper.readValue(playerResponse, Player.class).getId();

        CreateGameRequest gameRequest = CreateGameRequest.builder()
                .title(testGame.getTitle())
                .description(testGame.getDescription())
                .genres(testGame.getGenres())
                .build();

        String gameResponse = mockMvc.perform(post("/api/games/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(gameRequest)))
                .andReturn().getResponse().getContentAsString();

        UUID gameId = objectMapper.readValue(gameResponse, Game.class).getId();

        // Act & Assert
        mockMvc.perform(patch("/api/players/{id}/games/{gameId}/love", playerId, gameId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lovedGames").isArray())
                .andExpect(jsonPath("$.lovedGames", hasSize(1)))
                .andExpect(jsonPath("$.lovedGames[0].id").value(gameId.toString()));
    }

    @Test
    @DisplayName("PATCH /api/players/{id}/games/{gameId}/love - Should return 404 when player not found")
    void loveGame_WhenPlayerNotFound_ShouldReturnNotFound() throws Exception {
        // Arrange
        UUID nonExistentPlayerId = UUID.randomUUID();
        UUID gameId = UUID.randomUUID();

        // Act & Assert
        mockMvc.perform(patch("/api/players/{id}/games/{gameId}/love", nonExistentPlayerId, gameId))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PATCH /api/players/{id}/games/{gameId}/love - Should return 404 when game not found")
    void loveGame_WhenGameNotFound_ShouldReturnNotFound() throws Exception {
        // Arrange
        CreatePlayerRequest playerRequest = CreatePlayerRequest.builder()
                .username(testPlayer.getUsername())
                .firstName(testPlayer.getFirstName())
                .lastName(testPlayer.getLastName())
                .build();

        String response = mockMvc.perform(post("/api/players/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(playerRequest)))
                .andReturn().getResponse().getContentAsString();

        UUID playerId = objectMapper.readValue(response, Player.class).getId();
        UUID nonExistentGameId = UUID.randomUUID();

        // Act & Assert
        mockMvc.perform(patch("/api/players/{id}/games/{gameId}/love", playerId, nonExistentGameId))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PATCH /api/players/{id}/games/{gameId}/love - Should be idempotent when game already loved")
    void loveGame_WhenGameAlreadyLoved_ShouldBeIdempotent() throws Exception {
        // Arrange
        CreatePlayerRequest playerRequest = CreatePlayerRequest.builder()
                .username(testPlayer.getUsername())
                .firstName(testPlayer.getFirstName())
                .lastName(testPlayer.getLastName())
                .build();

        String playerResponse = mockMvc.perform(post("/api/players/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(playerRequest)))
                .andReturn().getResponse().getContentAsString();

        UUID playerId = objectMapper.readValue(playerResponse, Player.class).getId();

        CreateGameRequest gameRequest = CreateGameRequest.builder()
                .title(testGame.getTitle())
                .description(testGame.getDescription())
                .genres(testGame.getGenres())
                .build();

        String gameResponse = mockMvc.perform(post("/api/games/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(gameRequest)))
                .andReturn().getResponse().getContentAsString();

        UUID gameId = objectMapper.readValue(gameResponse, Game.class).getId();

        // Love the game first time
        mockMvc.perform(patch("/api/players/{id}/games/{gameId}/love", playerId, gameId))
                .andExpect(status().isOk());

        // Act & Assert - Love the game again (idempotent)
        mockMvc.perform(patch("/api/players/{id}/games/{gameId}/love", playerId, gameId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lovedGames", hasSize(1)));
    }

    // ==================== PATCH /api/players/{id}/games/{gameId}/unlove ====================

    @Test
    @DisplayName("PATCH /api/players/{id}/games/{gameId}/unlove - Should unlove a game")
    void unloveGame_WithValidRequest_ShouldReturnPlayerWithoutGame() throws Exception {
        // Arrange
        CreatePlayerRequest playerRequest = CreatePlayerRequest.builder()
                .username(testPlayer.getUsername())
                .firstName(testPlayer.getFirstName())
                .lastName(testPlayer.getLastName())
                .build();

        String playerResponse = mockMvc.perform(post("/api/players/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(playerRequest)))
                .andReturn().getResponse().getContentAsString();

        UUID playerId = objectMapper.readValue(playerResponse, Player.class).getId();

        CreateGameRequest gameRequest = CreateGameRequest.builder()
                .title(testGame.getTitle())
                .description(testGame.getDescription())
                .genres(testGame.getGenres())
                .build();

        String gameResponse = mockMvc.perform(post("/api/games/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(gameRequest)))
                .andReturn().getResponse().getContentAsString();

        UUID gameId = objectMapper.readValue(gameResponse, Game.class).getId();

        // Love the game first
        mockMvc.perform(patch("/api/players/{id}/games/{gameId}/love", playerId, gameId));

        // Act & Assert
        mockMvc.perform(patch("/api/players/{id}/games/{gameId}/unlove", playerId, gameId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lovedGames").isArray())
                .andExpect(jsonPath("$.lovedGames", hasSize(0)));
    }

    @Test
    @DisplayName("PATCH /api/players/{id}/games/{gameId}/unlove - Should return 404 when player not found")
    void unloveGame_WhenPlayerNotFound_ShouldReturnNotFound() throws Exception {
        // Arrange
        UUID nonExistentPlayerId = UUID.randomUUID();
        UUID gameId = UUID.randomUUID();

        // Act & Assert
        mockMvc.perform(patch("/api/players/{id}/games/{gameId}/unlove", nonExistentPlayerId, gameId))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PATCH /api/players/{id}/games/{gameId}/unlove - Should return 404 when game not found")
    void unloveGame_WhenGameNotFound_ShouldReturnNotFound() throws Exception {
        // Arrange
        CreatePlayerRequest playerRequest = CreatePlayerRequest.builder()
                .username(testPlayer.getUsername())
                .firstName(testPlayer.getFirstName())
                .lastName(testPlayer.getLastName())
                .build();

        String response = mockMvc.perform(post("/api/players/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(playerRequest)))
                .andReturn().getResponse().getContentAsString();

        UUID playerId = objectMapper.readValue(response, Player.class).getId();
        UUID nonExistentGameId = UUID.randomUUID();

        // Act & Assert
        mockMvc.perform(patch("/api/players/{id}/games/{gameId}/unlove", playerId, nonExistentGameId))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PATCH /api/players/{id}/games/{gameId}/unlove - Should return 404 when game not loved")
    void unloveGame_WhenGameNotLoved_ShouldReturnNotFound() throws Exception {
        // Arrange
        CreatePlayerRequest playerRequest = CreatePlayerRequest.builder()
                .username(testPlayer.getUsername())
                .firstName(testPlayer.getFirstName())
                .lastName(testPlayer.getLastName())
                .build();

        String playerResponse = mockMvc.perform(post("/api/players/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(playerRequest)))
                .andReturn().getResponse().getContentAsString();

        UUID playerId = objectMapper.readValue(playerResponse, Player.class).getId();

        CreateGameRequest gameRequest = CreateGameRequest.builder()
                .title(testGame.getTitle())
                .description(testGame.getDescription())
                .genres(testGame.getGenres())
                .build();

        String gameResponse = mockMvc.perform(post("/api/games/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(gameRequest)))
                .andReturn().getResponse().getContentAsString();

        UUID gameId = objectMapper.readValue(gameResponse, Game.class).getId();

        // Act & Assert - Try to unlove without loving first
        mockMvc.perform(patch("/api/players/{id}/games/{gameId}/unlove", playerId, gameId))
                .andExpect(status().isNotFound());
    }

    // ==================== PATCH /api/players/{id}/status ====================

    @Test
    @DisplayName("PATCH /api/players/{id}/status - Should update player status")
    void updatePlayerStatus_WithValidRequest_ShouldReturnUpdatedPlayer() throws Exception {
        // Arrange
        CreatePlayerRequest request = CreatePlayerRequest.builder()
                .username(testPlayer.getUsername())
                .firstName(testPlayer.getFirstName())
                .lastName(testPlayer.getLastName())
                .build();

        String response = mockMvc.perform(post("/api/players/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn().getResponse().getContentAsString();

        UUID playerId = objectMapper.readValue(response, Player.class).getId();

        // Act & Assert
        mockMvc.perform(patch("/api/players/{id}/status", playerId)
                        .param("value", Status.INACTIVE.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(Status.INACTIVE.toString()))
                .andExpect(jsonPath("$.id").value(playerId.toString()))
                .andExpect(jsonPath("$.username").value(testPlayer.getUsername()));
    }

    @Test
    @DisplayName("PATCH /api/players/{id}/status - Should return 404 when player not found")
    void updatePlayerStatus_WhenPlayerNotFound_ShouldReturnNotFound() throws Exception {
        // Arrange
        UUID nonExistentId = UUID.randomUUID();

        // Act & Assert
        mockMvc.perform(patch("/api/players/{id}/status", nonExistentId)
                        .param("value", Status.INACTIVE.toString()))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PATCH /api/players/{id}/status - Should update to SUSPENDED status")
    void updatePlayerStatus_ToSuspended_ShouldReturnUpdatedPlayer() throws Exception {
        // Arrange
        CreatePlayerRequest request = CreatePlayerRequest.builder()
                .username(testPlayer.getUsername())
                .firstName(testPlayer.getFirstName())
                .lastName(testPlayer.getLastName())
                .build();

        String response = mockMvc.perform(post("/api/players/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn().getResponse().getContentAsString();

        UUID playerId = objectMapper.readValue(response, Player.class).getId();

        // Act & Assert
        mockMvc.perform(patch("/api/players/{id}/status", playerId)
                        .param("value", Status.SUSPENDED.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(Status.SUSPENDED.name()));
    }

    // ==================== DELETE /api/players/{id} ====================

    @Test
    @DisplayName("DELETE /api/players/{id} - Should delete player with valid ID")
    void deletePlayer_WithValidId_ShouldReturnNoContent() throws Exception {
        // Arrange
        CreatePlayerRequest request = CreatePlayerRequest.builder()
                .username(testPlayer.getUsername())
                .firstName(testPlayer.getFirstName())
                .lastName(testPlayer.getLastName())
                .build();

        String response = mockMvc.perform(post("/api/players/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn().getResponse().getContentAsString();

        UUID playerId = objectMapper.readValue(response, Player.class).getId();

        // Act
        mockMvc.perform(delete("/api/players/{id}", playerId))
                .andExpect(status().isNoContent());

        // Assert - Verify player is deleted
        mockMvc.perform(get("/api/players/{id}", playerId))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /api/players/{id} - Should return 404 when player not found")
    void deletePlayer_WhenPlayerNotFound_ShouldReturnNotFound() throws Exception {
        // Arrange
        UUID nonExistentId = UUID.randomUUID();

        // Act & Assert
        mockMvc.perform(delete("/api/players/{id}", nonExistentId))
                .andExpect(status().isNotFound());
    }
}