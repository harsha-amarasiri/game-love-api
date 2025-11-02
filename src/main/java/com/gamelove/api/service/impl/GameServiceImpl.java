package com.gamelove.api.service.impl;

import com.gamelove.api.exception.ResourceAlreadyExistsException;
import com.gamelove.api.exception.ResourceNotFoundException;
import com.gamelove.api.mapper.GameMapper;
import com.gamelove.api.model.Game;
import com.gamelove.api.model.Status;
import com.gamelove.api.repository.GameRepository;
import com.gamelove.api.service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final GameMapper gameMapper;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, GameMapper gameMapper) {
        this.gameRepository = gameRepository;
        this.gameMapper = gameMapper;
    }

    @Override
    public List<Game> getAllGames() {
        log.info("Fetching all games");
        return this.gameRepository.findAll();
    }

    @Override
    public Game findGameById(UUID id) {
        log.debug("Fetching game with id: {}", id);
        var game = this.gameRepository.findById(id);

        if (game.isEmpty()) {
            log.warn("Game with id {} was not found", id);
            throw new ResourceNotFoundException("Game with id " + id + " was not found.");
        }

        log.info("Game with id {} was found", id);
        return game.get();
    }

    @Override
    public Game createGame(Game game) {

        log.debug("Creating game: {}", game);
        // check if game with the title already exists
        if (gameExistsWithTitle(game.getTitle())) {
            log.warn("Game with title {} already exists when trying to create game", game.getTitle());
            throw new ResourceAlreadyExistsException("Game with title " + game.getTitle() + " already exists.");
        }

        // set new game status to active
        game.setStatus(Status.ACTIVE);

        var createdGame = this.gameRepository.save(game);

        log.info("Game with id {} was created", createdGame.getId());

        return createdGame;
    }

    @Override
    public Game updateGameDetails(UUID id, Game gameUpdates) {


        var game = this.gameRepository.findById(id);

        // check if game exists
        if (game.isEmpty()) {
            log.warn("Game with id {} was not found when trying to update game details", id);
            throw new ResourceNotFoundException("Game with id " + id + " was not found.");
        }

        // check if game with conflicting title exists
        if (gameExistsWithTitleExcludingId(gameUpdates.getTitle(), id)) {
            log.warn("Game with title {} already exists when trying to update game details", gameUpdates.getTitle());
            throw new ResourceAlreadyExistsException("Game with title " + gameUpdates.getTitle() + " already exists.");
        }


        // map changes
        var existingGame = game.get();
        this.gameMapper.transferDetails(gameUpdates, existingGame);

        // persist changes
        var updatedGame = this.gameRepository.save(existingGame);

        log.info("Game with id {} was updated", updatedGame.getId());

        return updatedGame;
    }

    @Override
    public Game updateGameStatus(UUID id, Status status) {

        var game = this.gameRepository.findById(id);

        if (game.isEmpty()) {
            log.warn("Game with id {} was not found when trying to update game status", id);
            throw new ResourceNotFoundException("Game with id " + id + " was not found.");
        }

        var existingGame = game.get();

        // update status
        existingGame.setStatus(status);


        var updatedGame = this.gameRepository.save(existingGame);
        log.info("Status for game with id {} was updated to {} ", id, status);

        return updatedGame;
    }

    @Override
    public void deleteGame(UUID id) {
        // check if game exists
        if (!gameExists(id)) {
            throw new ResourceNotFoundException("Game with id " + id + " was not found.");
        }

        this.gameRepository.deleteById(id);
    }


    private boolean gameExists(UUID id) {
        return this.gameRepository.findById(id).isPresent();
    }

    private boolean gameExistsWithTitle(String title) {
        return this.gameRepository.existsGameByTitleIgnoreCase(title);
    }

    private boolean gameExistsWithTitleExcludingId(String title, UUID id) {
        return this.gameRepository.existsByTitleIgnoreCaseAndIdNot(title, id);
    }


}
