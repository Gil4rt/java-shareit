package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemFullDto;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ItemService {
    List<ItemFullDto> findUserItems(long userId);

    ItemDto saveItem(ItemDto itemDto, long userId);

    ItemDto updateItem(long itemId, ItemDto itemDto, long userId);

    boolean deleteItem(long id, long userId);

    Optional<ItemFullDto> getItem(long id, long userId);

    Collection<ItemDto> searchItems(String text);

    Optional<CommentDto> addItemComment(long itemId, long userId, CommentDto commentDto);

}
