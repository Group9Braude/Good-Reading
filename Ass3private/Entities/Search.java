package Entities;

import java.io.Serializable;
/**
 * Search class helps by sending the server a specific type of variable:
 *      bookid- we want to see statistics of
 *      from- a date, we want to see statistics until
 * <p>
 * Useful for Manager's reports screen; get statistics by date option
 * @author ozdav
 *
 */
public class Search extends GeneralMessage implements Serializable{
	private static final long serialVersionUID = 1L;
	private int bookid;
	private String from,until;
/**
 * Construct a search type 
 * @param bookid Book's id
 * @param from Date- from this date, statistics would be shown
 * @param until Date- until this date, statistics would be shown
 */
	public Search(int bookid,String from,String until){
		this.setBookid(bookid);
		this.setFrom(from);
		this.setUntil(until);
	}
/**
 *Gets from date
 * @return String which represents the from date
 */
	public String getFrom() {
		return from;
	}
/**
 * Sets from date
 * @param from String represents the new date 
 */
	public void setFrom(String from) {
		this.from = from;
	}
/**
 * Gets book id
 * @return book's id
 */
	public int getBookid() {
		return bookid;
	}
/**
 * Sets a bookid
 * @param bookid Integer represented the new book's ID
 */
	public void setBookid(int bookid) {
		this.bookid = bookid;
	}
/**
 * Gets until date
 * @return String, represents the until date
 */
	public String getUntil() {
		return until;
	}
/**
 * Sets a new Until date
 * @param until String represents the new until date
 */
	public void setUntil(String until) {
		this.until = until;
	}




}