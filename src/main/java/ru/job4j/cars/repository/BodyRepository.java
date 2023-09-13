package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Body;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Engine;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Класс репозитория - кузов.
 *
 * @author Buslaev
 */
@Repository
@AllArgsConstructor
public class BodyRepository {

    private static final Logger LOG = LoggerFactory.getLogger(BodyRepository.class.getName());
    private final CrudRepository crudRepository;

    public Optional<Body> create(Body body) {
        try {
            crudRepository.run(session -> session.save(body));
        } catch (Exception e) {
            LOG.error("create body", e);
        }
        return Optional.of(body);
    }

    public boolean delete(int id) {
        boolean result = false;
        try {
            crudRepository.run("DELETE Body WHERE id = :id", Map.of("id", id));
            result = true;
        } catch (Exception e) {
            LOG.error("done body", e);
        }
        return result;
    }

    public Optional<Body> findById(int id) {
        return crudRepository.optional("FROM Body f "
                + "WHERE f.id = :id", Body.class, Map.of("id", id));
    }

    public List<Body> findByCategory(int id) {
        return crudRepository.query("FROM Body as b JOIN FETCH b.category as c WHERE c.id = :fId",
                Body.class, Map.of("fId", id));
    }

    public List<Body> findAll() {
        return crudRepository.query("FROM Body", Body.class);
    }
}
