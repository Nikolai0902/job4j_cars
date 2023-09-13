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

    public Optional<Post> create(Post post) {
        try {
            crudRepository.run(session -> session.save(post));
        } catch (Exception e) {
            LOG.error("create post", e);
        }
        return Optional.of(post);
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
            crudRepository.run(session -> session.update(post));
            result = true;
        } catch (Exception e) {
            LOG.error("update post", e);
        }
        return result;
    }

    public boolean sold(int id) {
        boolean result = false;
        try {
            crudRepository.run("UPDATE Post SET sold = :fSold where id = :pId", Map.of("fSold", true, "pId", id));
            result = true;
        } catch (Exception e) {
            LOG.error("update sold", e);
        }
        return result;
    }

    public Optional<Post> findById(int id) {
        return crudRepository.optional(
                "SELECT DISTINCT p FROM Post as p LEFT JOIN FETCH p.car  LEFT JOIN FETCH p.files WHERE p.id = :fId",
                Post.class,
                Map.of("fId", id)
        );
    }

    public List<Post> findAll() {
        return crudRepository.query("SELECT DISTINCT p FROM Post as p LEFT JOIN FETCH p.car LEFT JOIN FETCH p.files ORDER BY p.id", Post.class);
    }

    /**
     * Показать объявления за последний день.
     */
    public List<Post> findAllNowDay() {
        var dateNow = LocalDate.now();
        var date = LocalDate.now().minusDays(1);
        return crudRepository.query("SELECT DISTINCT p FROM Post AS p "
                + " JOIN FETCH p.files WHERE p.created BETWEEN :fDate AND :fDateNow",
                Post.class, Map.of("fDate", date, "fDateNow", dateNow));
    }

    /**
     * Показать объявления с фото.
     */
    public List<Post> findAllAndPhoto() {
        return crudRepository.query("SELECT DISTINCT p FROM Post as p "
                + "JOIN FETCH p.files f where f.size is not null", Post.class);
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
