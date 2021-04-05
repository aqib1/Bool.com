package com.mancala.main.unit.controller.advice;

import com.mancala.domain.exceptions.GameNotFoundException;
import com.mancala.domain.exceptions.InvalidGameStateException;
import com.mancala.domain.exceptions.InvalidMoveException;
import com.mancala.domain.response.ResponseError;
import com.mancala.main.controller.advice.ControllerAdvice;
import com.mancala.utilities.DataHelper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ControllerAdviceUT {

    private final DataHelper dataHelper = DataHelper.getInstance();

    @Mock
    private ControllerAdvice advice;

    @Test
    public void testHandleIllegalArgumentException() {
        when(advice.handleIllegalArgumentException(any(IllegalArgumentException.class)))
        .thenReturn(dataHelper.errorResponseEntity());

        ResponseEntity<ResponseError> response = advice.handleIllegalArgumentException(dataHelper.illegalArgumentException());

        assertEquals(dataHelper.errorResponseEntity(), response);

        verify(advice, times(1))
                .handleIllegalArgumentException(any(IllegalArgumentException.class));
    }

    @Test
    public void testHandleGameNotFoundException() {
        when(advice.handleGameNotFoundException(any(GameNotFoundException.class)))
                .thenReturn(dataHelper.errorResponseEntity());

        ResponseEntity<ResponseError> response = advice.handleGameNotFoundException(dataHelper.gameNotFoundException());

        assertEquals(dataHelper.errorResponseEntity(), response);

        verify(advice, times(1))
                .handleGameNotFoundException(any(GameNotFoundException.class));
    }

    @Test
    public void testHandleInvalidGameStateException() {
        when(advice.handleInvalidGameStateException(any(InvalidGameStateException.class)))
                .thenReturn(dataHelper.errorResponseEntity());

        ResponseEntity<ResponseError> response = advice.handleInvalidGameStateException(dataHelper.invalidGameStateException());

        assertEquals(dataHelper.errorResponseEntity(), response);

        verify(advice, times(1))
                .handleInvalidGameStateException(any(InvalidGameStateException.class));
    }

    @Test
    public void testHandleInvalidMoveException() {
        when(advice.handleInvalidMoveException(any(InvalidMoveException.class)))
                .thenReturn(dataHelper.errorResponseEntity());

        ResponseEntity<ResponseError> response = advice.handleInvalidMoveException(dataHelper.invalidMoveException());

        assertEquals(dataHelper.errorResponseEntity(), response);

        verify(advice, times(1))
                .handleInvalidMoveException(any(InvalidMoveException.class));
    }
}
