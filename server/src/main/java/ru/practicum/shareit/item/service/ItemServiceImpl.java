package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.CommentMapper;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemFullDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository repository;
    private final UserRepository userRepository;

    private final CommentRepository commentRepository;

    private final BookingRepository bookingRepository;

    private final ItemMapper itemMapper;

    @Override
    public Collection<ItemFullDto> findUserItems(long userId) {
        return repository.findByOwnerOrderById(userId)
                .stream()
                .map(item -> itemMapper.toItemFullDto(item,
                        bookingRepository.findLastBooking(item.getId(), userId, LocalDateTime.now()),
                        bookingRepository.findNextBooking(item.getId(), userId, LocalDateTime.now()),
                        commentRepository.findAllByItemIdOrderByCreatedDesc(item.getId())
                                .stream()
                                .map(comment -> CommentMapper.toCommentDto(comment, validateUser(comment.getAuthorId()).getName()))
                                .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }


    @Transactional
    @Override
    public ItemDto saveItem(ItemDto itemDto, long userId) {
        if (itemDto.getAvailable() == null) {
            throw new ValidationException("The status of the item has not been transferred");
        }
        validateUser(userId);
        Item item = itemMapper.toItem(itemDto, userId);
        return itemMapper.toItemDto(repository.save(item));
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
    public Optional<ItemFullDto> getItem(long id, long userId) {
        Optional<Item> item = repository.findById(id);
        if (item.isPresent()) {
            return Optional.of(itemMapper.toItemFullDto(item.get(),
                    bookingRepository.findLastBooking(id, userId, LocalDateTime.now()),
                    bookingRepository.findNextBooking(id, userId, LocalDateTime.now()),
                    commentRepository.findAllByItemIdOrderByCreatedDesc(id)
                            .stream()
                            .map(comment -> CommentMapper.toCommentDto(comment, validateUser(comment.getAuthorId()).getName()))
                            .collect(Collectors.toList())));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Collection<ItemDto> searchItems(String text) {
        return text == null || text.isBlank() ? new ArrayList<>() : itemMapper.toItemDtoCollection(repository.search(text));
    }

    @Override
    public Optional<CommentDto> addItemComment(long itemId, long userId, CommentDto commentDto) {
        User user = validateUser(userId);
        if (validateBookingItem(itemId, userId)) {
            Comment comment = commentRepository.save(CommentMapper.toComment(commentDto, itemId, userId));
            return Optional.of(CommentMapper.toCommentDto(comment, user.getName()));
        } else {
            return Optional.empty();
        }
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
    @Transactional
    @Override
    public boolean validateBookingItem(long itemId, long userId) {
        Optional<Booking> booking = bookingRepository.findByItemIdAndBookerIdAndStatusAndEndBefore(
                itemId, userId, BookingStatus.APPROVED, LocalDateTime.now());
        if (booking.isPresent()) {
            return true;
        } else {
            throw new ValidationException(String.format(
                    "Пользователь (id = %s) не брал вещь (id = %s) в аренду", userId, itemId));
        }
    }
}
