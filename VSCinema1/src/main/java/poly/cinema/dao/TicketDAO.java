package poly.cinema.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import poly.cinema.entity.Movie;
import poly.cinema.entity.Ticket;
import poly.cinema.entity.Top5Movie;

public interface TicketDAO extends JpaRepository<Ticket, String>{
	@Query("Select tk.createDate from Ticket tk group by tk.createDate order by tk.createDate")
	List<String> getListDate();
	@Query("Select tk.show.movie from Ticket tk group by tk.show.movie.id")
	List<Movie> getListMovie();
	@Query("Select tk.createDate,sum(tk.totalmoney) from Ticket tk group by tk.createDate")
	List<Object[]> getSumPriceTicket();
	
	@Query("select new SumRevenuesByCinema(sum(t.order.totalmoney), t.show.movie.id, t.show.room.cinema.name) from Ticket t where YEAR(createDate) = (YEAR(CURDATE())) group by t.show.room.cinema.id") 
	List<poly.cinema.entity.SumRevenuesByCinema> SumRevenuesByCinema();
	@Query("select new Top5Movie(count(t.id), t.show.movie.id, t.show.movie.name) from Ticket t group by t.show.movie.id order by count(t.show.movie.id) desc")
	List<Top5Movie> Top5SumticketMovie();
	
	//@Query(value="select * from tickets t join Orders o on o.id = t.Orderid where o.time between date_add(current_time(),interval -30 Minute) and current_time()", nativeQuery=true)
	@Query("select t from Ticket t where t.order.time between '00:00:00' and :timeend")
	List<Ticket> getSumTicketByTimes(Date time,Date timeend);
	
	@Query("select t from Ticket t where t.show.id=:id")
	List<Ticket> findByShowId(Integer id);
	
}