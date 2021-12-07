package poly.cinema.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import poly.cinema.entity.Rated;
import poly.cinema.service.RatedService;
import poly.cinema.service.RoleService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/rated")
public class RatedRestController {

	@Autowired
	RatedService ratedService;
	
	@GetMapping()
	public List<Rated> findAll() {
		return ratedService.finAll();
	}
	
	
}
