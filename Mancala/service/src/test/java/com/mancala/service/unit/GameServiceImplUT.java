package com.mancala.service.unit;

import com.mancala.domain.entities.GameEntity;
import com.mancala.domain.exceptions.GameNotFoundException;
import com.mancala.domain.request.GameMoveRequest;
import com.mancala.service.Impl.GameServiceImpl;
import com.mancala.utilities.DataHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameServiceImplUT {

    private final DataHelper dataHelper = DataHelper.getInstance();

    @Mock
    private GameServiceImpl gameService;

    @Test
    public void testStart() {
        when(gameService.start(anyString(), anyString()))
                .thenReturn(dataHelper.gameEntity());

        GameEntity gameEntity = gameService.start(DataHelper.FIRST_PLAYER, DataHelper.SECOND_PLAYER);

        assertEquals(dataHelper.gameEntity(), gameEntity);
        verify(gameService, times(1))
                .start(DataHelper.FIRST_PLAYER, DataHelper.SECOND_PLAYER);
    }

    @Test
    public void testUpdateMove() {
        when(gameService.updateMove(any(GameMoveRequest.class)))
                .thenReturn(dataHelper.gameEntity());

        GameEntity gameEntity = gameService.updateMove(dataHelper.gameMoveRequest());

        assertEquals(dataHelper.gameEntity(), gameEntity);
        verify(gameService, times(1))
                .updateMove(dataHelper.gameMoveRequest());
    }

    @Test
    public void testCreate() {
        when(gameService.create(any(GameEntity.class)))
                .thenReturn(dataHelper.gameEntity());

        GameEntity gameEntity = gameService.create(dataHelper.gameEntity());

        assertEquals(dataHelper.gameEntity(), gameEntity);
        verify(gameService, times(1))
                .create(dataHelper.gameEntity());
    }

    @Test
    public void testGetGameById() {
        when(gameService.getGameById(anyString()))
                .thenReturn(dataHelper.gameEntity());

        GameEntity gameEntity = gameService.getGameById(DataHelper.GAME_UUID.toString());

        assertEquals(dataHelper.gameEntity(), gameEntity);
        verify(gameService, times(1))
                .getGameById(DataHelper.GAME_UUID.toString());
    }

    @Test
    public void testGetGameByIdGameNotFoundException() {
        when(gameService.getGameById(anyString()))
                .thenThrow(new GameNotFoundException());
        assertThrows(GameNotFoundException.class, () ->
            gameService.getGameById(DataHelper.GAME_UUID.toString())
        );
    }

    @Test
    public void testDelete() {
        doNothing().when(gameService).delete();

        gameService.delete();

        verify(gameService, times(1)).delete();
    }
}
