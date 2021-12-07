package poly.cinema.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import poly.cinema.entity.Cinema;

public interface CinemaDAO extends JpaRepository<Cinema, Integer> {
	// Tìm Rạp phim theo thành phố
		@Query("SELECT c FROM Cinema c WHERE c.city.id = :cityid")
		List<Cinema> findCinemaByCity(String cityid);
}
