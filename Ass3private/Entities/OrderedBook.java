package Entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class OrderedBook extends GeneralMessage
{	
	private static final long serialVersionUID = 1L;
	private String readerID,title;
	private String author;
	private String purchasedate;
	private int bookid;

	public OrderedBook(){}
	
	public OrderedBook(String readerID,int bookid,String title, String author)
	{
		this.readerID=readerID;
		this.bookid=bookid;
		this.title=title;
		this.author=author;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		purchasedate=LocalDate.now().format(formatter);
	}
	public String getPurchaseDate(){
		return purchasedate;
	}
	public String getReaderID() {
		return readerID;
	}
	public void setReaderID(String readerID) {
		this.readerID = readerID;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public int getBookid() {
		return bookid;
	}
	public void setBookid(int bookid) {
		this.bookid = bookid;
	} 
	@Override
	public String toString()
	{	
		return title + " by " + author;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(this == null || obj == null)
			return false;
		OrderedBook other = (OrderedBook)obj;
		if(this.getReaderID().equals(other.getReaderID()) && this.getBookid()==other.getBookid())
			return true;
		return false;
	}
}
