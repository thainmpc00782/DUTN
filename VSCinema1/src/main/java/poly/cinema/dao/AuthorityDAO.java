package poly.cinema.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import poly.cinema.entity.Authority;

public interface AuthorityDAO extends JpaRepository<Authority, Integer>{
	 @Query("Select a from Authority a where a.role.id = 'STA'")
		List<Authority> getListStaf();
		@Query("Select a from Authority a where a.role.id = 'US'")
		List<Authority> getListUser();
}
