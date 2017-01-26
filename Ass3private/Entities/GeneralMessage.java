package Entities;

import java.io.Serializable;
/**
 * This class is meant to hold a String to know in MyServer which case is it and what to do accordingly.
 * @author orel zilberman
 *
 */
public class GeneralMessage implements Serializable
{
	private static final long serialVersionUID = 1L;
	public  String actionNow;
}
