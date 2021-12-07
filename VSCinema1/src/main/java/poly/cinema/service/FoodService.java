package poly.cinema.service;

import java.util.List;

import poly.cinema.dto.FoodRequest;
import poly.cinema.entity.Food;

public interface FoodService {
	List<Food> findAll();

	Food findById(Integer id);

	Food create(Food food);

	Food update(Food food);

	void delete(Integer id);

	List<FoodRequest> getFoodCinema(Integer id);
}
