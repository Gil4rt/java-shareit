package ru.practicum.shareit.item.service;

import org.springframework.http.ResponseEntity;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemFullDto;
import ru.practicum.shareit.item.model.Item;

import java.util.Collection;
import java.util.Optional;

public interface ItemService {
    Collection<ItemFullDto> findUserItems(long userId);

    ResponseEntity<ItemDto> saveItem(ItemDto itemDto, long userId);

    ResponseEntity<Item> updateItem(long itemId, ItemDto itemDto, long userId);

    ResponseEntity<ItemDto> deleteItem(long id, long userId);

    ResponseEntity<ItemFullDto> getItem(long id, long userId);

    Collection<Item> searchItems(String text);

    ResponseEntity<CommentDto> addItemComment(long itemId, long userId, CommentDto commentDto);

}
