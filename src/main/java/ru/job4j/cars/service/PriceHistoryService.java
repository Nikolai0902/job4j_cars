package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.PriceHistory;
import ru.job4j.cars.repository.CarRepository;
import ru.job4j.cars.repository.PriceHistoryRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PriceHistoryService {

    public final PriceHistoryRepository priceHistoryRepository;

    public Optional<PriceHistory> create(PriceHistory priceHistory) {
        return priceHistoryRepository.create(priceHistory);
    }

    public void update(PriceHistory priceHistory) {
        priceHistoryRepository.update(priceHistory);
    }

    public void delete(int id) {
        priceHistoryRepository.delete(id);
    }

    public Optional<PriceHistory> findById(int id) {
        return priceHistoryRepository.findById(id);
    }

    public List<PriceHistory> findAll() {
        return priceHistoryRepository.findAll();
    }
}
