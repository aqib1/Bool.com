package com.mancala.main.unit.controller;


import com.mancala.domain.request.GameMoveRequest;
import com.mancala.domain.request.GamePlayRequest;
import com.mancala.domain.request.GameStartRequest;
import com.mancala.main.controller.GameController;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import com.mancala.utilities.DataHelper;

@SpringBootTest
public class GameControllerUT {

    private final DataHelper dataHelper = DataHelper.getInstance();

    @Mock
    private GameController gameController;

    @Test
    public void testVerifyGamePlay() {
        when(gameController.gamePlay(any(GamePlayRequest.class)))
                .thenReturn(dataHelper.modelAndView());

        gameController.gamePlay(dataHelper.gamePlayRequest());

        verify(gameController, times(1))
                .gamePlay(dataHelper.gamePlayRequest());
    }

    @Test
    public void testVerifyStart() {
        when(gameController.start(any(GameStartRequest.class)))
                .thenReturn(dataHelper.gameStartResponse());

        gameController.start(dataHelper.gameStartRequest());

        verify(gameController, times(1))
                .start(dataHelper.gameStartRequest());
    }

    @Test
    public void testVerifyUpdateMove() {
        when(gameController.updateMove(any(GameMoveRequest.class)))
                .thenReturn(dataHelper.gameResponse());

        gameController.updateMove(dataHelper.gameMoveRequest());

        verify(gameController, times(1))
                .updateMove(dataHelper.gameMoveRequest());
    }

    @Test
    public void testVerifyGetGame() {
        when(gameController.getGameById(anyString()))
                .thenReturn(dataHelper.gameResponse());

        gameController.getGameById(DataHelper.GAME_UUID.toString());

        verify(gameController, times(1))
                .getGameById(DataHelper.GAME_UUID.toString());
    }

    @Test
    public void testClear() {
        doNothing().when(gameController).delete();

        gameController.delete();

        verify(gameController, times(1)).delete();
    }
}
