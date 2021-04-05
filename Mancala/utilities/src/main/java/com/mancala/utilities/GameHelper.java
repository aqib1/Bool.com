package com.mancala.utilities;

import com.mancala.domain.entities.GameEntity;
import com.mancala.domain.entities.PlayerEntity;
import com.mancala.domain.enums.PlayerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.IntStream;

import static com.mancala.domain.enums.PlayerType.FIRST;
import static com.mancala.domain.enums.PlayerType.SECOND;
import static com.mancala.utilities.Constants.*;

public class GameHelper {

    private static final Logger logger = LoggerFactory.getLogger(GameHelper.class);

    public int[] getInitialBoard() {
        logger.info("Initializing board for game");
        int[] board = new int[TOTAL_BOARD_PITS];
        IntStream.range(0, TOTAL_BOARD_PITS)
                .filter(index -> (index != FIRST_PLAYER_SCORE_PIT && index != SECOND_PLAYER_SCORE_PIT))
                .forEach(index -> board[index] = INIT_PIT_VALUE);
        return board;
    }

    public int getLargePitByPlayerType(PlayerType playerType) {
        logger.debug("Player score pit for player type {}", playerType);
        return switch (playerType) {
            case FIRST -> FIRST_PLAYER_SCORE_PIT;
            case SECOND -> SECOND_PLAYER_SCORE_PIT;
        };
    }

    public int maxIndexByPlayerType(PlayerType type) {
        return PITS_PER_ROW + (type.ordinal() * PITS_PER_ROW) - 2;
    }

    public int minIndexByPlayerType(PlayerType type) {
        return type.ordinal() * PITS_PER_ROW;
    }

    public int getOpponentPitIndex(int currentIndex) {
        return TOTAL_BOARD_PITS - currentIndex - 2;
    }

    public int captureOpponentStones(int nextIndex, int[] board) {
        int oppositeIndex = getOpponentPitIndex(nextIndex);
        int scoreToAd = ++board[oppositeIndex];
        board[nextIndex] = 0;
        board[oppositeIndex] = 0;
        return scoreToAd;
    }

    public PlayerType getOpponentByType(PlayerType currentPlayer) {
        logger.debug("Request opponent against player type {}", currentPlayer);
        return switch (currentPlayer) {
            case FIRST -> SECOND;
            case SECOND -> FIRST;
        };
    }

    public PlayerEntity getPlayerFromGameEntityByType(GameEntity gameEntity, PlayerType playerType) {
        logger.debug("Request opponent against player type {} from game entity", playerType);
        return switch (playerType) {
            case FIRST -> gameEntity.getFirstPlayer();
            case SECOND -> gameEntity.getSecondPlayer();
        };
    }

    public int collectStones(PlayerType playerType, int[] board) {
        logger.debug("Collect stones against player type : {}", playerType);
        return IntStream
                .rangeClosed(minIndexByPlayerType(playerType), maxIndexByPlayerType(playerType))
                .map(i -> board[i]).sum();
    }

    public void fillBoard(PlayerType playerType, int[] board, int number) {
        logger.debug("Fill board for player type {} with value {}", playerType, number);
        IntStream.rangeClosed(minIndexByPlayerType(playerType), maxIndexByPlayerType(playerType))
                .forEach(i -> board[i] = number);
    }

    public boolean anyEmptyBoard(int[] board) {
        return checkPlayerBoardEmptyByType(FIRST, board) || checkPlayerBoardEmptyByType(SECOND, board);
    }

    private boolean checkPlayerBoardEmptyByType(PlayerType playerType, int[] board) {
        logger.debug("Check board empty against player type {}", playerType);
        return IntStream
                .rangeClosed(minIndexByPlayerType(playerType), maxIndexByPlayerType(playerType))
                .noneMatch(index -> board[index] != 0);
    }

    /**
     * Initialization on demand pattern
     * this pattern is alternative of double check locking pattern
     * which not even support lazy loading but also safe to use in
     * multi-processor distributed instances
     */

    private static class InstanceHolder {
        private static final GameHelper INSTANCE = new GameHelper();

        private InstanceHolder() {

        }
    }


    public static GameHelper getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private GameHelper() {

    }
}
