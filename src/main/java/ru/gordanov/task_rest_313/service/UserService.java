package ru.gordanov.task_rest_313.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.gordanov.task_rest_313.entity.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    User getUserByUsername(String username);

    List<User> getAllUsers();

    void save(User user);

    void saveOrUpdate(User user);

    void saveWithRole(User user, String[] roles);

    User getUserById(long id);

    void delete(long id);
}
