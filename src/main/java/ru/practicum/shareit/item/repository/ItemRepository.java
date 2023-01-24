package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.model.Item;

import java.util.Collection;

public interface ItemRepository {
    Collection<Item> findUserItems(long userId);

    Item save(Item item);

    Item update(Item item);

    boolean delete(long id, long userId);

    Item get(long id);

    Collection<Item> search(String text);

    boolean checkItem(Item item);
}
