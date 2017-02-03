package Entities;

import java.io.Serializable;
import java.util.ArrayList;

public class themesBooks extends GeneralMessage implements Serializable{
	private static final long serialVersionUID = 1L;
	public ArrayList<String> theme;	
	public ArrayList<Integer> bookid;
	
	
	public themesBooks(){
		theme=new ArrayList<String>();
		bookid=new ArrayList<Integer>();
	}		
}