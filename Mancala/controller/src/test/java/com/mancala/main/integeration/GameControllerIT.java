package com.mancala.main.integeration;

import com.mancala.business.GameBusiness;
import com.mancala.domain.entities.GameEntity;
import com.mancala.domain.entities.PlayerEntity;
import com.mancala.domain.exceptions.GameNotFoundException;
import com.mancala.domain.exceptions.InvalidGameStateException;
import com.mancala.domain.exceptions.InvalidMoveException;
import com.mancala.domain.response.GameResponse;
import com.mancala.domain.response.GameStartResponse;
import com.mancala.main.controller.GameController;
import com.mancala.mapper.GameResponseMapper;
import com.mancala.mapper.GameStartResponseMapper;
import com.mancala.repository.Impl.GameRepository;
import com.mancala.repository.Impl.PlayerRepository;
import com.mancala.service.Impl.GameEvaluatorServiceImpl;
import com.mancala.service.Impl.GameServiceImpl;
import com.mancala.service.Impl.PlayerServiceImpl;
import com.mancala.utilities.DataHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.UUID;

import static com.mancala.domain.enums.GameStatus.*;
import static com.mancala.domain.enums.PlayerType.FIRST;
import static com.mancala.domain.enums.PlayerType.SECOND;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GameControllerIT {

    private final DataHelper dataHelper = DataHelper.getInstance();

    @Mock
    private GameRepository gameRepository;

    @Mock
    private PlayerRepository playerRepository;

    @Spy
    private final GameResponseMapper gameResponseMapper = Mappers.getMapper(GameResponseMapper.class);

    @Spy
    private final GameStartResponseMapper gameStartResponseMapper = Mappers.getMapper(GameStartResponseMapper.class);

    @Spy
    @InjectMocks
    private PlayerServiceImpl playerService;

    @Spy
    private GameEvaluatorServiceImpl gameEvaluatorService;

    @Spy
    @InjectMocks
    private GameServiceImpl gameService;

    @Spy
    @InjectMocks
    private GameBusiness gameBusiness;

    @Spy
    @InjectMocks
    private GameController gameController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGamePlay() {
        when(gameRepository.findById(any(UUID.class)))
                .thenReturn(dataHelper.gameEntity());
        gameController.gamePlay(dataHelper.gamePlayRequest());

        verify(gameController, times(1))
                .gamePlay(dataHelper.gamePlayRequest());
    }

    @Test
    public void testStart() {
        when(playerRepository.save(any(PlayerEntity.class)))
                .thenReturn(dataHelper.firstPlayerEntity());
        when(gameRepository.save(any(GameEntity.class)))
                .thenReturn(dataHelper.gameEntity());

        GameStartResponse response = gameController.start(dataHelper.gameStartRequest());

        assertEquals(DataHelper.GAME_UUID, response.getUuid());
    }

    @Test
    public void testUpdateMove() {
        when(gameRepository.save(any(GameEntity.class)))
                .thenReturn(dataHelper.gameEntity());
        when(gameRepository.findById(any(UUID.class)))
                .thenReturn(dataHelper.gameEntity());

        GameResponse response = gameController.updateMove(dataHelper.gameMoveRequest());

        assertEquals(DataHelper.GAME_UUID, response.getUuid());
        assertEquals(START, response.getStatus());
        assertEquals(FIRST, response.getPlayerTurn());
    }

    @Test
    public void testGetGameById() {
        when(gameRepository.findById(any(UUID.class)))
                .thenReturn(dataHelper.gameEntity());

        GameResponse response = gameController.getGameById(DataHelper.GAME_UUID.toString());

        assertEquals(DataHelper.GAME_UUID, response.getUuid());
        assertEquals(START, response.getStatus());
        assertEquals(FIRST, response.getPlayerTurn());
        assertEquals(DataHelper.FIRST_PLAYER, response.getWinnerPlayer());
    }

    @Test
    public void testUpdateMove_FirstPlayerMove() {
        GameEntity genericGame = dataHelper.genericGameEntity(
                dataHelper.genericPlayerEntity("Aqib", FIRST),
                dataHelper.genericPlayerEntity("Ali", SECOND),
                new int[]{3, 3, 3, 3, 3, 3, 0, 3, 3, 3, 3, 3, 3, 0});

        when(gameRepository.save(any(GameEntity.class))).thenReturn(genericGame);
        when(gameRepository.findById(any(UUID.class))).thenReturn(genericGame);

        GameResponse response = gameController.updateMove(dataHelper.gameMoveRequestForFirstPlayer(0));

        assertEquals(genericGame.getUuid(), response.getUuid());
        assertEquals(PROGRESS, response.getStatus());
        assertEquals(1, response.getTotalTurn());
        assertArrayEquals(new int[]{0, 4, 4, 4, 3, 3, 0, 3, 3, 3, 3, 3, 3, 0}, response.getBoard());

    }

    @Test
    public void testUpdateMove_SecondPlayerMove() {
        GameEntity genericGame = dataHelper.genericGameEntity(
                dataHelper.genericPlayerEntity("Jack", FIRST),
                dataHelper.genericPlayerEntity("Zoltan", SECOND),
                dataHelper.getInitBoard());

        when(gameRepository.save(any(GameEntity.class))).thenReturn(genericGame);
        when(gameRepository.findById(any(UUID.class))).thenReturn(genericGame);

        GameResponse response = gameController.updateMove(dataHelper.gameMoveRequestForSecondPlayer(7));

        assertEquals(genericGame.getUuid(), response.getUuid());
        assertEquals(PROGRESS, response.getStatus());
        assertEquals(1, response.getTotalTurn());
        assertArrayEquals(new int[]{6, 6, 6, 6, 6, 6, 0, 0, 7, 7, 7, 7, 7, 1}, response.getBoard());

    }

    @Test
    public void firstPlayerMove_crossOverToOpponentsBoard() {

        GameEntity genericGame = dataHelper.genericGameEntity(
                dataHelper.genericPlayerEntity("Jack", FIRST),
                dataHelper.genericPlayerEntity("Zoltan", SECOND),
                dataHelper.getInitBoard());

        when(gameRepository.save(any(GameEntity.class))).thenReturn(genericGame);
        when(gameRepository.findById(any(UUID.class))).thenReturn(genericGame);

        GameResponse response = gameController.updateMove(dataHelper.gameMoveRequestForFirstPlayer(3));

        assertEquals(genericGame.getUuid(), response.getUuid());
        assertEquals(PROGRESS, response.getStatus());
        assertEquals(1, response.getTotalTurn());
        assertArrayEquals(new int[]{6, 6, 6, 0, 7, 7, 1, 7, 7, 7, 6, 6, 6, 0}, response.getBoard());
    }


    @Test
    public void secondPlayerMove_crossOverToOpponentsBoard() {

        GameEntity genericGame = dataHelper.genericGameEntity(
                dataHelper.genericPlayerEntity("Jack", FIRST),
                dataHelper.genericPlayerEntity("Zoltan", SECOND),
                dataHelper.getInitBoard());

        when(gameRepository.save(any(GameEntity.class))).thenReturn(genericGame);
        when(gameRepository.findById(any(UUID.class))).thenReturn(genericGame);

        GameResponse response = gameController.updateMove(dataHelper.gameMoveRequestForSecondPlayer(8));

        assertEquals(genericGame.getUuid(), response.getUuid());
        assertEquals(PROGRESS, response.getStatus());
        assertEquals(1, response.getTotalTurn());
        assertArrayEquals(new int[]{7, 6, 6, 6, 6, 6, 0, 6, 0, 7, 7, 7, 7, 1}, response.getBoard());
    }

    @Test
    public void firstPlayerMove_highNumberOnHand_loopBackToOwnPit() {
        GameEntity genericGame = dataHelper.genericGameEntity(
                dataHelper.genericPlayerEntity("Aqib", FIRST),
                dataHelper.genericPlayerEntity("Ali", SECOND),
                new int[]{6, 3, 3, 3, 3, 11, 0, 3, 3, 3, 3, 3, 3, 0});

        when(gameRepository.save(any(GameEntity.class))).thenReturn(genericGame);
        when(gameRepository.findById(any(UUID.class))).thenReturn(genericGame);

        GameResponse response = gameController.updateMove(dataHelper.gameMoveRequestForFirstPlayer(5));

        assertEquals(genericGame.getUuid(), response.getUuid());
        assertEquals(PROGRESS, response.getStatus());
        assertEquals(1, response.getTotalTurn());
        assertArrayEquals(new int[]{7, 4, 4, 4, 3, 0, 1, 4, 4, 4, 4, 4, 4, 0}, response.getBoard());
    }

    @Test
    public void secondPlayerMove_highNumberOnHand_loopBackToOwnPit() {
        GameEntity genericGame = dataHelper.genericGameEntity(
                dataHelper.genericPlayerEntity("Aqib", FIRST),
                dataHelper.genericPlayerEntity("Ali", SECOND),
                new int[]{6, 3, 3, 3, 3, 3, 0, 3, 3, 3, 3, 3, 11, 0});

        when(gameRepository.save(any(GameEntity.class))).thenReturn(genericGame);
        when(gameRepository.findById(any(UUID.class))).thenReturn(genericGame);

        GameResponse response = gameController.updateMove(dataHelper.gameMoveRequestForSecondPlayer(12));

        assertEquals(genericGame.getUuid(), response.getUuid());
        assertEquals(PROGRESS, response.getStatus());
        assertEquals(1, response.getTotalTurn());
        assertArrayEquals(new int[]{7, 4, 4, 4, 4, 4, 0, 4, 4, 4, 4, 3, 0, 1}, response.getBoard());
    }

    @Test
    public void firstPlayerMove_lastStonePlacedInEmptyPit_captureOpponentStones() {
        GameEntity genericGame = dataHelper.genericGameEntity(
                dataHelper.genericPlayerEntity("Aqib", FIRST),
                dataHelper.genericPlayerEntity("Ali", SECOND),
                new int[]{6, 3, 3, 3, 1, 0, 0, 3, 3, 3, 3, 3, 3, 0});

        when(gameRepository.save(any(GameEntity.class))).thenReturn(genericGame);
        when(gameRepository.findById(any(UUID.class))).thenReturn(genericGame);

        GameResponse response = gameController.updateMove(dataHelper.gameMoveRequestForFirstPlayer(4));

        assertEquals(genericGame.getUuid(), response.getUuid());
        assertEquals(PROGRESS, response.getStatus());
        assertEquals(1, response.getTotalTurn());
        assertArrayEquals(new int[]{6, 3, 3, 3, 0, 0, 4, 0, 3, 3, 3, 3, 3, 0}, response.getBoard());
    }

    @Test
    public void secondPlayerMove_lastStonePlacedInEmptyPit_captureOpponentStones() {
        GameEntity genericGame = dataHelper.genericGameEntity(
                dataHelper.genericPlayerEntity("Aqib", FIRST),
                dataHelper.genericPlayerEntity("Ali", SECOND),
                new int[]{6, 3, 3, 3, 3, 3, 0, 3, 3, 3, 1, 0, 2, 0});

        when(gameRepository.save(any(GameEntity.class))).thenReturn(genericGame);
        when(gameRepository.findById(any(UUID.class))).thenReturn(genericGame);

        GameResponse response = gameController.updateMove(dataHelper.gameMoveRequestForSecondPlayer(10));

        assertEquals(genericGame.getUuid(), response.getUuid());
        assertEquals(PROGRESS, response.getStatus());
        assertEquals(1, response.getTotalTurn());
        assertArrayEquals(new int[]{6, 0, 3, 3, 3, 3, 0, 3, 3, 3, 0, 0, 2, 4}, response.getBoard());
    }

    @Test
    public void firstPlayerMove_allPitsEmpty_shouldFinishGame() {
        GameEntity genericGame = dataHelper.genericGameEntity(
                dataHelper.genericPlayerEntity("Aqib", FIRST),
                dataHelper.genericPlayerEntity("Ali", SECOND),
                new int[]{0, 0, 0, 0, 0, 1, 22, 3, 3, 3, 1, 0, 2, 0});

        when(gameRepository.save(any(GameEntity.class))).thenReturn(genericGame);
        when(gameRepository.findById(any(UUID.class))).thenReturn(genericGame);

        GameResponse response = gameController.updateMove(dataHelper.gameMoveRequestForFirstPlayer(5));

        assertEquals(genericGame.getUuid(), response.getUuid());
        assertEquals(END, response.getStatus());
        assertEquals(1, response.getTotalTurn());
        assertEquals("Aqib", response.getWinnerPlayer());
        assertArrayEquals(new int[]{0, 0, 0, 0, 0, 0, 23, 0, 0, 0, 0, 0, 0, 12}, response.getBoard());
    }

    @Test(expected = InvalidMoveException.class)
    public void testInvalidUpdateMove() {
        when(gameRepository.findById(any(UUID.class)))
                .thenReturn(dataHelper.gameEntity());

        gameController.updateMove(dataHelper.InvalidGameMoveRequest());
    }

    @Test(expected = GameNotFoundException.class)
    public void testGetGameByUnknownId() {
        when(gameRepository.findById(any(UUID.class)))
                .thenReturn(null);

        gameController.getGameById(UUID.randomUUID().toString());
    }

    @Test(expected = InvalidGameStateException.class)
    public void testGetGameInvalidGameStateException() {
        when(gameRepository.findById(any(UUID.class)))
                .thenReturn(dataHelper.gameEntityEnd());

        gameController.getGameById(UUID.randomUUID().toString());
    }

}
