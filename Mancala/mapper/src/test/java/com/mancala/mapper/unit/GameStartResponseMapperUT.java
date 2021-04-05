package com.mancala.mapper.unit;

import com.mancala.domain.entities.GameEntity;
import com.mancala.domain.response.GameStartResponse;
import com.mancala.mapper.GameStartResponseMapper;
import com.mancala.utilities.DataHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameStartResponseMapperUT {

    private final DataHelper dataHelper = DataHelper.getInstance();

    @Mock
    private GameStartResponseMapper mapper;

    @Test
    public void testGameStartResponseFromGameEntity() {
        when(mapper.gameStartResponseFromGameEntity(any(GameEntity.class)))
                .thenReturn(dataHelper.gameStartResponse());

        GameStartResponse gameStartResponse = mapper.gameStartResponseFromGameEntity(dataHelper.gameEntity());

        assertEquals(dataHelper.gameStartResponse(), gameStartResponse);
        verify(mapper, times(1))
                .gameStartResponseFromGameEntity(dataHelper.gameEntity());
    }
}
