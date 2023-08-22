package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.configuration.HibernateConfiguration;
import ru.job4j.cars.model.*;

import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class FileRepositoryTest {

    private static SessionFactory sf;
    private final CrudRepository crudRepository = new CrudRepository(sf);
    private final FileRepository fileRepository = new FileRepository(crudRepository);

    @BeforeAll
    public static void init() {
        sf = new HibernateConfiguration().sf();
    }

    @AfterEach
    public void delete() {
        for (File file : fileRepository.findAll()) {
            fileRepository.delete(file.getId());
        }
    }

    @Test
    public void saveFile() {
        File file = new File();
        file.setName("file");
        fileRepository.create(file);
        assertThat(fileRepository.findById(file.getId()).orElseThrow(), is(file));
    }

    @Test
    public void updateFile() {
        File file = new File();
        file.setName("file");
        fileRepository.create(file);
        file.setName("updateFile");
        fileRepository.update(file);
        assertThat(fileRepository.findById(file.getId()).orElseThrow().getName(), is("updateFile"));
    }

    @Test
    public void findAllFile() {
        File file1 = new File();
        file1.setName("file1");
        File file2 = new File();
        file2.setName("file2");
        fileRepository.create(file1);
        fileRepository.create(file2);
        assertThat(fileRepository.findAll().size(), is(2));
    }
}