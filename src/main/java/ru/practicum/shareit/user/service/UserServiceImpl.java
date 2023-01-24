package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ConflictException;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    private final UserMapper userMapper;

    @Override
    public Collection<UserDto> getAllUsers() {
        return userMapper.toUserDtoCollection(repository.findAll());
    }

    private void validate(User user) {
        if (repository.checkDuplicateEmail(user)) {
            throw new ConflictException("A user with this email is already registered");
        }
    }

    @Override
    public UserDto saveUser(User user) {
        validate(user);
        return userMapper.toUserDto(repository.save(user));
    }

    @Override
    public Optional<UserDto> updateUser(User user) {
        validate(user);
        return Optional.of(userMapper.toUserDto(repository.update(user).get()));
    }

    @Override
    public boolean deleteUser(long id) {
        return repository.delete(id);
    }

    @Override
    public Optional<User> getUser(long id) {
        return repository.get(id);
    }

    @Override
    public Optional<UserDto> getUserDto(long id) {
        return Optional.of(userMapper.toUserDto(repository.get(id).get()));
    }
}
