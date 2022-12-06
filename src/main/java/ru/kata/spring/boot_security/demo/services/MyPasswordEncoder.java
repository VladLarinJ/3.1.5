package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MyPasswordEncoder {
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
