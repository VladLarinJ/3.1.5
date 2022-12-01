package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.repositories.RoleDao;

import java.util.List;

@Transactional
@Service
public class RoleServiceImp implements RoleService {
	
	@Autowired
	private RoleDao roleDao;
	
	@Override
	public List<Role> roleList() {
		return roleDao.roleList();
	}
	
	@Override
	public Role getById(Integer id) {
		return roleDao.getById(id);
	}

	@Override
	public Role getByName(String name) {
		return roleDao.getByName(name);
	}


}
