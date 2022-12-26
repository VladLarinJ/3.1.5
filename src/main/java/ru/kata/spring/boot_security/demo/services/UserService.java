package ru.kata.spring.boot_security.demo.services;



import ru.kata.spring.boot_security.demo.entities.User;

import java.util.ArrayList;
import java.util.List;

public interface UserService {
    User add(ArrayList<Integer> roles, String name, String lastName, String password, String email, Integer age);
    User add(User user);
    User getUserById(Integer id);
    List<User> listUsers();
    User updateUser(ArrayList<Integer> roles, String name, String lastName, String password, String email, Integer age, Integer id);
    User deleteUser(Integer id);
    User getUserByNamePass(String name, String password);
    User getUserByName(String name);
    User getUserByEmail(String email);
    void updateUser(User updatedUser);
}
