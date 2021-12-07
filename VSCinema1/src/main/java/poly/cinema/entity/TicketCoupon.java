package poly.cinema.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity 
@Table(name = "tickets_coupons")
public class TicketCoupon  implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	
	
	@Temporal(TemporalType.DATE)
	@Column(name = "Dateuse")
	Date expiry = new Date();
	
	@ManyToOne
	@JoinColumn(name = "Couponsid")
	Coupon coupon;
	
	@ManyToOne
	@JoinColumn(name = "Username")
	Account account;
}
