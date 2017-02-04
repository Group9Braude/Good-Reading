package Fixtures;

import Controllers.ReviewScreenController;
import Entities.OrderedBook;
import Entities.Review;
import fit.ActionFixture;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.TextField;

public class AddReviewFit extends ActionFixture {
	Review review;
	ReviewScreenController control = new ReviewScreenController();	


	public void startReview() {
		new JFXPanel();
		review = new Review();
		OrderedBook reviewBook = new OrderedBook();
		reviewBook.setReaderID("483");
		reviewBook.setTitle("Star Wars The Last Jedi");
		reviewBook.setBookid(1337);
		reviewBook.setAuthor("Eran Simhon");
		control.setReviewBook(reviewBook);
		control.reviewField = new TextField();
		control.keywordField = new TextField();
		control.signatureField = new TextField();
	}
	
	public void setReviewText(String review)
	{
		control.reviewField.setText(review);
	}
	
	public void setSignatureText(String signature)
	{
		control.signatureField.setText(signature);
	}
	
	public void setKeywordText(String keyWord)
	{
		control.keywordField.setText(keyWord);
	}

	public boolean addReview()throws InterruptedException
	{
		control.sent = false;
		control.onSend();
		return control.sent;
	}

}
