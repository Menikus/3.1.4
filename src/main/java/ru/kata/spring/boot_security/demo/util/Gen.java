package ru.kata.spring.boot_security.demo.util;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class Gen {

    private final UserService userService;
    private final RoleService roleService;

    public Gen(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void initDB() {
        Role roleAdmin = new Role(1, "ROLE_ADMIN");
        Role roleUser = new Role(2, "ROLE_USER");
        Set<Role> adminSet = new HashSet<>();
        Set<Role> userSet = new HashSet<>();

        roleService.save(roleAdmin);
        roleService.save(roleUser);

        adminSet.add(roleAdmin);
        adminSet.add(roleUser);
        userSet.add(roleUser);


        User admin = new User("admin",
                "admin", 25,
                "admin@mail.ru", adminSet);
        admin.setId(1);
        admin.setPassword("100");

        User user = new User("user", "user", 22,
                "user@mail.ru", userSet);
        user.setId(2);
        user.setPassword("100");
        userService.save(admin);
        userService.save(user);
    }
}

