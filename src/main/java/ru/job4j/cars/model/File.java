package ru.job4j.cars.model;

import lombok.Data;

import javax.persistence.*;

/**
 * Модель файла/фото.
 */
@Data
@Entity
@Table(name = "files")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String path;
}
