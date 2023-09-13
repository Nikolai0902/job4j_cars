package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.dto.FileDto;
import ru.job4j.cars.dto.PostDto;
import ru.job4j.cars.model.*;
import ru.job4j.cars.repository.PostRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService {

    public final PostRepository postRepository;
    public final FileService fileService;
    public final CarService carService;
    public final EngineService engineService;
    public final OwnerService ownerService;
    public final PriceHistoryService priceHistoryService;
    public final BodyService bodyService;
    public final CategoryService categoryService;

    public Optional<Post> create(PostDto postDto, List<MultipartFile> files, User user) throws IOException {
        Owner owner = ownerService.findByUser(user).orElseGet(() -> {
            Owner newOwner = new Owner();
            newOwner.setUser(user);
            newOwner.setName(user.getLogin());
            ownerService.create(newOwner);
            return newOwner;
        });
        Car car = new Car();
        car.setName(postDto.getCarName());
        car.setModel(postDto.getCarModel());
        car.setCategory(categoryService.findById(postDto.getCategoryId()).get());
        car.setBody(bodyService.findById(postDto.getBodyId()).get());
        car.setEngine(engineService.findById(postDto.getEngineId()).get());
        car.getOwners().add(owner);
        carService.create(car);
        Post post = new Post();
        post.setDescription(postDto.getDescription());
        post.setCar(car);
        post.setMileage(postDto.getMileage());
        post.setPrice(postDto.getPrice());
        post.setUser(user);
        if (Objects.equals(files.get(0).getOriginalFilename(), "")) {
            files = new ArrayList<>(0);
        }
        List<File> savedFiles = fileService.convertMultipartInFile(files);
        post.setFiles(savedFiles);
        return Optional.of(postRepository.create(post).get());
    }

    public boolean update(Post post, MultipartFile photo) throws IOException {
        var file = fileService.create(new FileDto(photo.getOriginalFilename(), photo.getBytes()));
        post.getFiles().add(file);
        return postRepository.update(post);
    }

    public boolean sold(int id) {
        return postRepository.sold(id);
    }

    public boolean delete(int id) {
        var post = postRepository.findById(id).get();
        fileService.delete(post.getFiles());
        var rsl = postRepository.delete(id);
        return rsl;
    }

    public Optional<Post> findById(int id) {
        return postRepository.findById(id);
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public List<Post> findAllNowDay() {
        return postRepository.findAllNowDay();
    }

    public List<Post> findAllAndPhoto() {
        return postRepository.findAllAndPhoto();
    }

    public List<Post> findAllCarBrand(String brand) {
        return postRepository.findAllCarBrand(brand);
    }
}
