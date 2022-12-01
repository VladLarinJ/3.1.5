package ru.kata.spring.boot_security.demo.repositories;

import ru.kata.spring.boot_security.demo.entities.Role;

import java.util.List;

public interface RoleDao {
	List<Role> roleList();
	
	Role getById(Integer id);

	Role getByName(String name);
}
