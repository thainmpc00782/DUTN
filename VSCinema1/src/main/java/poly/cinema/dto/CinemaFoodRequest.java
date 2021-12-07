package poly.cinema.dto;

import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import poly.cinema.entity.Cinema;

@Data
@NoArgsConstructor 
@AllArgsConstructor
public class CinemaFoodRequest {

	String name;
	String description;
	String image;
	Integer price;
	Date createdate = new Date();
	Boolean active;

	List<Cinema> cinema;
}