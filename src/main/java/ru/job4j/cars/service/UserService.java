package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.User;
import ru.job4j.cars.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> create(User user) {
        return userRepository.create(user);
    }

    public void update(User user) {
        userRepository.update(user);
    }

    public void delete(int id) {
        userRepository.delete(id);
    }

    public List<User> findAllOrderById() {
        return userRepository.findAllOrderById();
    }

    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }

    public List<User> findByLikeLogin(String key) {
        return userRepository.findByLikeLogin(key);
    }

    public Optional<User> findByLogin(String key) {
        return userRepository.findByLogin(key);
    }
}
