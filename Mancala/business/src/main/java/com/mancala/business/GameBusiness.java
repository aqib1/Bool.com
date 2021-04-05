package com.mancala.business;

import com.mancala.domain.request.GameMoveRequest;
import com.mancala.domain.request.GameStartRequest;
import com.mancala.domain.response.GameResponse;
import com.mancala.domain.response.GameStartResponse;
import com.mancala.mapper.GameResponseMapper;
import com.mancala.mapper.GameStartResponseMapper;
import com.mancala.service.Impl.GameServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameBusiness {

    private static final Logger logger = LoggerFactory.getLogger(GameBusiness.class);

    @Autowired
    private GameServiceImpl service;

    @Autowired
    private GameResponseMapper gameResponseMapper;

    @Autowired
    private GameStartResponseMapper gameStartResponseMapper;


    public GameStartResponse start(GameStartRequest request) {
        logger.debug("GameStartRequest {} received in business for start", request);
        return gameStartResponseMapper.gameStartResponseFromGameEntity(service
                .start(request.getFirstPlayer(), request.getSecondPlayer()));
    }

    public GameResponse getGameById(String uuid) {
        logger.debug("UUID {} received in business for getGameById", uuid);
        return gameResponseMapper.gameResponseFromGameEntity(service.getGameById(uuid));
    }

    public GameResponse updateMove(GameMoveRequest moveRequest) {
        logger.debug("GameMoveRequest {} received in business for updateMove", moveRequest);
        return gameResponseMapper.gameResponseFromGameEntity(service.updateMove(moveRequest));
    }

    public void delete() {
        service.delete();
    }
}
