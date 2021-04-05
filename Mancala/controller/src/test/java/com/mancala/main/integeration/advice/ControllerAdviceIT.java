package com.mancala.main.integeration.advice;

import com.mancala.domain.exceptions.GameNotFoundException;
import com.mancala.domain.exceptions.InvalidGameStateException;
import com.mancala.domain.exceptions.InvalidMoveException;
import com.mancala.domain.response.ResponseError;
import com.mancala.main.controller.advice.ControllerAdvice;
import com.mancala.utilities.DataHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ControllerAdviceIT {

    private final DataHelper dataHelper = DataHelper.getInstance();

    @Spy
    private ControllerAdvice controllerAdvice;

    @Test
    public void testHandleIllegalArgumentException() {
        ResponseEntity<ResponseError> response = controllerAdvice.handleIllegalArgumentException(dataHelper.illegalArgumentException());
        ResponseError responseError = Objects.requireNonNull(response.getBody());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(IllegalArgumentException.class.getName(), responseError.getExceptionName());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseError.getErrorCode());
        assertEquals("java.lang.IllegalArgumentException [Internal server exception! => (IllegalArgumentException)]", responseError.getDetailedMessage());
    }

    @Test
    public void testHandleGameNotFoundException() {
        ResponseEntity<ResponseError> response = controllerAdvice.handleGameNotFoundException(dataHelper.gameNotFoundException());
        ResponseError responseError = Objects.requireNonNull(response.getBody());

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(GameNotFoundException.class.getName(), responseError.getExceptionName());
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseError.getErrorCode());
        assertEquals("com.mancala.domain.exceptions.GameNotFoundException [Internal server exception! => (GameNotFoundException)]", responseError.getDetailedMessage());
    }

    @Test
    public void testHandleInvalidGameStateException() {
        ResponseEntity<ResponseError> response = controllerAdvice.handleInvalidGameStateException(dataHelper.invalidGameStateException());
        ResponseError responseError = Objects.requireNonNull(response.getBody());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(InvalidGameStateException.class.getName(), responseError.getExceptionName());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseError.getErrorCode());
        assertEquals("com.mancala.domain.exceptions.InvalidGameStateException [Internal server exception! => (InvalidGameStateException)]", responseError.getDetailedMessage());
    }

    @Test
    public void testHandleInvalidMoveException() {
        ResponseEntity<ResponseError> response = controllerAdvice.handleInvalidMoveException(dataHelper.invalidMoveException());
        ResponseError responseError = Objects.requireNonNull(response.getBody());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(InvalidMoveException.class.getName(), responseError.getExceptionName());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseError.getErrorCode());
        assertEquals("com.mancala.domain.exceptions.InvalidMoveException [Internal server exception! => (InvalidMoveException)]", responseError.getDetailedMessage());
    }
}
