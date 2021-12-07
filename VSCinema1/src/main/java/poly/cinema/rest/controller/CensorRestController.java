package poly.cinema.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import poly.cinema.entity.Censor;
import poly.cinema.service.CensorService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/censors")
public class CensorRestController {

	@Autowired
	CensorService censorService;
	
	@GetMapping()
	public List<Censor> getAll(){
		return censorService.findAll();
	}
	
	@PostMapping()
	public Censor create(@RequestBody Censor censor) {
		return censorService.create(censor);
	}
	
}
