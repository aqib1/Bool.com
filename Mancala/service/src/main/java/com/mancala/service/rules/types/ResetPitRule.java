package com.mancala.service.rules.types;

import com.mancala.domain.entities.GameEntity;
import com.mancala.domain.request.GameMoveRequest;
import com.mancala.service.rules.MancalaRules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.mancala.utilities.Constants.TOTAL_BOARD_PITS;

public class ResetPitRule implements MancalaRules {

    private static final Logger logger = LoggerFactory.getLogger(ResetPitRule.class);

    @Override
    public void evaluate(GameMoveRequest moveRequest, GameEntity gameEntity) {
        if(moveRequest.getIndex() >= TOTAL_BOARD_PITS ) {
            logger.info("Resetting index as index exceed from limit {}", TOTAL_BOARD_PITS);
            moveRequest.setIndex(0);
        }
    }
}
