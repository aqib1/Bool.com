package com.mancala.service.rules.types;

import com.mancala.domain.entities.GameEntity;
import com.mancala.domain.request.GameMoveRequest;
import com.mancala.service.rules.MancalaRules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.mancala.utilities.Constants.FIRST_PLAYER_SCORE_PIT;
import static com.mancala.utilities.Constants.SECOND_PLAYER_SCORE_PIT;

public class SmallPitRule implements MancalaRules {

    private static final Logger logger = LoggerFactory.getLogger(SmallPitRule.class);

    @Override
    public void evaluate(GameMoveRequest moveRequest, GameEntity gameEntity) {
        if (moveRequest.getIndex() != FIRST_PLAYER_SCORE_PIT &&
                moveRequest.getIndex() != SECOND_PLAYER_SCORE_PIT) {
            logger.info("Evaluating small pit rule..");
            if (isValidForOpponentCapture(moveRequest, gameEntity)) {
                logger.info("Capturing opponent stones...");
                int largerPitIndex = gameHelper.getLargePitByPlayerType(moveRequest.getPlayerTurn());
                gameEntity.getBoard()[largerPitIndex] += gameHelper.captureOpponentStones(moveRequest.getIndex(), gameEntity.getBoard());
            } else {
                logger.info("Adding stones to next pits");
                ++gameEntity.getBoard()[moveRequest.getIndex()];
            }
            moveRequest.setCurrentStones(moveRequest.getCurrentStones() - 1);
        }
    }

    private boolean isValidForOpponentCapture(GameMoveRequest moveRequest, GameEntity gameEntity) {
        return moveRequest.isPlayerBoard() &&
                moveRequest.getCurrentStones() == 1 &&
                gameEntity.getBoard()[moveRequest.getIndex()] == 0 &&
                gameEntity.getBoard()[gameHelper.getOpponentPitIndex(moveRequest.getIndex())] != 0;
    }
}
