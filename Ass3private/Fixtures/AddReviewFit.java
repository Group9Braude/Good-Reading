package Fixtures;

import Controllers.ReviewScreenController;
import Entities.Book;
import Entities.OrderedBook;
import Entities.Review;
import fit.ActionFixture;
import javafx.embed.swing.JFXPanel;

public class AddReviewFit extends ActionFixture {
	Review review;
	ReviewScreenController control = new ReviewScreenController();	
	private static String reviewText, signatureText, keyWordText;
	boolean succeed;


	public void startReview() {
		
		review = new Review();
		OrderedBook reviewBook = new OrderedBook();

		reviewBook.setReaderID("483");
		reviewBook.setTitle("Star Wars The Last Jedi");
		reviewBook.setBookid(1337);
		reviewBook.setAuthor("Eran Simhon");
		//review.setReview("My people. Sagiv is my Jedi brother!");
		control.setReviewBook(reviewBook);
		//review.setReviewID(1337);
		//review.setKeyword("Jedi");
		//review.setSignature("Gamer483");
	}
	public boolean addReview(String s){
		return succeed;
	}
	
	public void setReviewText(String review)
	{
		this.reviewText = review;
	}
	
	public void setSignatureText(String signature)
	{
		this.signatureText = signature;
	}
	
	public void setKeywordFieldText(String keyWord)
	{
		this.keyWordText = keyWord;
	}

	public boolean addReview()throws InterruptedException{
		
		new JFXPanel();	
		
		//control.testFlag=1;
		//control.InitializeBookList();
		//control.initialize();

		control.signatureField.setText(signatureText);
		control.reviewField.setText(reviewText);
		control.keywordField.setText(keyWordText);
		
		control.onSend();
		return control.sent;
		
		/*control.onAddBookController(book);
		if(control.addedSuccess)
			control.onRemoveBookController(book.getTitle(), book.getAuthor(), book.getLanguage(), book.getSummary(), book.getGenre(), book.getKeyword());
		control.testFlag=0;
		return control.addedSuccess;*/
	}

	/*public boolean AddBookTwice() throws InterruptedException{
		new JFXPanel();				
		control.testFlag=1;
		System.out.println("check1");
		control.InitializeBookList();
		System.out.println("2");
		control.initialize();
		System.out.println("3");
		control.onAddBookController(book);
		System.out.println("4");
		control.onAddBookController(book);
		System.out.println("5");
		if(control.addedSuccess)
			control.onRemoveBookController(book.getTitle(), book.getAuthor(), book.getLanguage(), book.getSummary(), book.getGenre(), book.getKeyword());
		control.testFlag=0;
		return control.addedSuccess;
	}

	public void bookTitle(String title) {
		book.setTitle(title);
	}

	public void bookAuthor(String author) {
		book.setAuthor(author);		 
	}

	public void bookGenre(String genre){
		book.setGenre(genre);
	}
	public void bookID(int bookid){
		book.setBookid(bookid);
	}

	public void bookLang(String lang) {
		book.setLanguage(lang);
	}

	public void bookSummary(String summary) {
		book.setSummary(summary);
	}

	public void bookToc(String toc)  {
		book.setToc(toc);
	}

	public void bookKeyword(String key){
		book.setKeyword(key);
	}

	public void bookIsSuspend(int suspend) {
		book.setisSuspend(suspend);
	}

	public void bookNumOfPurchases(int purchase){
		book.setNumOfPurchases(purchase);
	}*/
}
