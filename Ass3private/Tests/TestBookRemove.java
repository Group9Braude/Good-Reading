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

public class TestBookRemove extends TestCase {
	static int flag =0;
	String  title		=	"Tom Cruise",
			author  	=	"Tom Cruise",
			language	=	"Tom Cruise",
			summary		=	"Tom Cruise",
			genre		=	"Tom Cruise",
			keyword		=	"Tom Cruise",
			toc			=	"Tom Cruise";
	Book bookToRemove=new Book(title,0,author,language,summary,toc,keyword,0,0);
	
	public static int testFlag=0;
	/*flag=0 -> string
	 * flag=1 -> book
	 * flag=2 -> ArrayList
	 */
	ArrayList<String> books=new ArrayList<String>();
	
@Test
public void TestExistBookRemove() throws IOException {
	//Test 1 - Checks if the book removed successfully
	testFlag=1;
	new JFXPanel();				
	Controllers.WorkerController control=new Controllers.WorkerController();
	FoundClass found = new FoundClass();
	control.InitializeBookList();
	control.onRemoveBookController(title, author, language, summary, genre, keyword);
	control.genresComboBox.getSelectionModel().clearSelection();
	control.onRemoveBook();
	found.initialize();
	for(int i=0;i<found.items.size();i++)
		if(found.items.get(i).contains("Book ID: "+bookToRemove.getBookid()))
			found.foundListView.getSelectionModel().select(i);
	found.onRemove();
	for(int i=0;i<Book.bookList.size();i++)
			assertFalse(Book.bookList.get(i).getBookid()==bookToRemove.getBookid());
}

@Test
public void TestNotExistBookRemove() throws IOException {
	//Test 2 - Try to remove book that doesn't exists
	testFlag=1;
	new JFXPanel();
	Controllers.WorkerController control=new Controllers.WorkerController();
	FoundClass found = new FoundClass();

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
}