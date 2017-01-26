package Controllers;

import java.io.IOException;

import Entities.GeneralMessage;
import Entities.Review;
import Entities.Worker;
import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * This class describes the main manager screen.
 * <p>
 * There are 4 options in this screen:
 * <p>
 * 	1)Get periodical reports
 * <p>
 * 	2)Edit reviews
 * <p>
 *	3)Stock managment
 *<p>
 *	4)Temporarily remove a book/ Activate
 * @author ozdav
 *
 */
public class ManagerLoggedController {
	
	/**
	 * Presents the Temporarily remove a book screen
	 * @throws IOException
	 */
	public void goTempRemoveAbook() throws IOException{
		Main.showTempRemoveAbook();
	}
/**
 * Presents the 'Edit reviews' screen
 */
	public void onEdit(){
		Review review = new Review();
		sendServer(review, "GetReviews");
		while(WorkerController.foundReviews == null)
			try{Thread.sleep(2);}catch(Exception e){e.printStackTrace();}
		try {
			Main.showFinalReviewScreen();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
/**
 * Presents the 'Edit genre' screen
 */
	public void ongenre(){
		Main.showEditGenre();
	}
/**
 * Presents the 'Edit theme' screen
 */
	public void ontheme(){
		Main.showEditTheme();
	}
/**
 * Log out from server safety
 */
	public void onLogout(){ 
		sendServer(Main.getCurrentUser(), "Logout");
		try {Main.showMainMenu();} catch (IOException e) {e.printStackTrace();}
	}
/**
 * Presents the 'Remove book' screen
 */
	public void onRemove(){
		try {
			Main.showRemoveBook();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
/**
 * Presents the 'Add book' screen
 */
	public void onAdd(){
		try {
			Main.showAddBook();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
/**
 * Shortage the code for sending the server
 * @param msg What would be sent to server
 * @param actionNow A String represents the action in server
 */
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
/**
 * Presents the 'Get periodical reports' screen
 * @throws IOException
 */
	public void onReports() throws IOException{
		Main.showReports();
	}
}