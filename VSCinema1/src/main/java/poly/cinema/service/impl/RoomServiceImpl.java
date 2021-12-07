package poly.cinema.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import poly.cinema.dao.RoomDAO;
import poly.cinema.entity.Room;
import poly.cinema.service.RoomService;

@Service
public class RoomServiceImpl implements RoomService {
	@Autowired
	RoomDAO dao;

	@Override
	public List<Room> getAll() {
		return dao.findAll();
	}

	@Override
	public Room save(Room room) {
		room.setActivity(true);
		return dao.save(room);
	}

	@Override
	public void delete(Integer id) {
		dao.deleteById(id);
	}

	@Override
	public Room update(Room room) {
		// TODO Auto-generated method stub
		return dao.save(room);
	}

	@Override
	public List<Room> findByCinemaId(Integer cinemaid) {
		// TODO Auto-generated method stub
		return dao.getBycinemaId(cinemaid);
	}

	@Override
	public List<Room> findByName(String name) {
		return dao.findByName('%'+name+"%");
	}

	@Override
	public Room getByCinemaAndName(Integer cinemaid, String name) {
		System.out.println(dao.getByCinemaAndName(cinemaid,name));
		return dao.getByCinemaAndName(cinemaid,name);
	}

	@Override
	public Room getOne(Integer id) {
		return dao.getById(id);
	}

	@Override
	public String getNameByID(Integer id) {
		return dao.getNameById(id);
	}

	@Override
	public Room Roomtop1() {
		return dao.Roomtop1();
	}

	@Override
	public Room create(Room room) {
		return dao.save(room);
	}
	@Override
	public List<Room> getListName() {
		// TODO Auto-generated method stub
		return dao.ListNameRoom(); 
	}
}