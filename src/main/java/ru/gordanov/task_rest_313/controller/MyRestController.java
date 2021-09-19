package ru.gordanov.task_rest_313.controller;

import org.springframework.web.bind.annotation.*;
import ru.gordanov.task_rest_313.entity.Role;
import ru.gordanov.task_rest_313.entity.User;
import ru.gordanov.task_rest_313.service.RoleService;
import ru.gordanov.task_rest_313.service.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:63342/")
public class MyRestController {

    private final UserService userService;
    private final RoleService roleService;

    public MyRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/roles")
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }
    @GetMapping("/user")
    public User getPrincipal(Principal principal) {
        return userService.getUserByUsername(principal.getName());
    }

    @PostMapping("/users")
    public User saveUser(@RequestBody User user) {
        userService.save(user);
        return user;
    }

    @PatchMapping("/users")
    public User updateUser(@RequestBody User user) {
        userService.save(user);
        return user;
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable long id) {
        userService.delete(id);
        return "User with Id: " + id + " was deleted";
    }
}
