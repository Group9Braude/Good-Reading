package Fixtures;

import java.util.IllegalFormatException;
import javax.sound.sampled.Control;
import Controllers.WorkerController;
import Entities.Book;
import fit.ActionFixture;

public class AddBookFit extends ActionFixture {
	Entities.Book book;
	WorkerController control = new WorkerController();	
	boolean succeed;


	public void startBook() {
		book = new Book();
	}

	public boolean AddBook(){
		System.out.println("check1");
		control.InitializeBookList();
		System.out.println("2");
		control.initialize();
		System.out.println("3");
		control.onAddBookController(book);
		System.out.println("4");
		if( control.addedSuccess)
			control.onRemoveBookController(book.getTitle(), book.getAuthor(), book.getLanguage(), book.getSummary(), book.getGenre(), book.getKeyword());
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
	}
}
