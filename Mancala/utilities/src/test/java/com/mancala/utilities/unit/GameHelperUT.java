package com.mancala.utilities.unit;

import com.mancala.domain.entities.GameEntity;
import com.mancala.domain.entities.PlayerEntity;
import com.mancala.domain.enums.PlayerType;
import com.mancala.utilities.DataHelper;
import com.mancala.utilities.GameHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameHelperUT {

    private final DataHelper dataHelper = DataHelper.getInstance();

    @Mock
    private GameHelper gameHelper;

    @Test
    public void testGetInitialBoard() {
        when(gameHelper.getInitialBoard()).thenReturn(DataHelper.BOARD);

        int[] board = gameHelper.getInitialBoard();

        assertEquals(DataHelper.BOARD, board);
        verify(gameHelper, times(1))
                .getInitialBoard();
    }

    @Test
    public void testGetLargePitByPlayerType() {
        when(gameHelper.getLargePitByPlayerType(any(PlayerType.class)))
                .thenReturn(1);

        gameHelper.getLargePitByPlayerType(PlayerType.FIRST);

        verify(gameHelper, times(1))
                .getLargePitByPlayerType(PlayerType.FIRST);
    }

    @Test
    public void testMaxIndexByPlayerType() {
        when(gameHelper.maxIndexByPlayerType(any(PlayerType.class)))
                .thenReturn(1);

        gameHelper.maxIndexByPlayerType(PlayerType.FIRST);

        verify(gameHelper, times(1))
                .maxIndexByPlayerType(PlayerType.FIRST);
    }

    @Test
    public void testMinIndexByPlayerType() {
        when(gameHelper.minIndexByPlayerType(any(PlayerType.class)))
                .thenReturn(1);

        gameHelper.minIndexByPlayerType(PlayerType.FIRST);

        verify(gameHelper, times(1))
                .minIndexByPlayerType(PlayerType.FIRST);
    }

    @Test
    public void testGetOpponentPitIndex() {
        when(gameHelper.getOpponentPitIndex(anyInt()))
                .thenReturn(11);

        gameHelper.getOpponentPitIndex(0);

        verify(gameHelper, times(1))
                .getOpponentPitIndex(0);
    }

    @Test
    public void testCaptureOpponentStones() {
        when(gameHelper.captureOpponentStones(anyInt(), any(int[].class)))
                .thenReturn(5);

        gameHelper.captureOpponentStones(2, DataHelper.BOARD);

        verify(gameHelper, times(1))
                .captureOpponentStones(2, DataHelper.BOARD);
    }

    @Test
    public void testGetOpponentByType() {
        when(gameHelper.getOpponentByType(any(PlayerType.class)))
                .thenReturn(PlayerType.FIRST);

        gameHelper.getOpponentByType(PlayerType.SECOND);

        verify(gameHelper, times(1))
                .getOpponentByType(PlayerType.SECOND);
    }

    @Test
    public void testGetPlayerFromGameEntityByType() {
        when(gameHelper.getPlayerFromGameEntityByType(any(GameEntity.class), any(PlayerType.class)))
                .thenReturn(dataHelper.firstPlayerEntity());

        PlayerEntity playerEntity = gameHelper.getPlayerFromGameEntityByType(dataHelper.gameEntity(), PlayerType.FIRST);

        assertEquals(dataHelper.firstPlayerEntity(), playerEntity);
        verify(gameHelper, times(1))
                .getPlayerFromGameEntityByType(dataHelper.gameEntity(), PlayerType.FIRST);
    }

    @Test
    public void testCollectStones() {
        when(gameHelper.collectStones(any(PlayerType.class), any(int[].class)))
                .thenReturn(5);

        gameHelper.collectStones(PlayerType.FIRST, DataHelper.BOARD);

        verify(gameHelper, times(1))
                .collectStones(PlayerType.FIRST, DataHelper.BOARD);
    }

    @Test
    public void testFillBoard() {
        doNothing().when(gameHelper)
                .fillBoard(any(PlayerType.class), any(int[].class), anyInt());

        gameHelper.fillBoard(PlayerType.FIRST, DataHelper.BOARD, 0);

        verify(gameHelper, times(1))
                .fillBoard(PlayerType.FIRST, DataHelper.BOARD, 0);
    }

    @Test
    public void testAnyEmptyBoard() {
        when(gameHelper.anyEmptyBoard(any(int[].class)))
                .thenReturn(Boolean.TRUE);

        gameHelper.anyEmptyBoard(DataHelper.BOARD);

        verify(gameHelper, times(1))
                .anyEmptyBoard(DataHelper.BOARD);
    }
}
