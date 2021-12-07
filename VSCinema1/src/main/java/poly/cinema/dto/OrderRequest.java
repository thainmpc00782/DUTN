package poly.cinema.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import poly.cinema.entity.Cinema;
import poly.cinema.entity.Order;
import poly.cinema.entity.Show;

@Data
@NoArgsConstructor @AllArgsConstructor
public class OrderRequest {

	Cinema cinema;
	Order order;
	Show show;
	String seat;
	String food;
	
}
