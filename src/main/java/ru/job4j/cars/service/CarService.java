package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.repository.CarRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CarService {

    public final CarRepository carRepository;

    public Optional<Car> create(Car car) {
        return carRepository.create(car);
    }

    public void update(Car car) {
        carRepository.update(car);
    }

    public void delete(int id) {
        carRepository.delete(id);
    }

    public Optional<Car> findById(int id) {
        return carRepository.findById(id);
    }

    public List<Car> findAll() {
        return carRepository.findAll();
    }
}
