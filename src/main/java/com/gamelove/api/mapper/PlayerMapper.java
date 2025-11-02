package com.gamelove.api.mapper;

import com.gamelove.api.dto.CreatePlayerRequest;
import com.gamelove.api.dto.PlayerResponse;
import com.gamelove.api.dto.PlayerWithLovedGamesResponse;
import com.gamelove.api.dto.UpdatePlayerRequest;
import com.gamelove.api.model.Player;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PlayerMapper {

    PlayerResponse toPlayerResponse(Player player);

    List<PlayerResponse> toPlayerResponseList(List<Player> players);

    List<PlayerWithLovedGamesResponse> toPlayerWithLovedGamesResponseList(List<Player> players);

    Player toPlayer(CreatePlayerRequest request);

    Player toPlayer(UpdatePlayerRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "username", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void transferDetails(Player playerUpdates, @MappingTarget Player existingPlayer);
}
