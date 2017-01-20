package Entities;

import java.io.Serializable;
import java.util.ArrayList;

public class Theme extends GeneralMessage implements Serializable{
	public static ArrayList<Theme> themeList= new ArrayList<Theme>();
	public static ArrayList<String> books;
	private String theme;
	private Genre genre;
	
	public Theme(){}
	
	
	public Theme(String theme, Genre genre) {
		this.theme = theme;
		this.genre=genre;
		this.books=books;
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
}