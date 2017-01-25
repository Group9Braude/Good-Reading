package Entities;

import java.util.ArrayList;
 
public class Reader extends User
{
	private static final long serialVersionUID = 1L;
	private int subscribed;//0 - not subscribed, 1 - month, 2 - year
	private int isLogged;
	private String firstName,lastName,id;
	private int IBookValid,allowed,debt,isFrozen,premission;
	private String cardnum,secCode,expDate;
	private ArrayList<OrderedBook> myBooks = new ArrayList<OrderedBook>();
	public String query;
	
	public Reader(){}// LOOK ERAN! ADDED! Answer: I didn't need it so I didn't add it
	
	public Reader(String readerID, String pass, String firstName, String lastName,int subscribed, int iBookValid,
			int allowed, int debt, int isFrozen, int premission, String cardnum, String expDate, String secCode) {
		super(readerID, pass);
		this.subscribed = subscribed;
		this.firstName = firstName;
		this.lastName = lastName;
		IBookValid = iBookValid;
		this.allowed = allowed;
		this.debt = debt;
		this.isFrozen = isFrozen;
		this.premission = premission;
		this.cardnum = cardnum;
		this.secCode = secCode;
		this.expDate = expDate;
	}

	public Reader(String id,String password,String firstname,String lastname){
		super(id,password);
		this.firstName=firstname;
		this.lastName=lastname;
		this.id=id;
	}
	 
	public Reader(String id, String password) 
	{
		super(id, password);
		
	}
	public int getIsLogged() {
		return isLogged;
	}
	public void setIsLogged(int isLogged) {
		this.isLogged = isLogged;
	}   
	public int getSubscribed() {
		return subscribed;
	}
	public void setSubscribed(int subscribed) {
		this.subscribed = subscribed;
	}
	public String getCardnum() {
		return cardnum;
	}
	public void setCardnum(String cardnum) {
		this.cardnum = cardnum;
	}
	public String getSecCode() {
		return secCode;
	}
	public void setSecCode(String secCode) {
		this.secCode = secCode;
	}
	public String getExpDate() {
		return expDate;
	}
	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getIBookValid() {
		return IBookValid;
	}
	public void setIBookValid(int iBookValid) {
		IBookValid = iBookValid;
	}
	public int getAllowed() {
		return allowed;
	}
	public void setAllowed(int allowed) {
		this.allowed = allowed;
	}
	public int getDebt() {
		return debt;
	}
	public void setDebt(int debt) {
		this.debt = debt;
	}
	public int getIsFrozen() {
		return isFrozen;
	}
	public void setIsFrozen(int isFrozen) {
		this.isFrozen = isFrozen;
	}
	public int getPremission() {
		return premission;
	}
	public void setPremission(int premission) {
		this.premission = premission;
	}

	public ArrayList<OrderedBook> getMyBooks() {
		return myBooks;
	}

	public void setMyBooks(ArrayList<OrderedBook> myBooks) {
		this.myBooks = myBooks;
	}

}
