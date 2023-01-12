package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ConflictException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Override
    public Collection<User> getAllUsers() {
        return repository.findAll();
    }

    private void validate(User user) {
        if (repository.checkDuplicateEmail(user)) {
            throw new ConflictException("Пользователь с таким email уже зарегистрирован");
        }
    }

    @Override
    public User saveUser(User user) {
        validate(user);
        return repository.save(user);
    }

    @Override
    public Optional<User> updateUser(User user) {
        validate(user);
        return repository.update(user);
    }

    @Override
    public boolean deleteUser(long id) {
        return repository.delete(id);
    }

    @Override
    public Optional<User> getUser(long id) {
        return repository.get(id);
    }
}
