package com.mancala.utilities;

public class Constants {

    //////////////// URL /////////////////////
    public static final String GAME_BASE_URL = "/game";
    public static final String GAME_GET_URL = "/{uuid}";
    public static final String GAME_START_URL = "/start";
    public static final String GAME_PLAY_URL = "/play" + GAME_GET_URL;
    public static final String GAME_MOVE_URL = "/move";

    /////////////////// CONSTANTS /////////////////////
    public static final String PLAY_VIEW_NAME = "play";
    public static final String GAME_RESPONSE_DATA_KEY = "GAME_RESPONSE_DATA_KEY";
    public static int TOTAL_BOARD_PITS = 14;
    public static int FIRST_PLAYER_SCORE_PIT = 6;
    public static int SECOND_PLAYER_SCORE_PIT = 13;
    public static int PITS_PER_ROW = 7;
    public final static int INIT_PIT_VALUE = 6;

    private Constants() {

    }
}
