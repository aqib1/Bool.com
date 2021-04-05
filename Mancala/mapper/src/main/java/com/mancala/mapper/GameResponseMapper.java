package com.mancala.mapper;

import com.mancala.domain.entities.GameEntity;
import com.mancala.domain.response.GameResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GameResponseMapper {
    GameResponse gameResponseFromGameEntity(GameEntity gameEntity);
}
