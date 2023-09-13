package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Body;
import ru.job4j.cars.repository.BodyRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BodyService {

    public final BodyRepository bodyRepository;

    public Optional<Body> create(Body body) {
        return bodyRepository.create(body);
    }

    public void delete(int id) {
        bodyRepository.delete(id);
    }

    public Optional<Body> findById(int id) {
        return bodyRepository.findById(id);
    }

    public List<Body> findByCategory(int id) {
        return bodyRepository.findByCategory(id);
    }

    public List<Body> findAll() {
        return bodyRepository.findAll();
    }
}
