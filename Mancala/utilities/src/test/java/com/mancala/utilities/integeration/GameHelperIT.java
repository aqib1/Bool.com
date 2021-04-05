package com.mancala.utilities.integeration;

import com.mancala.utilities.DataHelper;
import com.mancala.utilities.GameHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.mancala.domain.enums.PlayerType.FIRST;
import static com.mancala.domain.enums.PlayerType.SECOND;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class GameHelperIT {

    private final DataHelper dataHelper = DataHelper.getInstance();
    private final GameHelper gameHelper = GameHelper.getInstance();

    @Test
    public void testGetInitialBoard() {
        assertArrayEquals(new int[]{6, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 0}, gameHelper.getInitialBoard());
    }

    @Test
    public void testGetLargePitByPlayerType() {
        assertEquals(6, gameHelper.getLargePitByPlayerType(FIRST));
    }

    @Test
    public void testMaxIndexByPlayerType() {
        assertEquals(5, gameHelper.maxIndexByPlayerType(FIRST));
    }

    @Test
    public void testMinIndexByPlayerType() {
        assertEquals(7, gameHelper.minIndexByPlayerType(SECOND));
    }

    @Test
    public void testGetOpponentPitIndex() {
        assertEquals(10, gameHelper.getOpponentPitIndex(2));
    }

    @Test
    public void testCaptureOpponentStones() {
        assertEquals(7, gameHelper.captureOpponentStones(2, dataHelper.getInitBoard()));
    }

    @Test
    public void testGetOpponentByType() {
        assertEquals(SECOND, gameHelper.getOpponentByType(FIRST));
    }

    @Test
    public void testGetPlayerFromGameEntityByType() {
        assertEquals(dataHelper.firstPlayerEntity(), gameHelper.getPlayerFromGameEntityByType(dataHelper.gameEntity(), FIRST));
    }

    @Test
    public void testCollectStones() {
        assertEquals(36, gameHelper.collectStones(FIRST, dataHelper.getInitBoard()));
    }

    @Test
    public void testFillBoard() {
        int[] board = dataHelper.getInitBoard();
        gameHelper.fillBoard(FIRST, board, 1);
        assertArrayEquals(new int[]{1, 1, 1, 1, 1, 1, 0, 6, 6, 6, 6, 6, 6, 0}, board);
    }

    @Test
    public void testAnyEmptyBoard() {
        boolean isEmpty = gameHelper.anyEmptyBoard(dataHelper.getInitBoardForEnd());
        assertTrue(isEmpty);
    }
}
