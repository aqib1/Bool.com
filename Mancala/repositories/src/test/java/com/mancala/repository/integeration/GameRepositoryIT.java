package com.mancala.repository.integeration;

import com.mancala.domain.entities.GameEntity;
import com.mancala.repository.Impl.GameRepository;
import com.mancala.utilities.DataHelper;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class GameRepositoryIT {

    private final DataHelper dataHelper = DataHelper.getInstance();

    @Spy
    private GameRepository gameRepository;

    @Test
    public void testSave() {
        GameEntity gameEntity = gameRepository.save(dataHelper.gameEntity());
        assertEquals(dataHelper.gameEntity(), gameEntity);
        assertEquals(1, gameRepository.size());
    }

    @Test
    public void testFindById() {
        gameRepository.save(dataHelper.gameEntity());
        GameEntity gameEntity = gameRepository.findById(DataHelper.GAME_UUID);
        assertEquals(dataHelper.gameEntity(), gameEntity);
        assertEquals(1, gameRepository.size());
    }

    @Test
    public void testClear() {
        gameRepository.save(dataHelper.gameEntity());
        assertEquals(1, gameRepository.size());
        gameRepository.clear();
        assertEquals(0, gameRepository.size());
    }

    @Test
    public void testSize() {
        gameRepository.save(dataHelper.gameEntity());
        assertEquals(1, gameRepository.size());
    }

    @After
    public void after() {
        gameRepository.clear();
    }
}
