package ru.gordanov.task_rest_313.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gordanov.task_rest_313.service.RoleService;
import ru.gordanov.task_rest_313.service.UserService;

import java.security.Principal;

@Controller
public class MyController {

    private final UserService userService;
    private final RoleService roleService;

    public MyController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/")
    public String index() {
        return "login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/user")
    public String showUser(Model model) {
        return "user";
    }

    @GetMapping("/admin")
    public String adminPage(Model model) {
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "index";
    }
}