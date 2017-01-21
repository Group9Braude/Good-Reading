package Entities;

import java.io.Serializable;
import java.util.ArrayList;

public class Genre extends GeneralMessage implements Serializable{

	private static final long serialVersionUID = 1L;
	public static ArrayList<Genre> genreList=new ArrayList<Genre>();
	private String genre, comments, oldGenre;
	public String query;
	public ArrayList<Theme> themeList;
	
	public Genre(){	
		this.setComments("");
		}
	
	public Genre(String genre){
		this.genre=genre;
	}
	
	public Genre(String genre,String comments) {
		this.genre = genre;
		this.comments=comments;
	}	
	
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
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
	
	@Override
	public String toString()
	{
		return genre;
	}

}