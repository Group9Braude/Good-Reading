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

public class TestBookSearch extends TestCase {
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
		Book bookToRemove=new Book("Tom Cruise", 4, "Tom Cruise", "Tom Cruise", "Tom Cruise", "Tom Cruise","Tom Cruise", 0,0);
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
			if(found.items.get(i).contains("Book ID: "+bookToRemove.getBookid()))
				found.foundListView.getSelectionModel().select(i);
		found.onRemove();
		for(int i=0;i<Book.bookList.size();i++)
				assertFalse(Book.bookList.get(i).getBookid()==bookToRemove.getBookid());

		//Test 2 - Try to remove book that doesn't exists
		control.InitializeBookList();
		bookToRemove=new Book("nothing", 0, "nothing", "nothing", "nothing", "nothing","nothing", 0,0);
		control.titleTextFieldR.setText("nothing");
		control.authorTextFieldR.setText("nothing");
		control.languageTextFieldR.setText("nothing");
		control.summaryTextFieldR.setText("nothing");
		control.keywordTextFieldR.setText("nothing");
		control.genresComboBox.getSelectionModel().clearSelection();
		control.onRemoveBook();
		found.initialize();
		for(int i=0;i<found.items.size();i++)
			if(found.items.get(i).contains("Book ID: "+bookToRemove.getBookid()))
				found.foundListView.getSelectionModel().select(i);
		found.onRemove();
		for(int i=0;i<Book.bookList.size();i++)
			assertFalse(Book.bookList.get(i).getBookid()==bookToRemove.getBookid());
		testFlag=0;
	}

	public void testSearchBook(){
		testFlag=1;
		new JFXPanel();
		TextField title = new TextField();
		Controllers.SearchBookScreenController control=new Controllers.SearchBookScreenController();
		control.initialize();
		control.action.getSelectionModel().select(0);
		Book bookToRemove=new Book("Tom Cruise", 4, "Tom Cruise", "Tom Cruise", "Tom Cruise", "Tom Cruise","Tom Cruise", 0,0);
		control.title=title;
		control.title.setText(bookToRemove.getTitle());
		control.author=title;
		control.author.setText(bookToRemove.getAuthor());
		control.lang=title;
		control.lang.setText(bookToRemove.getLanguage());
		control.genre=title;
		control.genre.setText(bookToRemove.getGenre());
		control.keyWord=title;
		control.keyWord.setText(bookToRemove.getKeyword());
		control.onSearch();
		System.out.println(control.updatedBookList.size());
	}
}