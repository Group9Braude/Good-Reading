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

	public ReviewsController(){//called before initialize!
		if(returnFromEdit){
			Review review = new Review();
			sendServer(review, "GetReviews");
			while(WorkerController.foundReviews==null)
				Main.Sleep(10);
			returnFromEdit=false;
		}

	}


	public void initialize(){

		if(firstTime){
			firstTime=false;
			holdButton.setVisible(false);
		}
		try{
			ObservableList<String> items =FXCollections.observableArrayList();
			items.addAll(WorkerController.foundReviews);
			foundReviewsListView.setItems(items);
		}catch(Exception e){}




	}

	public void onBack(){
		try {
			if(User.currentWorker.getType()==2)	
				Main.showLoggedInScreenWorker();
			if(User.currentWorker.getType()==3)
				Main.showManagerLoggedScreen();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

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


	public void onAcceptReview(){
		examineID(1);//Sets 1 to the review tuple
	}

	public void onDenyReview(){//#onDenyReview
		examineID(-1);
	}

	public void onHoldReview(){
		examineID(0);
	}



	public void onEdit(){

	}



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
		}catch(Exception e){e.printStackTrace(); }

		try {
			Parent parent = FXMLLoader.load(Main.class.getResource("/GUI/EditReview.fxml"));
			Main.popup.setScene(new Scene(parent));
			Main.getPrimaryStage().close();
			Main.popup.show();
		} catch (IOException e1) {e1.printStackTrace();}
	}
	
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
