package Entities;

import java.io.Serializable;
/**
 * This class holds all the information that is common to all types of users
 * @author Eran Simhon
 *
 */
public class User extends GeneralMessage implements Serializable
{
	private static final long serialVersionUID = 1L;
	protected String id,password;
	private int type;//1 - reader,2 - librarian,3 - manager
	public static Worker currentWorker;


	
	
	/**
	 * Constructs a user
	 */
	public User(){}
	/**
	 * Constructs a user
	 * @param id the id of the user
	 * @param password the password of the user
	 */
	public User(String id,String password)
	{  
		this.id=id;
		this.password = password;
	}
	/**
	 * Get the ID of the user
	 * @return the ID of the user
	 */
	public String getID()
	{
		return id;
	}
	/**
	 * Set the ID of the user
	 * @param id the new ID
	 */
	public void setID(String id){
		this.id = id;
	}
	/**
	 * Get the password of the user
	 * @return
	 */
	public String getPassword()
	{
		return password;
	}
	/** 
	 * set a new password
	 * @param password the new password
	 */
	public void setPassword(String password){
		this.password = password;
	}
	/**
	 * returns the type of the user
	 * @return
	 */
	public int getType()
	{
		return type;
	}
	/**
	 * set a the type of the user
	 * @param type the new type
	 */
	public void setType(int type)
	{
		this.type=type;
	}
}
