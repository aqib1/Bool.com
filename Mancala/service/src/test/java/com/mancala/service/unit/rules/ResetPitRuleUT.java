package com.mancala.service.unit.rules;

import com.mancala.domain.entities.GameEntity;
import com.mancala.domain.request.GameMoveRequest;
import com.mancala.service.rules.types.ResetPitRule;
import com.mancala.utilities.DataHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ResetPitRuleUT {

    private final DataHelper dataHelper = DataHelper.getInstance();

    @Mock
    private ResetPitRule resetPitRule;

    @Test
    public void testEvaluate() {
        doNothing().when(resetPitRule)
                .evaluate(any(GameMoveRequest.class), any(GameEntity.class));

        resetPitRule.evaluate(dataHelper.gameMoveRequest(), dataHelper.gameEntity());

        verify(resetPitRule, times(1))
                .evaluate(dataHelper.gameMoveRequest(), dataHelper.gameEntity());
    }
}
