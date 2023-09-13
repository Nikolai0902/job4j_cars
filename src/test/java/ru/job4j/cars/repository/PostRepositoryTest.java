package ru.job4j.cars.repository;

import org.checkerframework.checker.units.qual.C;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.configuration.HibernateConfiguration;
import ru.job4j.cars.model.*;

import java.time.LocalDate;
import java.util.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class PostRepositoryTest {

    private static SessionFactory sf;
    private final CrudRepository crudRepository = new CrudRepository(sf);
    private final PostRepository postRepository = new PostRepository(crudRepository);
    private final CarRepository carRepository = new CarRepository(crudRepository);
    private final OwnerRepository ownerRepository = new OwnerRepository(crudRepository);
    private final PriceHistoryRepository historyRepository = new PriceHistoryRepository(crudRepository);
    private final FileRepository fileRepository = new FileRepository(crudRepository);

    @BeforeAll
    public static void init() {
        sf = new HibernateConfiguration().sf();
    }

    @AfterEach
    public void delete() {
        for (Post post : postRepository.findAll()) {
            postRepository.delete(post.getId());
        }
        for (Car car : carRepository.findAll()) {
            carRepository.delete(car.getId());
        }
        for (File file : fileRepository.findAll()) {
            fileRepository.delete(file.getId());
        }
    }

    @Test
    public void savePost() {
        Post post = new Post();
        post.setDescription("test description");
        post.setCreated(LocalDate.now());
        postRepository.create(post);
        assertThat(postRepository.findById(post.getId()).orElseThrow().getDescription(),
                is(post.getDescription()));
    }

    @Test
    public void updatePost() {
        Post post = new Post();
        post.setDescription("test description");
        post.setCreated(LocalDate.now());
        postRepository.create(post);
        post.setDescription("update description");
        postRepository.update(post);
        assertThat(postRepository.findById(post.getId()).orElseThrow().getDescription(),
                is("update description"));
    }

    @Test
    public void findAllPost() {
        Owner owner = new Owner();
        owner.setName("1");
        ownerRepository.create(owner);
        Owner owner2 = new Owner();
        owner2.setName("2");
        ownerRepository.create(owner2);

        Car car1 = new Car();
        car1.setName("car1");
        car1.setOwners(Set.of(owner));
        carRepository.create(car1);
        Car car2 = new Car();
        car2.setName("car2");
        car2.setOwners(Set.of(owner2));
        carRepository.create(car2);

        PriceHistory priceHistory1 =  new PriceHistory();
        historyRepository.create(priceHistory1);
        PriceHistory priceHistory2 =  new PriceHistory();
        historyRepository.create(priceHistory2);

        Post post1 = new Post();
        post1.setDescription("description post1");
        post1.setCreated(LocalDate.now());
        post1.setCar(car1);
        post1.setPriceHistory(List.of(priceHistory1));

        Post post2 = new Post();
        post2.setDescription("description post2");
        post2.setCreated(LocalDate.now());
        post2.setCar(car2);
        post2.setPriceHistory(List.of(priceHistory2));

        postRepository.create(post1);
        postRepository.create(post2);
        assertThat(postRepository.findAll().size(),
                is(2));
    }

    @Test
    public void findAllNowDayPost() {
        Post post1 = new Post();
        Post post2 = new Post();
        File file1 = new File();
        File file2 = new File();
        fileRepository.create(file1);
        fileRepository.create(file2);
        post1.setFiles(List.of(file1));
        post2.setFiles(List.of(file2));
        postRepository.create(post1);
        postRepository.create(post2);
        assertThat(postRepository.findAllNowDay().size(),
                is(2));
    }

    @Test
    public void findAllAndPhotoPost() {
        Post post1 = new Post();
        post1.setDescription("description post1");
        post1.setCreated(LocalDate.now());
        File photo = new File(0, "photo", "/");
        fileRepository.create(photo);
        post1.setFiles(List.of(photo));
        Post post2 = new Post();
        post2.setDescription("description post2");
        post2.setCreated(LocalDate.now());
        postRepository.create(post1);
        postRepository.create(post2);
        assertThat(postRepository.findAllAndPhoto().size(),
                is(1));
    }

    @Test
    public void findAllCarBrand() {
        Car car = new Car();
        car.setName("car");
        carRepository.create(car);
        Post post = new Post();
        post.setDescription("post");
        post.setCreated(LocalDate.now());
        post.setCar(car);
        postRepository.create(post);
        assertThat(postRepository.findAllCarBrand(car.getName()).get(0).getDescription(),
                is("post"));
    }
}