package poly.cinema.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import poly.cinema.entity.City;
import poly.cinema.service.CityService;

@RestController
@RequestMapping("/rest/city")
public class CityRestController {
	@Autowired CityService cityservice;
	
	@GetMapping()
	public List<City> getAll(){
		return cityservice.getAll();
	}
}
