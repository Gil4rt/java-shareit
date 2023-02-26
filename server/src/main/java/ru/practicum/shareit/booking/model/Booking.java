package ru.practicum.shareit.booking.model;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings", schema = "public")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id; // уникальный идентификатор вещи;
    @Column(name = "start_date")
    LocalDateTime start; // дата и время начала бронирования
    @Column(name = "end_date")
    LocalDateTime end; // дата и время конца бронирования
    @Column(name = "item_id")
    Long itemId; // вещь, которую пользователь бронирует
    @Column(name = "booker_id")
    Long bookerId; // пользователь, который осуществляет бронирование
    @Enumerated(EnumType.STRING)
    BookingStatus status; // статус бронирования
}
