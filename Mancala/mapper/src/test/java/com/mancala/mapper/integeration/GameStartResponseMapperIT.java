package com.mancala.mapper.integeration;

import com.mancala.domain.response.GameStartResponse;
import com.mancala.mapper.GameStartResponseMapper;
import com.mancala.utilities.DataHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class GameStartResponseMapperIT {

    private final DataHelper dataHelper = DataHelper.getInstance();

    @Spy
    private final GameStartResponseMapper responseMapper = Mappers.getMapper(GameStartResponseMapper.class);

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGameStartResponseFromGameEntity() {
        GameStartResponse gameStartResponse = responseMapper.gameStartResponseFromGameEntity(dataHelper.gameEntity());
        assertEquals(dataHelper.gameStartResponse(), gameStartResponse);
    }
}
