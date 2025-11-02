package com.gamelove.api.dto;

import java.util.UUID;

public record GameStats(
        UUID gameId,
        String gameTitle,
        Long loveCount
) {
}
