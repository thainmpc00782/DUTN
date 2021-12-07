package poly.cinema.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import poly.cinema.dao.RatedDAO;

import poly.cinema.entity.Rated;
import poly.cinema.service.RatedService;



@Service
public class RatedServiceImpl implements RatedService{

	@Autowired
	RatedDAO ratedDAO;

	@Override
	public List<Rated> finAll() {
		return ratedDAO.findAll();
	}
	
	
	
}
