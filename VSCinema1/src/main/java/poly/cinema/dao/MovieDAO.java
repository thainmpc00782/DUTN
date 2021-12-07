package poly.cinema.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import poly.cinema.entity.Movie;

public interface MovieDAO extends JpaRepository<Movie, Integer>{
	List<Movie> findByName(String name);
	
	@Query(value = "select DISTINCT m.* from movies m join shows s on m.id=s.Moviesid where (s.Showdate between current_date() and date_add(current_date(), interval 7 day)) and s.Activity = 3", nativeQuery = true)
	List<Movie> findMovieByShowdate();
	
	@Query("Select m from Movie m where m.activity=true and m.releaseDate>CURRENT_DATE order by m.releaseDate")
	List<Movie> findComingSoonMovies();
	
	@Query(value="select DISTINCT m.* from shows s join movies m on s.Moviesid = m.Id where s.Showdate between current_date() and adddate('2021-01-01', interval 1 year)", nativeQuery = true)
	List<Movie> listshow();
}
