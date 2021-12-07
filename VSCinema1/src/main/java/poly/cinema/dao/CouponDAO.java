package poly.cinema.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import poly.cinema.entity.Coupon;

public interface CouponDAO extends JpaRepository<Coupon, String>{

}