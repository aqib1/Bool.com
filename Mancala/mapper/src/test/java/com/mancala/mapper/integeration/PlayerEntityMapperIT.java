package com.mancala.mapper.integeration;

import com.mancala.domain.entities.PlayerEntity;
import com.mancala.mapper.PlayerEntityMapper;
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
public class PlayerEntityMapperIT {

    private final DataHelper dataHelper = DataHelper.getInstance();

    @Spy
    private final PlayerEntityMapper mapper = Mappers.getMapper(PlayerEntityMapper.class);

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testPlayerEntityFromPlayerResponse() {
        PlayerEntity firstPlayer = mapper.playerEntityFromPlayerResponse(dataHelper.firstPlayerResponse());
        assertEquals(dataHelper.firstPlayerEntity(), firstPlayer);
    }
}
