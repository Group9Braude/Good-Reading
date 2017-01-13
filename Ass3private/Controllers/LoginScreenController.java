package Controllers;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import Entities.Book;
import Entities.GeneralMessage;
import Entities.Reader;
import Entities.User;
import Entities.Worker;
import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
	public static Worker currentWorker;
	private static boolean isLoggedFlag=false;
	final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);//For the login
	@FXML
	ImageView loginImageView;


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
		File file = new File("C:\\Users\\orels\\Desktop\\Ass3Logos\\Button.png");
		Image image = new Image(file.toURI().toString());
		loginImageView.setImage(image);
		User user = new User(idTextField.getText(),passwordTextField.getText());
		whatAmI="";
		sendServer(user, "CheckUser");
		boolean flag=false;
		while(whatAmI==""){
			try {Thread.sleep(10);} 
			catch (InterruptedException e) {e.printStackTrace();}
		}
		if(whatAmI!="User does not exist in the DB"){
			Thread initialize = new Thread(){
				public void run(){
					Book book = new Book();
					Worker worker = new Worker();
					book.bookList = new ArrayList<Book>();
					worker.workerList = new ArrayList<Worker>();
					sendServer(book, "InitializeBookList");//Get the book list in a static array
					sendServer(worker, "InitializeWorkerList");//Get the worker list in static array

				}
			};
			initialize.start();
		}

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

	public void onPress(){
		System.out.println("Press");
		File file = new File("C:\\Users\\orels\\Desktop\\Ass3Logos\\ButtonPressed.png");
		Image image = new Image(file.toURI().toString());
		loginImageView.
		setImage(image);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void handleMessageFromServer(Object msg) {
		if (msg instanceof String) {
			System.out.println((String)msg);//Wrong user name password
			whatAmI="User does not exist in the DB";
		}

		else{
			if(msg instanceof Worker)
				currentWorker = (Worker)msg;
			if(msg instanceof Reader)
			{  
				System.out.println("its a reader!");
				readerLogged=(Reader)msg;
				System.out.println(readerLogged.getFirstName());
				Main.setCurrentUser((Reader)msg);
				isLoggedFlag=true;
				whatAmI="reader";
			}

			else if(msg instanceof User)//Correct details were entered
			{

				isLoggedFlag=true;
				User res = (User)msg;
				if(res.getType()==2){
					/*System.out.println("its a Worker!");
     Worker worker = new Worker();
     worker = (Worker)msg;
     currentWorker = (Worker)msg;
     System.out.println(currentWorker.getWorkerID());*/
					whatAmI="worker";      
				}//end if
				else if(res.getType()==3)
					whatAmI="manager";
			}
		}//end else
		if(msg instanceof ArrayList){
			if(((ArrayList<?>)msg).get(0) instanceof Book)
				Book.bookList.addAll(((ArrayList<Book>)msg));//Now we have the books in arraylist!
			else if(((ArrayList<?>)msg).get(0) instanceof Worker)
				Worker.workerList.addAll(((ArrayList<Worker>)msg));//now we have the workers in arraylist

		}//end if arraylist
	}

	public static Reader getReaderLogged() {
		return readerLogged;
	}
}