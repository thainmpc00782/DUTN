package poly.cinema.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import poly.cinema.dao.TypeDAO;
import poly.cinema.entity.Type;
import poly.cinema.service.TypeService;

@Service
public class TypeServiceImpl implements TypeService{

	@Autowired
	TypeDAO typeDAO;

	@Override
	public List<Type> findAll() {
		return typeDAO.findAll();
	}
	
}