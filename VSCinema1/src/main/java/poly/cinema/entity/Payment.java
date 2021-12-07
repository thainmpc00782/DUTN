package poly.cinema.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
@Entity 
@Table(name = "payments")
public class Payment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	Integer vnp_Amount;
	String vnp_Orderinfo;
	String vnp_Ordertype;
	String vnp_Bankcode;
	String vnp_Currcode;
	String vnp_Locale;
	String vnp_Createdate;
	String vnp_Responsecode;
	String vnp_Transactionno;
	String vnp_Transactionstatus;
	
	@ManyToOne
	@JoinColumn(name = "vnp_Txnref")
	Order vnp_TxnRef;
}
