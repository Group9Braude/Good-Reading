package Fixtures;

import java.util.IllegalFormatException;
import javax.sound.sampled.Control;

import org.apache.xmlbeans.impl.xb.ltgfmt.TestsDocument.Tests;

import Controllers.WorkerController;
import Entities.Book;
import fit.ActionFixture;
import javafx.embed.swing.JFXPanel;

public class AddBookFit extends ActionFixture {
	Entities.Book book;
	WorkerController control = new WorkerController();	
	boolean succeed;


	public void startBook() {
		book = new Book();
	}

	public boolean AddBook() throws InterruptedException{
		new JFXPanel();				
		control.testFlag=1;
		System.out.println("check1");
		control.InitializeBookList();
		System.out.println("2");
		control.initialize();
		System.out.println("3");
		control.onAddBookController(book);
		System.out.println("4");
		if(control.addedSuccess)
			control.onRemoveBookController(book.getTitle(), book.getAuthor(), book.getLanguage(), book.getSummary(), book.getGenre(), book.getKeyword());
		control.testFlag=0;
		return control.addedSuccess;
	}
	
	public boolean AddBookTwice() throws InterruptedException{
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
	}
}
