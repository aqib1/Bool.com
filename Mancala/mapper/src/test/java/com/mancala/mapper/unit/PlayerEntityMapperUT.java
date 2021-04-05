package com.mancala.mapper.unit;

import com.mancala.domain.entities.PlayerEntity;
import com.mancala.domain.response.PlayerResponse;
import com.mancala.mapper.PlayerEntityMapper;
import com.mancala.utilities.DataHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PlayerEntityMapperUT {

    private final DataHelper dataHelper = DataHelper.getInstance();

    @Mock
    private PlayerEntityMapper mapper;

    @Test
    public void testPlayerEntityFromPlayerResponse() {
        when(mapper.playerEntityFromPlayerResponse(any(PlayerResponse.class)))
                .thenReturn(dataHelper.firstPlayerEntity());

        PlayerEntity playerEntity = mapper.playerEntityFromPlayerResponse(dataHelper.firstPlayerResponse());

        assertEquals(dataHelper.firstPlayerEntity(), playerEntity);
        verify(mapper, times(1))
                .playerEntityFromPlayerResponse(dataHelper.firstPlayerResponse());
    }
}
