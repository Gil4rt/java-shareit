package ru.practicum.shareit.booking.model;

import ru.practicum.shareit.exception.ValidationException;

public enum BookingState {
    ALL,
    CURRENT,
    PAST,
    FUTURE,
    WAITING,
    REJECTED;

    public static BookingState validateState(String value) throws ValidationException  {
        try {
            return BookingState.valueOf(value);
        } catch (RuntimeException exception) {
            throw new ValidationException("Unknown state: " + value);
        }
    }
}
