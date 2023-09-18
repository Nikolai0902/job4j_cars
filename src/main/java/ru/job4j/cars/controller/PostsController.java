package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.dto.FileDto;
import ru.job4j.cars.dto.PostDto;
import ru.job4j.cars.model.*;
import ru.job4j.cars.service.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("/posts")
public class PostsController {

    private final PostService postService;

    /**
     * Возвращает страницу со списком всех обьявлений.
     *
     * @param model
     * @return возвращает предствление - Основная страница. таблица со всеми объявлениям машин на продажу.
     */
    @GetMapping({"/allPost"})
    public String getAllPosts(Model model) {
        model.addAttribute("posts", postService.findAll());
        return "post/allPost";
    }

    /**
     * Возвращает страницу со списком всех обьявлений за последний день.
     *
     * @param model
     * @return возвращает предствление - таблица со всеми объявлениям машин на продажу.
     */
    @GetMapping({"/nowDay"})
    public String getAllPostsNowDay(Model model) {
        var list = postService.findAllNowDay();
        if (list.isEmpty()) {
            model.addAttribute("message", "Объявлений за сегодня не найдено");
            return "errors/404";
        }
        model.addAttribute("posts", list);
        return "post/allPost";
    }

    /**
     * Возвращает страницу со списком всех обьявлений c фото.
     *
     * @param model
     * @return возвращает предствление - таблица со всеми объявлениям машин на продажу.
     */
    @GetMapping({"/postAndPhoto"})
    public String findAllAndPhoto(Model model) {
        var list = postService.findAllAndPhoto();
        if (list.isEmpty()) {
            model.addAttribute("message", "Объявлений с фото не найдено");
            return "errors/404";
        }
        model.addAttribute("posts", list);
        return "post/allPost";
    }
}
