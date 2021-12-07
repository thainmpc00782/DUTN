package poly.cinema.service;

import java.util.List;

import poly.cinema.entity.Cinema;

public interface CinemaService {

	List<Cinema> findAll();

	

	List<Cinema> findByCity(String cityid);

	Cinema getById(Integer id);

	Cinema create(Cinema cinema);

	Cinema update(Cinema cinema);

	void delete(Integer id);

	Cinema findById(Integer id);
}
