package ru.job4j.cars.dto;

import lombok.*;
import ru.job4j.cars.model.User;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    private String carName;
    private String carModel;
    private int bodyId;
    private int categoryId;
    private int engineId;
    private int mileage;
    private int price;
    private String description;
}
