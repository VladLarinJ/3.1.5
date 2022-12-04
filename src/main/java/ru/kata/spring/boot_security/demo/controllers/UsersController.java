package ru.kata.spring.boot_security.demo.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class UsersController {

    private final UserService userService;
    private final RoleService roleService;

    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    @GetMapping("/user")
    public String userPage(Model model, Principal principal) {
        model.addAttribute("user", userService.getUserByName(principal.getName()));
        return "userPage";
    }

    @GetMapping("/admin")
    public String adminPage(Model model, Principal principal) {
        model.addAttribute("admin", userService.getUserByName(principal.getName()));
        return "adminPage";
    }

    @GetMapping("/admin/user_list")
    public String getUsersList(Model model) {
        model.addAttribute("users", userService.listUsers());
        return "/userList";
    }
    @GetMapping("/admin/user_list/{id}")
    public String showUser(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "/showUser";
    }

    @GetMapping("/admin/new_user")
    public String newUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("roles", roleService.roleList());
        return "/addUser";
    }

    @PostMapping("/admin/new_user")
    public String addUser(@RequestParam ArrayList<Integer> roles, @RequestParam String name,
                          @RequestParam String lastName, @RequestParam String password, @RequestParam String email) {

        Set<Role> roleSet = new HashSet<>();
        for (Integer roleId : roles) {
            roleSet.add(new Role(roleId));
        }
        User user = new User(name, password, email, lastName, roleSet);
        user.setRoles(roleSet);
        userService.add(user);
        return "redirect:/admin/user_list";
    }

    @GetMapping("/admin/user_list/{id}/update")
    public String editUser(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("roles", roleService.roleList());
        model.addAttribute("user", userService.getUserById(id));
        return "/updateUser";
    }

    @PatchMapping("/admin/user_list/{id}")
    public String updateUser(@RequestParam ArrayList<Integer> roles, @RequestParam String name,
                             @RequestParam String lastName, @RequestParam String password, @RequestParam String email) {
        Set<Role> roleSet = new HashSet<>();
        for (Integer roleId : roles) {
            roleSet.add(new Role(roleId));
        }
        User user = userService.getUserByName(name);
        user.setRoles(roleSet);
        user.setEmail(email);
        user.setName(name);
        user.setLastName(lastName);
        user.setPassword(password);
        userService.updateUser(user);
        return "redirect:/admin/user_list";
    }

    @DeleteMapping("/admin/user_list/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
        userService.deleteUser(id);
        return "redirect:/admin/user_list";
    }
}
