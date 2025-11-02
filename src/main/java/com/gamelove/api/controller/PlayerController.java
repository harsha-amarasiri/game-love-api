package com.gamelove.api.controller;

import com.gamelove.api.dto.CreatePlayerRequest;
import com.gamelove.api.dto.PlayerResponse;
import com.gamelove.api.dto.UpdatePlayerRequest;
import com.gamelove.api.mapper.PlayerMapper;
import com.gamelove.api.model.Player;
import com.gamelove.api.model.Status;
import com.gamelove.api.service.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/players/")
public class PlayerController {

    private final PlayerService playerService;
    private final PlayerMapper playerMapper;

    @Autowired
    public PlayerController(PlayerService playerService, PlayerMapper playerMapper) {
        this.playerService = playerService;
        this.playerMapper = playerMapper;
    }

    // get all players
    @Operation(summary = "Get all players")
    @GetMapping
    public ResponseEntity<List<?>> getAllPlayers(@RequestParam(name = "lovedGames", required = false, defaultValue = "false") Boolean lovedGames) {

        if (lovedGames) {
            List<Player> players = this.playerService.fetchAllPlayersWithLovedGames();
            var response = this.playerMapper.toPlayerWithLovedGamesResponseList(players);
            return ResponseEntity.ok(response);
        }

        List<Player> players = this.playerService.fetchAllPlayers();
        List<PlayerResponse> playersResponse = this.playerMapper.toPlayerResponseList(players);

        return ResponseEntity.ok(playersResponse);
    }

//    @Operation("Get players with their loved games")


    // get player by id
    @Operation(summary = "Get player by id")
    @GetMapping("/{id}")
    public ResponseEntity<PlayerResponse> getPlayerById(@PathVariable UUID id) {

        Player player = this.playerService.getPlayerById(id);
        PlayerResponse playerResponse = this.playerMapper.toPlayerResponse(player);

        return ResponseEntity.ok(playerResponse);
    }

    // create player
    @PostMapping
    public ResponseEntity<PlayerResponse> createPlayer(@Valid @RequestBody CreatePlayerRequest request) {
        var player = this.playerMapper.toPlayer(request);

        var createdPlayer = this.playerService.createPlayer(player);
        var playerResponse = this.playerMapper.toPlayerResponse(createdPlayer);

        // create resource path
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdPlayer.getId()).toUri();

        return ResponseEntity.created(location).body(playerResponse);
    }

    // update player
    @PutMapping("/{id}")
    public ResponseEntity<PlayerResponse> updatePlayer(@PathVariable UUID id, @Valid @RequestBody UpdatePlayerRequest request) {
        var player = this.playerMapper.toPlayer(request);
        var updatedPlayer = this.playerService.updatePlayer(id, player);

        var playerResponse = this.playerMapper.toPlayerResponse(updatedPlayer);

        return ResponseEntity.ok(playerResponse);
    }

    // love game
    @PatchMapping("/{id}/games/{gameId}/love")
    public ResponseEntity<PlayerResponse> loveGame(@PathVariable UUID id, @PathVariable UUID gameId) {
        var player = this.playerService.loveGame(id, gameId);

        var playerResponse = this.playerMapper.toPlayerResponse(player);
        return ResponseEntity.ok(playerResponse);
    }

    // update player status
    @PatchMapping("/{id}/status")
    public ResponseEntity<PlayerResponse> updatePlayerStatus(@PathVariable UUID id, @RequestParam(name = "value") Status status) {
        var player = this.playerService.updatePlayerStatus(id, status);

        var playerResponse = this.playerMapper.toPlayerResponse(player);
        return ResponseEntity.ok(playerResponse);
    }

    // delete player
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable UUID id) {
        this.playerService.deletePlayer(id);
        return ResponseEntity.noContent().build();
    }
}
