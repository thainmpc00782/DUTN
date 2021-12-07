package poly.cinema.service;

import java.util.List;

import poly.cinema.entity.Authority;

public interface AuthorityService {

	List<Authority> findAll();

	Authority create(Authority auth);

	void delete(Integer id);
	
	List<Authority> getListStaf();

	List<Authority> getListUser();

}
