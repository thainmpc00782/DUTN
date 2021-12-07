package poly.cinema.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import poly.cinema.dto.ConfirmCouponRequest;
import poly.cinema.dto.CouponRequest;
import poly.cinema.entity.Coupon;
import poly.cinema.service.CouponService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/coupons")
public class CouponRestController {

	@Autowired
	CouponService couponService;
	
	@GetMapping
	public List<Coupon> listCoupons(){
		return couponService.findAll();
	}
	
	@GetMapping("/autoCreate")
	public CouponRequest autoCreateCoupons(){
		return couponService.autoCreateCoupons();
	}
	
	@GetMapping("/{id}")
	public ConfirmCouponRequest getCoupon(@PathVariable("id") String id){
		return couponService.findById(id);
	}
	
	@PostMapping
	public Coupon create(@RequestBody Coupon coupon) {
		return couponService.create(coupon);
	}
	
	@PutMapping
	public Coupon update(@RequestBody Coupon coupon) {
		return couponService.update(coupon);
	}
	
}