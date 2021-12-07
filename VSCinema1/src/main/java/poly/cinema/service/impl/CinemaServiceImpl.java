package poly.cinema.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import poly.cinema.dao.CinemaDAO;
import poly.cinema.entity.Cinema;
import poly.cinema.service.CinemaService;

@Service
public class CinemaServiceImpl implements CinemaService {

	@Autowired
	CinemaDAO dao;

	@Override
	public List<Cinema> findAll() {
		// TODO Auto-generated method stub
		return dao.findAll();
	}

	

	@Override
	public List<Cinema> findByCity(String cityid) {
		// TODO Auto-generated method stub
		return dao.findCinemaByCity(cityid);
	}
	
	@Override
	public Cinema getById(Integer id) {
		return dao.findById(id).get();
	}

	@Override
	public Cinema create(Cinema cinema) {
		return dao.save(cinema);
	}

	@Override
	public Cinema update(Cinema cinema) {
		return dao.save(cinema);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
				dao.deleteById(id);
	}

	@Override
	public Cinema findById(Integer id) {
		return dao.findById(id).get();
	}

	

}