package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@Transactional
@RequestMapping("/")
public class UserController {

    private final UserService userService;

    private final RoleService roleService;


    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String index(Principal principal,Model model) {
        model.addAttribute("userAuthorized", userService.findByName((principal.getName())));
        model.addAttribute("users", userService.findAll());
        model.addAttribute("roles", roleService.findAll());
//        model.addAttribute("principal", authentication).addAttribute("rolesList", roleService.findAll())
//                .addAttribute("admin", userService.findByName(authentication.getName()));
        return "admin/index";
    }
//    @GetMapping("/admin")
//    public String index(Model model) {
//        model.addAttribute("users", userService.findAll());
//        model.addAttribute("roles", roleService.findAll());
//        return "admin/index";
//    }


    @GetMapping("/admin/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "admin/show";
    }

    @GetMapping("/admin/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "admin/new";
    }


    @PostMapping("/admin")
    public String create(@ModelAttribute("user") @Valid User user, BindingResult bindingRequest) {
        if (bindingRequest.hasErrors()) return "admin/new";
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userService.findById(id));
        return "admin/edit";
    }


    @PatchMapping("/admin/{id}")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingRequest,
                         @PathVariable("id") int id) {
        if (bindingRequest.hasErrors()) return "admin/edit";
        userService.update(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.delete(id);
        return "redirect:/admin";
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