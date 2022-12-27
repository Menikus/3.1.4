package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/")
public class UserController {

    private final UserService userService;

    private final RoleService roleService;


    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/user")
    public String myPage(Principal principal, Model model) {
        model.addAttribute("user", userService.findByName(principal.getName()));
        model.addAttribute("simpleGrantedAuthority", new SimpleGrantedAuthority("ADMIN"));
        return "user";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

}