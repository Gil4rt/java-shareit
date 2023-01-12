package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.User;

import java.util.Collection;
import java.util.Optional;

public interface UserService {

    Collection<User> getAllUsers();

    User saveUser(User user);

    Optional<User> updateUser(User user);

    boolean deleteUser(long id);

    Optional<User> getUser(long id);
}
