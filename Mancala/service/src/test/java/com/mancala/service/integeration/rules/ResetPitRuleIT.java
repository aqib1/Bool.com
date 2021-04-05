package com.mancala.service.integeration.rules;

import com.mancala.domain.request.GameMoveRequest;
import com.mancala.service.rules.types.ResetPitRule;
import com.mancala.utilities.DataHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ResetPitRuleIT {

    private final DataHelper dataHelper = DataHelper.getInstance();

    @Spy
    private ResetPitRule resetPitRule;

    @Test
    public void testEvaluate() {
        GameMoveRequest moveRequest = dataHelper.gameMoveRequest();
        moveRequest.setIndex(15);
        resetPitRule.evaluate(moveRequest, null);

        assertEquals(0, moveRequest.getIndex());
    }
}
