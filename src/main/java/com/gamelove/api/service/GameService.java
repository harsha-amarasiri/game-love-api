package com.gamelove.api.service;


import com.gamelove.api.dto.GameResponse;
import com.gamelove.api.dto.GameStatsProjection;
import com.gamelove.api.model.Game;
import com.gamelove.api.model.Status;

import java.util.List;
import java.util.UUID;

public interface GameService {
    List<Game> getAllGames();

    Game findGameById(UUID id);

    Game createGame(Game game);

    Game updateGameDetails(UUID id, Game game);

    Game updateGameStatus(UUID id, Status status);

    void deleteGame(UUID id);

    List<GameStatsProjection> getMostPopularGameStats(Integer size);

    List<GameResponse> getMostPopularGames(Integer size);
}
