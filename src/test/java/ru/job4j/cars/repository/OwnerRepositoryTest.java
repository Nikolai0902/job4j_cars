package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.configuration.HibernateConfiguration;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.model.Owner;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class OwnerRepositoryTest {

    private static SessionFactory sf;
    private final CrudRepository crudRepository = new CrudRepository(sf);
    private final OwnerRepository ownerRepository = new OwnerRepository(crudRepository);

    @BeforeAll
    public static void init() {
        sf = new HibernateConfiguration().sf();

    }

    @AfterEach
    public void delete() {
        for (Owner owner : ownerRepository.findAll()) {
            ownerRepository.delete(owner.getId());
        }
    }

    @Test
    public void saveOwner() {
        Owner owner = new Owner();
        owner.setName("owner");
        ownerRepository.create(owner);
        assertThat(ownerRepository.findById(owner.getId()).orElseThrow().getName(), is("owner"));
    }

    @Test
    public void updateOwner() {
        Owner owner = new Owner();
        owner.setName("owner");
        ownerRepository.create(owner);
        owner.setName("update owner");
        ownerRepository.update(owner);
        assertThat(ownerRepository.findById(owner.getId()).orElseThrow().getName(), is("update owner"));
    }

    @Test
    public void findAllOwner() {
        Owner owner1 = new Owner();
        owner1.setName("owner1");
        ownerRepository.create(owner1);
        Owner owner2 = new Owner();
        owner2.setName("owner2");
        ownerRepository.create(owner2);
        assertThat(ownerRepository.findAll().size(), is(2));
    }
}