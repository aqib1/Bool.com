package com.mancala.service.integeration.rules;

import com.mancala.domain.entities.GameEntity;
import com.mancala.domain.request.GameMoveRequest;
import com.mancala.service.rules.types.SmallPitRule;
import com.mancala.utilities.DataHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static com.mancala.domain.enums.GameStatus.PROGRESS;
import static com.mancala.domain.enums.PlayerType.FIRST;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class SmallPitRuleIT {

    private final DataHelper dataHelper = DataHelper.getInstance();

    @Spy
    private SmallPitRule smallPitRule;

    @Test
    public void testEvaluate() {
        GameMoveRequest moveRequest = dataHelper.gameMoveRequest();
        GameEntity gameEntity = dataHelper.gameEntityForIT();

        smallPitRule.evaluate(moveRequest, gameEntity);

        assertEquals(4, moveRequest.getCurrentStones());
        assertEquals(3, moveRequest.getIndex());
        assertFalse(moveRequest.isFinalOnLargePit());
        assertTrue(moveRequest.isPlayerBoard());
        assertArrayEquals(new int[]{6, 6, 6, 7, 6, 6, 0, 6, 6, 6, 6, 6, 6, 0}, gameEntity.getBoard());
        assertEquals(FIRST, gameEntity.getPlayerTurn());
        assertEquals(PROGRESS, gameEntity.getStatus());
    }
}
