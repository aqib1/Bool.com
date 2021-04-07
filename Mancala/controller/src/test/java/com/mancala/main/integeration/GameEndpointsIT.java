package com.mancala.main.integeration;

import com.mancala.domain.request.GameMoveRequest;
import com.mancala.domain.response.GameStartResponse;
import com.mancala.main.MancalaApplication;
import com.mancala.utilities.DataHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.mancala.domain.enums.PlayerType.FIRST;
import static com.mancala.utilities.DataHelper.*;
import static org.hamcrest.Matchers.hasItems;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest(classes = MancalaApplication.class)
public class GameEndpointsIT {

    private final DataHelper dataHelper = DataHelper.getInstance();

    @Autowired
    private MockMvc mvc;

    @Test
    public void testGamePlay() throws Exception {
        GameStartResponse gameStartResponse = dataHelper
                .jsonToObject(startGame()
                                .andReturn()
                                .getResponse()
                                .getContentAsString(),
                        GameStartResponse.class);

        this.mvc.perform(get(GAME_PLAY_URL + "/" + gameStartResponse.getUuid().toString()))
                .andExpect(status().isOk())
                .andExpect(view().name("play"))
                .andDo(print())
                .andReturn();
    }


    @Test
    public void testStart() throws Exception {
        ResultActions resultActions = startGame();

        resultActions.andDo(print())
                .andExpect(content().contentType(MEDIA_TYPE_JSON_UTF8))
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpdateMove() throws Exception {
        GameStartResponse gameStartResponse = dataHelper
                .jsonToObject(startGame()
                                .andReturn()
                                .getResponse()
                                .getContentAsString(),
                        GameStartResponse.class);

        GameMoveRequest moveRequest = dataHelper.gameMoveRequest(
                gameStartResponse.getUuid().toString(), FIRST, 2);

        mvc.perform(put(GAME_MOVE_URL)
                .content(dataHelper.asJsonString(moveRequest))
                .contentType(MEDIA_TYPE_JSON_UTF8))
                .andDo(print())
                .andExpect(content().contentType(MEDIA_TYPE_JSON_UTF8))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("PROGRESS"))
                .andExpect(jsonPath("$.board", hasItems(6, 6, 0, 7, 7, 7, 1, 7, 7, 6, 6, 6, 6, 0)))
                .andExpect(jsonPath("$.playerTurn").value("SECOND"))
                .andExpect(jsonPath("$.totalTurn").value(1));

    }

    @Test
    public void testGetGameById() throws Exception {
        GameStartResponse gameStartResponse = dataHelper
                .jsonToObject(startGame()
                                .andReturn()
                                .getResponse()
                                .getContentAsString(),
                        GameStartResponse.class);

        mvc.perform(get(GAME_BASE_URL + "/" + gameStartResponse.getUuid().toString()))
                .andDo(print())
                .andExpect(content().contentType(MEDIA_TYPE_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid").value(gameStartResponse.getUuid().toString()))
                .andExpect(jsonPath("$.status").value("START"))
                .andExpect(jsonPath("$.board", hasItems(6, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 0)))
                .andExpect(jsonPath("$.playerTurn").value("FIRST"))
                .andExpect(jsonPath("$.totalTurn").value(0));

    }

    @Test
    public void testDelete() throws Exception {
        mvc.perform(delete(GAME_BASE_URL))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    private ResultActions startGame() throws Exception {
        return mvc.perform(post(GAME_START_URL)
                .content(dataHelper.asJsonString(dataHelper.gameStartRequest()))
                .contentType(MEDIA_TYPE_JSON_UTF8));
    }


    @AfterEach
    public void after() throws Exception {
        mvc.perform(delete(GAME_BASE_URL)).andDo(print());
    }
}
