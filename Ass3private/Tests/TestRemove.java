package Tests;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.Test;
import Controllers.FoundClass;
import Entities.Book;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import junit.framework.TestCase;

public class TestRemove extends TestCase {
	String  title		=	"Tom Cruise",
			author  	=	"Tom Cruise",
			language	=	"Tom Cruise",
			summary		=	"Tom Cruise",
			genre		=	"Horror",
			keyword		=	"Tom Cruise",
			toc			=	"Tom Cruise";
	Book book=new Book(title,0,author,language,summary,toc,keyword,0,0);

	@Test
	public void testExistBook() {
		//Test 1 - Checks if the book removed successfully
		new JFXPanel();				
		Controllers.WorkerController control=new Controllers.WorkerController();
		control.testFlag=1;
		FoundClass found = new FoundClass();
		control.InitializeBookList();
		book.setGenre("Horror");
		control.onAddBookController(book);
		System.out.println(book.getGenre());
		control.onRemoveBookController(title, 
				author, 
				language,
				summary, 
				book.getGenre(),
				keyword);
		found.initialize();
		for(int i=0;i<found.items.size();i++)
			if(found.items.get(i).contains("Book ID: "+book.getBookid()))
				found.foundListView.getSelectionModel().select(i);
		found.onRemove();
		control.onAddBookController(book);
		control.testFlag=0;	
		assertTrue(control.removeSuccess);
	}

	@Test
	public void testNotExistBook() {
		//Test 2 - Try to remove book that doesn't exists
		new JFXPanel();
		Controllers.WorkerController control=new Controllers.WorkerController();
		control.testFlag=1;
		FoundClass found = new FoundClass();
		control.InitializeBookList();
		book=new Book("nothing", 0, "nothing", "nothing", "nothing", "nothing","nothing", 0,0);
		control.onRemoveBookController(book.getTitle(), book.getAuthor(), book.getLanguage(), book.getSummary(), book.getGenre(), book.getKeyword());
		found.onRemove();
		control.testFlag=0;
		assertFalse(control.removeSuccess);
	}
}