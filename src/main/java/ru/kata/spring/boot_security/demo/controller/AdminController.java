package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String getAdminPage(Model model, Principal principal) {
        model.addAttribute("user", userService.getUserByLogin(principal.getName()));
        return "user";
    }

    @GetMapping("/admin/view")
    public String getManagedAdminPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin";
    }

    @GetMapping("/admin/view/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "user";
    }

    @GetMapping("/admin/view/new")
    public String newPerson(Model model) {
        List<Role> allRoles = roleService.getAllRoles();
        model.addAttribute("roles", allRoles);
        model.addAttribute("user", new User());
        return "new";
    }

    @PostMapping("/admin/view")
    public String create(@ModelAttribute("user") User user,
                         @RequestParam("roleList") ArrayList<Long> roles) {
        user.setRoles(roleService.getAnyRoleById(roles));
        userService.saveUser(user);
        return "redirect:/admin/view";
    }

    @GetMapping("/admin/view/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        List<Role> allRoles = roleService.getAllRoles();
        model.addAttribute("roles", allRoles);
        model.addAttribute("user", userService.getUserById(id));
        return "edit";
    }

    @PatchMapping("/admin/view/{id}")
    public String update(@ModelAttribute("user") User user,
                         @RequestParam("roleList") ArrayList<Long> roles) {
        user.setRoles(roleService.getAnyRoleById(roles));
        userService.updateUser(user);
        return "redirect:/admin/view";
    }

    @DeleteMapping("/admin/view/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.removeUserById(id);
        return "redirect:/admin/view";
    }
}
