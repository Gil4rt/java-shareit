package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemFullDto;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    private static final String X_SHARER_USER_ID = "X-Sharer-User-Id";

    @GetMapping
    public Collection<ItemFullDto> findItemItems(@RequestHeader(X_SHARER_USER_ID) long userId) {
        return itemService.findUserItems(userId);
    }

    @PostMapping
    public ResponseEntity<ItemDto> createItem(@Valid @RequestBody ItemDto itemDto,
                                              @RequestHeader(X_SHARER_USER_ID) long userId) {
        return new ResponseEntity<>(itemService.saveItem(itemDto, userId), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ItemDto> updateItem(@PathVariable long id,
                                              @RequestBody ItemDto itemDto,
                                              @RequestHeader(X_SHARER_USER_ID) long userId) {
        return new ResponseEntity<>(itemService.updateItem(id, itemDto, userId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemFullDto> findItemById(@PathVariable long id,
                                                    @RequestHeader(X_SHARER_USER_ID) long userId) {
        return itemService.getItem(id, userId).map(item -> new ResponseEntity<>(item, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ItemDto> deleteItemById(@PathVariable long id,
                                                  @RequestHeader(X_SHARER_USER_ID) long userId) {
        return itemService.deleteItem(id, userId) ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/search")
    public Collection<ItemDto> searchItems(@RequestParam(required = false) String text) {
        return itemService.searchItems(text);
    }

    @PostMapping("/{id}/comment")
    public ResponseEntity<CommentDto> addItemComment(@PathVariable long id,
                                                     @Valid @RequestBody CommentDto commentDto,
                                                     @RequestHeader(X_SHARER_USER_ID) long userId) {
        return itemService.addItemComment(id, userId, commentDto).map(comment -> new ResponseEntity<>(comment, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
