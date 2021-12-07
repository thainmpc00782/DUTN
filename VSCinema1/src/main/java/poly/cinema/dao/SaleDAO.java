   
package poly.cinema.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import poly.cinema.entity.Sale;

public interface SaleDAO extends JpaRepository<Sale, Integer>{
	
	@Query("SELECT p FROM Sale p Where food.id=?1")
	List<Sale> findByFoodID(Integer id);
}
 