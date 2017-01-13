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
	public void onBack() throws IOException{
		  Main.showManagerLoggedScreen();
	}
	@Override
	protected void handleMessageFromServer(Object msg) {
			
	}

}



