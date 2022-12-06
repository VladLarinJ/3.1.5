package ru.kata.spring.boot_security.demo.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.entities.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RoleDaoImp implements RoleDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Role> roleList() {
		return entityManager.createQuery("from Role", Role.class).getResultList();
	}
	
	@Override
	public Role getById(Integer id) {
		return entityManager.find(Role.class, id);
	}

	@Override
	public Role getByName(String name) {
			return (Role) entityManager.createQuery("from Role where name =: name")
					.setParameter("name", name)
					.getSingleResult();
	}
}
