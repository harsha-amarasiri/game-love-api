package com.gamelove.api.dto;

import com.gamelove.api.model.Status;

import java.util.List;
import java.util.UUID;

/**
 *
 * NOTE: Separate DTO defined because spring OSIV causes eager loading with lovedGames field present
 */
public record PlayerLovedGamesResponse(
        UUID id,
        String username,

        String firstName,
        String lastName,

        List<GameResponse> lovedGames,

        Status status
) {
}
