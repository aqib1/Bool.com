package com.mancala.service.Impl;

import com.mancala.domain.entities.GameEntity;
import com.mancala.domain.enums.GameStatus;
import com.mancala.domain.enums.PlayerType;
import com.mancala.domain.exceptions.InvalidGameStateException;
import com.mancala.domain.exceptions.InvalidMoveException;
import com.mancala.domain.request.GameMoveRequest;
import com.mancala.service.GameEvaluatorService;
import com.mancala.utilities.GameHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;

import static com.mancala.domain.enums.PlayerType.FIRST;
import static com.mancala.domain.enums.PlayerType.SECOND;
import static com.mancala.utilities.Constants.FIRST_PLAYER_SCORE_PIT;
import static com.mancala.utilities.Constants.SECOND_PLAYER_SCORE_PIT;

@Service
public class GameEvaluatorServiceImpl implements GameEvaluatorService {

    private static final Logger logger = LoggerFactory.getLogger(GameEvaluatorServiceImpl.class);

    private final GameHelper gameHelper = GameHelper.getInstance();

    @Override
    public boolean validateGameStatus(GameEntity gameEntity) {
        logger.debug("Game {} received for validateGameStatus", gameEntity.getUuid());
        if (gameEntity.getStatus().equals(GameStatus.END)) {
            logger.error("Game {} has ended. Please start a new game.", gameEntity.getUuid());
            throw new InvalidGameStateException(
                    String.format("Game [%s] has ended. Please start a new game.", gameEntity.getUuid()));
        }
        return Boolean.TRUE;
    }

    @Override
    public void validateMove(GameMoveRequest moveRequest, int[] board) {
        logger.debug("Game {} received for validateMove", moveRequest.getUuid());
        int maxIndex = gameHelper.maxIndexByPlayerType(moveRequest.getPlayerTurn());
        int minIndex = gameHelper.minIndexByPlayerType(moveRequest.getPlayerTurn());
        if (moveRequest.getIndex() > maxIndex || moveRequest.getIndex() < minIndex || board[moveRequest.getIndex()] == 0) {
            logger.error("Index {} is not a valid move.", moveRequest.getIndex());
            throw new InvalidMoveException(String.format("Index %d is not a valid move.", moveRequest.getIndex()));
        }
    }

    @Override
    public void evaluateScore(GameEntity gameEntity, GameMoveRequest moveRequest) {
        logger.debug("Game {} received for evaluating score", moveRequest.getUuid());
        moveRequest.setCurrentStones(gameEntity.getBoard()[moveRequest.getIndex()]);
        gameEntity.getBoard()[moveRequest.getIndex()] = 0;
        iterateStones(gameEntity, moveRequest);
        gameEntity.setTotalTurn(gameEntity.getTotalTurn() + 1);
        if (!moveRequest.isFinalOnLargePit()) {
            gameEntity.setPlayerTurn(gameHelper.getOpponentByType(moveRequest.getPlayerTurn()));
        }
        evaluateEnd(gameEntity);
    }

    private void iterateStones(GameEntity gameEntity, GameMoveRequest moveRequest) {
        logger.debug("Iterate by current pit stones : {}", moveRequest.getCurrentStones());
        while (moveRequest.getCurrentStones() > 0) {
            moveRequest.setIndex(moveRequest.getIndex() + 1);
            Arrays.stream(MANCALA_RULES).forEach(rule ->
                    rule.evaluate(moveRequest, gameEntity));
        }
    }

    private void evaluateEnd(GameEntity gameEntity) {
        logger.debug("Game {} received for evaluating end game", gameEntity.getUuid());
        if (gameHelper.anyEmptyBoard(gameEntity.getBoard())) {
            gameEntity.getBoard()[FIRST_PLAYER_SCORE_PIT] += gameHelper.collectStones(FIRST, gameEntity.getBoard());
            gameEntity.getBoard()[SECOND_PLAYER_SCORE_PIT] += gameHelper.collectStones(SECOND, gameEntity.getBoard());
            gameEntity.setWinnerPlayer(gameEntity.getBoard()[FIRST_PLAYER_SCORE_PIT] > gameEntity.getBoard()[SECOND_PLAYER_SCORE_PIT] ?
                    gameHelper.getPlayerFromGameEntityByType(gameEntity, FIRST).getName() : gameHelper.getPlayerFromGameEntityByType(gameEntity, SECOND).getName());
            fillBoard(gameEntity.getBoard());
            gameEntity.setStatus(GameStatus.END);
            logger.info("Game {} successfully finished.", gameEntity.getUuid());
        }
    }

    private void fillBoard(int[] board) {
        for (PlayerType playerType : Arrays.asList(FIRST, SECOND)) {
            gameHelper.fillBoard(playerType, board, 0);
        }
    }
}
