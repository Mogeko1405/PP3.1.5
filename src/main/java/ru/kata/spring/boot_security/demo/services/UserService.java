package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.entities.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    void createNewUser(User user);

    User getUser(Long id);

    void updateUser(User user);

    void deleteUser(Long id);

    User findUserByUsername(String username);
}
