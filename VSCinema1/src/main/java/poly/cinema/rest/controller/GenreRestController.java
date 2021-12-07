package poly.cinema.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import poly.cinema.entity.Genre;
import poly.cinema.entity.Rated;
import poly.cinema.service.GenreService;
import poly.cinema.service.RatedService;
import poly.cinema.service.RoleService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/genre")
public class GenreRestController {

	@Autowired
	GenreService genreService;
	
	@GetMapping()
	public List<Genre> findAll() {
		return genreService.finAll();
	}
	
	
}
