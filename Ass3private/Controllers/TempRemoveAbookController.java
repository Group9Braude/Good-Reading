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
 * This class takes care on the 'Temporarily remove a book' option, in manager screen
 * @author ozdav
 *
 */
public class TempRemoveAbookController extends AbstractClient  {

	int index;
	ObservableList<String> options = FXCollections.observableArrayList();	
	@FXML
	private ChoiceBox booknames;
	ArrayList<Book> book;
 /**
  * Initializes host and port in AbstractClient
  */
	public TempRemoveAbookController() {
		super(Main.host, Main.port);
		// TODO Auto-generated constructor stub
	}



	@FXML
/**
 * Initializes the books data while screen boots
 */
	private void initialize(){
		book=new ArrayList<Book>();
		/*Making an array list of book names*/
		for(int i=0;i<Book.bookList.size();i++){
			if(Book.bookList.get(i).getisSuspend()==0){
				book.add(Book.bookList.get(i));
				String s="id:"+Book.bookList.get(i).getBookid()+"  "+Book.bookList.get(i).getTitle();
				options.add(s);
			}
		}
		booknames.setItems(options);
	}
/**
 * Shotage the writing of sending to the server
 * @param msg What would be sent to server
 * @param actionNow Name of function in server
 */
	public void sendServer(Object msg, String actionNow){/******************************/
		((GeneralMessage)msg).actionNow = actionNow;
		TempRemoveAbookController client = new TempRemoveAbookController();
		try {
			client.openConnection();
			client.sendToServer(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
/**
 * Suspend buton's handler.
 * <p>
 * This function send a request to server with the choosen book, and suspending it from
 * being showed in reader's search.
 */
	public void onSuspend(){
		index=booknames.getSelectionModel().getSelectedIndex();//chosen book; indexs refer to the unsuspend books
		for(int i=0;i<Book.bookList.size();i++)
			if(Book.bookList.get(i).getBookid()==book.get(index).getBookid())
				Book.bookList.get(i).setisSuspend(1);
		sendServer(book.get(index),"TempRemoveAbook");//send Book type
		JOptionPane.showMessageDialog(null, "The book has been suspended successfuly!");
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
 * Back button handler to the main manager screen.
 * @throws IOException
 */
	public void onBack() throws IOException{
		  Main.showManagerLoggedScreen();
	}
/**
 * Activate button's handler. Gets back into the main manager screen
 */
	public void onActivate(){
		try {
			Main.showActiveBooks();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	protected void handleMessageFromServer(Object msg) {
			
	}

}



