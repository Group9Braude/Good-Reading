package Controllers;

import java.io.IOException;

import Entities.GeneralMessage;
import Entities.Review;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class ReviewsController {

	@FXML
	ListView<String> foundReviewsListView;

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

	public void onBack(){
		try {
			Main.showLoggedInScreenWorker();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
 
	public void examineID(int isApproved){
		String str="";
		int i;
		String s="";
		boolean flag=false;//To know if it is the id
		String chosen = foundReviewsListView.getSelectionModel().getSelectedItem();
		
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
		else
			sendServer(review, "DenyReview");
	}

	public void onAcceptReview(){
		examineID(1);
	}

	public void onDenyReview(){//#onDenyReview
		examineID(-1);
	}


	public void initialize(){

		ObservableList<String> items =FXCollections.observableArrayList();
		items.addAll(WorkerController.foundReviews);
		foundReviewsListView.setItems(items);	

	}
}
