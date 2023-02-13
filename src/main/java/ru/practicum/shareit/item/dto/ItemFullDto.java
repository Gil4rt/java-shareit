package ru.practicum.shareit.item.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.booking.model.Booking;

import java.util.Collection;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemFullDto {
    long id;
    String name;
    String description;
    Boolean available;
    Booking lastBooking;
    Booking nextBooking;
    Collection<CommentDto> comments;
    Long requestId;
}
