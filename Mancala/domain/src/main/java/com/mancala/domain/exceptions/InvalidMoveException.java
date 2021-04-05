package com.mancala.domain.exceptions;

public class InvalidMoveException extends RuntimeException {

  public InvalidMoveException() {}
  public InvalidMoveException(String message) {
    super(message);
  }
}
