package com.mancala.service.integeration;

import com.mancala.domain.entities.GameEntity;
import com.mancala.domain.entities.PlayerEntity;
import com.mancala.domain.exceptions.GameNotFoundException;
import com.mancala.domain.exceptions.InvalidGameStateException;
import com.mancala.domain.exceptions.InvalidMoveException;
import com.mancala.repository.Impl.GameRepository;
import com.mancala.repository.Impl.PlayerRepository;
import com.mancala.service.Impl.GameEvaluatorServiceImpl;
import com.mancala.service.Impl.GameServiceImpl;
import com.mancala.service.Impl.PlayerServiceImpl;
import com.mancala.utilities.DataHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.UUID;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GameServiceImplIT {

    private final DataHelper dataHelper = DataHelper.getInstance();

    @Mock
    private GameRepository gameRepository;

    @Mock
    private PlayerRepository playerRepository;

    @Spy
    private GameEvaluatorServiceImpl gameEvaluatorService;

    @Spy
    @InjectMocks
    private PlayerServiceImpl playerService;

    @Spy
    @InjectMocks
    private GameServiceImpl gameService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testStart() {
        when(playerRepository.save(any(PlayerEntity.class)))
                .thenReturn(dataHelper.firstPlayerEntity());
        when(gameRepository.save(any(GameEntity.class)))
                .thenReturn(dataHelper.gameEntity());

        GameEntity gameEntity = gameService.start(DataHelper.FIRST_PLAYER, DataHelper.SECOND_PLAYER);

        assertEquals(dataHelper.gameEntity(), gameEntity);
    }

    @Test
    public void testUpdateMove() {
        when(gameRepository.save(any(GameEntity.class)))
                .thenReturn(dataHelper.gameEntityForIT());
        when(gameRepository.findById(any(UUID.class)))
                .thenReturn(dataHelper.gameEntityForIT());

        GameEntity gameEntity = gameService.updateMove(dataHelper.gameMoveRequest());

        assertArrayEquals(gameEntity.getBoard(), dataHelper.gameEntityForIT().getBoard());
        assertEquals(gameEntity.getStatus(), dataHelper.gameEntityForIT().getStatus());
        assertEquals(gameEntity.getTotalTurn(), dataHelper.gameEntityForIT().getTotalTurn());
    }

    @Test
    public void testCreate() {
        when(gameRepository.save(any(GameEntity.class)))
                .thenReturn(dataHelper.gameEntity());

        GameEntity gameEntity = gameService.create(dataHelper.gameEntity());

        assertEquals(dataHelper.gameEntity(), gameEntity);
    }

    @Test
    public void testGameById() {
        when(gameRepository.findById(any(UUID.class)))
                .thenReturn(dataHelper.gameEntity());

        GameEntity gameEntity = gameService.getGameById(DataHelper.GAME_UUID.toString());

        assertEquals(dataHelper.gameEntity(), gameEntity);
    }

    @Test(expected = GameNotFoundException.class)
    public void testGameByIdGameNotFoundException() {
        when(gameRepository.findById(any(UUID.class)))
                .thenReturn(null);

        gameService.getGameById(UUID.randomUUID().toString());
    }

    @Test(expected = InvalidMoveException.class)
    public void testInvalidUpdateMove() {
        when(gameRepository.findById(any(UUID.class)))
                .thenReturn(dataHelper.gameEntity());

        gameService.updateMove(dataHelper.InvalidGameMoveRequest());
    }

    @Test(expected = InvalidGameStateException.class)
    public void testGetGameInvalidGameStateException() {
        when(gameRepository.findById(any(UUID.class)))
                .thenReturn(dataHelper.gameEntityEnd());

        gameService.getGameById(UUID.randomUUID().toString());
    }
}
