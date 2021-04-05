package com.mancala.service.Impl;

import com.mancala.domain.entities.GameEntity;
import com.mancala.domain.exceptions.GameNotFoundException;
import com.mancala.domain.request.GameMoveRequest;
import com.mancala.repository.Impl.GameRepository;
import com.mancala.service.GameService;
import com.mancala.utilities.GameHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.mancala.domain.enums.GameStatus.PROGRESS;
import static com.mancala.domain.enums.GameStatus.START;
import static com.mancala.domain.enums.PlayerType.FIRST;
import static com.mancala.domain.enums.PlayerType.SECOND;
import static java.util.Optional.ofNullable;

@Service
public class GameServiceImpl implements GameService {

    private static final Logger logger = LoggerFactory.getLogger(GameServiceImpl.class);

    private final GameHelper gameHelper = GameHelper.getInstance();

    @Autowired
    private GameRepository repository;

    @Autowired
    private PlayerServiceImpl playerService;

    @Autowired
    private GameEvaluatorServiceImpl evaluatorService;


    @Override
    public GameEntity start(String firstPlayer, String secondPlayer) {
        var firstPlayerEntity = playerService.create(firstPlayer, FIRST);
        var secondPlayerEntity = playerService.create(secondPlayer, SECOND);
        logger.info("Creating game against players");
        return repository.save(GameEntity
                .builder()
                .uuid(UUID.randomUUID())
                .firstPlayer(firstPlayerEntity)
                .secondPlayer(secondPlayerEntity)
                .status(START)
                .playerTurn(FIRST)
                .board(gameHelper.getInitialBoard())
                .build());
    }

    @Override
    public GameEntity updateMove(GameMoveRequest moveRequest) {
        logger.debug("Game {} received for updateMove", moveRequest.getUuid());
        var game = getGameById(moveRequest.getUuid());
        game.setStatus(PROGRESS);
        evaluatorService.validateMove(moveRequest, game.getBoard());
        return updateMove(game, moveRequest);
    }

    private GameEntity updateMove(GameEntity gameEntity, GameMoveRequest moveRequest) {
        evaluatorService.evaluateScore(gameEntity, moveRequest);
        return create(gameEntity);
    }


    @Override
    public GameEntity create(GameEntity gameEntity) {
        logger.debug("Game {} received for Saving/Updating", gameEntity.getUuid());
        return repository.save(gameEntity);
    }

    @Override
    public GameEntity getGameById(String gameId) {
        logger.debug("Game {} received for getGameById", gameId);
        GameEntity gameEntity = repository.findById(UUID.fromString(gameId));
        ofNullable(gameEntity).ifPresentOrElse(game -> evaluatorService.validateGameStatus(game),
                () -> {
                    throw new GameNotFoundException(String.format("Game id [%s] not found", gameId));
                });
        return gameEntity;
    }

    @Override
    public void delete() {
        repository.clear();
    }


}
