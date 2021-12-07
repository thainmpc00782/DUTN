package poly.cinema.dto;


import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import poly.cinema.entity.Food;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceHistoryRequest { // Lấy dữ liệu từ form

	Food food;
	Integer price;
	Date date;

}