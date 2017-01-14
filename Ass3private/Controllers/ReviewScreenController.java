package Controllers;

import java.io.IOException;

import javax.swing.JOptionPane;

import Entities.OrderedBook;
import Entities.Review;
import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import ocsf.client.AbstractClient;

public class ReviewScreenController extends AbstractClient
{
	public ReviewScreenController() {
		super(Main.host, Main.port);
	}

	private OrderedBook reviewBook;
	@FXML
	public TextField reviewField;
	public TextField signatureField;
	public TextField keywordField;
	public Button send;
	
	public void initialize()
	{
		reviewBook = LoginReaderController.selectedBook;
		System.out.println(reviewBook.getTitle());
	}
	public OrderedBook getReviewBook() {
		return reviewBook;
	}
	public void setReviewBook(OrderedBook reviewBook) {
		this.reviewBook = reviewBook;
	}
	
	public void onBack()
	{
		Main.showReaderLoginScreen();
	}
	
	public void onSend()
	{
		if(reviewField.getText().equals("") || signatureField.getText().equals("") || keywordField.getText().equals("") )
			JOptionPane.showMessageDialog(null, "First enter the missing details");
		else{
			Review review = new Review(reviewBook,keywordField.getText(),reviewField.getText(),signatureField.getText(),0,1);
			review.actionNow = "AddReview"; 
			try {
				this.openConnection();
				this.sendToServer(review);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	@Override
	protected void handleMessageFromServer(Object msg) 
	{
		JOptionPane.showMessageDialog(null, (String)msg);
	}
	
	
	
	
	
	
}
