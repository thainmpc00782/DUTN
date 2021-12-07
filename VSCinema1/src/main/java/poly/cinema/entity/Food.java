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
@Table(name = "foods")
public class Food implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	String name;
	String description;
	String image;
	Integer price;

	@Temporal(TemporalType.DATE)
	@Column(name = "Createdate")
	Date createdate = new Date();
	Boolean active;

	@ManyToOne
	@JoinColumn(name = "Cinemasid")
	Cinema cinema;

	@JsonIgnore
	@OneToMany(mappedBy = "food")
	List<Sale> sales;

	@JsonIgnore
	@OneToMany(mappedBy = "food")
	List<PriceHistory> priceHistories;

	@JsonIgnore
	@OneToMany(mappedBy = "food")
	List<OrderFood> orderDetails;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Cinema getCinema() {
		return cinema;
	}

	public void setCinema(Cinema cinema) {
		this.cinema = cinema;
	}

	public List<Sale> getSales() {
		return sales;
	}

	public void setSales(List<Sale> sales) {
		this.sales = sales;
	}

	public List<PriceHistory> getPriceHistories() {
		return priceHistories;
	}

	public void setPriceHistories(List<PriceHistory> priceHistories) {
		this.priceHistories = priceHistories;
	}

	public List<OrderFood> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderFood> orderDetails) {
		this.orderDetails = orderDetails;
	}

}