package ru.practicum.shareit.booking.dto;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingDto {
    Long itemId; // вещь, которую пользователь бронирует
    LocalDateTime start; // дата и время начала бронирования
    LocalDateTime end; // дата и время конца бронирования
}
