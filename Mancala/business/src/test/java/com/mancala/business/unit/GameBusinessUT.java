package com.mancala.business.unit;

import com.mancala.business.GameBusiness;
import com.mancala.domain.request.GameMoveRequest;
import com.mancala.domain.request.GameStartRequest;
import com.mancala.domain.response.GameResponse;
import com.mancala.domain.response.GameStartResponse;
import com.mancala.utilities.DataHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameBusinessUT {

    private final DataHelper dataHelper = DataHelper.getInstance();

    @Mock
    private GameBusiness business;

    @Test
    public void testStart() {
        when(business.start(any(GameStartRequest.class)))
                .thenReturn(dataHelper.gameStartResponse());

        GameStartResponse gameStartResponse = business.start(dataHelper.gameStartRequest());

        assertEquals(dataHelper.gameStartResponse(), gameStartResponse);
        verify(business, times(1))
                .start(dataHelper.gameStartRequest());
    }

    @Test
    public void testGetGameById() {
        when(business.getGameById(anyString()))
                .thenReturn(dataHelper.gameResponse());

        GameResponse gameResponse = business.getGameById(DataHelper.GAME_UUID.toString());

        assertEquals(dataHelper.gameResponse(), gameResponse);
        verify(business, times(1))
                .getGameById(DataHelper.GAME_UUID.toString());
    }

    @Test
    public void testUpdateMove() {
        when(business.updateMove(any(GameMoveRequest.class)))
                .thenReturn(dataHelper.gameResponse());

        GameResponse gameResponse = business.updateMove(dataHelper.gameMoveRequest());

        assertEquals(dataHelper.gameResponse(), gameResponse);
        verify(business, times(1))
                .updateMove(dataHelper.gameMoveRequest());
    }

    @Test
    public void testDelete() {
        doNothing().when(business).delete();

        business.delete();

        verify(business, times(1)).delete();
    }
}
