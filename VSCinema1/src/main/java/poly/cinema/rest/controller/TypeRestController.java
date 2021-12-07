
package poly.cinema.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import poly.cinema.entity.Type;
import poly.cinema.service.TypeService;

@RestController
@RequestMapping("rest/types")
public class TypeRestController {

	@Autowired
	TypeService typeService;
	
	@GetMapping()
	public List<Type> getAll(){
		return typeService.findAll();
	}
	
}