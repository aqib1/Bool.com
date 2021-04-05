package com.mancala.service.unit;

import com.mancala.domain.entities.PlayerEntity;
import com.mancala.domain.enums.PlayerType;
import com.mancala.service.Impl.PlayerServiceImpl;
import com.mancala.utilities.DataHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceImplUT {

    private final DataHelper dataHelper = DataHelper.getInstance();

    @Mock
    private PlayerServiceImpl playerService;

    @Test
    public void testCreate() {
        when(playerService.create(anyString(), any(PlayerType.class)))
                .thenReturn(dataHelper.firstPlayerEntity());

        PlayerEntity playerEntity = playerService.create(DataHelper.FIRST_PLAYER, PlayerType.FIRST);

        assertEquals(dataHelper.firstPlayerEntity(), playerEntity);
        verify(playerService, times(1))
                .create(DataHelper.FIRST_PLAYER, PlayerType.FIRST);
    }
}
