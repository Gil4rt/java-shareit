package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserService {

    Collection<UserDto> findAll();

    UserDto save(UserDto userDto);

    Optional<UserDto> update(Long userId, UserDto userDto);

    boolean delete(long userId);

    Optional<User> getUser(long userId);

    Optional<UserDto> getUserDto(long userId);
}
