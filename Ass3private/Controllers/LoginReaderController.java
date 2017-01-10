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
	private Reader reader;
	
	public LoginReaderController()
	{
		System.out.println("enter");
		setReader((Reader)Main.getCurrentUser());
		System.out.println(reader.getName());
		this.setwelcomeText();
		this.setsubscribeText();
	}
	
	private void setReader(Reader reader)
	{
		this.reader=reader;
	}
	public void setwelcomeText()
	{
		welcomeText.setText("Hello " + reader.getName());
	}
	
	public void setsubscribeText()
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
