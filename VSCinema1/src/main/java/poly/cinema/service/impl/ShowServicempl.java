package poly.cinema.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import poly.cinema.dao.CinemaDAO;
import poly.cinema.dao.RoomDAO;
import poly.cinema.dao.ShowDAO;
import poly.cinema.dto.ShowtimesRequest;
import poly.cinema.entity.Movie;
import poly.cinema.entity.Room;
import poly.cinema.entity.Show;
import poly.cinema.service.ShowService;

@Service
public class ShowServicempl implements ShowService{
@Autowired ShowDAO showdao;
@Autowired CinemaDAO cinemadao;
@Autowired RoomDAO roomdao;
	@Override
	public List<Show> getAll() {
		return showdao.getAll();
	}


	@Override
	public Show create(Show show) {	
		return showdao.save(show);
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		Show show = showdao.findById(id).get();
		show.setActivity(2);
		showdao.save(show);
	}

	@Override
	public Show getOne(Integer id) {
		// TODO Auto-generated method stub
		return showdao.findById(1).get();
	}


	@Override
	public List<Show> fillByMovie(Integer movieid) {
		// TODO Auto-generated method stub
		return showdao.findByMovie(movieid);
	}
	@Override
	public List<Show> fillByRoom(String roomname) {
		// TODO Auto-generated method stub
		return showdao.findByRoom(roomname);
	}
	@Override
	public List<Show> fillByMovieAndRoom(Integer movieid, String roomname) {
		// TODO Auto-generated method stub
		return showdao.findByMovieAndRoom(movieid, roomname);
	}
	
	@Override
	public List<String> listShowDate(){
		return showdao.listShowDate();
	}


	@Override
	public Show update(Show show) {
		return showdao.save(show);
	}


	@Override
	public List<Show> getShowCensor() {
		List<Show> list = new ArrayList<Show>();
		List<Show> findAll = showdao.findAll();
		Date date = new Date();
		findAll.forEach(s ->{
			if(s.getShowDate().getTime() >= date.getTime()) {
				list.add(s);
			}
		});
		return list;
	}
	
	@Override
	public List<Show> findMovieByShowdate() {
		// TODO Auto-generated method stub
		return showdao.findMovieByShowdate();
	}
	
	@Override
	public List<ShowtimesRequest> getDate() {
		Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
		List<ShowtimesRequest> date = new ArrayList<ShowtimesRequest>();
		ShowtimesRequest showtimes1 = new ShowtimesRequest();
		showtimes1.setId(0);
		showtimes1.setDate(cld.getTime());
		date.add(showtimes1);
		for(int i = 0; i < 6; i++) {
			cld.add(Calendar.DATE, 1);
			ShowtimesRequest showtimes2 = new ShowtimesRequest();
			showtimes2.setId(0);
			showtimes2.setDate(cld.getTime());
			date.add(showtimes2);
		}
		return date;
	}
	
	@Override
	public List<Show> getShowDate(Integer id, ShowtimesRequest showtimes) throws ParseException {
		Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
		Date now = cld.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Show> show = showdao.getShowDate(showtimes.getDate(), id);
		List<Show> listShow = new ArrayList<Show>();
		for(Show s:show) {
			String date = s.getShowDate()+" "+s.getStartTime();
			Date showdate = formatter.parse(date);
			if((now.getTime()+900000) < showdate.getTime()) {
				listShow.add(s);
			}
		}
		return listShow;
	}
	
	@Override
	public Show getShow(Integer id) throws ParseException {
		Show show = showdao.findById(id).get();
		Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
		Date now = cld.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = show.getShowDate()+" "+show.getStartTime();
		Date showdate = formatter.parse(date);
		if((now.getTime()+900000) < showdate.getTime()) {
			return show;
		}else {
			return null;
		}
	}
	
}
