package com.mancala.domain.enums;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameStatusUT {

    @Test
    public void testGameStartStatus() {
        GameStatus startStatus = GameStatus.START;
        assertEquals("START", startStatus.toString());
    }

    @Test
    public void testGameProgressStatus() {
        GameStatus progressStatus = GameStatus.PROGRESS;
        assertEquals("PROGRESS", progressStatus.toString());
    }

    @Test
    public void testGameEndStatus() {
        GameStatus endStatus = GameStatus.END;
        assertEquals("END", endStatus.toString());
    }
}
