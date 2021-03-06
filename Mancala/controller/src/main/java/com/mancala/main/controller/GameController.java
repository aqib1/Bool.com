package com.mancala.main.controller;

import com.mancala.business.GameBusiness;
import com.mancala.domain.request.GameMoveRequest;
import com.mancala.domain.request.GamePlayRequest;
import com.mancala.domain.request.GameStartRequest;
import com.mancala.domain.response.GameResponse;
import com.mancala.domain.response.GameStartResponse;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

import static com.mancala.utilities.Constants.*;

@RestController
@RequestMapping(GAME_BASE_URL)
public class GameController {

    private static final Logger logger = LoggerFactory.getLogger(GameController.class);

    @Autowired
    private GameBusiness business;

    @GetMapping(GAME_PLAY_URL)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Game play screen API", response = ModelAndView.class)
    public ModelAndView gamePlay(@Valid GamePlayRequest gamePlayRequest) {
        logger.debug("GamePlayRequest received : {}", gamePlayRequest);
        ModelAndView modelAndView = new ModelAndView(PLAY_VIEW_NAME);
        modelAndView.addObject(GAME_RESPONSE_DATA_KEY, business.getGameById(gamePlayRequest.getUuid()));
        return modelAndView;
    }

    @PostMapping(GAME_START_URL)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Game start request API", response = GameStartResponse.class)
    public GameStartResponse start(@Valid @RequestBody GameStartRequest gameStartRequest) {
        logger.debug("GameStartRequest received : {}", gameStartRequest);
        return business.start(gameStartRequest);
    }

    @PutMapping(GAME_MOVE_URL)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Game update move API", response = GameResponse.class)
    public GameResponse updateMove(@Valid @RequestBody GameMoveRequest moveRequest) {
        logger.debug("GameMoveRequest received : {}", moveRequest);
        return business.updateMove(moveRequest);
    }

    @GetMapping(GAME_GET_URL)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get game by id API", response = GameResponse.class)
    public GameResponse getGameById(@PathVariable("uuid") String uuid) {
        logger.debug("Game id received : {}", uuid);
        return business.getGameById(uuid);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete all games")
    public void delete() {
        business.delete();
    }
}
