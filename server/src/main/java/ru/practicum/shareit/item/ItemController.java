package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemFullDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
    private static final String X_SHARER_USER_ID = "X-Sharer-User-Id";
    private final ItemService itemService;

    @GetMapping
    public Collection<ItemFullDto> findItemItems(@RequestHeader(X_SHARER_USER_ID) Long userId) {
        return itemService.findUserItems(userId);
    }

    @PostMapping
    public ResponseEntity<ItemDto> createItem(@RequestBody ItemDto itemDto,
                                           @RequestHeader(X_SHARER_USER_ID) Long userId) {
        return itemService.saveItem(itemDto, userId);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable Long id,
                                           @RequestBody ItemDto itemDto,
                                           @RequestHeader(X_SHARER_USER_ID) Long userId) {
        return itemService.updateItem(id, itemDto, userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemFullDto> findItemById(@PathVariable Long id,
                                                    @RequestHeader(X_SHARER_USER_ID) Long userId) {
        return itemService.getItem(id, userId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ItemDto> deleteItemById(@PathVariable Long id,
                                                  @RequestHeader(X_SHARER_USER_ID) Long userId) {
        return itemService.deleteItem(id, userId);
    }

    @GetMapping("/search")
    public Collection<Item> searchItems(@RequestParam(required = false) String text) {
        return itemService.searchItems(text);
    }

    @PostMapping("/{id}/comment")
    public ResponseEntity<CommentDto> addItemComment(@PathVariable Long id,
                                                     @Valid @RequestBody CommentDto commentDto,
                                                     @RequestHeader(X_SHARER_USER_ID) Long userId) {
        return itemService.addItemComment(id, userId, commentDto);
    }
}
