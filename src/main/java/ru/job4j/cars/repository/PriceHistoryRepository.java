package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.PriceHistory;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Класс репозитория - истории цены.
 * Реализация методов работы с обьектом PriceHistory.
 *
 * @author Buslaev
 */
@Repository
@AllArgsConstructor
public class PriceHistoryRepository {

    private static final Logger LOG = LoggerFactory.getLogger(PriceHistoryRepository.class.getName());
    private final CrudRepository crudRepository;

    public Optional<PriceHistory> create(PriceHistory priceHistory) {
        try {
            crudRepository.run(session -> session.save(priceHistory));
        } catch (Exception e) {
            LOG.error("create priceHistory", e);
        }
        return Optional.of(priceHistory);
    }

    public boolean delete(int id) {
        boolean result = false;
        try {
            crudRepository.run("DELETE PriceHistory WHERE id = :id", Map.of("id", id));
            result = true;
        } catch (Exception e) {
            LOG.error("done priceHistory", e);
        }
        return result;
    }

    public boolean update(PriceHistory priceHistory) {
        boolean result = false;
        try {
            crudRepository.run(session -> session.merge(priceHistory));
            result = true;
        } catch (Exception e) {
            LOG.error("update priceHistory", e);
        }
        return result;
    }

    public Optional<PriceHistory> findById(int id) {
        return crudRepository.optional("FROM PriceHistory as i WHERE i.id = :id",
                PriceHistory.class, Map.of("id", id));
    }

    public List<PriceHistory> findAll() {
        return crudRepository.query("FROM PriceHistory as i order by i.id ASC", PriceHistory.class);
    }
}
