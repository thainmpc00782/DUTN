package poly.cinema.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import poly.cinema.entity.City;

public interface CityDAO extends JpaRepository<City, String>{
	
}
