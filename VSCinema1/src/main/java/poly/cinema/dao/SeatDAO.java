package poly.cinema.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import poly.cinema.entity.Seat;

public interface SeatDAO extends JpaRepository<Seat, Integer> {
	@Query("Select s from Seat s where s.room.id=?1 AND s.activity = true")
	public List<Seat> findByRoomId(Integer roomid);
}
