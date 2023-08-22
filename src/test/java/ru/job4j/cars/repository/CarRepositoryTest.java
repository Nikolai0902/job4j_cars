package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.configuration.HibernateConfiguration;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.model.Owner;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class CarRepositoryTest {

    private static SessionFactory sf;
    private final CrudRepository crudRepository = new CrudRepository(sf);
    private final EngineRepository engineRepository = new EngineRepository(crudRepository);
    private final CarRepository carRepository = new CarRepository(crudRepository);

    @BeforeAll
    public static void init() {
        sf = new HibernateConfiguration().sf();
    }

    @AfterEach
    public void delete() {
        for (Car car : carRepository.findAll()) {
            carRepository.delete(car.getId());
        }
    }

    @Test
    public void saveCar() {
        Engine engine = new Engine();
        engine.setName("test engine");
        engineRepository.create(engine);
        Car car = new Car();
        car.setName("test car");
        car.setEngine(engine);
        car.setOwners(Set.of(new Owner()));
        carRepository.create(car);
        assertThat(carRepository.findById(car.getId()).orElseThrow(), is(car));
    }

    @Test
    public void updateCar() {
        Engine engine = new Engine();
        engine.setName("engine");
        Engine engineUpdate = new Engine();
        engineUpdate.setName("engineUpdate");
        engineRepository.create(engine);
        engineRepository.create(engineUpdate);
        Car car = new Car();
        car.setName("car");
        car.setEngine(engine);
        car.setOwners(Set.of(new Owner()));
        carRepository.create(car);
        car.setName("carUpdate");
        car.setEngine(engineUpdate);
        carRepository.update(car);
        assertThat(carRepository.findById(car.getId()).get().getName(), is("carUpdate"));
    }

    @Test
    public void findAllCar() {
        Engine engine = new Engine();
        engine.setName("engine");
        engineRepository.create(engine);
        Car car1 = new Car();
        car1.setName("car1");
        car1.setEngine(engine);
        car1.setOwners(Set.of(new Owner()));
        carRepository.create(car1);

        Car car2 = new Car();
        car2.setName("car2");
        car2.setEngine(engine);
        car2.setOwners(Set.of(new Owner()));
        carRepository.create(car2);
        assertThat(carRepository.findAll().size(), is(2));
    }
}
