package com.mancala.service;

import com.mancala.domain.entities.GameEntity;
import com.mancala.domain.request.GameMoveRequest;

public interface GameService {
    GameEntity start(String firstPlayer, String secondPlayer);
    GameEntity updateMove(GameMoveRequest request);
    GameEntity create(GameEntity gameEntity);
    GameEntity getGameById(String gameId);
    void delete();
}
