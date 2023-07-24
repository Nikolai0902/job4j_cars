package ru.job4j.cars.model;

import lombok.Data;

import javax.persistence.*;

/**
 * Модель двигателя.
 */
@Data
@Entity
@Table(name = "engine")
public class Engine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
}
