package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Класс репозитория - авто.
 * Реализация методов работы с обьектом Car.
 *
 * @author Buslaev
 */
@Repository
@AllArgsConstructor
@Slf4j
public class CarRepository {

    private final CrudRepository crudRepository;

    public Optional<Car> create(Car car) {
        try {
            crudRepository.run(session -> session.save(car));
        } catch (Exception e) {
            log.error("create car", e);
        }
        return Optional.of(car);
    }

    public boolean update(Car car) {
        boolean result = false;
        try {
            crudRepository.run(session -> session.merge(car));
            result = true;
        } catch (Exception e) {
            log.error("update сar", e);
        }
        return result;
    }

    public boolean delete(int id) {
        boolean result = false;
        try {
            crudRepository.run("DELETE Car WHERE id = :id", Map.of("id", id));
            result = true;
        } catch (Exception e) {
            log.error("done car", e);
        }
        return result;
    }

    public Optional<Car> findById(int id) {
        return crudRepository.optional("FROM Car f "
                + "JOIN FETCH f.owners "
                + "WHERE f.id = :id", Car.class, Map.of("id", id));
    }

    public List<Car> findAll() {
        return crudRepository.query("FROM Car as f JOIN FETCH f.engine "
                + "JOIN FETCH f.owners "
                + "order by f.id ASC", Car.class);
    }
}
