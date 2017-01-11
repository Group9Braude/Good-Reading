package Controllers;

import Entities.Reader;
import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class LoginReaderController {
 
	@FXML
	TextField welcomeText;
	@FXML
	TextField subscribeText;
    @FXML
	static ComboBox<?> bookList;
	@FXML
	Button Subscribe;
	private Reader reader;
	@FXML
	private void initialize()
	{
		reader=((Reader)Main.getCurrentUser());
		welcomeText.setText("Hello " + reader.getFirstName());
		this.setSubscribeText();
	}
	
	public void setSubscribeText()
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
