package com.mancala.mapper.unit;

import com.mancala.domain.entities.PlayerEntity;
import com.mancala.domain.response.PlayerResponse;
import com.mancala.mapper.PlayerResponseMapper;
import com.mancala.utilities.DataHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PlayerResponseMapperUT {

    private final DataHelper dataHelper = DataHelper.getInstance();

    @Mock
    private PlayerResponseMapper mapper;

    @Test
    public void testPlayerResponseFromPlayerEntity() {
        when(mapper.playerResponseFromPlayerEntity(any(PlayerEntity.class)))
                .thenReturn(dataHelper.firstPlayerResponse());

        PlayerResponse playerResponse = mapper.playerResponseFromPlayerEntity(dataHelper.firstPlayerEntity());

        assertEquals(dataHelper.firstPlayerResponse(), playerResponse);
        verify(mapper, times(1))
                .playerResponseFromPlayerEntity(dataHelper.firstPlayerEntity());
    }

}
