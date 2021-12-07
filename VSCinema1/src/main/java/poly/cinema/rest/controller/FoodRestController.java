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

import poly.cinema.dto.FoodRequest;
import poly.cinema.entity.Food;
import poly.cinema.service.FoodService;

@CrossOrigin("*")
@RestController
@RequestMapping("rest/food")
public class FoodRestController {
	@Autowired
	FoodService foodService;

	@GetMapping()
	public List<Food> getAll() {
		return foodService.findAll();
	}

	@GetMapping("{id}")
	public Food getOne(@PathVariable("id") Integer id) {
		return foodService.findById(id);
	}
	
	@GetMapping("/Cinema/{id}")
	public List<FoodRequest> getFood(@PathVariable("id") Integer id) {
		return foodService.getFoodCinema(id);
	}

	@PostMapping
	public Food create(@RequestBody Food food) {
		return foodService.create(food);
	}

	@PutMapping()
	public Food update(@RequestBody Food food) {
		return foodService.update(food);
	}

	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") Integer id) {
		foodService.delete(id);
	}
}
