package ru.practicum.shareit.booking.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class BookingDto {
    private Long itemId; // вещь, которую пользователь бронирует
    private LocalDateTime start; // дата и время начала бронирования
    private LocalDateTime end; // дата и время конца бронирования
}
