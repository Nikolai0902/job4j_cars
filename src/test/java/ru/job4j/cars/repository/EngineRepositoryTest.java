package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.configuration.HibernateConfiguration;
import ru.job4j.cars.model.Engine;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class EngineRepositoryTest {

    private static SessionFactory sf;
    private final CrudRepository crudRepository = new CrudRepository(sf);
    private final EngineRepository engineRepository = new EngineRepository(crudRepository);

    @BeforeAll
    public static void init() {
        sf = new HibernateConfiguration().sf();

    }

    @AfterEach
    public void delete() {
        for (Engine engine : engineRepository.findAll()) {
            engineRepository.delete(engine.getId());
        }
    }

    @Test
    public void saveEngine() {
        Engine engine = new Engine();
        engine.setName("engine");
        engineRepository.create(engine);
        assertThat(engineRepository.findById(engine.getId()).orElseThrow().getName(), is("engine"));
    }

    @Test
    public void updateEngine() {
        Engine engine = new Engine();
        engine.setName("engine");
        engineRepository.create(engine);
        engine.setName("update engine");
        engineRepository.update(engine);
        assertThat(engineRepository.findById(engine.getId()).orElseThrow().getName(), is("update engine"));
    }

    @Test
    public void saveEngineFindByName() {
        Engine engine = new Engine();
        engine.setName("engine");
        engineRepository.create(engine);
        assertThat(engineRepository.findByName("engine").get(0), is(engine));
    }

    @Test
    public void findAllEngine() {
        Engine engine1 = new Engine();
        engine1.setName("engine1");
        engineRepository.create(engine1);
        Engine engine2 = new Engine();
        engine2.setName("engine2");
        engineRepository.create(engine2);
        assertThat(engineRepository.findAll().size(), is(2));
    }
}