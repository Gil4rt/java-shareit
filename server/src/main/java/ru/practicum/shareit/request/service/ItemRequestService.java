package ru.practicum.shareit.request.service;

import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestFullDto;
import ru.practicum.shareit.request.model.ItemRequest;

import java.util.Collection;
import java.util.Optional;

public interface ItemRequestService {
    ItemRequest saveItemRequest(ItemRequestDto itemRequestDto, long userId);

    Collection<ItemRequestFullDto> findAllItemRequests(long userId, int from, int size);

    Collection<ItemRequestFullDto> findUserItemRequests(long userId, int from, int size);

    Optional<ItemRequestFullDto> getItemRequest(long id, long userId);
}