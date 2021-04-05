package com.mancala.mapper.integeration;

import com.mancala.domain.response.GameResponse;
import com.mancala.mapper.GameResponseMapper;
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
public class GameResponseMapperIT {

    private final DataHelper dataHelper = DataHelper.getInstance();

    @Spy
    private final GameResponseMapper gameResponseMapper = Mappers.getMapper(GameResponseMapper.class);

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGameResponseFromGameEntity() {
        GameResponse gameResponse = gameResponseMapper.gameResponseFromGameEntity(dataHelper.gameEntity());
        assertEquals(dataHelper.gameResponse(), gameResponse);
    }
}
