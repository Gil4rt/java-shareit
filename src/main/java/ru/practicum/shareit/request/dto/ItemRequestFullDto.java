package ru.practicum.shareit.request.dto;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.item.model.Item;

import java.time.LocalDateTime;
import java.util.Collection;

@Getter
@Setter
public class ItemRequestFullDto {
    private Long id;
    private String description;
    private LocalDateTime created;
    private Collection<Item> items;
}
