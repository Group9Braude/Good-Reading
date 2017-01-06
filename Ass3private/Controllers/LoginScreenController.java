package Controllers;
import java.io.IOException;
import java.util.ArrayList;

import Entities.Book;
import Entities.GeneralMessage;
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


	public void sendServer(Object msg, String actionNow){/******************************/
		((GeneralMessage)msg).actionNow = actionNow;
		LoginScreenController client = new LoginScreenController();
		try {
			client.openConnection();
			client.sendToServer(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}




	public void onLogin(){
		User user = new User(idTextField.getText(),passwordTextField.getText());
		sendServer(user, "CheckUser");


	}




	public void onContinue(){
		Book book = new Book();
		book.actionNow="hi";
		if(whatAmI!=null){
			
			book.bookList = new ArrayList<Book>();
			sendServer(book, "initializeBookList");
			
			if(whatAmI=="reader")
			{
				Main.showReaderLoginScreen();
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
		if (msg instanceof String) {
			System.out.println((String)msg);//Wrong username/password
		}
		
		else{
			
			if(msg instanceof Reader)
			{
				System.out.println("reader");
				readerLogged=(Reader)msg;
				whatAmI="reader";
				Main.setCurrentUser(readerLogged);
			}

			else if(msg instanceof User)//Correct details were entered
			{
				User res = (User)msg;
				if(res.getType()==2)
					whatAmI="worker";
				else if(res.getType()==3)
					whatAmI="manager";
			}
		}//end else
		if(msg instanceof ArrayList)
			Book.bookList.addAll(((ArrayList<Book>)msg));//Now we have the books in arraylist!
	}
	
	
	
	public static Reader getReaderLogged() {
		return readerLogged;
	}
}