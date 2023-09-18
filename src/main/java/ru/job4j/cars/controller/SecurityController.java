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
@RequestMapping("/security")
public class SecurityController {

    private final UserService userService;

    /**
     * форма входа пользователя в систему.
     * @return форма входа.
     */
    @GetMapping("/login")
    public String getLoginPage() {
        return "users/login";
    }

    /**
     * Осуществление входа пользователя.
     * @param user собранная модель данных пользователя.
     * @param model
     * @param request оъект HttpServletRequest для связи пользователя с текущей сессией.
     * Объект HttpSession можно получить через HttpServletRequest.
     * В нем можно хранить информацию о текущем пользователе, которую можно получить на другой странице.
     * Данные сессии привязываются к клиенту и доступны во всем приложении.
     * @return представление ошибки входа либо кинотеку.
     */
    @PostMapping("/login")
    public String login(@ModelAttribute User user, Model model, HttpServletRequest request) {
        var userOptional = userService.findByLogin(user.getLogin());
        if (userOptional.isEmpty()) {
            model.addAttribute("error", "Почта или пароль введены неверно");
            return "users/login";
        }
        var session = request.getSession();
        session.setAttribute("user", userOptional.get());
        return "redirect:/index";
    }

    /**
     * Выход из системы.
     * @param session
     * @return старницу входа в систему.
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/security/login";
    }
}
