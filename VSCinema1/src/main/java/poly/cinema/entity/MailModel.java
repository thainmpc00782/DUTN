package poly.cinema.entity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class MailModel {

	String from;
	String to;
	String subject;
	String body;
	List<String> cc = new ArrayList<String>();
	List<String> bcc = new ArrayList<String>();
	List<File> files = new ArrayList<>();
	public MailModel(String to, String subject, String body) {
		super();
		this.to = to;
		this.subject = subject;
		this.body = body;
	}
	
	
}
