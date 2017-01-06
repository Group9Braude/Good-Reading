package Controllers;

import java.io.IOException;

import Entities.CreditCard;
import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import ocsf.client.AbstractClient;

public class CreditCardController extends AbstractClient
{
	
	@FXML TextField cardNumField;
	@FXML TextField monthField,yearField;
	@FXML TextField codeField;
	
	public CreditCardController() {
		super(Main.host, Main.port);
		try {
			this.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void onConfirm()
	{
		CreditCard newCard = new CreditCard(cardNumField.getText(),monthField.getText(),yearField.getText(),codeField.getText(),Main.getCurrentUser().getID());
		newCard.actionNow="creditCard";
		
		try {
			this.sendToServer(newCard);
			System.out.println("sent");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	protected void handleMessageFromServer(Object msg) {
		System.out.println((String)msg);
	}

	
}
