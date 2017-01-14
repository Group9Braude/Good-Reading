package Controllers;

import java.io.IOException;
import java.util.ArrayList;

import Entities.Book;
import Entities.GeneralMessage;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import ocsf.client.AbstractClient;

public class BuyBookScreenController extends AbstractClient
{
	private ArrayList<Book> allBooks = null;
	@FXML
	public ObservableList <Book> items = FXCollections.observableArrayList();
	public TableView <Book> bookList = new TableView <Book>(items);
	
	@FXML
	public void initialize()
	{
		try {
			this.openConnection();
			GeneralMessage dummy = new GeneralMessage();
			dummy.actionNow = "getBooks";
			this.sendToServer(dummy);
			while(allBooks==null)
				try {
					Thread.sleep(10);
				} catch (InterruptedException e){
					e.printStackTrace();
				}
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		items = FXCollections.observableArrayList(allBooks);
		bookList.setItems(items);
	}

	public BuyBookScreenController() 
	{
		super(Main.host, Main.port);
		try {
			this.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	@Override
	protected void handleMessageFromServer(Object msg) 
	{
		
		
	}

}
