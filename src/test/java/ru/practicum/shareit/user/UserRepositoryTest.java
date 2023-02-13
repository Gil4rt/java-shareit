package ru.practicum.shareit.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private UserRepository repository;

    private User user = new User();

    @BeforeEach
    void setUp() {
        user.setName("Eugene");
        user.setEmail("jyk@gmail.com");
        em.persist(user);
    }

    @Test
    void findByEmailIsPresent() {
        Optional<User> foundItem = repository.findByEmail("jyk@gmail.com");
        assertThat(foundItem.isPresent());
    }

    @Test
    void findByEmailIsEmpty() {
        Optional<User> foundItem = repository.findByEmail("Eugeneno2@mail.ru");
        assertThat(foundItem.isEmpty());
    }
}
