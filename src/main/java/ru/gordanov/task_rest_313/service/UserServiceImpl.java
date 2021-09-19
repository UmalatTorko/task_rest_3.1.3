package ru.gordanov.task_rest_313.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.gordanov.task_rest_313.entity.User;
import ru.gordanov.task_rest_313.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleService roleService;
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findUserByEmail(username);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void saveOrUpdate(User user) {
        User userDB = getUserById(user.getId());

        if (user.getPassword().equals("")) {
            user.setPassword(userDB.getPassword());
        } else {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        userRepository.save(user);
    }

    @Override
    public void saveWithRole(User user, String[] roles) {
        user.setRoles(Arrays.stream(roles)
                .map(roleService::getRoleByName)
                .collect(Collectors.toSet()));
        saveOrUpdate(user);
    }

    @Override
    public void save(User user) {
        user.setRoles(user.getRoles().stream()
                .map(role -> roleService.getRoleByName(role.getName()))
                .collect(Collectors.toSet()));
        saveOrUpdate(user);
    }

    @Override
    public User getUserById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }
}
