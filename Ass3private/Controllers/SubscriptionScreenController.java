package Controllers;

import Entities.Reader;
import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SubscriptionScreenController 
{
	@FXML 
	Button back,year,month;
	@FXML TextField outputText;
	private static Stage primaryStage;
	private static Pane mainLayout;
	private Reader reader;
	public void onBack()
	{
		Main.showReaderLoginScreen();
	}
	public SubscriptionScreenController()
	{
		primaryStage=Main.getStage();
		reader = LoginScreenController.getReaderLogged();
	}
	
	public void onMonth()
	{
		System.out.println(reader.getSubscribed());
		if(reader.getSubscribed()==1)//If already subscribed
			outputText.setText("You're already subscribed for month!");
		else
		{
			if(reader.getCardnum().equals(""))
			{
				//Goto "enter credit card" screen 
			}
			else outputText.setText("Subscribed successfully");
		}
	}
	
	public void onYear()
	{
		System.out.println("entered");
		if(reader.getSubscribed()==2)//If already subscribed
			outputText.setText("You're already subscribed for year!");
	}
	
	
	
}
