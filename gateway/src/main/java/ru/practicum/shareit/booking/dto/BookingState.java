package ru.practicum.shareit.booking.dto;

import ru.practicum.shareit.exception.ValidationException;

import java.util.Optional;

public enum BookingState {
    ALL,
    CURRENT,
    FUTURE,
    PAST,
    REJECTED,
    WAITING;

    public static Optional<BookingState> validateState(String stringState) {
        for (BookingState state : values()) {
            if (state.name().equalsIgnoreCase(stringState)) {
                return Optional.of(state);
            }
        }
        throw new ValidationException(String.format("Unknown state: %s", stringState));
    }
}