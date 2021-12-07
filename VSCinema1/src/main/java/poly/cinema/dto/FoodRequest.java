package poly.cinema.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import poly.cinema.entity.Food;
import poly.cinema.entity.Sale;

@Data
@NoArgsConstructor @AllArgsConstructor
public class FoodRequest {

	Food food;
	Sale sale;
	Integer quantity = 0;
}
