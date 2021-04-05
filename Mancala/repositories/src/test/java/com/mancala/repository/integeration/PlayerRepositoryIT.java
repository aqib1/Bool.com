package com.mancala.repository.integeration;

import com.mancala.domain.entities.PlayerEntity;
import com.mancala.repository.Impl.PlayerRepository;
import com.mancala.utilities.DataHelper;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class PlayerRepositoryIT {

    private final DataHelper dataHelper = DataHelper.getInstance();

    @Spy
    private PlayerRepository repository;

    @Test
    public void testSave() {
        PlayerEntity playerEntity = repository.save(dataHelper.firstPlayerEntity());
        assertEquals(dataHelper.firstPlayerEntity (), playerEntity);
        assertEquals(1, repository.size());
    }

    @Test
    public void testFindById() {
        repository.save(dataHelper.firstPlayerEntity());
        PlayerEntity playerEntity = repository.findById(DataHelper.FIRST_PLAYER_UUID);
        assertEquals(dataHelper.firstPlayerEntity(), playerEntity);
        assertEquals(1, repository.size());
    }

    @Test
    public void testClear() {
        repository.save(dataHelper.secondPlayerEntity());
        assertEquals(1, repository.size());
        repository.clear();
        assertEquals(0, repository.size());
    }

    @Test
    public void testSize() {
        repository.save(dataHelper.firstPlayerEntity());
        assertEquals(1, repository.size());
    }

    @After
    public void after() {
        repository.clear();
    }
}
