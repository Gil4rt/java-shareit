package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.ConflictException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserMapper userMapper;

    @Override
    public Collection<UserDto> findAll() {
        return userMapper.toUserDtoCollection(repository.findAll());
    }

    private void validate(User user) {
        Optional<User> foundUser = repository.findByEmail(user.getEmail());
        if (foundUser.isPresent() && foundUser.get().getId() != user.getId()) {
            throw new ConflictException("A user with this email is already registered");
        }
        long userId = user.getId();
        repository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User ID %s doesn't exist.", userId)));
    }

    @Transactional
    @Override
    public UserDto save(UserDto userDto) {
        User user = repository.save(UserMapper.toUser(userDto));
        return userMapper.toUserDto(user);
    }

    @Transactional
    @Override
    public Optional<UserDto> update(UserDto userDto) {
        User user = repository.findById(userDto.getId())
                .orElseThrow(() -> new NotFoundException(String.format("User ID %s is already exist.", userDto)));
        if (userDto.getName() != null && !userDto.getName().isBlank()) {
            user.setName(userDto.getName());
        }
        if (userDto.getEmail() != null && !userDto.getEmail().isBlank()) {
            user.setEmail(userDto.getEmail());
        }

        return Optional.of(userMapper.toUserDto(user));
    }

    @Transactional
    @Override
    public boolean delete(long userId) {
        validate(repository.findById(userId).get());
        Optional<User> user = getUser(userId);
        if (user.isPresent()) {
            repository.deleteById(userId);
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    @Override
    public Optional<User> getUser(long userId) {
        return repository.findById(userId);
    }

    @Transactional
    @Override
    public Optional<UserDto> getUserDto(long userId) {
        Optional<User> user = repository.findById(userId);
        return user.map(userMapper::toUserDto);
    }
}
