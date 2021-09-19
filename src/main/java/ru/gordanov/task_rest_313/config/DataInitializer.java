package ru.gordanov.task_rest_313.config;

import org.springframework.stereotype.Component;
import ru.gordanov.task_rest_313.entity.Role;
import ru.gordanov.task_rest_313.entity.User;
import ru.gordanov.task_rest_313.service.RoleService;
import ru.gordanov.task_rest_313.service.UserService;

import javax.annotation.PostConstruct;
import java.util.Set;

@Component
public class DataInitializer {

    private final UserService userService;
    private final RoleService roleService;

    public DataInitializer(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    private void init() {
        roleService.saveRole(new Role("ADMIN"));
        roleService.saveRole(new Role("USER"));


        User user = new User();
        user.setName("Admin");
        user.setSurname("Adminov");
        user.setAge(30);
        user.setEmail("admin@gmail.com");
        user.setPassword("admin");
        user.setRoles(Set.of(roleService.getRoleByName("ADMIN"), roleService.getRoleByName("USER")));
        userService.saveOrUpdate(user);

        User user2 = new User();
        user2.setName("User");
        user2.setSurname("Userov");
        user2.setAge(45);
        user2.setEmail("user@gmail.com");
        user2.setPassword("user");
        user2.setRoles(Set.of(roleService.getRoleByName("USER")));
        userService.saveOrUpdate(user2);
    }
}
