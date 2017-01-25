package Controllers;

import java.io.IOException;

import javax.swing.JOptionPane;

import Entities.GeneralMessage;
import Entities.Review;
import Entities.User;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ReviewsController {


	@FXML
	public  ListView<String> foundReviewsListView;
	@FXML
	Button holdButton, back, acceptReviewButton, declineReviewButton, editReviewButton;

	public static ObservableList<String> list;
	static public String  status = "";
	public static boolean isHoldScreen = false, firstTime=true, returnFromEdit=false;
	public static int editID, doubleClick=0, indexClicked=-1;
	/**
	 * This function is a general function, used all across my controllers.
	 * <p>
	 * It's main purpose is to send the server a message that it knows how to deal with.
	 * @param msg is a parameter that extends GeneralMessage and is used mainly to hold the string for the server, to get to the right case.
	 * @param actionNow is the string that contains the information for to server to get us to the right case.
	 * @author orel zilberman
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
 * This method is a constructor that is used to initialize the reviews list.
 * @author orel zilberman
 */
	public ReviewsController(){
		if(returnFromEdit){
			Review review = new Review();
			sendServer(review, "GetReviews");
			while(WorkerController.foundReviews==null)
				Main.Sleep(10);
			returnFromEdit=false;
		}

	}

/**
 * This initializer initializes information in the ListView. 
 * @author orel zilberman
 */
	public void initialize(){

		if(firstTime){
			firstTime=false;
			holdButton.setVisible(false);
		}
		try{
			ObservableList<String> items = FXCollections.observableArrayList();
			items.addAll(WorkerController.foundReviews);
			foundReviewsListView.setItems(items);
		}catch(Exception e){}
	}

	/**
	 * onBack sets the review checking thread back alive and ultimately goes back to the right screen.
	 * @authors orel zilberman and oz david
	 */
	
	public void onBack(){
		WorkerController.setAlive(true);
		try {
			if(User.currentWorker.getType()==2)	
				Main.showLoggedInScreenWorker();
			if(User.currentWorker.getType()==3)
				Main.showManagerLoggedScreen();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
/**
 * This method examines a chosen review from the foundReviewsListView that is presented on the screen.
 * <p>
 * This method ultimately signals the server to run a query to either accept/deny/hold a review.
 * @param isApproved is a parameter that contains information to let the method know whether to signal a denial/acception/hold of a review.
 * @author orel zilberman
 */
	
	public void examineID(int isApproved){
		String str="";
		int i;
		String s="", chosen = null;
		try{
			chosen = foundReviewsListView.getSelectionModel().getSelectedItem();
			i=chosen.indexOf("_ID");
			i+=4;
			for(;chosen.charAt(i)<='9' && chosen.charAt(i)>='0';i++)
				str+=chosen.charAt(i);
			int ID=Integer.parseInt(str);
			Review review = new Review();
			review.setReviewID(ID);
			if(isApproved==1)
				sendServer(review, "AcceptReview");
			else if(isApproved==-1)
				sendServer(review, "DenyReview");
			else if(isApproved ==0)
				sendServer(review, "HoldReview");

			WorkerController.foundReviews=null;

			switch(status){
			case "AcceptedReviews":
				onAcceptedReviews();break;
			case "DeclinedReviews":
				onDeclinedReviews();break;
			default:
				sendServer(new Review(), "GetReviews");break;
			}

			while(WorkerController.foundReviews==null)
				Main.Sleep(10);
			initialize();
		}catch(Exception e){}

	}

	/**
	 * This method presents the reviews that were accepted by a librarian.
	 * @author orel zilberman
	 */
	
	public void onAcceptedReviews(){
		declineReviewButton.setVisible(true);
		holdButton.setVisible(true);
		acceptReviewButton.setVisible(false);
		editReviewButton.setVisible(false);

		status = "AcceptedReviews";
		WorkerController.foundReviews=null;
		isHoldScreen=true;
		Review review = new Review();
		sendServer(review, "FindAcceptedReviews");
		while(WorkerController.foundReviews==null)
			Main.Sleep(10);
		initialize();
	}

	/**
	 * This method presents the reviews that were denied by a librarian.
	 * @author orel zilberman
	 */
	
	public void onDeclinedReviews(){

		acceptReviewButton.setVisible(true);
		holdButton.setVisible(true);
		declineReviewButton.setVisible(false);
		editReviewButton.setVisible(false);
		status = "DeclinedReviews";
		WorkerController.foundReviews=null;
		isHoldScreen=true;
		Review review = new Review();
		sendServer(review, "FindDeclinedReviews");
		while(WorkerController.foundReviews==null)
			Main.Sleep(10);
		initialize();
	}
	
	/**
	 * This method presents the reviews that were either held by a librarian or is requiring some attention from a librarian.
	 * @author orel zilberman
	 */

	public void onHoldReviews(){

		declineReviewButton.setVisible(true);
		acceptReviewButton.setVisible(true);
		holdButton.setVisible(false);
		editReviewButton.setVisible(true);

		status = "HoldReviews";
		WorkerController.foundReviews=null;
		isHoldScreen=false;
		Review review = new Review();
		sendServer(review, "GetReviews");
		while(WorkerController.foundReviews==null)
			Main.Sleep(10);
		initialize();
	}

/**
 * This method signals the server to execute a query to accept a review.
 * @author orel zilberman
 * 
 */
	public void onAcceptReview(){
		examineID(1);//Sets 1 to the review tuple
	}


/**
 * This method signals the server to execute a query to deny a review.
 * @author orel zilberman
 * 
 */
	public void onDenyReview(){//#onDenyReview
		examineID(-1);
	}

/**
 * This method signals the server to execute a query to hold a review.
 * @author orel zilberman
 * 
 */
	public void onHoldReview(){
		examineID(0);
	}

/**
 * This methods is called whenever the user presses the edit button.
 * <p>
 * The methods signals the server to execute a query to update a chosen review.
 * @author orel zilberman
 */

	public void onEditReview(){
		if(foundReviewsListView.getSelectionModel().getSelectedIndex()==0)
			return;
		list = foundReviewsListView.getSelectionModel().getSelectedItems(); 
		if(list.get(0)==null){
			JOptionPane.showMessageDialog(null, "Please select a review", "REVIEW NOT CHOSEN", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		String str="", chosen;
		int i;
		try{
			chosen = foundReviewsListView.getSelectionModel().getSelectedItem();
			i=chosen.indexOf("_ID");
			i+=4;
			for(;chosen.charAt(i)<='9' && chosen.charAt(i)>='0';i++)
				str+=chosen.charAt(i);

			editID=Integer.parseInt(str);
			System.out.println("ID: " + editID) ;
		}catch(Exception e){e.printStackTrace(); }

		try {
			Parent parent = FXMLLoader.load(Main.class.getResource("/GUI/EditReview.fxml"));
			Main.popup.setScene(new Scene(parent));
			Main.getPrimaryStage().close();
			Main.popup.show();
		} catch (IOException e1) {e1.printStackTrace();}
	}
	/**
	 * A double click listener for the user comfort.
	 * @author orel zilberman
	 */
	public void onReviewPress(){
		if(doubleClick==1&&indexClicked==foundReviewsListView.getSelectionModel().getSelectedIndex()&&indexClicked!=0){
			doubleClick++;
			onEditReview();
			return;
		}
		if(doubleClick==2)
			doubleClick=0;
		indexClicked = foundReviewsListView.getSelectionModel().getSelectedIndex();
		doubleClick++;

		Thread thread = new Thread(){
			public void run(){
				try {Thread.sleep(300);} catch (InterruptedException e) {e.printStackTrace();}
				doubleClick=0;
			}
		};
		thread.start();
		
	}


}
