package Controllers;

import java.io.IOException;

import Entities.Book;
import application.Main;

public class LoggedInWorkerController {


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
	
	public void onLogout(){
		WorkerController.onLogout();
	}
}