package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import javax.validation.Valid;
import javax.validation.ValidationException;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/requests")
public class ItemRequestController {
    private static final String X_SHARER_USER_ID = "X-Sharer-User-Id";
    private final ItemRequestClient itemRequestClient;

    @PostMapping
    public ResponseEntity<Object> createItemRequest(@Valid @RequestBody ItemRequestDto itemRequestDto,
                                                    @RequestHeader(X_SHARER_USER_ID) long userId) {
        return itemRequestClient.createItemRequest(userId, itemRequestDto);
    }

    @GetMapping
    public ResponseEntity<Object> findUserItemRequests(@RequestHeader(X_SHARER_USER_ID) long userId,
                                                       @RequestParam(defaultValue = "0") int from,
                                                       @RequestParam(defaultValue = "20") int size) {
        validatePage(from, size);
        return itemRequestClient.getItemRequests(userId, from, size);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> findAllItemRequests(@RequestHeader(X_SHARER_USER_ID) long userId,
                                                      @RequestParam(defaultValue = "0") int from,
                                                      @RequestParam(defaultValue = "20") int size) {
        validatePage(from, size);
        return itemRequestClient.getAllItemRequests(userId, from, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findItemById(@PathVariable long id,
                                               @RequestHeader(X_SHARER_USER_ID) long userId) {
        return itemRequestClient.getItemRequest(userId, id);
    }

    private int validatePage(int from, int size) {
        if (size <= 0) {
            throw new ValidationException(String.format("Параметр size (%s) задан некорректно", size));
        }
        if (from < 0) {
            throw new ValidationException(String.format("Параметр from (%s) задан некорректно", from));
        }
        int page = from / size;
        return page;
    }
}
