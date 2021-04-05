package com.mancala.repository.unit;

import com.mancala.domain.entities.PlayerEntity;
import com.mancala.repository.Impl.PlayerRepository;
import com.mancala.utilities.DataHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PlayerRepositoryUT {

    private final DataHelper dataHelper = DataHelper.getInstance();

    @Mock
    private PlayerRepository repository;

    @Test
    public void testSave() {
        when(repository.save(any(PlayerEntity.class)))
                .thenReturn(dataHelper.firstPlayerEntity());

        PlayerEntity playerEntity = repository.save(dataHelper.firstPlayerEntity());

        assertEquals(dataHelper.firstPlayerEntity(), playerEntity);
        verify(repository, times(1))
                .save(dataHelper.firstPlayerEntity());
    }

    @Test
    public void testFindById() {
        when(repository.findById(any(UUID.class)))
                .thenReturn(dataHelper.firstPlayerEntity());

        PlayerEntity playerEntityOptional = repository.findById(DataHelper.FIRST_PLAYER_UUID);

        assertEquals(dataHelper.firstPlayerEntity(), playerEntityOptional);
        verify(repository, times(1)).findById(DataHelper.FIRST_PLAYER_UUID);
    }

    @Test
    public void testClear() {
        doNothing().when(repository).clear();

        repository.clear();

        verify(repository, times(1)).clear();
    }

    @Test
    public void testSize() {
        when(repository.size()).thenReturn(1);

        repository.size();

        verify(repository, times(1)).size();
    }
}
