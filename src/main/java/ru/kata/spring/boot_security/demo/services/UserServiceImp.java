package ru.kata.spring.boot_security.demo.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    public void add(ArrayList<Integer> roles, String name, String lastName, String password, String email) {
        Set<Role> roleSet = new HashSet<>();
        for (Integer roleId : roles) {
            roleSet.add(new Role(roleId));
        }
        User user = new User(name, password, email, lastName, roleSet);
        user.setRoles(roleSet);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.add(user);
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
    public void updateUser(ArrayList<Integer> roles, String name, String lastName, String password, String email) {
        Set<Role> roleSet = new HashSet<>();
        for (Integer roleId : roles) {
            roleSet.add(new Role(roleId));
        }
        User user = userDao.getUserByName(name);
        user.setRoles(roleSet);
        user.setEmail(email);
        user.setName(name);
        user.setLastName(lastName);
        user.setPassword(password);

        Set<Role> roleSet1 = new HashSet<>();
        for (Role role : user.getRoles()) {
            roleSet1.add(roleDao.getById(role.getId()));
        }
        user.setRoles(roleSet1);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.updateUser(user);
    }

    @Transactional
    @Override
    public void deleteUser(Integer id) {
        userDao.deleteUser(id);
    }

    @Override
    public User getUserByNamePass(String name, String password) {
        return userDao.getUserByNamePass(name, password);
    }

    @Override
    public User getUserByName(String name) {
        return userDao.getUserByName(name);
    }

//    @Override
//    @Transactional
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = getUserByName(username);
//        if (user == null) {
//            throw new UsernameNotFoundException("User not found");
//        }
//        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), getAuthorities(user.getRoles()));
//    }
//
//    private static Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
//        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
//    }
}