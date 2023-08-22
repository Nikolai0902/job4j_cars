package ru.job4j.cars;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import ru.job4j.cars.model.User;
import ru.job4j.cars.repository.UserRepository;

/**
 * Проверка методов на языке HQL класса UserReposition -
 * вставка, вывод, изменение, удаление обьекта User.
 *
 * SessionFactory - это объект конфигуратор. Он создается один раз на все приложение.
 * В нем происходит создания пулов, загрузка кешей, проверка моделей.
 * SessionFactory имеет метод openSession, который позволяет записать,
 * удалить и прочитать данные из базы. Этот объект создается быстро.
 */
public class UserUsage {

    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try (SessionFactory sf = new MetadataSources(registry)
                .buildMetadata().buildSessionFactory()) {
            var userRepository = new UserRepository(sf);
            var user = new User();
            user.setLogin("admin");
            user.setPassword("admin");
            userRepository.create(user);
            userRepository.findAllOrderById()
                    .forEach(System.out::println);
            userRepository.findByLikeLogin("e")
                    .forEach(System.out::println);
            userRepository.findById(user.getId())
                    .ifPresent(System.out::println);
            userRepository.findByLogin("admin")
                    .ifPresent(System.out::println);
            user.setPassword("password");
            userRepository.update(user);
            userRepository.findById(user.getId())
                    .ifPresent(System.out::println);
            userRepository.delete(user.getId());
            userRepository.findAllOrderById()
                    .forEach(System.out::println);
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
