package Entities;

import java.io.Serializable;

public class User extends GeneralMessage implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String id,password;
	private int type;//1 - reader,2 - librarian,3 - manager
	public static Worker currentWorker;


	
	
	
	public User(){}
	
	public User(String id,String password)
	{  
		this.id=id;
		this.password = password;
	}
	
	public String getID()
	{
		return id;
	}
	
	public String getPassword()
	{
		return password;
	}
	public int getType()
	{
		return type;
	}
	public void setType(int type)
	{
		this.type=type;
	}
}
