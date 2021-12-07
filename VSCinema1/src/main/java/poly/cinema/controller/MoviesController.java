package poly.cinema.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import poly.cinema.dao.MovieDAO;
import poly.cinema.entity.Movie;
import poly.cinema.service.MovieService;

@Controller
public class MoviesController {
	
	@Autowired
	MovieDAO movieDao;
	
	@Autowired
	MovieService movieService;
	
	@RequestMapping("movie/showing-film")
	public String showingFilm(Model model) {
		List<Movie> showing = movieService.findMovieByShowdate();
		model.addAttribute("movies", showing);
		model.addAttribute("title1", "Phim đang chiếu");
		model.addAttribute("title2", "Phim sắp chiếu");
		model.addAttribute("url", "comming-soon-film");
		return "movie/film-list";
	}
	
	@RequestMapping("movie/comming-soon-film")
	public String commingSoonFilm(Model model) {
		List<Movie> comingsoon = movieService.findComingSoonMovies();
		model.addAttribute("movies", comingsoon);
		model.addAttribute("title1", "Phim sắp chiếu");
		model.addAttribute("title2", "Phim đang chiếu");
		model.addAttribute("url", "showing-film");
		return "movie/film-list";
	}

	
	@RequestMapping("movie/film-detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id) {
		Movie movie = movieService.findById(id);
		model.addAttribute("movies", movie);
		return "movie/film-detail";
	}
	
}
