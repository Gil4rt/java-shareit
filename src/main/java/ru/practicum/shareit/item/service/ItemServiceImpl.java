package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.Collection;
import java.util.Optional;

@Transactional(readOnly = false)
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository repository;
    private final UserRepository userRepository;

    private final ItemMapper itemMapper;

    @Override
    public Collection<ItemDto> findUserItems(long userId) {
        return itemMapper.toItemDtoCollection(repository.findByOwner(userId));
    }

    @Transactional
    @Override
    public ItemDto saveItem(ItemDto itemDto, long userId) {
        if (itemDto.getAvailable() == null) {
            throw new ValidationException("The status of the item has not been transferred");
        }
        validateUser(userId);
        Item item = itemMapper.toItem(itemDto, userId);
        return itemMapper.toItemDto(item);
    }

    @Transactional
    @Override
    public ItemDto updateItem(long itemId, ItemDto itemDto, long userId) {

        validateUser(userId);
        Optional<Item> itemOld = validateUserItem(itemId, userId);
        Item item = itemMapper.toItem(itemDto, itemOld.get());
        repository.save(item);
        return itemMapper.toItemDto(item);
    }

    @Transactional
    @Override
    public boolean deleteItem(long id, long userId) {
        validateUser(userId);
        Optional<Item> item = repository.findById(id);
        if (item.isPresent()) {
            repository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Optional<Item> getItem(long id) {
        return Optional.of(repository.findById(id).get());
    }

    @Override
    public Optional<ItemDto> getItemDto(long id) {
        return Optional.of(itemMapper.toItemDto(repository.findById(id).get()));
    }

    @Override
    public Collection<ItemDto> searchItems(String text) {
        return itemMapper.toItemDtoCollection(repository.search(text));
    }

    private User validateUser(long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            throw new NotFoundException(String.format("User (id = %s) not found", userId));
        }
        return user.get();
    }
    private Optional<Item> validateUserItem(long itemId, long userId) {
        Optional<Item> item = repository.findById(itemId);
        if (!item.isPresent()) {
            throw new NotFoundException(String.format("Item (id = %s) not found", itemId));
        } else if (!item.get().getOwner().equals(userId)) {
            throw new NotFoundException(String.format(
                    "Item (id = %s) was not found on the user (id = %s)", itemId, userId));
        }
        return item;
    }
}
