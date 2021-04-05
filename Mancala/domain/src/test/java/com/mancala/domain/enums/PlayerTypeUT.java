package com.mancala.domain.enums;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTypeUT {

    @Test
    public void testPlayerTypeFirst() {
        PlayerType playerType = PlayerType.FIRST;
        assertEquals("FIRST", playerType.toString());
    }

    @Test
    public void testPlayerTypeSecond() {
        PlayerType playerType = PlayerType.SECOND;
        assertEquals("SECOND", playerType.toString());
    }

}
