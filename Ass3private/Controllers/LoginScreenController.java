package Controllers;
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
	private static Worker currentWorker;
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
		//while(whatAmI=="")
		try {Thread.sleep(10);} 
		catch (InterruptedException e) {e.printStackTrace();}
		boolean flag=false;
		while(whatAmI==""){
			try {
				Thread.sleep(50);
				}

			catch (InterruptedException e) {e.printStackTrace();}
		}
		Thread initialize = new Thread(){
			public void run(){
				Book book = new Book();
				Worker worker = new Worker();
				book.bookList = new ArrayList<Book>();
				worker.workerList = new ArrayList<Worker>();
				sendServer(book, "InitializeBookList");//Get the book list in a static array

				sendServer(worker, "InitializeWorkerList");//Get the worker list in static array

				sendServer(worker, "InitializeWorkerList");//Get the worker list in static array
			}
		};
		initialize.start();


		try {
			switch(whatAmI){
			case "reader":
				try{
					System.out.println("Bazooka");
					Main.showReaderLoginScreen();
				}catch(Exception e)
				{
					e.printStackTrace();
				}
				break;
			case "worker":
				Main.showLoggedInScreenWorker();break;
			case "manager":
				Main.showManagerLoggedScreen();break;
			}
		} catch (Exception e) {e.printStackTrace();}

	}//End onLogin


	@SuppressWarnings("unchecked")
	@Override
	protected void handleMessageFromServer(Object msg) {
		if (msg instanceof String) {
			System.out.println((String)msg);//Wrong user name password
		}

		else{
			if(msg instanceof Reader)
			{  
				System.out.println("its a reader!");
				readerLogged=(Reader)msg;
				System.out.println(readerLogged.getFirstName());
				isLoggedFlag=true;
				whatAmI="reader";
				Main.setCurrentUser((Reader)msg);
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
}