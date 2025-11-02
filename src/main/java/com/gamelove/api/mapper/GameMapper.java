package com.gamelove.api.mapper;

import com.gamelove.api.dto.*;
import com.gamelove.api.model.Game;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface GameMapper {
    GameResponse toGameResponse(Game game);

    List<GameResponse> toGameResponseList(List<Game> games);

    Game toGame(CreateGameRequest request);

    Game toGame(UpdateGameRequest response);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void transferDetails(Game gameUpdates, @MappingTarget Game existingGame);

    @Mapping(target = "id", ignore = true)
    List<GameStats> toStatsResponse(List<GameStatsProjection> stats);
}
