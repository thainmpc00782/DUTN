package poly.cinema.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import poly.cinema.entity.Order;

public interface OrderDAO extends JpaRepository<Order, String> {

	@Query("Select o from Order o where o.createdate=?1")
	List<Order> findByDate(Date createdate);
	
	@Query("select YEAR(createdate), sum(totalmoney) from Order o where  YEAR(createdate) > (YEAR(CURDATE())-5) and YEAR(createdate) < (YEAR(CURDATE())+5) group by YEAR(createdate)") 
	List<Object[]> getListSumRevebues();
	@Query("select sum(o.totalmoney) from Order o where o.createdate = :now group by o.totalmoney")
	Long revenueOfDay(Date now);
	@Query("select sum(o.totalmoney) from Order o where MONTH(createdate) = MONTH(CURRENT_DATE())")
	Long revenueOfMonth(Date now);
	@Query("select sum(o.totalmoney) from Order o where YEAR(createdate) = YEAR(CURRENT_DATE())")
	Long revenueOfYears(Date now);

	@Query("select o from Order o")
	List<Order> findByActivity(int i);

	@Query("select o from Order o where o.user.username=:username ORDER BY o.createdate DESC")
	List<Order> findByUsername(String username);
}