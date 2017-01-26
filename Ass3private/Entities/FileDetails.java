package Entities;

public class FileDetails extends GeneralMessage{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int bookid;
	private String type;
	public FileDetails(int bookid,String type)
	{
		this.bookid = bookid;
		this.type = type;
	}
	public int getBookid() {
		return bookid;
	}
	public void setBookid(int bookid) {
		this.bookid = bookid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	

}
