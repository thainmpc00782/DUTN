package poly.cinema.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import poly.cinema.entity.Food;

public interface FoodDao extends JpaRepository<Food, Integer> {

	@Query("select f from Food f where f.cinema.id=:id and f.active = true")
	List<Food> findByCinemaId(Integer id);

}
