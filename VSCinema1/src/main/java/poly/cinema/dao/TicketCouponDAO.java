package poly.cinema.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import poly.cinema.entity.TicketCoupon;

public interface TicketCouponDAO extends JpaRepository<TicketCoupon, Integer>{

	@Query("select t from TicketCoupon t where t.coupon.id =:id and t.account.username=:username")
	TicketCoupon findByIdAndUsername(String id, String username);

}
