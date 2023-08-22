package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Post;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Класс репозитория - обьявления.
 * Реализация методов работы с обьектом Post.
 *
 * @author Buslaev
 */
@Repository
@AllArgsConstructor
public class PostRepository {

    private static final Logger LOG = LoggerFactory.getLogger(PostRepository.class.getName());
    private final CrudRepository crudRepository;

    public Post create(Post post) {
        try {
            crudRepository.run(session -> session.persist(post));
        } catch (Exception e) {
            LOG.error("create post", e);
        }
        return post;
    }

    public boolean delete(int id) {
        boolean result = false;
        try {
            crudRepository.run("DELETE Post WHERE id = :id", Map.of("id", id));
            result = true;
        } catch (Exception e) {
            LOG.error("delete post", e);
        }
        return result;
    }

    public boolean update(Post post) {
        boolean result = false;
        try {
            crudRepository.run(session -> session.merge(post));
            result = true;
        } catch (Exception e) {
            LOG.error("update post", e);
        }
        return result;
    }

    public Optional<Post> findById(int id) {
        return crudRepository.optional(
                "FROM Post WHERE id = :id",
                Post.class,
                Map.of("id", id)
        );
    }

    public List<Post> findAll() {
        return crudRepository.query("FROM Post", Post.class);
    }

    /**
     * Показать объявления за последний день.
     */
    public List<Post> findAllNowDay() {
        return crudRepository.query("FROM Post as f "
                + "where f.created >= :fDate", Post.class, Map.of("fDate", LocalDate.now().minusDays(1)));
    }

    /**
     * Показать объявления с фото.
     */
    public List<Post> findAllAndPhoto() {
        return crudRepository.query("FROM Post as p "
                + "where p.photo.size IS NOT NULL", Post.class);
    }

    /**
     * Показать объявления определенной марки.
     */
    public List<Post> findAllCarBrand(String brand) {
        return crudRepository.query("FROM Post as f "
                + "JOIN FETCH f.car "
                + "where f.car.name = :fName", Post.class, Map.of("fName", brand));
    }
}
