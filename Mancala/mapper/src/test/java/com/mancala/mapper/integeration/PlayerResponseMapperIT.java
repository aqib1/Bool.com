package com.mancala.mapper.integeration;

import com.mancala.domain.response.PlayerResponse;
import com.mancala.mapper.PlayerResponseMapper;
import com.mancala.utilities.DataHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class PlayerResponseMapperIT {

    private final DataHelper dataHelper = DataHelper.getInstance();

    @Spy
    private final PlayerResponseMapper mapper = Mappers.getMapper(PlayerResponseMapper.class);

    @Test
    public void testPlayerResponseFromPlayerEntity() {
        PlayerResponse response = mapper.playerResponseFromPlayerEntity(dataHelper.firstPlayerEntity());
        assertEquals(dataHelper.firstPlayerResponse(), response);
    }
}
