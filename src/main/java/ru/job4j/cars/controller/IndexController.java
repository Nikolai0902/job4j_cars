package ru.job4j.cars.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    /**
     * Возвращает начальную страницу.
     *
     * @return возвращает предствление - Выбор списка машин.
     */
    @GetMapping({"/", "/index"})
    public String getIndex() {
        return "index";
    }
}
