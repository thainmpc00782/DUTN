package poly.cinema.service;

import java.util.List;

import poly.cinema.entity.Movie;

public interface MovieService {
	List<Movie> getAll();

	List<Movie> fillByName(String name);	

	Movie findById(Integer id);

	Movie create(Movie movie);

	Movie update(Movie movie);

	void delete(Integer id);
	
	List<Movie> findMovieByShowdate();
	List<Movie> findComingSoonMovies();
	List<Movie> listshow();
}
