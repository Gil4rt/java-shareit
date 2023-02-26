package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentRequestDto;
import ru.practicum.shareit.item.dto.ItemRequestDto;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
    private static final String X_SHARER_USER_ID = "X-Sharer-User-Id";
    private final ItemClient itemClient;

    @GetMapping
    public ResponseEntity<Object> findItemItems(@RequestHeader(X_SHARER_USER_ID) long userId) {
        return itemClient.getItems(userId);
    }

    @PostMapping
    public ResponseEntity<Object> createItem(@Valid @RequestBody ItemRequestDto itemDto,
                                             @RequestHeader(X_SHARER_USER_ID) long userId) {
        return itemClient.createItem(userId, itemDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateItem(@PathVariable long id,
                                             @RequestBody ItemRequestDto itemDto,
                                             @RequestHeader(X_SHARER_USER_ID) long userId) {
        return itemClient.updateItem(userId, id, itemDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findItemById(@PathVariable long id,
                                               @RequestHeader(X_SHARER_USER_ID) long userId) {
        return itemClient.getItem(userId, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteItemById(@PathVariable long id,
                                                 @RequestHeader(X_SHARER_USER_ID) long userId) {
        return itemClient.deleteItem(userId, id);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchItems(@RequestParam(required = false) String text,
                                              @RequestHeader(X_SHARER_USER_ID) long userId) {
        return itemClient.searchItems(userId, text);
    }

    @PostMapping("/{id}/comment")
    public ResponseEntity<Object> addItemComment(@PathVariable long id,
                                                 @Valid @RequestBody CommentRequestDto commentDto,
                                                 @RequestHeader(X_SHARER_USER_ID) long userId) {
        return itemClient.addItemComment(userId, id, commentDto);
    }
}
