package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Owner;

/**
 * Класс репозитория - владельца.
 * Реализация методов работы с обьектом Engine.
 *
 * @author Buslaev
 */
@Repository
@AllArgsConstructor
public class OwnerRepository {

    private static final Logger LOG = LoggerFactory.getLogger(CarRepository.class.getName());
    private final CrudRepository crudRepository;

    /**
     * Добавить владельца.
     *
     * @param owner
     * @return car
     */
    public Owner create(Owner owner) {
        try {
            crudRepository.run(session -> session.persist(owner));
        } catch (Exception e) {
            LOG.error("create owner", e);
        }
        return owner;
    }
}
