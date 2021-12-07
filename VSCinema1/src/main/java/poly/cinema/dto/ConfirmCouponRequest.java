package poly.cinema.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import poly.cinema.entity.Coupon;

@Data
@NoArgsConstructor @AllArgsConstructor
public class ConfirmCouponRequest {

	Coupon coupon;
	String message;
	
}
