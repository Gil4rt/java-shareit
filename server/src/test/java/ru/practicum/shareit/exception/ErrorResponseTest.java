package ru.practicum.shareit.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ErrorResponseTest {

    @Test
    void setError() {
        ErrorResponse response = new ErrorResponse("Error message");

        assertEquals("Error message", response.getError());
    }

    @Test
    void returnError() {
        ErrorResponse response = new ErrorResponse("Error message");

        assertEquals("Error message", response.getError());
    }
}

