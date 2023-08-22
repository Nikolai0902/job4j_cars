package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.model.File;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Класс репозитория - двигателя.
 * Реализация методов работы с обьектом Engine.
 *
 * @author Buslaev
 */
@Repository
@AllArgsConstructor
public class EngineRepository {

    private static final Logger LOG = LoggerFactory.getLogger(EngineRepository.class.getName());
    private final CrudRepository crudRepository;

    public Engine create(Engine engine) {
        try {
            crudRepository.run(session -> session.persist(engine));
        } catch (Exception e) {
            LOG.error("create engine", e);
        }
        return engine;
    }

    public boolean delete(int id) {
        boolean result = false;
        try {
            crudRepository.run("DELETE Engine WHERE id = :id", Map.of("id", id));
            result = true;
        } catch (Exception e) {
            LOG.error("done Engine", e);
        }
        return result;
    }

    public boolean update(Engine engine) {
        boolean result = false;
        try {
            crudRepository.run(session -> session.merge(engine));
            result = true;
        } catch (Exception e) {
            LOG.error("update Engine", e);
        }
        return result;
    }

    public List<Engine> findAll() {
        return crudRepository.query("from Engine ORDER BY id", Engine.class);
    }

    public List<Engine> findByName(String name) {
        return crudRepository.query("from Engine as i where i.name = :fName",
                Engine.class, Map.of("fName", name));
    }

    public Optional<Engine> findById(int id) {
        return crudRepository.optional("from Engine as i where i.id = :fId",
                Engine.class, Map.of("fId", id));
    }
}
