package poly.cinema.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import poly.cinema.entity.Coupon;
import poly.cinema.entity.Seat;
import poly.cinema.entity.Show;

@Data
@NoArgsConstructor @AllArgsConstructor
public class CreateOrderRequest {

	Show show;
	List<Seat> seat;
	List<FoodRequest> food;
	Coupon coupon;
	Integer amount;
	String bank;
}
