package Entities;

import java.io.Serializable;
/**
 * This class contains info about credit cards
 * @author Eran Simhon
 *
 */

public class CreditCard extends GeneralMessage implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String cardNum,expMonth,expYear,secCode,id;
	/**
	 * Constructs a credit card
	 * @param cardNum 16 digits
	 * @param expMonth expiration month
	 * @param expYear expiration year
	 * @param secCode 3 digits on the back
	 * @param id The reader id this card belongs to
	 */
	public CreditCard(String cardNum,String expMonth,String expYear,String secCode,String id)
	{
		this.cardNum=cardNum;
		this.setExpMonth(expMonth);
		this.setExpYear(expYear);
		this.secCode=secCode;
		this.id=id;
	}
	/**
	 * get this card's security code
	 * @return a string representing the security code
	 */
	public String getSecCode() {
		return secCode;
	}
	/**
	 * Setting the security code
	 * @param secCode
	 */
	public void setSecCode(String secCode) {
		this.secCode = secCode;
	}
	/**
	 * get this card's 16 digits
	 * @return a string representing this card's 16 digits
	 */
	public String getCardNum() {
		return cardNum;
	}
	/**
	 * set this card's 16 digits
	 * @param cardNum new 16 digits
	 */
	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	/**
	 * get the owner's reader id
	 * @return a string representing the reader id
	 */
	public String getId() {
		return id;  
	}
	/**
	 * Set the reader ID of the owner
	 * @param id the reader id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * get the expiration month of this card
	 * @return the expiration month as a string
	 */
	public String getExpMonth() {
		return expMonth;
	}
	/**
	 * set the expiration month
	 * @param expMonth
	 */
	public void setExpMonth(String expMonth) {
		this.expMonth = expMonth;
	}
	/**
	 * Get the expiration year
	 * @return the expiration year as a string
	 */
	public String getExpYear() {
		return expYear;
	}
	/**
	 * set the expiration year
	 * @param expYear the new expiration year
	 */
	public void setExpYear(String expYear) {
		this.expYear = expYear;
	}
	/**
	 * Return a string formatted in the following way: MM/YY
	 * @return the string in the specified above format
	 */
	public String getExpDate()
	{
		return expMonth + "/" + expYear;
	}

}
