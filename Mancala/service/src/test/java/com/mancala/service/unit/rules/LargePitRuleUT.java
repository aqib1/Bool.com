package com.mancala.service.unit.rules;

import com.mancala.domain.entities.GameEntity;
import com.mancala.domain.request.GameMoveRequest;
import com.mancala.service.rules.types.LargePitRule;
import com.mancala.utilities.DataHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LargePitRuleUT {

    private final DataHelper dataHelper = DataHelper.getInstance();

    @Mock
    private LargePitRule largePitRule;

    @Test
    public void testEvaluate() {
        doNothing().when(largePitRule)
                .evaluate(any(GameMoveRequest.class), any(GameEntity.class));

        largePitRule.evaluate(dataHelper.gameMoveRequest(), dataHelper.gameEntity());

        verify(largePitRule, times(1))
                .evaluate(dataHelper.gameMoveRequest(), dataHelper.gameEntity());
    }
}
