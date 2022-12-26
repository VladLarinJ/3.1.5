package ru.kata.spring.boot_security.demo.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repositories.RoleDao;
import ru.kata.spring.boot_security.demo.repositories.UserDao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserDao userDao;
    private final RoleDao roleDao;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public User add(ArrayList<Integer> roles, String name, String lastName, String password, String email, Integer age) {
        Set<Role> roleSet = new HashSet<>();
        for (Integer roleId : roles) {
            roleSet.add(new Role(roleId));
        }
        User user = new User(name, password, email, lastName, age, roleSet);
        user.setRoles(roleSet);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.add(user);
        return user;
    }

    @Transactional
    @Override
    public User add(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.add(user);
        return user;
    }

    @Transactional
    @Override
    public User getUserById(Integer id) {
        return userDao.getUserById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> listUsers() {
        return userDao.listUsers();
    }

    @Transactional
    @Override
    public User updateUser(ArrayList<Integer> roles, String name, String lastName, String password, String email, Integer age, Integer id) {
        Set<Role> roleSet = new HashSet<>();
        for (Integer roleId : roles) {
            roleSet.add(new Role(roleId));
        }
        User user = userDao.getUserById(id);
        user.setRoles(roleSet);
        user.setEmail(email);
        user.setName(name);
        user.setLastName(lastName);
        user.setPassword(passwordEncoder.encode(password));
        user.setAge(age);

        Set<Role> roleSet1 = new HashSet<>();
        for (Role role : user.getRoles()) {
            roleSet1.add(roleDao.getById(role.getId()));
        }
        user.setRoles(roleSet1);
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.updateUser(user);
        return user;
    }

    @Transactional
    @Override
    public User deleteUser(Integer id) {
        userDao.deleteUser(id);
        return null;
    }

    @Override
    public User getUserByNamePass(String name, String password) {
        return userDao.getUserByNamePass(name, password);
    }

    @Override
    public User getUserByName(String name) {
        return userDao.getUserByName(name);
    }

    @Override
    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Override
    public void updateUser(User updatedUser) {
        userDao.updateUser(updatedUser);
    }
}