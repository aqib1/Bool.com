package com.mancala.service.unit;

import com.mancala.domain.entities.GameEntity;
import com.mancala.domain.exceptions.InvalidGameStateException;
import com.mancala.domain.exceptions.InvalidMoveException;
import com.mancala.domain.request.GameMoveRequest;
import com.mancala.service.Impl.GameEvaluatorServiceImpl;
import com.mancala.utilities.DataHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameEvaluatorServiceImplUT {

    private final DataHelper dataHelper = DataHelper.getInstance();

    @Mock
    private GameEvaluatorServiceImpl service;

    @Test
    public void testValidateGameStatus() {
        when(service.validateGameStatus(any(GameEntity.class)))
                .thenReturn(Boolean.TRUE);

        service.validateGameStatus(dataHelper.gameEntity());

        verify(service, times(1))
                .validateGameStatus(dataHelper.gameEntity());
    }

    @Test
    public void testValidateGameStatusInvalidGameState() {
        when(service.validateGameStatus(any(GameEntity.class)))
                .thenThrow(new InvalidGameStateException());
        assertThrows(InvalidGameStateException.class,
                () -> service.validateGameStatus(dataHelper.gameEntity()));
    }

    @Test
    public void testValidateMove() {
        doNothing().when(service)
                .validateMove(any(GameMoveRequest.class), any(int[].class));

        service.validateMove(dataHelper.gameMoveRequest(), DataHelper.BOARD);

        verify(service, times(1))
                .validateMove(dataHelper.gameMoveRequest(), DataHelper.BOARD);
    }

    @Test
    public void testValidateMoveInvalidMoveException() {
        doThrow(InvalidMoveException.class).when(service)
                .validateMove(any(GameMoveRequest.class), any(int[].class));

        assertThrows(InvalidMoveException.class,
                () -> service.validateMove(dataHelper.gameMoveRequest(), DataHelper.BOARD));
    }

    @Test
    public void testEvaluateScore() {
        doNothing().when(service)
                .evaluateScore(any(GameEntity.class), any(GameMoveRequest.class));

        service.evaluateScore(dataHelper.gameEntity(), dataHelper.gameMoveRequest());

        verify(service, times(1))
                .evaluateScore(dataHelper.gameEntity(), dataHelper.gameMoveRequest());
    }
}
