package com.gamelove.api.controller;

import com.gamelove.api.dto.*;
import com.gamelove.api.mapper.GameMapper;
import com.gamelove.api.model.Genre;
import com.gamelove.api.model.Status;
import com.gamelove.api.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/games/")
@Tag(name = "Games", description = "Games API")
public class GameController {

    private final GameService gameService;
    private final GameMapper gameMapper;

    @Autowired
    public GameController(GameService gameService, GameMapper gameMapper) {
        this.gameService = gameService;
        this.gameMapper = gameMapper;
    }

    // get all games
    @Operation(summary = "Get all games")
    @GetMapping
    public ResponseEntity<List<GameResponse>> getAllGames() {

        // adding trace logging as an example
        log.trace("Processing GET /api/games/ request");

        var games = this.gameService.getAllGames();
        var gamesResponse = this.gameMapper.toGameResponseList(games);

        return ResponseEntity.ok(gamesResponse);
    }

    @Operation(summary = "Get popular games")
    @GetMapping("/popular")
    public ResponseEntity<List<GameResponse>> getMostPopularGames(@RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
        List<GameResponse> games = this.gameService.getMostPopularGames(size);

        return ResponseEntity.ok(games);
    }

    @Operation(summary = "Get stats for popular games")
    @GetMapping("/stats/popular")
    public ResponseEntity<List<GameStats>> getMostPopularGameStats(@RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
        var stats = this.gameService.getMostPopularGameStats(size);

        var statsResponse = this.gameMapper.toStatsResponse(stats);

        return ResponseEntity.ok(statsResponse);
    }


    // get game by id
    @Operation(summary = "Get game by id")
    @GetMapping("/{id}")
    public ResponseEntity<GameResponse> getGameById(@PathVariable UUID id) {

        // adding trace logging as an example
        log.trace("Processing GET /api/games/{} request", id);

        var game = this.gameService.findGameById(id);
        var gameResponse = this.gameMapper.toGameResponse(game);

        return ResponseEntity.ok(gameResponse);
    }

    // create game
    @Operation(summary = "Create a game")
    @PostMapping
    public ResponseEntity<GameResponse> createGame(@Valid @RequestBody CreateGameRequest request) {

        var game = this.gameMapper.toGame(request);
        var createdGame = this.gameService.createGame(game);
        var gameResponse = this.gameMapper.toGameResponse(createdGame);

        // create resource path
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdGame.getId()).toUri();

        return ResponseEntity.created(location).body(gameResponse);
    }


    // update game details
    @Operation(summary = "Update a game")
    @PutMapping("/{id}")
    public ResponseEntity<GameResponse> updateGame(@PathVariable UUID id, @Valid @RequestBody UpdateGameRequest request) {

        var game = this.gameMapper.toGame(request);
        var updatedGame = this.gameService.updateGameDetails(id, game);
        var gameResponse = this.gameMapper.toGameResponse(updatedGame);

        return ResponseEntity.ok(gameResponse);
    }

    // update game status
    @Operation(summary = "Update a game status")
    @PatchMapping("/{id}/status")
    public ResponseEntity<GameResponse> updateGameStatus(@PathVariable UUID id, @RequestParam(name = "value") Status status) {

        var game = this.gameService.updateGameStatus(id, status);

        var gameResponse = this.gameMapper.toGameResponse(game);
        return ResponseEntity.ok(gameResponse);
    }

    // delete game
    @Operation(summary = "Delete a game")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable UUID id) {
        this.gameService.deleteGame(id);

        return ResponseEntity.noContent().build();
    }

    // game genres
    @Operation(summary = "Get all game genres")
    @GetMapping("/genres")
    public ResponseEntity<List<String>> getAllGameGenres() {
        return ResponseEntity.ok(Arrays.stream(Genre.values()).map(Genre::toString).toList());
    }


}
