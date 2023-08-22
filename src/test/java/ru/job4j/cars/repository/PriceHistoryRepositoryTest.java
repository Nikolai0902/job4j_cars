package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.configuration.HibernateConfiguration;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.File;
import ru.job4j.cars.model.PriceHistory;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class PriceHistoryRepositoryTest {

    private static SessionFactory sf;
    private final CrudRepository crudRepository = new CrudRepository(sf);
    private final PriceHistoryRepository priceHistoryRepository = new PriceHistoryRepository(crudRepository);

    @BeforeAll
    public static void init() {
        sf = new HibernateConfiguration().sf();
    }

    @AfterEach
    public void delete() {
        for (PriceHistory priceHistory : priceHistoryRepository.findAll()) {
            priceHistoryRepository.delete(priceHistory.getId());
        }
    }

    @Test
    public void saveCar() {
        PriceHistory price = new PriceHistory();
        price.setAfter(1);
        priceHistoryRepository.create(price);
        assertThat(priceHistoryRepository.findById(price.getId()).orElseThrow().getAfter(), is(1));
    }

    @Test
    public void updateCar() {
        PriceHistory price = new PriceHistory();
        price.setAfter(1);
        priceHistoryRepository.create(price);
        price.setAfter(2);
        priceHistoryRepository.update(price);
        assertThat(priceHistoryRepository.findById(price.getId()).orElseThrow().getAfter(), is(2));
    }

    @Test
    public void findAllCar() {
        PriceHistory price1 = new PriceHistory();
        price1.setAfter(1);
        PriceHistory price2 = new PriceHistory();
        price2.setAfter(2);
        priceHistoryRepository.create(price1);
        priceHistoryRepository.create(price2);
        assertThat(priceHistoryRepository.findAll().size(), is(2));
    }
}