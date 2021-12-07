package poly.cinema.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import poly.cinema.dao.MovieDAO;
import poly.cinema.entity.Movie;
import poly.cinema.service.MovieService;

@Controller
public class CinemaController {
	
	@Autowired
	MovieDAO movieDao;
	
	@Autowired
	MovieService movieService;
	
	@RequestMapping("cinema/cinema-list")
	public String theaterList(Model model) {
		List<Movie> showing = movieService.findMovieByShowdate();
		model.addAttribute("movies", showing);
		return "cinema/cinema-list";
	}
}
