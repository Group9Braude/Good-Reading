package Controllers;

import java.io.IOException;

import javax.swing.JOptionPane;

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

	public  void initialize(){
		String temp="", str="";
		for(int j=0;j<(ReviewsController.list).size();j++)
			str += ReviewsController.list.get(j);

		if(str.equals("")){
			JOptionPane.showMessageDialog(null, "Select a review");
			return;
		}
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

	/**
	 * This method is called whenever the user inserted the information he wants to put in the review he has chosen in advance.
	 * @author orel zilberman
	 */
	public void onEdit(){
		Review review = new Review();
		review.setReviewID(ReviewsController.editID);
		System.out.println("ID: " + ReviewsController.editID);
		review.setReview(editReviewTextField.getText());
		WorkerController.foundReviews = null;
		sendServer(review, "EditReview");
		while(WorkerController.foundReviews == null)
			try {Thread.sleep(10);} catch (InterruptedException e) {e.printStackTrace();}
		ReviewsController.returnFromEdit = true;
		onBackEdit();

	}
	/**
	 * This method returns the user to the previous screen
	 * @author orel zilberman
	 */
	public void onBackEdit(){
		try {Main.showFinalReviewScreen();} catch (IOException e) {e.printStackTrace();}
		Main.popup.close();
	}

}
