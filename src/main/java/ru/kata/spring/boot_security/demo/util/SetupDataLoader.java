package ru.kata.spring.boot_security.demo.util;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.Set;


@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    boolean alreadySetup = false;
    private final UserService userService;
    private final RoleService roleService;

    public SetupDataLoader(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup) {
            return;
        }

        Role adminRole = createRoleIfNotFound("ADMIN");
        Role userRole = createRoleIfNotFound("USER");

        Set<Role> allRoles = new HashSet<>();
        allRoles.add(adminRole);
        allRoles.add(userRole);

        Set<Role> userRoles = new HashSet<>();
        userRoles.add(userRole);


        User admin = new User();
        admin.setFirstName("Admin");
        admin.setLastName("Adminovich");
        admin.setAge((byte) 22);
        admin.setUsername("admin@gmail.com");
        admin.setPassword("pass");
        admin.setRoles(allRoles);

        User user = new User();
        user.setFirstName("User");
        user.setLastName("Userovich");
        user.setAge((byte) 11);
        user.setUsername("user@gmail.com");
        user.setPassword("pass");
        user.setRoles(userRoles);

        userService.saveUser(admin);
        userService.saveUser(user);

        alreadySetup = true;
    }

    @Transactional
    Role createRoleIfNotFound(String name) {

        Role role = roleService.findByName(name);
        if (role == null) {
            role = new Role(name);
            roleService.save(role);
        }
        return role;
    }
}
