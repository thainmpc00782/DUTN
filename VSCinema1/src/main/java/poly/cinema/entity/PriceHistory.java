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
@Table(name = "price_history")
public class PriceHistory implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	Integer price;

	@Temporal(TemporalType.TIME)
	@Column(name = "Time")
	Date time = new Date();

	@Temporal(TemporalType.DATE)
	@Column(name = "Createdate")
	Date createdate = new Date();

	@ManyToOne
	@JoinColumn(name = "Foodsid")
	Food food;

	@ManyToOne
	@JoinColumn(name = "Username")
	Account user;

	public PriceHistory(Account user,Food food, Integer price, Date createdate, Date time ) {
		super();
		this.user = user;
		this.food = food;
		this.price = price;
		this.createdate = createdate;
		this.time = time;
		
	}

}