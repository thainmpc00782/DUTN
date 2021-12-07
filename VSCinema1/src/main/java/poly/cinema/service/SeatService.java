
package poly.cinema.service;

import java.util.List;

import poly.cinema.entity.Seat;

public interface SeatService {
	
	List<Seat> findByRoomId(Integer roomid);

	Seat create(Seat seat);

	Seat update(Seat seat);
}