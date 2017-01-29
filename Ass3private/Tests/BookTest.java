package Tests;
import javax.swing.*;
import javafx.*;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import Entities.Book;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.Test;

import Controllers.FoundClass;
import Entities.Book;
import application.Main;
import junit.framework.TestCase;

public class BookTest extends TestCase {
	static int flag =0;
	public static int testFlag=0;
	/*flag=0 -> string
	 * flag=1 -> book
	 * flag=2 -> ArrayList
	 */

	ArrayList<String> books=new ArrayList<String>();


	@Test
	public void testRemoveBook() throws IOException {
		//Test 1 - Checks if the book removed successfully
		testFlag=1;
		new JFXPanel();
		TextField title = new TextField();
		ComboBox<String> test=new ComboBox<String>();
		Controllers.WorkerController control=new Controllers.WorkerController();
		FoundClass found = new FoundClass();
		control.InitializeBookList();
		Book bookToTest=new Book("Tom Cruise", 4, "Tom Cruise", "Tom Cruise", "Tom Cruise", "Tom Cruise","Tom Cruise", 0,0);
		control.titleTextFieldR=title;
		control.authorTextFieldR=title;
		control.languageTextFieldR=title;
		control.summaryTextFieldR=title;
		control.keywordTextFieldR=title;
		control.genresComboBox=test;
		control.titleTextFieldR.setText("Tom Cruise");
		control.authorTextFieldR.setText("Tom Cruise");
		control.languageTextFieldR.setText("Tom Cruise");
		control.summaryTextFieldR.setText("Tom Cruise");
		control.keywordTextFieldR.setText("Tom Cruise");
		control.genresComboBox.getSelectionModel().clearSelection();
		control.onRemoveBook();
		found.initialize();
		for(int i=0;i<found.items.size();i++)
			if(found.items.get(i).contains("Book ID: "+bookToTest.getBookid()))
				found.foundListView.getSelectionModel().select(i);
		found.onRemove();
		for(int i=0;i<Book.bookList.size();i++)
				assertTrue(Book.bookList.get(i).getBookid()==bookToTest.getBookid());

		//Test 2 - Try to remove book that doesn't exists
		control.InitializeBookList();
		bookToTest=new Book("nothing", 0, "nothing", "nothing", "nothing", "nothing","nothing", 0,0);
		control.titleTextFieldR.setText("nothing");
		control.authorTextFieldR.setText("nothing");
		control.languageTextFieldR.setText("nothing");
		control.summaryTextFieldR.setText("nothing");
		control.keywordTextFieldR.setText("nothing");
		control.genresComboBox.getSelectionModel().clearSelection();
		control.onRemoveBook();
		found.initialize();
		for(int i=0;i<found.items.size();i++)
			if(found.items.get(i).contains("Book ID: "+bookToTest.getBookid()))
				found.foundListView.getSelectionModel().select(i);
		found.onRemove();
		for(int i=0;i<Book.bookList.size();i++)
			assertFalse(Book.bookList.get(i).getBookid()==bookToTest.getBookid());
		testFlag=0;
	}
	
	
	
	
	public void testSearchBook(){
		//Test 1 - search and find book
		testFlag=1;
		new JFXPanel();
		TextField title = new TextField();
		Controllers.SearchBookScreenController control=new Controllers.SearchBookScreenController();
		control.InitializeBookList();
		control.initialize();
		control.action.getSelectionModel().select(0);
		Book bookToTest=new Book("Angelina Julie", 6, "Angelina Julie", "Angelina Julie", "Angelina Julie", "Angelina Julie","Angelina Julie", 0,0);
		control.title=title;
		control.author=title;
		control.lang=title;
		control.genre=title;
		control.keyWord=title;
		control.title.setText(bookToTest.getTitle());
		control.author.setText(bookToTest.getAuthor());
		control.lang.setText(bookToTest.getLanguage());
		control.genre.setText(bookToTest.getGenre());
		control.keyWord.setText(bookToTest.getKeyword());
		control.onSearch();
		assertTrue(control.updatedBookList.get(0).getBookid()==bookToTest.getBookid());
		
		
		//Test 2 - search a book that doesn't exists
		control.InitializeBookList();
		control.action.getSelectionModel().select(0);
		bookToTest=new Book("nothing", 0, "nothing", "nothing", "nothing", "nothing","nothing", 0,0);
		control.title.setText(bookToTest.getTitle());
		control.author.setText(bookToTest.getAuthor());
		control.lang.setText(bookToTest.getLanguage());
		control.genre.setText(bookToTest.getGenre());
		control.keyWord.setText(bookToTest.getKeyword());
		control.onSearch();
		for(int i=0;i<control.updatedBookList.size();i++)
			assertFalse(control.updatedBookList.get(i).getBookid()==bookToTest.getBookid());
		testFlag=0;
	}
}