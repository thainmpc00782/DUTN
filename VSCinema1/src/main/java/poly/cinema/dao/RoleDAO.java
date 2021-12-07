package poly.cinema.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import poly.cinema.common.ERole;
import poly.cinema.entity.Role;

public interface RoleDAO extends JpaRepository<Role, String>{

	Role findByName(ERole roleUser);
	
}
