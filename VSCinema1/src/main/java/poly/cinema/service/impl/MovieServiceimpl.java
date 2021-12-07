package poly.cinema.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import poly.cinema.dao.MovieDAO;
import poly.cinema.entity.Account;
import poly.cinema.entity.Cinema;
import poly.cinema.entity.Movie;
import poly.cinema.entity.Movie;
import poly.cinema.service.MovieService;

@Service
public class MovieServiceimpl implements MovieService{
@Autowired MovieDAO moviedao;
	
	@Override
	public List<Movie> getAll() {
		// TODO Auto-generated method stub
		return moviedao.findAll();
	}

	@Override
	public List<Movie> fillByName(String name) {
		// TODO Auto-generated method stub
		return moviedao.findByName(name);
	}
	@Override
	public Movie create(Movie movie) {
		// TODO Auto-generated method stub
		
		
		moviedao.save(movie);
	
		return movie;
	}

	@Override
	public Movie update(Movie movie) {
		// TODO Auto-generated method stub
		return moviedao.save(movie);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		moviedao.deleteById(id);
	}

	@Override
	public Movie findById(Integer id) {
		return moviedao.findById(id).get();
	}
	
	@Override
	public List<Movie> findMovieByShowdate() {
		// TODO Auto-generated method stub
		return moviedao.findMovieByShowdate();
	}
	@Override
	public List<Movie> findComingSoonMovies() {
		// TODO Auto-generated method stub
		return moviedao.findComingSoonMovies();
	}
	
	@Override
	public List<Movie> listshow() {
		// TODO Auto-generated method stub
		return moviedao.listshow();
	}
	
}
