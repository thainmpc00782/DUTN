package poly.cinema.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
import poly.cinema.entity.Food;
import poly.cinema.entity.Movie;
import poly.cinema.service.FoodService;
import poly.cinema.service.MovieService;

@CrossOrigin("*")
@RestController
@RequestMapping("rest/movie")
public class MovieRestController {
	@Autowired
	MovieService movieService;

	@GetMapping()
	public List<Movie> getAll() {
		return movieService.getAll();
	}

	@GetMapping("{id}")
	public Movie getOne(@PathVariable("id") Integer id) {
		return movieService.findById(id);
	}

	@PostMapping
	public Movie create(@RequestBody Movie movie) {
		return movieService.create(movie);
	}

	@PutMapping()
	public Movie update(@RequestBody Movie movie) {
		return movieService.update(movie);
	}

	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") Integer id) {
		movieService.delete(id);
	}
}
