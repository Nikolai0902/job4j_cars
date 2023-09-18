package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.cars.model.User;
import ru.job4j.cars.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    /**
     * форма регистрации.
     * @return форма регистрации.
     */
    @GetMapping("/register")
    public String getRegistrationPage(Model model) {
        return "users/register";
    }

    /**
     * Добавление пользователя.
     * Обеспечение уникальности будет нести ответственность СУБД.
     * @param model
     * @param user собранная модель данных пользователя.
     * @return если пользователь создан то redirect:/index или предупреждение о существующем пользователе.
     */
    @PostMapping("/register")
    public String register(Model model, @ModelAttribute User user) {
        var savedUser = userService.create(user);
        if (savedUser.isEmpty()) {
            model.addAttribute("error", "Пользователь с такой почтой уже существует");
            return "users/register";
        }
        return "redirect:/index";
    }
}
