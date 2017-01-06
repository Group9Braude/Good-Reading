package Entities;

import java.io.Serializable;

public class CreditCard extends GeneralMessage implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String cardNum,expMonth,expYear,secCode,id;
	public CreditCard(String cardNum,String expMonth,String expYear,String secCode,String id)
	{
		this.cardNum=cardNum;
		this.setExpMonth(expMonth);
		this.setExpYear(expYear);
		this.secCode=secCode;
		this.id=id;
	}
	public String getSecCode() {
		return secCode;
	}
	public void setSecCode(String secCode) {
		this.secCode = secCode;
	}
	public String getCardNum() {
		return cardNum;
	}
	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	public String getId() {
		return id;  
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getExpMonth() {
		return expMonth;
	}
	public void setExpMonth(String expMonth) {
		this.expMonth = expMonth;
	}
	public String getExpYear() {
		return expYear;
	}
	public void setExpYear(String expYear) {
		this.expYear = expYear;
	}
	
	public String getExpDate()
	{
		return expMonth + "/" + expYear;
	}

}
