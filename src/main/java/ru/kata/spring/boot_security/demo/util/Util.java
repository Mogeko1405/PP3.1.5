package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import javax.annotation.PostConstruct;

@Component
public class Util {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public Util(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    @Transactional
    public void doUtil() {
        if (userRepository.findAll().size() == 0) {
            User user = new User("Admin", "Adminovich", 22, "admin@gmail.com", "pass");
            User user2 = new User("User", "Userovich", 11, "user@gmail.com", "pass");
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user2.setPassword(passwordEncoder.encode(user2.getPassword()));
            Role roleUser = new Role("ROLE_USER");
            Role roleAdmin = new Role("ROLE_ADMIN");
            roleRepository.save(roleAdmin);
            roleRepository.save(roleUser);
            userRepository.save(user);
            userRepository.save(user2);
            user.getRoleList().add(roleRepository.findRoleByRole("ROLE_ADMIN"));
            user.getRoleList().add(roleRepository.findRoleByRole("ROLE_USER"));

            user2.getRoleList().add(roleRepository.findRoleByRole("ROLE_USER"));
            userRepository.save(user);
            userRepository.save(user2);

        }
    }
}
