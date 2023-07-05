package ru.job4j.cars.model;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Модель описывает изменение цены в Post.
 * Связан с пост как @OneToMany.
 */
@Data
@Entity
@Table(name = "price_history")
public class PriceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int before;

    private int after;

    private LocalDateTime created = LocalDateTime.now();
}
