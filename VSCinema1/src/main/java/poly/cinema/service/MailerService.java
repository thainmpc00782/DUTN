package poly.cinema.service;

import javax.mail.MessagingException;

import poly.cinema.entity.MailModel;


public interface MailerService {

	void send(MailModel mail) throws MessagingException;
	
	void send(String to, String subject, String body) throws MessagingException;
	
}
