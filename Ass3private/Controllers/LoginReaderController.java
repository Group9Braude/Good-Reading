package Controllers;

import Entities.Reader;
import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class LoginReaderController {
 
	@FXML
	static
	TextArea welcomeText;
	@FXML
	static
	TextArea subscribeText;
    @FXML
	static ComboBox<?> bookList;
	@FXML
	static Button Subscribe;
	static private Reader reader;
	
	public static void setReader(Reader reader)
	{
		LoginReaderController.reader=reader;
	}
	public static void setwelcomeText()
	{
		welcomeText.setText("Hello "/*+reader.getName()*/);
	}
	
	public static void setsubscribeText()
	{
		int status = reader.getSubscribed();
		switch(status)
		{
		case 0:
			subscribeText.setText("Not subscribed");
			break;
		case 1:
			subscribeText.setText("Monthly subscription");
			break;
		case 2:
			subscribeText.setText("Yearly subscription");
			break;
		default:break;
		}
	} 
	
	public void onSubscribe()
	{
		Main.showSubscriptionScreen();
	}
}
