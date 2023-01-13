package ru.practicum.shareit.item.repository;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.Item;

import java.util.*;

@Component
public class ItemRepositoryImpl implements ItemRepository {
    private static long itemId;
    private Map<Long, Item> items;
    private Map<Long, List> userItems;

    public ItemRepositoryImpl() {
        items = new HashMap<>();
        userItems = new HashMap<>();
    }

    private long generateId() {
        return ++itemId;
    }

    @Override
    public List<Item> findUserItems(long userId) {
        return userItems.get(userId);
    }

    @Override
    public Item save(Item item) {
        item.setId(generateId());
        items.put(item.getId(), item);
        if (item.getOwner() != null) {
            List itemList = userItems.get(item.getOwner().getId());
            if (itemList == null) {
                itemList = new ArrayList();
            }
            itemList.add(item);
            userItems.put(item.getOwner().getId(), itemList);
        }
        return item;
    }

    @Override
    public Optional<Item> update(Item item) {
        if (items.get(item.getId()) != null) {
            if (item.getName() != null) {
                items.get(item.getId()).setName(item.getName());
            }
            if (item.getDescription() != null) {
                items.get(item.getId()).setDescription(item.getDescription());
            }
            if (item.getAvailable() != null) {
                items.get(item.getId()).setAvailable(item.getAvailable());
            }
            return Optional.of(items.get(item.getId()));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean delete(long id, long userId) {
        if (userItems.get(userId) != null &&
                userItems.get(userId) != null) {
            items.remove(id);
            userItems.get(userId).remove(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Optional<Item> get(long id) {
        if (items.get(id) != null) {
            return Optional.of(items.get(id));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Collection<Item> search(String text) {
        Collection<Item> itemCollection = new ArrayList<>();
        if (text != null && text.length() > 0) {
            for (Item item : items.values()) {
                if (item.getAvailable()) {
                    if ((item.getName() != null
                            && item.getName().toLowerCase().contains(text.toLowerCase()))
                            || (item.getDescription() != null
                            && item.getDescription().toLowerCase().contains(text.toLowerCase()))) {
                        itemCollection.add(item);
                    }
                }
            }
        }
        return itemCollection;
    }

    @Override
    public boolean checkItem(Item item) {
        return item != null;
    }
}
