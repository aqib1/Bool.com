package com.mancala.service.rules;

import com.mancala.domain.entities.GameEntity;
import com.mancala.domain.request.GameMoveRequest;
import com.mancala.utilities.GameHelper;

public interface MancalaRules {
    GameHelper gameHelper = GameHelper.getInstance();
    void evaluate(GameMoveRequest moveRequest, GameEntity gameEntity);
}
