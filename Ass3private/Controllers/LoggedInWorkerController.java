package Controllers;

import java.io.IOException;

import application.Main;

public class LoggedInWorkerController {
 

	public void  onAddBook(){
		try {
			Main.showAddBook();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
