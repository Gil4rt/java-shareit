package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserService {

    Collection<UserDto> getAllUsers();

    UserDto saveUser(User user);

    Optional<User> updateUser(User user);

    boolean deleteUser(long id);

    Optional<User> getUser(long id);

    Optional<UserDto> getUserDto(long id);
}
