package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.model.File;
import ru.job4j.cars.model.Owner;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Класс репозитория - владельца.
 * Реализация методов работы с обьектом Engine.
 *
 * @author Buslaev
 */
@Repository
@AllArgsConstructor
public class OwnerRepository {

    private static final Logger LOG = LoggerFactory.getLogger(OwnerRepository.class.getName());
    private final CrudRepository crudRepository;

    public Optional<Owner> create(Owner owner) {
        try {
            crudRepository.run(session -> session.save(owner));
        } catch (Exception e) {
            LOG.error("create Owner", e);
        }
        return Optional.of(owner);
    }

    public boolean delete(int id) {
        boolean result = false;
        try {
            crudRepository.run("DELETE Owner WHERE id = :id", Map.of("id", id));
            result = true;
        } catch (Exception e) {
            LOG.error("done Owner", e);
        }
        return result;
    }

    public boolean update(Owner owner) {
        boolean result = false;
        try {
            crudRepository.run(session -> session.merge(owner));
            result = true;
        } catch (Exception e) {
            LOG.error("update owner", e);
        }
        return result;
    }

    public Optional<Owner> findById(int id) {
        return crudRepository.optional("from Owner as i where i.id = :fId",
                Owner.class, Map.of("fId", id));
    }

    public Optional<Owner> findByUser(User user) {
        return crudRepository.optional("from Owner as i JOIN FETCH i.user as o where o = :FUser",
                Owner.class, Map.of("FUser", user));
    }

    public List<Owner> findAll() {
        return crudRepository.query("from Owner ORDER BY id", Owner.class);
    }
}
