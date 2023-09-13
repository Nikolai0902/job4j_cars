package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Category;
import ru.job4j.cars.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryService {

    public final CategoryRepository categoryRepository;

    public Optional<Category> create(Category category) {
        return categoryRepository.create(category);
    }

    public void delete(int id) {
        categoryRepository.delete(id);
    }

    public Optional<Category> findById(int id) {
        return categoryRepository.findById(id);
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
