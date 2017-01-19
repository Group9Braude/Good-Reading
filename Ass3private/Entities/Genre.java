package Entities;

import java.io.Serializable;
import java.util.ArrayList;

public class Genre extends GeneralMessage implements Serializable{

	private static final long serialVersionUID = 1L;
	public static ArrayList<Genre> genreList;
	private String genre, comments, oldGenre;
	public String query;
	private int bookNum;
	public static ArrayList<Theme> themeList;
	
	public Genre(){	
		genreList=new ArrayList<Genre>();
		this.setComments("");
		this.setBookNum(0);
		}
	
	public Genre(String genre){
		this.genre=genre;
	}
	
	public Genre(String genre,String comments) {
		this.genre = genre;
		this.bookNum=bookNum;
		this.comments=comments;
	}	
	
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public int getBookNum() {
		return bookNum;
	}

	public void setBookNum(int bookNum) {
		this.bookNum = bookNum;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	public String getGenre() {
		return genre;
	}

	public String getOldGenre() {
		return oldGenre;
	}

	public void setOldGenre(String oldGenre) {
		this.oldGenre = oldGenre;
	}

}