package epdrs.model;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="message")
public class Message {
	
	@Id
	private String id;
	private Credential sender;
	private Credential receiver;
	private String text;
	private String date;
	
	public Message() {}
	
	public Message(Credential sender, Credential receiver, String text) {
		this.sender = sender;
		this.receiver = receiver;
		this.text = text;
		Date date = Calendar.getInstance().getTime();  
    	DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
    	String strDate = dateFormat.format(date);   
		this.date= strDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Credential getSender() {
		return sender;
	}

	public void setSender(Credential sender) {
		this.sender = sender;
	}

	public Credential getReceiver() {
		return receiver;
	}

	public void setReceiver(Credential receiver) {
		this.receiver = receiver;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	
	
	
}
