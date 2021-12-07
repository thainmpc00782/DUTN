package poly.cinema.entity;

import java.util.Date;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SumRevenuesByCinema {
	Long summoney;
	@Id
	Integer movieid;
	String moviename;
}