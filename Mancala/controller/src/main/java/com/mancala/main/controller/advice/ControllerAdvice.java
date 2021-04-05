package com.mancala.main.controller.advice;

import com.mancala.domain.exceptions.GameNotFoundException;
import com.mancala.domain.exceptions.InvalidGameStateException;
import com.mancala.domain.exceptions.InvalidMoveException;
import com.mancala.domain.response.ResponseError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Optional;

@RestControllerAdvice
public class ControllerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ControllerAdvice.class);

    @ExceptionHandler(value
            = IllegalArgumentException.class)
    public ResponseEntity<ResponseError> handleIllegalArgumentException(
            IllegalArgumentException e) {
        String error = Optional.ofNullable(e.getMessage()).orElse(e.getClass().getName())
                + " [Internal server exception! => (IllegalArgumentException)]";
        ResponseError errorResponse = ResponseError.builder()
                .createdAt(LocalDateTime.now().toString())
                .detailedMessage(error)
                .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .exceptionName(IllegalArgumentException.class.getName())
                .errorMessage(e.getMessage()).build();
        logger.error("Response error created : {}", errorResponse);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value
            = GameNotFoundException.class)
    public ResponseEntity<ResponseError> handleGameNotFoundException(
            GameNotFoundException e) {
        String error = Optional.ofNullable(e.getMessage()).orElse(e.getClass().getName())
                + " [Internal server exception! => (GameNotFoundException)]";
        ResponseError errorResponse = ResponseError.builder()
                .createdAt(LocalDateTime.now().toString())
                .detailedMessage(error)
                .errorCode(HttpStatus.BAD_REQUEST.value())
                .exceptionName(GameNotFoundException.class.getName())
                .errorMessage(e.getMessage()).build();
        logger.error("Response error created : {}", errorResponse);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value
            = InvalidGameStateException.class)
    public ResponseEntity<ResponseError> handleInvalidGameStateException(
            InvalidGameStateException e) {
        String error = Optional.ofNullable(e.getMessage()).orElse(e.getClass().getName())
                + " [Internal server exception! => (InvalidGameStateException)]";
        ResponseError errorResponse = ResponseError.builder()
                .createdAt(LocalDateTime.now().toString())
                .detailedMessage(error)
                .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .exceptionName(InvalidGameStateException.class.getName())
                .errorMessage(e.getMessage()).build();
        logger.error("Response error created : {}", errorResponse);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value
            = InvalidMoveException.class)
    public ResponseEntity<ResponseError> handleInvalidMoveException(
            InvalidMoveException e) {
        String error = Optional.ofNullable(e.getMessage()).orElse(e.getClass().getName())
                + " [Internal server exception! => (InvalidMoveException)]";
        ResponseError errorResponse = ResponseError.builder()
                .createdAt(LocalDateTime.now().toString())
                .detailedMessage(error)
                .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .exceptionName(InvalidMoveException.class.getName())
                .errorMessage(e.getMessage()).build();
        logger.error("Response error created : {}", errorResponse);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
