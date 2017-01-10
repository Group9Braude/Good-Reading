package Controllers;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ScheduledThreadPoolExecutor;

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
	private static String whatAmI;
	private static String host = "localhost";
	private static int port = Main.port;
	private static Reader readerLogged;
	private static boolean isLoggedFlag=false;
	final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);//For the login


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
		whatAmI="";
		sendServer(user, "CheckUser");
		Book book = new Book();
		while(whatAmI=="")
			try {Thread.sleep(10);} 
		catch (InterruptedException e) {e.printStackTrace();}
		book.bookList = new ArrayList<Book>();
		sendServer(book, "initializeBookList");//Get the book list in a static array

		try {
			switch(whatAmI){
			case "reader":
				Main.showReaderLoginScreen();break;
			case "worker":
				Main.showLoggedInScreenWorker();break;
			case "manager":
				Main.showManagerLoggedScreen();break;
			}
		} catch (Exception e) {e.printStackTrace();}

	}//End onLogin


	@Override
	protected void handleMessageFromServer(Object msg) {
		if (msg instanceof String) {
			System.out.println((String)msg);//Wrong user name password
		}

		else{

			if(msg instanceof Reader)
			{
				
				readerLogged=(Reader)msg;
				System.out.println(readerLogged.getName());
				isLoggedFlag=true;
				whatAmI="reader";
			}

			else if(msg instanceof User)//Correct details were entered
			{
				isLoggedFlag=true;
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