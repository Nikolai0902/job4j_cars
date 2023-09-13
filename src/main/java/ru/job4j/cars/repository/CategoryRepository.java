package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Body;
import ru.job4j.cars.model.Category;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Класс репозитория - категория.
 *
 * @author Buslaev
 */
@Repository
@AllArgsConstructor
public class CategoryRepository {

    private static final Logger LOG = LoggerFactory.getLogger(CategoryRepository.class.getName());
    private final CrudRepository crudRepository;

    public Optional<Category> create(Category category) {
        try {
            crudRepository.run(session -> session.save(category));
        } catch (Exception e) {
            LOG.error("create сategory", e);
        }
        return Optional.of(category);
    }

    public boolean delete(int id) {
        boolean result = false;
        try {
            crudRepository.run("DELETE Category WHERE id = :id", Map.of("id", id));
            result = true;
        } catch (Exception e) {
            LOG.error("done category", e);
        }
        return result;
    }

    public Optional<Category> findById(int id) {
        return crudRepository.optional("FROM Category f "
                + "WHERE f.id = :id", Category.class, Map.of("id", id));
    }

    public List<Category> findAll() {
        return crudRepository.query("FROM Category", Category.class);
    }
}
