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
		reader = LoginScreenController.getReaderLogged();
	}
	
	public void onMonth()
	{
		popUpCredit();
		/*System.out.println(reader.getSubscribed());
		if(reader.getSubscribed()==1)//If already subscribed
			outputText.setText("You're already subscribed for month!");
		else
		{
			if(reader.getCardnum().equals(""))
			{
				//Goto "enter credit card" screen 
			}
			else outputText.setText("Subscribed successfully");
		}*/
	}
	
	public void onYear()
	{
		System.out.println("entered");
		if(reader.getSubscribed()==2)//If already subscribed
			outputText.setText("You're already subscribed for year!");
	}
	
	private void popUpCredit()
	{
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/GUI/CreditCardScreen.fxml"));
			primaryStage.setScene(new Scene(root));
			primaryStage.initModality(Modality.APPLICATION_MODAL);
			primaryStage.showAndWait();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
