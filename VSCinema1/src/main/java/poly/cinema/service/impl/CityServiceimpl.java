package poly.cinema.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import poly.cinema.dao.CityDAO;
import poly.cinema.entity.City;
import poly.cinema.service.CityService;

@Service
public class CityServiceimpl implements CityService{
@Autowired CityDAO citydao;
	@Override
	public List<City> getAll() {
		// TODO Auto-generated method stub
		return citydao.findAll();
	}
	
}
