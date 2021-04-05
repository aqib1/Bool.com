package com.mancala.mapper;

import com.mancala.domain.entities.PlayerEntity;
import com.mancala.domain.response.PlayerResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlayerResponseMapper {
    PlayerResponse playerResponseFromPlayerEntity(PlayerEntity playerEntity);
}
