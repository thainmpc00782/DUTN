package poly.cinema.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import poly.cinema.entity.Room;

public interface RoomDAO extends JpaRepository<Room, Integer> {
	@Query("SELECT r FROM Room r where cinema.id=?1")
	public List<Room> getBycinemaId(Integer cinemaid);
	@Query("SELECT r FROM Room r where r.name like ?1")
	public List<Room> findByName(String name);
	
	
	@Query("Select r from Room r group by r.name")
	List<Room> ListNameRoom();
	@Query("Select r from Room r where r.cinema.id = :cinemaid and r.name = :name")
	Room getByCinemaAndName(Integer cinemaid, String name);
	@Query("Select r.name from Room r where r.id = :id")
	String getNameById(Integer id);
	@Query(value="Select Top(1) * from Rooms order by desc", nativeQuery = true)
	Room Roomtop1();
	@Query("Select r from Room r where r.cinema.id = :cinema")
	List<Room> getByCinema(Integer cinema);
}
