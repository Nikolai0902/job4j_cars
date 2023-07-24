package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Engine;

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

    private final CrudRepository crudRepository;

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
