package com.gamelove.api.service;

import com.gamelove.api.model.Player;
import com.gamelove.api.model.Status;

import java.util.List;
import java.util.UUID;

public interface PlayerService {
    List<Player> fetchAllPlayersWithLovedGames();

    List<Player> fetchAllPlayers();

    Player getPlayerById(UUID id);

    Player createPlayer(Player player);

    Player updatePlayer(UUID id, Player player);

    // update player status
    Player updatePlayerStatus(UUID id, Status status);

    void deletePlayer(UUID id);

    Player loveGame(UUID id, UUID gameId);

    Player unloveGame(UUID id, UUID gameId);
}
