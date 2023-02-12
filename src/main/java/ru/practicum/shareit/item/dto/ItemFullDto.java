package ru.practicum.shareit.item.dto;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.booking.model.Booking;

import java.util.Collection;

@Getter
@Setter
public class ItemFullDto {
    private long id;
    private String name;
    private String description;
    private Boolean available;
    private Booking lastBooking;
    private Booking nextBooking;
    private Collection<CommentDto> comments;
}
