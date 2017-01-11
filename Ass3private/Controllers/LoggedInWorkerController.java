package Controllers;

import java.io.IOException;

import Entities.Book;
import Entities.GeneralMessage;
import Entities.User;
import Entities.Worker;
import application.Main;

public class LoggedInWorkerController {



	public void sendServer(Object msg, String actionNow){/******************************/
		((GeneralMessage)msg).actionNow = actionNow;
		WorkerController client = new WorkerController();
		try {
			client.openConnection();
			client.sendToServer(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public void  onAddBook(){
		System.out.println(Book.bookCnt);
		try {
			Main.showAddBook();
		} catch (IOException e) {e.printStackTrace();}
	}
	
	public void onRemoveBook(){
		try{
			Main.showRemoveBook();
		}catch (IOException e){e.printStackTrace();}
	}
	
	public void onSearchUser(){
		try{
			Main.showSearchUser();
		}catch(IOException e){e.printStackTrace();}
	}
	
	public  void onLogout(){
		sendServer(LoginScreenController.currentWorker, "Logout");
		try {Main.showMainMenu();} catch (IOException e) {e.printStackTrace();}
		
	}

}