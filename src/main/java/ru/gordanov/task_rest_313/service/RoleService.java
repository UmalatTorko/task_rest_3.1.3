package ru.gordanov.task_rest_313.service;


import ru.gordanov.task_rest_313.entity.Role;

import java.util.List;

public interface RoleService {
    Role getRoleByName(String name);

    List<Role> getAllRoles();

    void saveRole(Role role);
}
