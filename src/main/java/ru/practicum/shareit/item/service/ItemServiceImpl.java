package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository repository;
    private final UserRepository userRepository;

    private final ItemMapper itemMapper;

    @Override
    public Collection<ItemDto> findUserItems(long userId) {
        return itemMapper.toItemDtoCollection(repository.findUserItems(userId));
    }

    @Override
    public ItemDto saveItem(ItemDto itemDto, long userId) {
        if (itemDto.getAvailable() == null) {
            throw new ValidationException("The status of the item has not been transferred");
        }
        if (!userRepository.get(userId).isPresent()) {
            throw new NotFoundException(String.format("User (id = %s) not found", userId));
        }
        User user = userRepository.get(userId).get();
        Item item = itemMapper.toItem(itemDto, user, null);
        return itemMapper.toItemDto(repository.save(item));
    }

    @Override
    public ItemDto updateItem(long itemId, ItemDto itemDto, long userId) {

        if (!userRepository.get(userId).isPresent()) {
            throw new NotFoundException(String.format("User (id = %s) not found", userId));
        }
        User user = userRepository.get(userId).get();
        itemDto.setId(itemId);
        Item item = itemMapper.toItem(itemDto, user, null);
        if (!getItem(itemId).isPresent()) {
            throw new NotFoundException(String.format("Item (id = %s) not found", itemId));
        } else if (!getItem(itemId).get().getOwner().equals(user)) {
            throw new NotFoundException(String.format(
                    "Item (id = %s) was not found on the user (id = %s)", itemId, userId));
        }


        return itemMapper.toItemDto(repository.update(item));
    }

    @Override
    public boolean deleteItem(long id, long userId) {
        return repository.delete(id, userId);
    }

    @Override
    public Optional<Item> getItem(long id) {
        return Optional.of(repository.get(id));
    }

    @Override
    public Optional<ItemDto> getItemDto(long id) {
        return Optional.of(itemMapper.toItemDto(repository.get(id)));
    }

    @Override
    public Collection<ItemDto> searchItems(String text) {
        return itemMapper.toItemDtoCollection(repository.search(text));
    }
}
