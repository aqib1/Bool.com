package com.mancala.repository.unit;

import com.mancala.domain.entities.GameEntity;
import com.mancala.repository.Impl.GameRepository;
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
public class GameRepositoryUT {

    private final DataHelper dataHelper = DataHelper.getInstance();

    @Mock
    private GameRepository gameRepository;

    @Test
    public void testSave() {
        when(gameRepository.save(any(GameEntity.class)))
                .thenReturn(dataHelper.gameEntity());

        GameEntity gameEntity = gameRepository.save(dataHelper.gameEntity());

        assertEquals(dataHelper.gameEntity(), gameEntity);
        verify(gameRepository, times(1))
                .save(dataHelper.gameEntity());
    }

    @Test
    public void testFindById() {
        when(gameRepository.findById(any(UUID.class)))
                .thenReturn(dataHelper.gameEntity());

        GameEntity gameEntity = gameRepository.findById(DataHelper.GAME_UUID);

        assertEquals(dataHelper.gameEntity(), gameEntity);
        verify(gameRepository, times(1))
                .findById(DataHelper.GAME_UUID);
    }

    @Test
    public void testClear() {
        doNothing().when(gameRepository).clear();

        gameRepository.clear();

        verify(gameRepository, times(1)).clear();
    }

    @Test
    public void testSize() {
        when(gameRepository.size()).thenReturn(1);

        gameRepository.size();

        verify(gameRepository, times(1)).size();
    }
}
