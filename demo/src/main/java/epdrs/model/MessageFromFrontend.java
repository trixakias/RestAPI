package epdrs.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MessageFromFrontend {

	private String senderID;
	private String receiverID;
	private String text;
	private String date;
	
	
	
	
	public MessageFromFrontend(String senderID, String receiverID, String text) {
		this.senderID = senderID;
		this.receiverID = receiverID;
		this.text = text;
		Date date = Calendar.getInstance().getTime();  
    	DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
    	String strDate = dateFormat.format(date);   
		this.date= strDate;
	}

	

	

	public String getSenderID() {
		return senderID;
	}

	public void setSenderID(String senderID) {
		this.senderID = senderID;
	}

	public String getReceiverID() {
		return receiverID;
	}

	public void setReceiverID(String receiverID) {
		this.receiverID = receiverID;
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
