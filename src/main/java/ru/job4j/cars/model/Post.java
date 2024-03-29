package ru.job4j.cars.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Модель обьявления.
 *
 * OneToMany - связь Post и История цены.
 * @JoinColumn -  колонка для вторичного ключа в таблице PRICE_HISTORY.
 * Если это не сделать, то hibernate будет создавать отдельную таблицу, а не использовать нашу схему.
 *
 * @ManyToMany - связь между родительской сущностью Post и User
 * @JoinTable.
 * name - указывает на таблицу participates, где идет связь вторичных ключей.
 * joinColumns - определяет ключ родительского объекта. В данном примере Post.id
 * inverseJoinColumns - определяет ключ объекта, который мы загружаем в родительский объект.
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "of")
@Table(name = "auto_post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String description;
    private LocalDate created = LocalDate.now();
    private boolean sold = false;
    private int price;
    private int mileage;

    @ManyToOne
    @JoinColumn(name = "auto_user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "auto_post_id")
    private List<PriceHistory> priceHistory = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "participates",
            joinColumns = { @JoinColumn(name = "auto_user_id") },
            inverseJoinColumns = { @JoinColumn(name = "auto_post_id") }
    )
    private List<User> participates = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "auto_post_id")
    private List<File> files = new ArrayList<>();

    @Override
    public String toString() {
        return "Post{" + "id=" + id + ", description='" + description
                + '\'' + ", created=" + created + ", sold=" + sold + ", user="
                + user + ", files=" + files + '}';
    }
}
