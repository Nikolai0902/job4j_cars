package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
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
public class CarRepository {

    private static final Logger LOG = LoggerFactory.getLogger(CarRepository.class.getName());
    private final CrudRepository crudRepository;

    /**
     * Добавить авто.
     *
     * @param car задачу.
     * @return car.
     */
    public Car create(Car car) {
        try {
            crudRepository.run(session -> session.persist(car));
        } catch (Exception e) {
            LOG.error("create car", e);
        }
        return car;
    }

    /**
     * Удалить авто.
     *
     * @param id ID
     */
    public boolean delete(int id) {
        boolean result = false;
        try {
            crudRepository.run("DELETE Car WHERE id = :id", Map.of("id", id));
            result = true;
        } catch (Exception e) {
            LOG.error("done car", e);
        }
        return result;
    }

    /**
     * Найти авто по ID
     *
     * @param id ID
     * @return задача.
     */
    public Optional<Car> findById(int id) {
        return crudRepository.optional("FROM Car f JOIN FETCH f.engine "
                + "JOIN FETCH f.owners "
                + "WHERE f.id = :id", Car.class, Map.of("id", id));
    }

    /**
     * Список авто.
     *
     * @return список задач.
     */
    public List<Car> findAll() {
        return crudRepository.query("FROM Car f JOIN FETCH f.engine "
                + "JOIN FETCH f.owners "
                + "order by f.id ASC", Car.class);
    }
}
