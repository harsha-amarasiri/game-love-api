package com.gamelove.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamelove.api.dto.CreatePlayerRequest;
import com.gamelove.api.dto.UpdatePlayerRequest;
import com.gamelove.api.model.Game;
import com.gamelove.api.model.Player;
import com.gamelove.api.model.Status;
import com.gamelove.api.repository.PlayerRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PlayerControllerTest {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PlayerRepository playerRepository;

    private Player testPlayer;

    @BeforeEach
    void setUp() {
        testPlayer = new Player();
        testPlayer.setUsername("testPlayer");
        testPlayer.setFirstName("Test");
        testPlayer.setLastName("Player");
        testPlayer.setStatus(Status.ACTIVE);
    }

    @AfterEach
    void tearDown() {
        // delete all players after test
        playerRepository.deleteAll();
    }

    // test create player
    @Test
    void createPlayer_WithValidRequest_ShouldReturnCreatedPlayer() throws Exception {

        var player = CreatePlayerRequest.builder()
                .username(testPlayer.getUsername())
                .firstName(testPlayer.getFirstName())
                .lastName(testPlayer.getLastName())
                .build();

        // create player
        this.mockMvc.perform(
                        post("/api/players/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(player)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.username").value(testPlayer.getUsername()));
    }

    @Test
    void createPlayer_WithDuplicateUsername_ShouldReturnConflict() throws Exception {
        // create player

        var player = CreatePlayerRequest.builder()
                .username(testPlayer.getUsername())
                .firstName(testPlayer.getFirstName())
                .lastName(testPlayer.getLastName())
                .build();

        this.mockMvc.perform(
                        post("/api/players/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(player)))
                .andExpect(status().isCreated());

        // create player with duplicate username
        this.mockMvc.perform(
                        post("/api/players/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(player))
                )
                .andExpect(status().isConflict());
    }

    // create player with invalid request
    @Test
    void createPlayer_WithInvalidRequest_ShouldReturnBadRequest() throws Exception {
        // create player with invalid request
        this.mockMvc.perform(
                        post("/api/players/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(CreatePlayerRequest.builder().build())))
                .andExpect(status().isBadRequest());
    }

    // update player detals
    @Test
    void updatePlayerDetails_WithValidRequest_ShouldReturnUpdatedPlayer() throws Exception {

        var player = CreatePlayerRequest.builder()
                .username(testPlayer.getUsername())
                .firstName(testPlayer.getFirstName())
                .lastName(testPlayer.getLastName())
                .build();

        // create player
        this.mockMvc.perform(
                        post("/api/players/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(player))
                )
                .andExpect(status().isCreated())
                .andDo(result -> {
                    var playerId = objectMapper.readValue(result.getResponse().getContentAsString(), Player.class).getId();
                    testPlayer.setId(playerId);
                })
        ;

        // update player details
        var updatedPlayer = UpdatePlayerRequest.builder()
                .username(testPlayer.getUsername()) // username not changed
                .firstName("Updated")
                .lastName("Player")
                .build();

        this.mockMvc.perform(
                        put("/api/players/{id}", testPlayer.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updatedPlayer))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testPlayer.getId().toString()))
                // username not changed
                .andExpect(jsonPath("$.username").value(testPlayer.getUsername()))
                // updated details
                .andExpect(jsonPath("$.firstName").value(updatedPlayer.firstName()))
                .andExpect(jsonPath("$.lastName").value(updatedPlayer.lastName()));
    }

    // test update player status
    @Test
    void updatePlayerStatus_WithValidRequest_ShouldReturnUpdatedPlayer() throws Exception {
        // create player
        this.mockMvc.perform(
                        post("/api/players/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(testPlayer))
                )
                .andExpect(status().isCreated())
                .andDo(result -> {
                    var playerId = objectMapper.readValue(result.getResponse().getContentAsString(), Player.class).getId();
                    testPlayer.setId(playerId);
                });

        // update player status
        this.mockMvc.perform(
                        patch("/api/players/{id}/status?value={status}", testPlayer.getId(), Status.INACTIVE.toString())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                // check if status was updated
                .andExpect(jsonPath("$.status").value(Status.INACTIVE.toString()))
                // other details unchanged
                .andExpect(jsonPath("$.id").value(testPlayer.getId().toString()))
                .andExpect(jsonPath("$.username").value(testPlayer.getUsername()))
                .andExpect(jsonPath("$.firstName").value(testPlayer.getFirstName()));
    }

    // test delete player
    @Test
    void deletePlayer_WithValidRequest_ShouldReturnNoContent() throws Exception {
        // create player
        this.mockMvc.perform(
                        post("/api/players/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(testPlayer))
                )
                .andExpect(status().isCreated())
                .andDo(result -> {
                    var playerId = objectMapper.readValue(result.getResponse().getContentAsString(), Player.class).getId();
                    testPlayer.setId(playerId);
                });

        // delete player
        this.mockMvc.perform(delete("/api/players/{id}", testPlayer.getId()))
                .andExpect(status().isNoContent());

        // check if player was deleted
        var deletedPlayer = playerRepository.findById(testPlayer.getId());
        assert(deletedPlayer.isEmpty());

        this.mockMvc.perform(get("/api/players/{id}", testPlayer.getId()))
                .andExpect(status().isNotFound());
    }

}