package com.mancala.mapper.unit;

import com.mancala.domain.entities.GameEntity;
import com.mancala.domain.response.GameResponse;
import com.mancala.mapper.GameResponseMapper;
import com.mancala.utilities.DataHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameResponseMapperUT {

    private final DataHelper dataHelper = DataHelper.getInstance();

    @Mock
    private GameResponseMapper mapper;

    @Test
    public void testGameResponseFromGameEntity() {
        when(mapper.gameResponseFromGameEntity(any(GameEntity.class)))
        .thenReturn(dataHelper.gameResponse());

        GameResponse gameResponse = mapper.gameResponseFromGameEntity(dataHelper.gameEntity());

        assertEquals(dataHelper.gameResponse(), gameResponse);
        verify(mapper, times(1))
                .gameResponseFromGameEntity(dataHelper.gameEntity());
    }
}
