package com.mancala.mapper;

import com.mancala.domain.entities.GameEntity;
import com.mancala.domain.response.GameStartResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GameStartResponseMapper {
    GameStartResponse gameStartResponseFromGameEntity(GameEntity gameEntity);
}
