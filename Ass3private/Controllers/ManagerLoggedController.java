package Controllers;

import java.io.IOException;

import Entities.GeneralMessage;
import Entities.Review;
import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ManagerLoggedController {
	@FXML
	private static Button temporarilyRemoveABook,getAbsolutePopularityofBook,getRelativePopularityofBook,getPurchaseNumPerBook,getSearchNumPerBook,getUserBookList,getPeriodicReports;

	/**
	 * @throws IOException
	 */
	public void goTempRemoveAbook() throws IOException{
		Main.showTempRemoveAbook();
	}
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
	public void onEditgenretheme(){
		Main.showEditGenre();
	}
	public void onRemove(){
		try {
			Main.showRemoveBook();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void onAdd(){
		try {
			Main.showAddBook();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
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
	public void onReports() throws IOException{
		Main.showReports();
	}
}