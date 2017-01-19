package Controllers;

import java.io.IOException;
import java.util.ArrayList;

import Entities.Book;
import Entities.GeneralMessage;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import ocsf.client.AbstractClient;

public class SearchBookScreenController extends AbstractClient
{
	private ArrayList <Book> allBooks = null;
	private boolean origin/*From where the message to the server was sent*/ = false;//false - initialize(), true - onSearch()
	@FXML
	public ObservableList <Book> items = FXCollections.observableArrayList();
	public TableView <Book> bookList = new TableView <Book>(items);
	public ComboBox<String> action = new ComboBox<String>();
	public TextField title,author,lang,genre,keyWord;
	
	@SuppressWarnings("unchecked")
	@FXML
	public void initialize()
	{
		action.getItems().addAll("AND","OR");
		try {
			this.openConnection();
			GeneralMessage dummy = new GeneralMessage();
			dummy.actionNow="getBooks";
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
			System.out.println("---------------------------BOOOOOO--------------------------------------");
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
		items = FXCollections.observableArrayList(allBooks);
		bookList.setItems(items);
	}

	public void onBack()
	{
		Main.showReaderLoginScreen();
	}

	public SearchBookScreenController() 
	{
		super(Main.host, Main.port);
		try {
			this.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void onSearch()
	{
		String userTitle = title.getText(), userAuthor = author.getText(),
				userLang=lang.getText(), genreUser = genre.getText(), keyWordUser = keyWord.getText();
		int i;	
		String query = "SELECT * FROM books WHERE";
		String op = action.getSelectionModel().getSelectedItem();
		if(!userTitle.equals(""))
			query +=" title LIKE '%" + userTitle + "%'" + op;
		System.out.println("boo0");
		if(!userAuthor.equals(""))
		{
			String[] temp = userAuthor.split(",");
			for(i=0; i<temp.length;i++)
				query +=" author LIKE '%" + temp[i] + "%'" + op;
		}
		System.out.println("boo1");
		if(!userLang.equals(""))
			query+=" language LIKE '%" + userLang + "%'" + op;
		System.out.println("boo2");
		
		if(!genreUser.equals(""))
		{
			String[] temp = genreUser.split(",");
			for(i=0; i<temp.length;i++)
				query+=" genre LIKE '%" + temp[i] + "%'" + op;
		}
		System.out.println("boo3");
		
		if(!keyWordUser.equals(""))
		{
			String[] temp = keyWordUser.split(",");
			for(i=0; i<temp.length;i++)
				query+=" keyWord LIKE '%" + temp[i] + "%'" + op;
		}
		String finalQuery="";
		for(i=0;i<query.length()-op.length();i++)
			finalQuery+=query.charAt(i);//Remove the and from the end of the query
		finalQuery+=";";
		System.out.println(finalQuery);
		allBooks = null;
		Book dummy = new Book();
		dummy.query = finalQuery;
		dummy.actionNow = "updateBookList";
		try {
			this.sendToServer(dummy);
			while(allBooks==null)
				Thread.sleep(10);
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		items = FXCollections.observableArrayList(allBooks);
		bookList.setItems(items);
	}
	
	public void onOrder()
	{
		
	}


	@SuppressWarnings("unchecked")
	@Override
	protected void handleMessageFromServer(Object msg) 
	{
		allBooks = (ArrayList<Book>)msg;
	}

}
