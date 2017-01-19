package Controllers;

import java.io.IOException;

import Entities.GeneralMessage;
import Entities.Review;
import Entities.User;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class ReviewsController {

	@FXML
	ListView<String> foundReviewsListView;
	@FXML
	Button holdButton, back;
	static String  status = "";

	private static boolean isHoldScreen = false;

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



	public void initialize(){


		ObservableList<String> items =FXCollections.observableArrayList();
		items.addAll(WorkerController.foundReviews);
		foundReviewsListView.setItems(items);	
	}

	public void onBack(){
		System.out.println("hereback: " + User.currentWorker.getType());
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


		for( i=0;i<chosen.length();i++){
			s=""+ chosen.charAt(i);
			if(s.equals("" + "_"))
				str="";
			else if((chosen.charAt(i)-'0'<=9 && chosen.charAt(i)-'0'>=0))//INTEGER! THE ID MIGHT BE HERE!
				str+=chosen.charAt(i);
		}

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
		status = "AcceptedReviews";
		WorkerController.foundReviews=null;
		isHoldScreen=true;
		holdButton.setVisible(true);
		Review review = new Review();
		sendServer(review, "FindAcceptedReviews");
		while(WorkerController.foundReviews==null)
			Main.Sleep(10);
		initialize();
	}

	public void onDeclinedReviews(){
		status = "DeclinedReviews";
		WorkerController.foundReviews=null;
		isHoldScreen=true;
		holdButton.setVisible(true);
		Review review = new Review();
		sendServer(review, "FindDeclinedReviews");
		while(WorkerController.foundReviews==null)
			Main.Sleep(10);
		initialize();
	}
	
	public void onHoldReviews(){
		status = "HoldReviews";
		WorkerController.foundReviews=null;
		isHoldScreen=false;
		holdButton.setVisible(false);
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
public void onBackEdit(){
	Main.showReviewScreen();
}


}
