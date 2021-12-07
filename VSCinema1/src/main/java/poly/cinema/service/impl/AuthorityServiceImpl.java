package poly.cinema.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import poly.cinema.dao.AuthorityDAO;
import poly.cinema.entity.Authority;
import poly.cinema.service.AuthorityService;

@Service
public class AuthorityServiceImpl implements AuthorityService{

	@Autowired
	AuthorityDAO authorityDAO;
	
	@Override
	public List<Authority> findAll() {
		return authorityDAO.findAll();
	}

	@Override
	public Authority create(Authority auth) {
		return authorityDAO.save(auth);
	}

	@Override
	public void delete(Integer id) {
		authorityDAO.deleteById(id);
	}
	
	@Override
	public List<Authority> getListStaf() {
		// TODO Auto-generated method stub
		return authorityDAO.getListStaf();
	}

	@Override
	public List<Authority> getListUser() {
		// TODO Auto-generated method stub
		return authorityDAO.getListUser();
	}

}
