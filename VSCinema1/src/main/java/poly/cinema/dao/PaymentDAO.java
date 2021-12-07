package poly.cinema.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import poly.cinema.entity.Payment;

public interface PaymentDAO extends JpaRepository<Payment, Integer>{

	@Query("select p from Payment p where p.vnp_TxnRef.id =:vnp_TxnRef")
	Payment findByOrderID(String vnp_TxnRef);

}
