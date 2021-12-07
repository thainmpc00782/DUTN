package poly.cinema.service;

import java.util.List;

import poly.cinema.dto.ConfirmCouponRequest;
import poly.cinema.dto.CouponRequest;
import poly.cinema.entity.Coupon;

public interface CouponService {

	List<Coupon> findAll();

	CouponRequest autoCreateCoupons();

	Coupon create(Coupon coupon);

	Coupon update(Coupon coupon);

	ConfirmCouponRequest findById(String id);

}