package poly.cinema.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import poly.cinema.entity.OrderFood;

public interface OrderFoodDAO extends JpaRepository<OrderFood, Integer> {
	@Query("SELECT orf FROM OrderFood orf where orf.order.id=?1")
	public List<OrderFood> findByOrderId(String orderid);

	@Query("SELECT orf.order.createdate,sum((orf.price*orf.quantity) - (orf.price.discount)) FROM OrderFood orf group by orf.order.createdate")
	public List<Object[]> getSumPriceOrderFood();
	@Query("select o from OrderFood o where o.order.time between '00:00:00' and :timeend")
	List<OrderFood> getSumFoodByTimes(Date time,Date timeend);
}
