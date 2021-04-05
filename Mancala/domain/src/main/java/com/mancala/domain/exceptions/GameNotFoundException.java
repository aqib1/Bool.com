package com.mancala.domain.exceptions;

public class GameNotFoundException extends RuntimeException {
    public GameNotFoundException() {}
    public GameNotFoundException(String message) {
        super(message);
    }
}
