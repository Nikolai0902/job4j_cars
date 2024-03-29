package ru.job4j.cars.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Модель описывает изменение цены в Post.
 * Связан с пост как @OneToMany.
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "of")
@Table(name = "price_history")
public class PriceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int before;

    private int after;

    private LocalDateTime created = LocalDateTime.now();
}
