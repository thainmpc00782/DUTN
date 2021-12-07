package poly.cinema.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import poly.cinema.dao.TicketDAO;
import poly.cinema.entity.*;
import poly.cinema.service.TicketService;

@Service
public class TicketServiceImpl implements TicketService{

	@Autowired
	TicketDAO dao;
	

	@Override
	public List<Ticket> findAll() {
		return dao.findAll();
	}


	@Override
	public List<Ticket> getAll() {
		// TODO Auto-generated method stub
		return dao.findAll();
	}


	@Override
	public List<String> getListDate() {
		// TODO Auto-generated method stub
		return dao.getListDate();
	}


	@Override
	public List<Movie> getListMovie() {
		// TODO Auto-generated method stub
		return dao.getListMovie();
	}


	@Override
	public List<Object[]> getSumPriceTicket() {
		// TODO Auto-generated method stub
		return dao.getSumPriceTicket();
	}


	@Override
	public List<SumRevenuesByCinema> SumRevenuesByCinema() {
		// TODO Auto-generated method stub
		return dao.SumRevenuesByCinema();
	}


	@Override
	public List<Top5Movie> Top5SumticketMovie() {
		// TODO Auto-generated method stub
		return dao.Top5SumticketMovie();
	}


	@Override
	public List<Ticket> getSumTicketByTime(Date time, Date timeend) {
		// TODO Auto-generated method stub
		return dao.getSumTicketByTimes(time, timeend);
	}
	
	@Override
	public List<Ticket> getByShowId(Integer id) {
		return dao.findByShowId(id);
	}
	
}