package com.mancala.service;

import com.mancala.domain.entities.GameEntity;
import com.mancala.domain.request.GameMoveRequest;
import com.mancala.service.rules.MancalaRules;
import com.mancala.service.rules.types.LargePitRule;
import com.mancala.service.rules.types.ResetPitRule;
import com.mancala.service.rules.types.SmallPitRule;


public interface GameEvaluatorService {
    MancalaRules[] MANCALA_RULES = { new ResetPitRule(), new SmallPitRule(), new LargePitRule() };
    boolean validateGameStatus(GameEntity gameEntity);
    void validateMove(GameMoveRequest request, int[] board);
    void evaluateScore(GameEntity gameEntity, GameMoveRequest moveRequest);
}
