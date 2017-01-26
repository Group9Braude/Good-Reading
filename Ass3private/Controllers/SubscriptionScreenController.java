package Controllers;

import java.io.IOException;

import Entities.Reader;
import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import ocsf.client.AbstractClient;

public class SubscriptionScreenController extends AbstractClient
{
	@FXML 
	Button back,year,month; 
	@FXML TextField outputText;
	private static Pane mainLayout;
	private Reader reader;
	/**
	 * Open the connection to the server and get the currently logged in reader
	 */
	public SubscriptionScreenController() 
	{
		super(Main.host, Main.port);
		try {
			this.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		reader = (Reader)Main.getCurrentUser();
	}
	/**
	 * Go back to the main reader login screen
	 */
	public void onBack()
	{
		Main.showReaderLoginScreen();
	}
	/**
	 * If not already subscribed for a month, set this user as a subscribed user for a month
	 */
	public void onMonth()
	{
		if(reader.getSubscribed()==1)//If already subscribed
			outputText.setText("You're already subscribed for month!");
		else if(reader.getCardnum().equals(""))
			outputText.setText("No credit card associated with your account was found");
		else
		{
			Main.getCurrentUser().actionNow="Monthly";
			try {
				this.sendToServer(Main.getCurrentUser());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	/**
	 * If not already subscribed for a year, set this user as a subscribed user for a year
	 */
	public void onYear()
	{
		if(reader.getSubscribed()==2)//If already subscribed
			outputText.setText("You're already subscribed for year!");
		else if(reader.getCardnum().equals(""))
			outputText.setText("No credit card associated with your account was found");
		else
		{
			Main.getCurrentUser().actionNow = "Yearly";
			try {
				this.sendToServer(Main.getCurrentUser());
			} catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}
	

	/*public void popUpCredit()
	{
		try {
			mainLayout = FXMLLoader.load(Main.class.getResource("/GUI/CreditCardScreen.fxml"));
			Main.popup.setScene(new Scene(mainLayout));
			Main.popup.showAndWait();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}*/
	/**
	 * Handle the message that returns from the server
	 */
	@Override
	protected void handleMessageFromServer(Object msg) 
	{
		if(msg instanceof Integer)
		{
			((Reader)Main.getCurrentUser()).setSubscribed((int)msg);
			outputText.setText("Subscribed successfully");
		}
	}
}
