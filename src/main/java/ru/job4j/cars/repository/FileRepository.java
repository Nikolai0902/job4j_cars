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
 * Класс репозитория - фото.
 * Реализация методов работы с обьектом File.
 *
 * @author Buslaev
 */
@Repository
@AllArgsConstructor
public class FileRepository {

    private static final Logger LOG = LoggerFactory.getLogger(FileRepository.class.getName());
    private final CrudRepository crudRepository;

    public File create(File file) {
        try {
            crudRepository.run(session -> session.save(file));
        } catch (Exception e) {
            LOG.error("create car", e);
        }
        return file;
    }

    public boolean update(File file) {
        boolean result = false;
        try {
            crudRepository.run(session -> session.update(file));
            result = true;
        } catch (Exception e) {
            LOG.error("update file", e);
        }
        return result;
    }

    public boolean delete(int id) {
        boolean result = false;
        try {
            crudRepository.run("DELETE File WHERE id = :id", Map.of("id", id));
            result = true;
        } catch (Exception e) {
            LOG.error("done file", e);
        }
        return result;
    }

    public Optional<File> findById(int id) {
        return crudRepository.optional("FROM File WHERE id = :fId",
                File.class, Map.of("fId", id));
    }

    public List<File> findAll() {
        return crudRepository.query("FROM File ORDER BY id", File.class);
    }
}
