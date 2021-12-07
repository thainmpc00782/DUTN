package poly.cinema.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity 
@Table(name = "coupons")
public class Coupon  implements Serializable{
	@Id	
	String id;
	Double discount ;
	Integer quantity ;
	Boolean activity;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "Expiry")
	Date expiry = new Date();
	
	@Temporal(TemporalType.DATE)
	@Column(name = "Createdate")
	Date createdate = new Date();

	
	
}