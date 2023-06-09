package ru.job4j.cars.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Модель обьявления.
 *
 * OneToMany - связь Post и История цены.
 * @JoinColumn -  колонка для вторичного ключа в таблице PRICE_HISTORY.
 * Если это не сделать, то hibernate будет создавать отдельную таблицу, а не использовать нашу схему.
 *
 */
@Data
@Entity
@Table(name = "auto_post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String description;
    private LocalDate created;
    private int price;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "auto_post_id")
    private List<PriceHistory> messengers = new ArrayList<>();
}
