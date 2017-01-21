package Entities;

import java.io.Serializable;
import java.util.ArrayList;

public class Theme extends GeneralMessage implements Serializable{
	private static final long serialVersionUID = 1L;
	public static ArrayList<Theme> themeList= new ArrayList<Theme>();
	public ArrayList<String> books;
	private String theme;
	private String genre;
	
	public Theme(){}
	
	
	public Theme(String theme, String genre) {
		this.theme = theme;
		this.genre=genre;
	}

	
	public String getGenre() {
		return genre;
	}


	public void setGenre(String genre) {
		this.genre = genre;
	}


	public void setTheme(String theme) {
		this.theme = theme;
	}
	public String getTheme() {
		return theme;
	}
}