package com.javarush.zdanovskih.controller.http;

import com.javarush.zdanovskih.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Slf4j
@Controller

public class LoginController {

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginDto", new User());
        return "login";
    }

    @GetMapping("/welcome")
    public String showWelcomePage(Model model) {
        return "welcome";
    }

}
