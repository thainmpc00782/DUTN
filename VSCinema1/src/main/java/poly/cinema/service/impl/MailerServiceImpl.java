package poly.cinema.service.impl;

import javax.mail.MessagingException;




import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import poly.cinema.entity.MailModel;
import poly.cinema.service.MailerService;

@Service
public class MailerServiceImpl implements MailerService{

	@Autowired
	JavaMailSender sender;
	
	@Override
	public void send(MailModel mail) throws MessagingException {
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
		helper.setTo(mail.getTo());
		helper.setSubject(mail.getSubject());
		helper.setText(mail.getBody(), true);
		sender.send(message);
	}

	@Override
	public void send(String to, String subject, String body) throws MessagingException {
		this.send(new MailModel(to, subject, body));
	}

}
