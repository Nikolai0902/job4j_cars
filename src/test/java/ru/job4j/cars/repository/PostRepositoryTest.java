package ru.job4j.cars.repository;

import org.checkerframework.checker.units.qual.C;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.configuration.HibernateConfiguration;
import ru.job4j.cars.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static java.time.LocalDateTime.now;
import static java.util.Optional.empty;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PostRepositoryTest {

    private static SessionFactory sf;
    private final CrudRepository crudRepository = new CrudRepository(sf);
    private final PostRepository postRepository = new PostRepository(crudRepository);
    private final CarRepository carRepository = new CarRepository(crudRepository);
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
        Post post1 = new Post();
        post1.setDescription("description post1");
        post1.setCreated(LocalDate.now());
        Post post2 = new Post();
        post2.setDescription("description post2");
        post2.setCreated(LocalDate.now());
        postRepository.create(post1);
        postRepository.create(post2);
        assertThat(postRepository.findAll().size(),
                is(2));
    }

    @Test
    public void findAllNowDayPost() {
        Post post1 = new Post();
        post1.setDescription("description post1");
        post1.setCreated(LocalDate.now());
        Post post2 = new Post();
        post2.setDescription("description post2");
        post2.setCreated(LocalDate.now().minusDays(2));
        postRepository.create(post1);
        postRepository.create(post2);
        assertThat(postRepository.findAllNowDay().size(),
                is(1));
    }

    @Test
    public void findAllAndPhotoPost() {
        Post post1 = new Post();
        post1.setDescription("description post1");
        post1.setCreated(LocalDate.now());
        File photo = new File(0, "photo", "/");
        fileRepository.create(photo);
        post1.setPhoto(Set.of(photo));
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