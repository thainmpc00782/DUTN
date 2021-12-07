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
@Table(name = "orderfoods")
public class OrderFood  implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	Integer price ;
	Integer quantity ;
	Double discount ;
	
	@ManyToOne
	@JoinColumn(name = "Foodsid")
	Food food;
	
	@ManyToOne
	@JoinColumn(name = "Orderid")
	Order order;
	
	
}
