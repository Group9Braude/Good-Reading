package Controllers;

import java.io.IOException;

import javax.swing.JOptionPane;

import Entities.OrderedBook;
import Entities.Review;
import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import ocsf.client.AbstractClient;

public class ReviewScreenController extends AbstractClient
{
	/**
	 * Initializes the class
	 */
	public ReviewScreenController() {
		super(Main.host, Main.port);
	}

	private OrderedBook reviewBook;
	@FXML
	public TextField reviewField;
	public TextField signatureField;
	public TextField keywordField;
	public Button send;//If the review was successfully sent to the server
	public boolean sent = false;
	/**
	 * get the review book
	 */
	public void initialize()
	{
		reviewBook = LoginReaderController.selectedBook;
		System.out.println(reviewBook.getTitle());
	}
	/**
	 * get the review book
	 * @return review book
	 */
	public OrderedBook getReviewBook() {
		return reviewBook;
	}
	/**
	 * set the review book
	 * @param reviewBook the new review book
	 */
	public void setReviewBook(OrderedBook reviewBook) {
		this.reviewBook = reviewBook;
	}
	/**
	 * go back to the main screen of the reader
	 */
	public void onBack()
	{
		Main.showReaderLoginScreen();
	}
	/**
	 * If the all necessary fields have been entered, a new review will be inserted into the DB
	 */
	public void onSend()
	{
		if(reviewField.getText() == null || signatureField.getText() == null || keywordField.getText() == null)
			return;
		if(reviewField.getText().equals("") || signatureField.getText().equals("") || keywordField.getText().equals("") )
			JOptionPane.showMessageDialog(null, "First enter the missing details");
		else{
			Review review = new Review(reviewBook,keywordField.getText(),reviewField.getText(),signatureField.getText(),0);
			review.actionNow = "AddReview"; 
			try {
				this.openConnection();
				this.sendToServer(review);
				sent = true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	@Override
	/**
	 * Handle the message from the server
	 */
	protected void handleMessageFromServer(Object msg) 
	{
		JOptionPane.showMessageDialog(null, (String)msg);
	}
	
	

	
	
	
	
	
}
