package ru.practicum.shareit.item.repository;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.*;

@Repository
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
    public Item update(Item item) {

        Item oldItem = items.get(item.getId());

        if (item.getName() != null) {
            oldItem.setName(item.getName());
        }
        if (item.getDescription() != null) {
            oldItem.setDescription(item.getDescription());
        }
        if (item.getAvailable() != null) {
            oldItem.setAvailable(item.getAvailable());
        }

        items.put(item.getId(), oldItem);

        return oldItem;
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
    public Item get(long id) {
        if (items.get(id) != null) {
            return items.get(id);
        } else {
            return null;
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
