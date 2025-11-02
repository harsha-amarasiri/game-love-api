package com.gamelove.api.dto;

import java.util.UUID;

public interface GameStatsProjection {

    UUID getGameId();

    String getGameTitle();

    Long getLoveCount();


}
