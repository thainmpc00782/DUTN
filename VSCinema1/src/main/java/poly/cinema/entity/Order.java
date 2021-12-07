package poly.cinema.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "orders")
public class Order  implements Serializable{
	@Id
	String id;
	Integer totalmoney ;
	Integer active ;
	
	@Temporal(TemporalType.TIME)
	@Column(name = "Time")
	Date time = new Date();
	
	@Temporal(TemporalType.DATE)
	@Column(name = "Createdate")
	Date createdate = new Date();	
	
	@ManyToOne
	@JoinColumn(name = "Username")
	Account user;
	
	@JsonIgnore
	@OneToMany(mappedBy = "order")
	List<OrderFood> orderDetails ;
	
	@JsonIgnore
	@OneToMany(mappedBy = "order")
	List<Ticket> tickets ;
}
