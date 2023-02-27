package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestFullDto;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.service.ItemRequestService;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/requests")
public class ItemRequestController {
    private static final String X_SHARER_USER_ID = "X-Sharer-User-Id";
    private final ItemRequestService service;

    @PostMapping
    public ResponseEntity<ItemRequest> createItemRequest(@Valid @RequestBody ItemRequestDto itemRequestDto,
                                                         @RequestHeader(X_SHARER_USER_ID) Long userId) {
        return new ResponseEntity<>(service.saveItemRequest(itemRequestDto, userId), HttpStatus.OK);
    }

    @GetMapping
    public Collection<ItemRequestFullDto> findUserItemRequests(@RequestHeader(X_SHARER_USER_ID) Long userId,
                                                               @RequestParam(defaultValue = "0") Integer from,
                                                               @RequestParam(defaultValue = "20") Integer size) {
        return service.findUserItemRequests(userId, from, size);
    }

    @GetMapping("/all")
    public Collection<ItemRequestFullDto> findAllItemRequests(@RequestHeader(X_SHARER_USER_ID) Long userId,
                                                              @RequestParam(defaultValue = "0") Integer from,
                                                              @RequestParam(defaultValue = "20") Integer size) {
        return service.findAllItemRequests(userId, from, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemRequestFullDto> findItemById(@PathVariable Long id,
                                                           @RequestHeader(X_SHARER_USER_ID) Long userId) {
        return service.getItemRequest(id, userId).map(itemRequest -> new ResponseEntity<>(itemRequest, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
