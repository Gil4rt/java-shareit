package ru.practicum.shareit.item;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class ItemMapper {
    public ItemDto toItemDto(Item item) {
        return new ItemDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                item.getRequestId()
        );
    }

    public Item toItem(ItemDto itemDto, Long ownerId) {
        Item item = new Item();
        item.setId(itemDto.getId());
        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setAvailable(itemDto.getAvailable());
        item.setOwner(ownerId);
        item.setRequestId(itemDto.getRequestId());
        return item;
    }
    public Item toItem(ItemDto itemDto, Item itemOld) {
        Item item = itemOld;
        item.setName(itemDto.getName() == null ? itemOld.getName() : itemDto.getName());
        item.setDescription(itemDto.getDescription() == null ? itemOld.getDescription() : itemDto.getDescription());
        item.setAvailable(itemDto.getAvailable() == null ? itemOld.getAvailable() : itemDto.getAvailable());
        return item;
    }


    public Collection<ItemDto> toItemDtoCollection(Collection<Item> items) {
        return items.stream()
                .map(this::toItemDto)
                .collect(Collectors.toList());
    }
}
