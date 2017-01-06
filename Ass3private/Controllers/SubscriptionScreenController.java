package Controllers;

import java.io.IOException;

import Entities.Reader;
import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
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
		reader = (Reader)Main.getCurrentUser();
	}

	public void onMonth()
	{
		if(reader.getSubscribed()==1)//If already subscribed
			outputText.setText("You're already subscribed for month!");
		else
		{
			if(reader.getCardnum()==null)
				popUpCredit();
			else outputText.setText("Subscribed successfully");//In case the user already has a credit card stored in the db
		}
	}

	public void onYear()
	{
		System.out.println("entered");
		if(reader.getSubscribed()==2)//If already subscribed
			outputText.setText("You're already subscribed for year!");
	}

	private void popUpCredit()
	{
		if(reader.getCardnum()==null)
		{
			try {
				mainLayout = FXMLLoader.load(Main.class.getResource("/GUI/CreditCardScreen.fxml"));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			Main.popup.setScene(new Scene(mainLayout));
			Main.popup.show();
		}
	}


}
