package poly.cinema.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import poly.cinema.dao.RoleDAO;
import poly.cinema.entity.Role;
import poly.cinema.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	RoleDAO roleDAO;

	@Override
	public List<Role> finAll() {
		return roleDAO.findAll();
	}
	
}
