package poly.cinema.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import poly.cinema.dao.GenreDAO;
import poly.cinema.dao.RatedDAO;
import poly.cinema.entity.Genre;
import poly.cinema.entity.Rated;
import poly.cinema.service.GenreService;
import poly.cinema.service.RatedService;



@Service
public class GenreServiceImpl implements GenreService{

	@Autowired
	GenreDAO genredDAO;

	@Override
	public List<Genre> finAll() {
		return genredDAO.findAll();
	}
	
	
	
}
