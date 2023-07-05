package ru.job4j.cars.model;

import lombok.Data;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 *  Модель пользвателя.
 *
 *  @ManyToMany - связь между родительской сущностью User и Post
 *  @JoinTable.
 *  name - указывает на таблицу participates, где идет связь вторичных ключей.
 *  joinColumns - определяет ключ родительского объекта. В данном примере User.id
 *  inverseJoinColumns - определяет ключ объекта, который мы загружаем в родительский объект.
 */
@Data
@Entity
@Table(name = "auto_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String login;
    private String password;

    @ManyToMany
    @JoinTable(
            name = "participates",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "post_id") }
    )
    private List<Post> participates = new ArrayList<>();
}
