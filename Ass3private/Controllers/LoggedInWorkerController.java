package Controllers;

import java.io.IOException;

import Entities.Book;
import application.Main;

public class LoggedInWorkerController {


	public void  onAddBook(){
		try {
			Main.showAddBook();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void onRemoveBook(){
		try{
			Main.showRemoveBook();
		}catch (IOException e){
			e.printStackTrace();
		}
	}
}