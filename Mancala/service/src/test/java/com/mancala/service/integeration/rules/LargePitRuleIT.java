package com.mancala.service.integeration.rules;

import com.mancala.domain.entities.GameEntity;
import com.mancala.domain.request.GameMoveRequest;
import com.mancala.service.rules.types.LargePitRule;
import com.mancala.utilities.DataHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static com.mancala.domain.enums.PlayerType.FIRST;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class LargePitRuleIT {

    private final DataHelper dataHelper = DataHelper.getInstance();

    @Spy
    private LargePitRule largePitRule;

    @Test
    public void testEvaluate() {
        GameMoveRequest moveRequest = dataHelper.gameMoveRequest();
        GameEntity gameEntity = dataHelper.gameEntityForIT();

        largePitRule.evaluate(moveRequest, gameEntity);

        assertEquals(5, moveRequest.getCurrentStones());
        assertEquals(3, moveRequest.getIndex());
        assertFalse(moveRequest.isFinalOnLargePit());
        assertTrue(moveRequest.isPlayerBoard());
        assertEquals(FIRST, moveRequest.getPlayerTurn());
    }
}
