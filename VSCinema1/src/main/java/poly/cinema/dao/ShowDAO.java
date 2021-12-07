package poly.cinema.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import poly.cinema.entity.Movie;
import poly.cinema.entity.Show;

public interface ShowDAO extends JpaRepository<Show, Integer> {
	@Query("Select s from Show s where s.Activity = 3")
	List<Show> getAll();

	@Query("Select s from Show s where s.Activity = 3 and s.movie.id = :movieid")
	List<Show> findByMovie(Integer movieid);

	@Query("Select s from Show s where s.Activity = 3 and s.room.name = :roomname")
	List<Show> findByRoom(String roomname);

	@Query("Select s from Show s where s.Activity = 3 and s.movie.id = :movieid and s.room.name = :roomname")
	List<Show> findByMovieAndRoom(Integer movieid, String roomname);

	@Query("Select s.showDate from Show s where s.Activity = 3 group by s.showDate")
	List<String> listShowDate();

	@Query(value = "select DISTINCT m.* from movies m join shows s on m.id=s.Moviesid where (s.Showdate between current_date() and date_add(current_date(), interval 31 day)) and s.Activity = 3", nativeQuery = true)
	List<Show> findMovieByShowdate();

	@Query("Select s from Show s where s.Activity = 3 and s.showDate = :showdate and s.movie.id=:id")
	List<Show> getShowDate(Date showdate, Integer id);
	
}