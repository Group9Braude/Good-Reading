package Entities;

import java.io.Serializable;
import java.util.ArrayList;

public class Theme extends GeneralMessage implements Serializable{
	private static final long serialVersionUID = 1L;
	public static ArrayList<Theme> themeList= new ArrayList<Theme>();
	public ArrayList<Book> books;
	private String theme, oldTheme;
	private Genre genre;
	
	public Theme(){
		books=new ArrayList<Book>();
	}
	
	
	public Theme(String theme, String genreName) {
		this.theme = theme;
		genre = new Genre(genreName);
	}

	
	public Genre getGenre() {
		return genre;
	}


	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}
	
	public String getTheme() {
		return theme;
	}
	
	@Override
	public String toString()
	{
		return theme;
	}

	public String getOldTheme() {
		return oldTheme;
	}


	public void setOldTheme(String oldTheme) {
		this.oldTheme = oldTheme;
	}
}