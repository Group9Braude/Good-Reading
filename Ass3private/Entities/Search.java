package Entities;

import java.io.Serializable;

public class Search extends GeneralMessage implements Serializable{
	private static final long serialVersionUID = 1L;
	private int bookid;
	private String from,until;
	public Search(int bookid,String from,String until){
		this.setBookid(bookid);
		this.setFrom(from);
		this.setUntil(until);
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public int getBookid() {
		return bookid;
	}
	public void setBookid(int bookid) {
		this.bookid = bookid;
	}
	public String getUntil() {
		return until;
	}
	public void setUntil(String until) {
		this.until = until;
	}




}