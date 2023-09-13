package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.File;
import ru.job4j.cars.model.Owner;
import ru.job4j.cars.model.User;
import ru.job4j.cars.repository.OwnerRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OwnerService {

    public final OwnerRepository ownerRepository;

    public Optional<Owner> create(Owner owner) {
        return ownerRepository.create(owner);
    }

    public void update(Owner owner) {
        ownerRepository.update(owner);
    }

    public void delete(int id) {
        ownerRepository.delete(id);
    }

    public Optional<Owner> findById(int id) {
        return ownerRepository.findById(id);
    }

    public Optional<Owner> findByUser(User user) {
        return ownerRepository.findByUser(user);
    }

    public List<Owner> findAll() {
        return ownerRepository.findAll();
    }
}
