package Entities;

public class OrderedBook extends GeneralMessage
{	
	private static final long serialVersionUID = 1L;
	private String readerID,title,author;
	private int bookid;
	
	public OrderedBook(){}
	
	public OrderedBook(String readerID,int bookid,String title, String author)
	{
		this.readerID=readerID;
		this.bookid=bookid;
		this.title=title;
		this.author=author;
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
}
