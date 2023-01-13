package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.Collection;
import java.util.Optional;

public interface ItemService {
    Collection<ItemDto> findUserItems(long userId);

    ItemDto saveItem(ItemDto itemDto, long userId);

    Optional<Item> updateItem(long itemId, ItemDto itemDto, long userId);

    boolean deleteItem(long id, long userId);

    Optional<Item> getItem(long id);

    Collection<ItemDto> searchItems(String text);
}
