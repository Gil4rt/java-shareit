package ru.practicum.shareit.user.repository;

import ru.practicum.shareit.user.User;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository {
    Collection<User> findAll();

    User save(User user);

    Optional<User> update(User user);

    boolean delete(long id);

    Optional<User> get(long id);

    boolean checkDuplicateEmail(User user);
}
