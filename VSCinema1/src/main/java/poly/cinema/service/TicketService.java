   
package poly.cinema.service;

import java.util.Date;
import java.util.List;

import poly.cinema.entity.Movie;
import poly.cinema.entity.Ticket;
import poly.cinema.entity.Top5Movie;
import poly.cinema.entity.SumRevenuesByCinema;

public interface TicketService {

	List<Ticket> findAll();

	List<Ticket> getAll();

	List<String> getListDate();

	List<Movie> getListMovie();

	List<Object[]> getSumPriceTicket();
	
	List<SumRevenuesByCinema> SumRevenuesByCinema();
	
	List<Top5Movie> Top5SumticketMovie();

	List<Ticket> getSumTicketByTime(Date time,Date endtime);

	List<Ticket> getByShowId(Integer id);
}