package poly.cinema.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class CreateOrderFoodRequest {

	List<FoodRequest> food;
	Integer amount;
	String bank;
	
}
