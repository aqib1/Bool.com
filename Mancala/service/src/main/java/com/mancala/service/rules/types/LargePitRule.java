package com.mancala.service.rules.types;

import com.mancala.domain.entities.GameEntity;
import com.mancala.domain.request.GameMoveRequest;
import com.mancala.service.rules.MancalaRules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LargePitRule implements MancalaRules {

    private static final Logger logger = LoggerFactory.getLogger(MancalaRules.class);

    @Override
    public void evaluate(GameMoveRequest moveRequest, GameEntity gameEntity) {
        int largerPitIndex = gameHelper.getLargePitByPlayerType(moveRequest.getPlayerTurn());
        if (moveRequest.getIndex() == largerPitIndex) {
            logger.info("Evaluating large pit rule..");
            ++gameEntity.getBoard()[largerPitIndex];
            moveRequest.setCurrentStones(moveRequest.getCurrentStones() - 1);
            if (moveRequest.getCurrentStones() == 0) {
                logger.info("Final move exists on larger pit");
                moveRequest.setFinalOnLargePit(Boolean.TRUE);
            }
            moveRequest.setPlayerBoard(!moveRequest.isPlayerBoard());
        }

    }
}
