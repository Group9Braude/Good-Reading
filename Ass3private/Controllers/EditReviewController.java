package Controllers;

import java.io.IOException;

import Entities.GeneralMessage;
import Entities.Review;
import application.Main;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

public class EditReviewController {

	@FXML
	public TextField editReviewTextField;

	public static boolean backOn = false;

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

	public  void initialize(){
		String temp="", str="";
		for(int j=0;j<(ReviewsController.list).size();j++)
			str += ReviewsController.list.get(j);

		if(str == null)
			return;/*********************************** ADD HERE POP UP WITH SWING THAT WILL TELL THE USER TO CHOOSE A REVIEW***********************/

		int i, cnt=0;
		for(i=0; i<str.length();i++)
			if(str.charAt(i)=='\n'){
				cnt++;
				if(cnt==2){
					i=i+1;break;
				}
			}
		for(;i<str.length();i++)
			temp+=str.charAt(i);
		editReviewTextField.insertText(0, temp);
	}

	public void onEdit(){
		Review review = new Review();
		review.setReviewID(ReviewsController.editID);
		review.setReview(editReviewTextField.getText());
		WorkerController.foundReviews = null;
		sendServer(review, "EditReview");
		while(WorkerController.foundReviews == null)
			try {Thread.sleep(10);} catch (InterruptedException e) {e.printStackTrace();}
		ReviewsController.returnFromEdit = true;
		onBackEdit();

	}
	public void onBackEdit(){
		try {Main.showFinalReviewScreen();} catch (IOException e) {e.printStackTrace();}
		Main.popup.close();
	}

}
