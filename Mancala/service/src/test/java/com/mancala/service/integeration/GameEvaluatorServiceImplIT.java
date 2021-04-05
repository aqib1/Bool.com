package com.mancala.service.integeration;

import com.mancala.domain.entities.GameEntity;
import com.mancala.domain.exceptions.InvalidGameStateException;
import com.mancala.domain.exceptions.InvalidMoveException;
import com.mancala.service.Impl.GameEvaluatorServiceImpl;
import com.mancala.utilities.DataHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static com.mancala.domain.enums.GameStatus.END;
import static com.mancala.domain.enums.GameStatus.PROGRESS;
import static com.mancala.domain.enums.PlayerType.SECOND;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class GameEvaluatorServiceImplIT {

    private final DataHelper dataHelper = DataHelper.getInstance();

    @Spy
    private GameEvaluatorServiceImpl gameEvaluatorService;

    @Test
    public void testValidateGameStatus() {
        boolean validate = gameEvaluatorService.validateGameStatus(dataHelper.gameEntity());
        assertTrue(validate);
    }

    @Test(expected = InvalidGameStateException.class)
    public void testValidateGameStatusInvalidGameStateException() {
        gameEvaluatorService.validateGameStatus(dataHelper.gameEntityEnd());
    }

    @Test(expected = InvalidMoveException.class)
    public void testValidateMoveInvalidMoveException() {
        gameEvaluatorService.validateMove(dataHelper.InvalidGameMoveRequest(), DataHelper.BOARD);
    }

    @Test
    public void testEvaluateScore() {
        GameEntity gameEntity = dataHelper.gameEntityForIT();
        gameEvaluatorService.evaluateScore(gameEntity, dataHelper.gameMoveRequest());
        assertArrayEquals(new int[]{6, 6, 6, 0, 7, 7, 1, 7, 7, 7, 6, 6, 6, 0}, gameEntity.getBoard());
        assertEquals(1, gameEntity.getTotalTurn());
        assertEquals(PROGRESS, gameEntity.getStatus());
        assertEquals(SECOND, gameEntity.getPlayerTurn());
    }

    @Test
    public void testEvaluateEnd() {
        GameEntity gameEntity = dataHelper.gameEntityForEnd();
        gameEvaluatorService.evaluateScore(gameEntity, dataHelper.gameMoveRequest());
        assertArrayEquals(new int[]{0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 16}, gameEntity.getBoard());
        assertEquals(1, gameEntity.getTotalTurn());
        assertEquals(END, gameEntity.getStatus());
        assertEquals("SECOND PLAYER", gameEntity.getWinnerPlayer());
    }
}
