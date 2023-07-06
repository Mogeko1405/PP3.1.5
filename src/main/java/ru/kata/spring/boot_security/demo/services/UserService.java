package ru.kata.spring.boot_security.demo.services;

import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    List<User> findAll();

    User findOne(int id);

    void saveUser(User user);

    @Transactional
    void update(Long id, User updatedUser);

    void deleteUser(int id);

    Set<Role> getRole();
}
