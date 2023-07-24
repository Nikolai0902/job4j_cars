package ru.job4j.cars.model;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Модель владельца.
 */
@Data
@Entity
@Table(name = "owners")
public class Owner {

    private int id;

    private String name;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
