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
@Table(name = "sales")
public class Sale  implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	Double discount ;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "Startdate")
	Date startDate = new Date();

	@Temporal(TemporalType.DATE)
	@Column(name = "Enddate")
	Date endDate = new Date();
	
	@ManyToOne
	@JoinColumn(name = "Foodsid")
	Food food;
	
	
	


	

	
	
}