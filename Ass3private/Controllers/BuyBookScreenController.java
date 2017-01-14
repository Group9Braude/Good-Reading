package Controllers;

import java.io.IOException;
import java.util.ArrayList;

import Entities.Book;
import Entities.GeneralMessage;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ocsf.client.AbstractClient;

public class BuyBookScreenController extends AbstractClient
{
	private ArrayList <Book> allBooks = null;
	@FXML
	public ObservableList <Book> items = FXCollections.observableArrayList();
	public TableView <Book> bookList = new TableView <Book>(items);
	
	@SuppressWarnings("unchecked")
	@FXML
	public void initialize()
	{
		try {
			this.openConnection();
			GeneralMessage dummy = new GeneralMessage();
			dummy.actionNow = "getBooks";
			this.sendToServer(dummy);
			
			TableColumn<Book/*The type of data in the table*/,String/*The type of the data in this column*/> titleColumn =new TableColumn<Book,String>("Title");
			titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));//What attribute of class this column takes
			
			TableColumn<Book,Integer> IDColumn =new TableColumn<Book,Integer>("Book ID");
			IDColumn.setCellValueFactory(new PropertyValueFactory<>("bookid"));
			
			TableColumn<Book,String> authorColumn =new TableColumn<Book,String>("Author");
			authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
			
			TableColumn<Book,String> langColumn =new TableColumn<Book,String>("Language");
			langColumn.setCellValueFactory(new PropertyValueFactory<>("language"));
			
			TableColumn<Book,String> summColumn =new TableColumn<Book,String>("Summary");
			summColumn.setCellValueFactory(new PropertyValueFactory<>("summary"));
			
			TableColumn<Book,String> keyWordColumn =new TableColumn<Book,String>("Key word");
			keyWordColumn.setCellValueFactory(new PropertyValueFactory<>("keyword"));
			
			bookList.getColumns().addAll(titleColumn,IDColumn,authorColumn,langColumn,summColumn,keyWordColumn);//Adding the columns to the table
			
			
			
			while(allBooks==null)
				try {
					if(allBooks==null)
						System.out.println("null");
					Thread.sleep(10);
				} catch (InterruptedException e){
					e.printStackTrace();
				}
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		System.out.println(allBooks.get(0).getBookid());
		items = FXCollections.observableArrayList(allBooks);
		bookList.setItems(items);
		//Obser
	
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
	

	@SuppressWarnings("unchecked")
	@Override
	protected void handleMessageFromServer(Object msg) 
	{
		allBooks = (ArrayList<Book>)msg;
	}

}
