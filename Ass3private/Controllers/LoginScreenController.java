package Controllers;
import java.io.IOException;
import java.util.ArrayList;

import Entities.Book;
import Entities.Reader;
import Entities.User;
import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import ocsf.client.AbstractClient;

public class LoginScreenController extends AbstractClient {



	@FXML
	private TextField idTextField;
	@FXML
	private TextField passwordTextField;
	private static String whatAmI=null;
	private static String host = "localhost";
	private static int port = Main.port;
	private static Reader readerLogged;
	public LoginScreenController() {
		super(host, port);

	}
	public void onLogin(){
		User user = new User(idTextField.getText(),passwordTextField.getText());
		System.out.println("onLogin");
		try {
			LoginScreenController client = new LoginScreenController();
			try {
				client.openConnection();
			}
			catch (IOException e1) {
				e1.printStackTrace();
			} 
			client.sendToServer(user);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("onLoginEnd");
	}

	public void onContinue(){
		if(whatAmI!=null){
			if(whatAmI=="reader")
			{
				Main.showReaderLoginScreen();
				//LoginReaderController.setReader(readerLogged);
				//LoginReaderController.setwelcomeText();
				//LoginReaderController.setsubscribeText();
				//whatAmI=null;
			}
			else if(whatAmI=="worker")
				try {
					Main.showLoggedInScreenWorker();
					System.out.println("worker");
				} catch (IOException e) {}
			else if(whatAmI=="manager")
			{
				try {
					System.out.println("Manager!");
					Main.showManagerLoggedScreen();
				} catch (IOException e) {}
			}
		}

	}


	@Override
	protected void handleMessageFromServer(Object msg) {
		if (msg instanceof String) 
			System.out.println((String)msg);
		else if(msg instanceof User)//Correct details were entered
		{
			if(msg instanceof Reader)
			{
				Main.setCurrentUser((Reader)msg);
				readerLogged=(Reader)msg;
				whatAmI="reader";
			}

			else
			{
				User res = (User)msg;
				if(res.getType()==2)
					whatAmI="worker";
				else if(res.getType()==3)
					whatAmI="manager";
			}
		}
	}
		public static Reader getReaderLogged() {
			return readerLogged;
		}
	}