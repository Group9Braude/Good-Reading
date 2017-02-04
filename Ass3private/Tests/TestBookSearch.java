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
	String  title		=	"Tom Cruise",
			author  	=	"Tom Cruise",
			language	=	"Tom Cruise",
			summary		=	"Tom Cruise",
			genre		=	"Horror",
			keyword		=	"Tom Cruise",
			toc			=	"Tom Cruise";
	Book book=new Book(title,0,author,language,summary,toc,keyword,0,0);
	
	public void testANDSearchExistsBook(){
		//search with AND
		new JFXPanel();
		Controllers.SearchBookScreenController control=new Controllers.SearchBookScreenController();
		control.testFlag=1;
		control.initialize();	
		control.action.getSelectionModel().select(0);
		control.onSearchController(title, author, language, genre, keyword, control.action.getSelectionModel().getSelectedItem());
		//control.updatedBookList holds the returned found books from server
		assertTrue(control.updatedBookList.get(0).equals(book));
		control.testFlag=0;
	}
	
	/*public void testORSearchExistsBook(){
		//search with Or
		new JFXPanel();
		Controllers.SearchBookScreenController control=new Controllers.SearchBookScreenController();
		control.testFlag=1;
		control.initialize();	
		control.action.getSelectionModel().select(1);
		control.onSearchController(title, author, language, genre, keyword, control.action.getSelectionModel().getSelectedItem());
		//control.updatedBookList holds the returned found books from server
		assertTrue(control.updatedBookList.get(0).equals(book));
		control.testFlag=0;
	}*/
	
	public void testSearchNotExistsBook(){
		//search with AND
		new JFXPanel();
		Controllers.SearchBookScreenController control=new Controllers.SearchBookScreenController();
		control.testFlag=1;
		control.initialize();	
		control.action.getSelectionModel().select(0);
		control.onSearchController("invalid", "", "", "", "", control.action.getSelectionModel().getSelectedItem());
		//control.updatedBookList holds the returned found books from server
		assertTrue(control.updatedBookList.size()==0);
		control.testFlag=0;
	}
	
}