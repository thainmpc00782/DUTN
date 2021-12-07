package poly.cinema.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import poly.cinema.dao.SeatDAO;
import poly.cinema.entity.Seat;
import poly.cinema.service.SeatService;

@Service
public class SeatServiceImpl implements SeatService {
	@Autowired
	SeatDAO seatDAO;

	@Override
	public List<Seat> findByRoomId(Integer roomid) {
		return seatDAO.findByRoomId(roomid);
	}

	@Override
	public Seat create(Seat seat) {
		return seatDAO.save(seat);
	}

	@Override
	public Seat update(Seat seat) {
		return seatDAO.save(seat);
	}

}