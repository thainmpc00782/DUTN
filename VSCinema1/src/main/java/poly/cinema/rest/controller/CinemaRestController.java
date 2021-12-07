package poly.cinema.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import poly.cinema.entity.Cinema;
import poly.cinema.service.CinemaService;

@RestController
@RequestMapping("rest/cinemas")
public class CinemaRestController {
	@Autowired
	CinemaService cinemaService;
	@GetMapping
	public List<Cinema> findAll(){
		return cinemaService.findAll();
	}
	
	@GetMapping("{id}")
	public Cinema getOne(@PathVariable("id") Integer id) {
		return cinemaService.findById(id);
	}

	@PostMapping
	public Cinema create(@RequestBody Cinema category) { // gọi bằng ajax nên dùng RequestBody
		return cinemaService.create(category);
	}

	@PutMapping()
	public Cinema update(@RequestBody Cinema category) {
		return cinemaService.update(category);
	}

	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") Integer id) {
		cinemaService.delete(id);
	}
	
	
	@PostMapping("/findByCity/{cityid}")
	public List<Cinema> findByCity(@PathVariable("cityid") String cityid){	
		cityid = cityid.replace("string:", "");
		//System.out.println(cityid);
		return cinemaService.findByCity(cityid);
	}
	@GetMapping("/findByCity/{cityid}")
	public List<Cinema> GetCinemaCity(@PathVariable("cityid") String cityid){	
		cityid = cityid.replace("string:", "");
		//System.out.println(cityid);
		return cinemaService.findByCity(cityid);
	}
}