package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
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

    /**
     * Показать объявления за последний день.
     */
    public List<Post> findAllNowDay() {
        return crudRepository.query("FROM Post as f JOIN FETCH f.messengers JOIN FETCH f.photo "
                + "where f.created >= :fDate", Post.class, Map.of("fDate", LocalDate.now().minusDays(1)));
    }

    /**
     * Показать объявления с фото.
     */
    public List<Post> findAllAndPhoto() {
        return crudRepository.query("FROM Post as f JOIN FETCH f.messengers JOIN FETCH f.photo "
                + "where f.photo IS NOT NULL", Post.class);
    }

    /**
     * Показать объявления определенной марки.
     */
    public List<Post> findAllCarBrand(String brand) {
        return crudRepository.query("FROM Post as f JOIN FETCH f.messengers JOIN FETCH f.photo "
                + "where f.car.name = :fName", Post.class, Map.of("fName", brand));
    }
}
