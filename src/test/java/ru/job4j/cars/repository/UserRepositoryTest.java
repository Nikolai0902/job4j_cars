package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.configuration.HibernateConfiguration;
import ru.job4j.cars.model.User;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class UserRepositoryTest {

    private static SessionFactory sf;
    private final UserRepository userRepository = new UserRepository(sf);

    @BeforeAll
    public static void init() {
        sf = new HibernateConfiguration().sf();
    }

    @AfterEach
    public void delete() {
        for (User user : userRepository.findAllOrderById()) {
            userRepository.delete(user.getId());
        }
    }

    @Test
    public void saveUser() {
        User user = new User();
        user.setLogin("test login");
        user.setPassword("test password");
        userRepository.create(user);
        assertThat(userRepository.findById(user.getId()).orElseThrow(), is(user));
    }

    @Test
    public void saveUserThenUpdate() {
        User user = new User();
        user.setLogin("test login");
        user.setPassword("test password");
        userRepository.create(user);
        user.setLogin("updated login");
        userRepository.update(user);
        assertThat(
                userRepository.findById(user.getId()).orElseThrow().getLogin(),
                is("updated login")
        );
    }

    @Test
    public void findAllOrderByIdUser() {
        User user1 = new User();
        user1.setLogin("test login1");
        user1.setPassword("test password1");
        userRepository.create(user1);

        User user2 = new User();
        user2.setLogin("test login2");
        user2.setPassword("test password2");
        userRepository.create(user2);
        assertThat(userRepository.findAllOrderById().size(), is(2));
    }
}