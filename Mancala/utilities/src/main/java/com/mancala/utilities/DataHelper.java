package com.mancala.utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mancala.domain.entities.GameEntity;
import com.mancala.domain.entities.PlayerEntity;
import com.mancala.domain.enums.PlayerType;
import com.mancala.domain.exceptions.GameNotFoundException;
import com.mancala.domain.exceptions.InvalidGameStateException;
import com.mancala.domain.exceptions.InvalidMoveException;
import com.mancala.domain.request.GameMoveRequest;
import com.mancala.domain.request.GamePlayRequest;
import com.mancala.domain.request.GameStartRequest;
import com.mancala.domain.response.GameResponse;
import com.mancala.domain.response.GameStartResponse;
import com.mancala.domain.response.PlayerResponse;
import com.mancala.domain.response.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

import static com.mancala.domain.enums.GameStatus.*;
import static com.mancala.domain.enums.PlayerType.FIRST;
import static com.mancala.domain.enums.PlayerType.SECOND;

public class DataHelper {

    public static final String GAME_BASE_URL = "/game";
    public static final String GAME_START_URL = GAME_BASE_URL + "/start";
    public static final String GAME_MOVE_URL = GAME_BASE_URL + "/move";
    public static final String GAME_PLAY_URL = GAME_BASE_URL + "/play";
    public static final MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json");
    public static final UUID GAME_UUID = UUID.randomUUID();
    public static final UUID FIRST_PLAYER_UUID = UUID.randomUUID();
    public static final UUID SECOND_PLAYER_UUID = UUID.randomUUID();
    public static int[] BOARD = {6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6};
    public static final String FIRST_PLAYER = "FIRST PLAYER";
    public static final String SECOND_PLAYER = "SECOND PLAYER";
    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public ModelAndView modelAndView() {
        return new ModelAndView();
    }

    public GameEntity gameEntity() {
        return GameEntity.builder()
                .uuid(GAME_UUID)
                .board(BOARD)
                .playerTurn(FIRST)
                .status(START)
                .firstPlayer(firstPlayerEntity())
                .secondPlayer(secondPlayerEntity())
                .winnerPlayer(FIRST_PLAYER)
                .build();
    }

    public GameEntity gameEntityEnd() {
        return GameEntity.builder()
                .uuid(GAME_UUID)
                .board(BOARD)
                .playerTurn(FIRST)
                .status(END)
                .firstPlayer(firstPlayerEntity())
                .secondPlayer(secondPlayerEntity())
                .winnerPlayer(FIRST_PLAYER)
                .build();
    }

    public GameEntity gameEntityForIT() {
        return GameEntity.builder()
                .uuid(UUID.randomUUID())
                .board(getInitBoard())
                .playerTurn(FIRST)
                .status(PROGRESS)
                .firstPlayer(firstPlayerEntity())
                .secondPlayer(secondPlayerEntity())
                .build();
    }

    public GameEntity genericGameEntity(PlayerEntity firstPlayer, PlayerEntity secondPlayer, int [] board) {
        return GameEntity.builder()
                .uuid(UUID.randomUUID())
                .board(board)
                .playerTurn(FIRST)
                .status(PROGRESS)
                .firstPlayer(firstPlayer)
                .secondPlayer(secondPlayer)
                .build();
    }

    public PlayerEntity genericPlayerEntity(String playerName, PlayerType type) {
        return PlayerEntity.builder()
                .uuid(UUID.randomUUID())
                .playerType(type)
                .name(playerName)
                .build();
    }

    public GameEntity gameEntityForEnd() {
        return GameEntity.builder()
                .uuid(UUID.randomUUID())
                .board(getInitBoardForEnd())
                .playerTurn(FIRST)
                .status(PROGRESS)
                .firstPlayer(firstPlayerEntity())
                .secondPlayer(secondPlayerEntity())
                .build();
    }

    public int[] getInitBoardForEnd() {
        return new int[]{0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 6, 10};
    }

    public int[] getInitBoard() {
        return new int[]{6, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 0};
    }

    public PlayerEntity firstPlayerEntity() {
        return PlayerEntity.builder()
                .uuid(FIRST_PLAYER_UUID)
                .playerType(FIRST)
                .name(FIRST_PLAYER)
                .build();
    }

    public PlayerEntity secondPlayerEntity() {
        return PlayerEntity.builder()
                .uuid(SECOND_PLAYER_UUID)
                .playerType(SECOND)
                .name(SECOND_PLAYER)
                .build();
    }

    public GamePlayRequest gamePlayRequest() {
        return GamePlayRequest.builder()
                .uuid(GAME_UUID.toString())
                .build();
    }

    public GameStartRequest gameStartRequest() {
        return GameStartRequest.builder()
                .firstPlayer(FIRST_PLAYER)
                .secondPlayer(SECOND_PLAYER)
                .build();
    }

    public GameMoveRequest gameMoveRequest() {
        return GameMoveRequest.builder()
                .uuid(GAME_UUID.toString())
                .index(3)
                .currentStones(5)
                .isFinalOnLargePit(false)
                .playerTurn(FIRST)
                .isPlayerBoard(Boolean.TRUE)
                .build();
    }

    public GameMoveRequest gameMoveRequestForFirstPlayer(int index) {
        return GameMoveRequest.builder()
                .uuid(GAME_UUID.toString())
                .index(index)
                .isPlayerBoard(true)
                .isFinalOnLargePit(false)
                .playerTurn(FIRST)
                .build();
    }
    public GameMoveRequest gameMoveRequestForSecondPlayer(int index) {
        return GameMoveRequest.builder()
                .uuid(GAME_UUID.toString())
                .index(index)
                .playerTurn(SECOND)
                .isPlayerBoard(Boolean.TRUE)
                .build();
    }

    public GameMoveRequest InvalidGameMoveRequest() {
        return GameMoveRequest.builder()
                .uuid(GAME_UUID.toString())
                .index(8)
                .currentStones(5)
                .isFinalOnLargePit(false)
                .playerTurn(FIRST)
                .isPlayerBoard(Boolean.TRUE)
                .build();
    }

    public GameStartResponse gameStartResponse() {
        return GameStartResponse.builder()
                .uuid(GAME_UUID)
                .build();
    }

    public GameResponse gameResponse() {
        return GameResponse.builder()
                .uuid(GAME_UUID)
                .board(BOARD)
                .status(START)
                .playerTurn(FIRST)
                .firstPlayer(firstPlayerResponse())
                .secondPlayer(secondPlayerResponse())
                .winnerPlayer(FIRST_PLAYER)
                .build();
    }

    public PlayerResponse firstPlayerResponse() {
        return PlayerResponse.builder()
                .uuid(FIRST_PLAYER_UUID)
                .playerType(FIRST)
                .name(FIRST_PLAYER)
                .build();
    }

    public PlayerResponse secondPlayerResponse() {
        return PlayerResponse.builder()
                .uuid(SECOND_PLAYER_UUID)
                .playerType(SECOND)
                .name(SECOND_PLAYER)
                .build();
    }

    public GameMoveRequest gameMoveRequest(String uuid, PlayerType type, int index) {
        return GameMoveRequest.builder()
                .uuid(uuid)
                .playerTurn(type)
                .index(index)
                .build();
    }

    public IllegalArgumentException illegalArgumentException() {
        return new IllegalArgumentException();
    }

    public GameNotFoundException gameNotFoundException() {
        return new GameNotFoundException();
    }

    public InvalidGameStateException invalidGameStateException() {
        return new InvalidGameStateException();
    }

    public InvalidMoveException invalidMoveException() {
        return new InvalidMoveException();
    }

    public ResponseError responseError() {
        return ResponseError.builder()
                .errorCode(1)
                .errorMessage("")
                .createdAt("")
                .detailedMessage("")
                .exceptionName("")
                .build();
    }

    public ResponseEntity<ResponseError> errorResponseEntity() {
        return new ResponseEntity<>(responseError(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public String asJsonString(final Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T jsonToObject(String value, Class<T> classRef) {
        try {
            return objectMapper.readValue(value, classRef);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Initialization on demand pattern
     * this pattern is alternative of double check locking pattern
     * which not even support lazy loading but also safe to use in
     * multi-processor distributed instances
     */

    private static class InstanceHolder {
        private static final DataHelper INSTANCE = new DataHelper();

        private InstanceHolder() {

        }
    }


    public static DataHelper getInstance() {
        return DataHelper.InstanceHolder.INSTANCE;
    }

    private DataHelper() {
    }
}

