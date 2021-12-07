package poly.cinema.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import poly.cinema.common.ERole;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "roles")
public class Role  implements Serializable{
	@Id
	private String id;
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private ERole name;
	@JsonIgnore
	@OneToMany(mappedBy = "role")
	List<Authority> authorities;
}