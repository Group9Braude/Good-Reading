package Controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Entities.Book;
import Entities.GeneralMessage;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import ocsf.client.AbstractClient;

/**
 * This class takes care on the Activate book screens which are suspended 
 * and not been presented to the reader in search.
 * @author ozdav
 *
 */
public class activeBooksController extends AbstractClient  {

	int index;
	ObservableList<String> options = FXCollections.observableArrayList();	
	@FXML
	private ChoiceBox booknames;
	ArrayList<Book> book;

	public activeBooksController() {
		super(Main.host, Main.port);
		// TODO Auto-generated constructor stub
	}



	@FXML
/**
 * Initializes the book list in screen
 */
	private void initialize(){
		book=new ArrayList<Book>();
		/*Making an array list of book names*/
		for(int i=0;i<Book.bookList.size();i++){
			if(Book.bookList.get(i).getisSuspend()==1){
				book.add(Book.bookList.get(i));
				String s="id:"+Book.bookList.get(i).getBookid()+"  "+Book.bookList.get(i).getTitle();
				options.add(s);
			}
		}
		booknames.setItems(options);
	}
/**
 * Shortage the code by accessing the server
 * @param msg What that has been sent to server
 * @param actionNow Function to do in server
 */
	public void sendServer(Object msg, String actionNow){/******************************/
		((GeneralMessage)msg).actionNow = actionNow;
		activeBooksController client = new activeBooksController();
		try {
			client.openConnection();
			client.sendToServer(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
/**
 * Activate button's handler. 
 * <p>
 * Gets into the server, and update the book's status to suspend
 */
	public void onActivate(){
		index=booknames.getSelectionModel().getSelectedIndex();//chosen book; indexs refer to the unsuspend books
		for(int i=0;i<Book.bookList.size();i++)
			if(Book.bookList.get(i).getBookid()==book.get(index).getBookid())
				Book.bookList.get(i).setisSuspend(0);
		sendServer(book.get(index),"activeBooks");//send Book type
		JOptionPane.showMessageDialog(null, "The book has been activated successfuly!");
		try {
			Thread.sleep(400);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Main.showManagerLoggedScreen();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
/**
 * Back button's handler. Gets back to the manager main screen.
 * @throws IOException
 */
	public void onBack() throws IOException{
		  Main.showManagerLoggedScreen();
	}
	@Override
	protected void handleMessageFromServer(Object msg) {
			
	}

}



