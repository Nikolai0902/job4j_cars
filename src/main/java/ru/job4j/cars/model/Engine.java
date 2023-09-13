package ru.job4j.cars.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Модель двигателя.
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "of")
@Table(name = "engine")
public class Engine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
}
