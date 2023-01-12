package ru.practicum.shareit.user.repository;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class UserRepositoryImpl implements UserRepository {
    private static long userId;
    private Map<Long, User> users;
    private Map<String, Long> emails;

    public UserRepositoryImpl() {
        users = new HashMap<>();
        emails = new HashMap<>();
    }

    private long generateId() {
        return ++userId;
    }

    @Override
    public Collection<User> findAll() {
        return users.values();
    }

    @Override
    public User save(User user) {
        user.setId(generateId());
        users.put(user.getId(), user);
        emails.put(user.getEmail(), user.getId());
        return user;
    }

    @Override
    public Optional<User> update(User user) {
        if (users.get(user.getId()) != null) {
            if (user.getName() != null) {
                users.get(user.getId()).setName(user.getName());
            }
            if (user.getEmail() != null) {
                emails.remove(users.get(user.getId()).getEmail());
                users.get(user.getId()).setEmail(user.getEmail());
                emails.put(user.getEmail(), user.getId());
            }
            return Optional.of(users.get(user.getId()));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean delete(long id) {
        emails.remove(users.get(id).getEmail());
        return users.remove(id) != null;
    }

    @Override
    public Optional<User> get(long id) {
        if (users.get(id) != null) {
            return Optional.of(users.get(id));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean checkDuplicateEmail(User user) {
        return user.getEmail() != null
                && emails.get(user.getEmail()) != null
                && emails.get(user.getEmail()) != user.getId();
    }
}
