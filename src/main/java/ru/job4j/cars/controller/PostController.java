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
public class PostController {

    private static final Logger LOG = LoggerFactory.getLogger(PostController.class.getName());

    private final PostService postService;

    private final FileService fileService;
    private final CategoryService categoryService;
    private final BodyService bodyService;
    private final EngineService engineService;

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

    /**
     * Возвращает страницу добавления нового обьявления.
     *
     * @param model
     * @return возвращает предствление - Страница добавления.
     */
    @GetMapping("/createPost")
    public String createPost(Model model) {
        model.addAttribute("engines", engineService.findAll());
        model.addAttribute("categoryes", categoryService.findAll());
        model.addAttribute("bodyes", bodyService.findAll());
        return "post/createPost";
    }

    /**
     * Оборабатывается добавления нового обьявления.
     * При условии что все поля заполнены и категория авто соответствует кузову автомобиля.
     *
     * @param postDto собранная сущность параметров обьявления.
     * @param files добавленное фотою
     * @param model
     * @param user текущий пользователь.
     * @return возвращает предствление - Объявление добавлено.
     * @throws IOException
     */
    @PostMapping("/createPost")
    public String save(@ModelAttribute PostDto postDto,
                       @RequestPart("files") List<MultipartFile> files,
                       Model model,
                       @SessionAttribute User user) throws IOException {

        var bodyList = bodyService.findByCategory(postDto.getCategoryId());
        if (!bodyList.contains(bodyService.findById(postDto.getBodyId()).get())) {
            model.addAttribute("error", "Неверно выбрана категория авто");
            return "post/createPost";
        }
        var postOptional = postService.create(postDto, files, user);
        if (postOptional.isEmpty()) {
            model.addAttribute("message", "Ошибка при добавлении объявления");
            LOG.error("error add post");
            return "errors/409";
        }
        model.addAttribute("message", "Объявление добавлено");
        return "post/success";
    }


    /**
     * Возвращает страницу с описанием конкретного авто.
     *
     * @param model
     * @param id обьявления.
     * @return возвращает описанием конкретного авто по id.
     */
    @GetMapping("/one/{id}")
    public String one(Model model, @PathVariable("id") int id) {
        var postOptional = postService.findById(id);
        if (postOptional.isEmpty()) {
            model.addAttribute("message", "Объявление не найдено");
            LOG.error(String.format("post id %d not found", id));
            return "errors/404";
        }
        model.addAttribute("post", postOptional.get());
        return "post/one";
    }

    /**
     * Возвращает страницу с удалением обьявления.
     *
     * @param model
     * @param id обьявления.
     * @return возвращает предствление - Объявление удалено успешно.
     */
    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable int id) {
        var isDeleted = postService.delete(id);
        if (!isDeleted) {
            model.addAttribute("message", "Ошибка. Объявление не удалено");
            return "errors/404";
        }
        model.addAttribute("message", "Объявление удалено успешно");
        return "post/success";
    }

    /**
     * Возвращает страницу с изменением состояния автомобиоя.
     *
     * @param model
     * @param id обьявления.
     * @return возвращает предствление - Автомобиль продан.
     */
    @GetMapping("/sold/{id}")
    public String sold(Model model, @PathVariable int id) {
        var isSold = postService.sold(id);
        if (!isSold) {
            model.addAttribute("message", "Состояние не изменилось");
            return "errors/404";
        }
        model.addAttribute("message", "Автомобиль продан");
        return "post/success";
    }

    /**
     * Возвращает страницу для изменения обьявления.
     *
     * @param model
     * @param id обьявления.
     * @return возвращает предствление - Изменение обьявления.
     */
    @GetMapping("/update/{id}")
    public String formUpdate(Model model, @PathVariable("id") int id) {
        var postOptional = postService.findById(id);
        if (postOptional.isEmpty()) {
            model.addAttribute("message", "Объявление не найдено");
            LOG.error(String.format("post id %d not found", id));
            return "errors/404";
        }
        model.addAttribute("post", postOptional.get());
        return "post/update";
    }

    /**
     * Оборабатывается редактирование обьявления.
     *
     * @param post собранная сущность обьвления.
     * @param photo добавленное фото.
     * @param model
     * @return возвращает предствление - Фото добавлено.
     * @throws IOException
     */
    @PostMapping("/update")
    public String update(@ModelAttribute Post post,
                         @RequestPart("photo") MultipartFile photo,
                         Model model) throws IOException {
        var postUpdate = postService.findById(post.getId());
        if (postUpdate.isEmpty()) {
            model.addAttribute("message", "Объявление не найдено");
            LOG.error(String.format("post id %d not found", post.getId()));
            return "errors/404";
        }
        if (!postService.update(postUpdate.get(), photo)) {
            model.addAttribute("message", "При обновлении данных произошла ошибка");
            return "errors/404";
        }
        model.addAttribute("message", "Фото добавлено");
        return "post/success";
    }
}
