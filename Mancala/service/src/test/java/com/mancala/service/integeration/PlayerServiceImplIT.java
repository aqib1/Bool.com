package com.mancala.service.integeration;

import com.mancala.domain.entities.PlayerEntity;
import com.mancala.repository.Impl.PlayerRepository;
import com.mancala.service.Impl.PlayerServiceImpl;
import com.mancala.utilities.DataHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static com.mancala.domain.enums.PlayerType.FIRST;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PlayerServiceImplIT {

    private final DataHelper dataHelper = DataHelper.getInstance();

    @Mock
    private PlayerRepository playerRepository;

    @Spy
    @InjectMocks
    private PlayerServiceImpl playerService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreate() {
        when(playerRepository.save(any(PlayerEntity.class)))
                .thenReturn(dataHelper.firstPlayerEntity());

        PlayerEntity playerEntity = playerService.create(DataHelper.FIRST_PLAYER, FIRST);

        assertEquals(dataHelper.firstPlayerEntity(), playerEntity);
    }

}
