package com.mancala.business.integeration;

import com.mancala.business.GameBusiness;
import com.mancala.domain.entities.GameEntity;
import com.mancala.domain.entities.PlayerEntity;
import com.mancala.domain.exceptions.GameNotFoundException;
import com.mancala.domain.exceptions.InvalidGameStateException;
import com.mancala.domain.exceptions.InvalidMoveException;
import com.mancala.domain.response.GameResponse;
import com.mancala.domain.response.GameStartResponse;
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

import static com.mancala.domain.enums.GameStatus.START;
import static com.mancala.domain.enums.PlayerType.FIRST;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GameBusinessIT {

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

        GameStartResponse response = gameBusiness.start(dataHelper.gameStartRequest());

        assertEquals(DataHelper.GAME_UUID, response.getUuid());
    }

    @Test
    public void testGetGameById() {
        when(gameRepository.findById(any(UUID.class)))
                .thenReturn(dataHelper.gameEntity());

        GameResponse response = gameBusiness.getGameById(DataHelper.GAME_UUID.toString());

        assertEquals(DataHelper.GAME_UUID, response.getUuid());
        assertEquals(START, response.getStatus());
        assertEquals(FIRST, response.getPlayerTurn());
        assertEquals(DataHelper.FIRST_PLAYER, response.getWinnerPlayer());
    }

    @Test
    public void testUpdateMove() {
        when(gameRepository.save(any(GameEntity.class)))
                .thenReturn(dataHelper.gameEntity());
        when(gameRepository.findById(any(UUID.class)))
                .thenReturn(dataHelper.gameEntity());

        GameResponse response = gameBusiness.updateMove(dataHelper.gameMoveRequest());

        assertEquals(DataHelper.GAME_UUID, response.getUuid());
        assertEquals(START, response.getStatus());
        assertEquals(FIRST, response.getPlayerTurn());
        assertEquals(DataHelper.FIRST_PLAYER, response.getWinnerPlayer());
    }

    @Test(expected = InvalidMoveException.class)
    public void testInvalidUpdateMove() {
        when(gameRepository.findById(any(UUID.class)))
                .thenReturn(dataHelper.gameEntity());

        gameBusiness.updateMove(dataHelper.InvalidGameMoveRequest());
    }

    @Test(expected = GameNotFoundException.class)
    public void testGetGameByUnknownId() {
        when(gameRepository.findById(any(UUID.class)))
                .thenReturn(null);

        gameBusiness.getGameById(UUID.randomUUID().toString());
    }

    @Test(expected = InvalidGameStateException.class)
    public void testGetGameInvalidGameStateException() {
        when(gameRepository.findById(any(UUID.class)))
                .thenReturn(dataHelper.gameEntityEnd());

        gameBusiness.getGameById(UUID.randomUUID().toString());
    }
}
