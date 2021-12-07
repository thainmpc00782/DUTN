package poly.cinema.service;

import java.text.ParseException;
import java.util.List;

import poly.cinema.dto.ShowtimesRequest;
import poly.cinema.entity.Movie;
import poly.cinema.entity.Show;

public interface ShowService {
	List<Show> getAll();
	Show create(Show show);
	void deleteById(Integer id);
	Show getOne(Integer id);
	List<Show> fillByMovie(Integer movieid);
	List<Show> fillByMovieAndRoom(Integer movieid, String roomname);
	List<Show> fillByRoom(String roomname);
	List<String> listShowDate();
	Show update(Show show);
	List<Show> getShowCensor();
	List<Show> findMovieByShowdate();
	List<ShowtimesRequest> getDate();
	List<Show> getShowDate(Integer id, ShowtimesRequest showtimes) throws ParseException;
	Show getShow(Integer id) throws ParseException;
}
