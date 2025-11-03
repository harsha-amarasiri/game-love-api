package com.gamelove.api.service.impl;

import com.gamelove.api.exception.GameLoveException;
import com.gamelove.api.exception.ResourceAlreadyExistsException;
import com.gamelove.api.exception.ResourceNotFoundException;
import com.gamelove.api.mapper.PlayerMapper;
import com.gamelove.api.model.Player;
import com.gamelove.api.model.Status;
import com.gamelove.api.repository.PlayerRepository;
import com.gamelove.api.service.GameService;
import com.gamelove.api.service.PlayerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional(readOnly = true)
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;
    private final GameService gameService;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository, PlayerMapper playerMapper, GameService gameService) {
        this.playerRepository = playerRepository;
        this.playerMapper = playerMapper;
        this.gameService = gameService;
    }


    @Override
    public List<Player> fetchAllPlayers() {
        log.info("Fetching all players");

        var players = this.playerRepository.findAll();
        log.info("Fetched  all players");

        return players;
    }

    @Override
    public List<Player> fetchAllPlayersWithLovedGames() {
        log.debug("Fetching all players with loved games");

        var players = this.playerRepository.findAllWithLovedGames();

        log.info("Fetched  all players with loved games");
        return players;
    }


    @Override
    public Player getPlayerById(UUID id) {
        log.debug("Fetching player with id: {}", id);

        var player = this.playerRepository.findById(id);

        if (player.isEmpty()) {
            log.warn("Player with id {} was not found", id);
            throw new ResourceNotFoundException("Player with id " + id + " was not found.");
        }

        return player.get();
    }


    @Override
    @Transactional
    public Player createPlayer(Player player) {
        log.debug("Creating player: {}", player);

        // check if player with username exists
        if (playerExistsWithUsername(player.getUsername())) {
            log.warn("Player with username {} already exists when trying to create player", player.getUsername());
            throw new ResourceAlreadyExistsException("Player with username " + player.getUsername() + " already exists.");
        }

        // business rule: set new player status to active
        player.setStatus(Status.ACTIVE);

        var createdPlayer = this.playerRepository.save(player);
        log.info("Player with id {} was created", createdPlayer.getId());

        return createdPlayer;
    }

    @Override
    @Transactional
    public Player updatePlayer(UUID id, Player playerUpdates) {
        var player = this.playerRepository.findById(id);

        // check if player exists
        if (player.isEmpty()) {
            log.warn("Player with id {} was not found when trying to update player details", id);
            throw new ResourceNotFoundException("Player with id " + id + " was not found.");
        }

        var existingPlayer = player.get();

        // check if the plauer is suspended
        if (existingPlayer.getStatus() == Status.SUSPENDED) {
            log.warn("Player with id {} is suspended. Denying update.", id);
            throw new GameLoveException("Updates to player with id " + id + " are not allowed. Player is suspended.");
        }


        // check if username has changed
        if (!existingPlayer.getUsername().equals(playerUpdates.getUsername())) {
            log.warn("Username change detected for player with id {}. Denying update.", id);
            throw new GameLoveException("Username cannot be changed.");
        }

        // perform the update
        this.playerMapper.transferDetails(playerUpdates, existingPlayer);

        var updatedPlayer = this.playerRepository.save(existingPlayer);
        log.info("Player with id {} was updated", updatedPlayer.getId());

        return updatedPlayer;
    }

    // update player status
    @Override
    @Transactional
    public Player updatePlayerStatus(UUID id, Status status) {

        // get player
        var player = this.playerRepository.findById(id);

        // check if player exists
        if (player.isEmpty()) {
            log.warn("Player with id {} was not found when trying to update player status", id);
            throw new ResourceNotFoundException("Player with id " + id + " was not found.");
        }

        // update status
        var existingPlayer = player.get();
        existingPlayer.setStatus(status);

        var updatedPlayer = this.playerRepository.save(existingPlayer);
        log.info("Status for player with id {} was updated to {} ", id, status);

        return updatedPlayer;

    }

    @Override
    @Transactional
    public void deletePlayer(UUID id) {
        log.debug("Deleting player with id: {}", id);

        // cek if player exists
        if (!playerExists(id)) {
            log.warn("Player with id {} was not found when trying to delete player", id);
            throw new ResourceNotFoundException("Player with id " + id + " was not found.");
        }

        this.playerRepository.deleteById(id);
        log.info("Player with id {} was deleted", id);
    }

    @Override
    @Transactional
    public Player loveGame(UUID id, UUID gameId) {
        // check if player exists
        var player = this.playerRepository.findById(id);

        if (player.isEmpty()) {
            log.warn("Player with id {} was not found when trying to love game", id);
            throw new ResourceNotFoundException("Player with id " + id + " was not found.");
        }
        var existingPlayer = player.get();

        // check if game exists
        var game = this.gameService.findGameById(gameId);
        if (game == null) {
            log.warn("Game with id {} was not found when trying to love game", gameId);
            throw new ResourceNotFoundException("Game with id " + gameId + " was not found.");
        }

        // player loves game
        if (existingPlayer.getLovedGames().contains(game)) {
            log.warn("Player with id {} already loves the game with id {}", id, gameId);

            // options are available to either throw exception or return existing player.
            // I am choosing to return the existing player. because it makes the operation idempotent.
            // otherwise the client needs additional logic to handle the exception.
            return existingPlayer;
        }

        existingPlayer.loveGame(game);

        // save changes
        var updatedPlayer = this.playerRepository.save(existingPlayer);

        log.info("Player with id {} loved the game with id {}", id, gameId);
        return updatedPlayer;
    }

    @Override
    @Transactional
    public Player unloveGame(UUID id, UUID gameId) {
        // check if player exists
        var player = this.playerRepository.findById(id);

        if (player.isEmpty()) {
            log.warn("Player with id {} was not found when trying to unlove game", id);
            throw new ResourceNotFoundException("Player with id " + id + " was not found.");
        }
        var existingPlayer = player.get();

        // check if game exists
        var game = this.gameService.findGameById(gameId);
        if (game == null) {
            log.warn("Game with id {} was not found when trying to unlove game", gameId);
            throw new ResourceNotFoundException("Game with id " + gameId + " was not found.");
        }

        // player unloves game
        if (!existingPlayer.getLovedGames().contains(game)) {
            log.warn("Player with id {} does not love the game with id {}", id, gameId);
            throw new ResourceNotFoundException("Player with id " + id + " does not love the game with id " + gameId);
        }
        existingPlayer.unloveGame(game);

        // save changes
        var updatedPlayer = this.playerRepository.save(existingPlayer);

        log.info("Player with id {} unloved the game with id {}", id, gameId);
        return updatedPlayer;
    }


    private boolean playerExists(UUID id) {
        return this.playerRepository.existsById(id);
    }

    private boolean playerExistsWithUsername(String username) {
        return this.playerRepository.existsPlayerByUsername(username);
    }
}
