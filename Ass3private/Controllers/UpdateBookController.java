package Controllers;

import java.io.IOException;
import java.util.ArrayList;

import Entities.Book;
import Entities.GeneralMessage;
import Entities.Genre;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

public class UpdateBookController {
	@FXML
	TableView<Book> booksTableView;
	public static ArrayList<Book> bookList, genresBooksList, previousBookList;
	@FXML
	private Text titleText,keywordText,authorText,languageText,summaryText,tocText,genresText, removeBookTitle;
	@FXML
	private TextField titleTextFieldR, authorTextFieldR, languageTextFieldR, summaryTextFieldR, GenreTextFieldR, keywordTextFieldR,//TextFields for book removal
	idTextFieldR, firstNameTextFieldR, lastNameTextFieldR, readerIDTextFieldR,//For reader search
	workerIDTextFieldW, TextFieldW, lastNameTextFieldW, idTextFieldW, firstNameTextFieldW,//TextFields for Worker search
	titleTextField, authorTextField, languageTextField, summaryTextField, tocTextField, keywordTextField;//TextFields for book search/add
	@FXML
	public ComboBox<String> genresComboBox, genresAddComboBox;
	static boolean  flag;//Make sure initialized wont be called after udpatebook


	public void sendServer(Object msg, String actionNow){/******************************/
		flag=false;
		try {
			((GeneralMessage)msg).actionNow = actionNow;
			WorkerController client = new WorkerController();
			try {
				client.openConnection();
				client.sendToServer(msg);
			} catch (Exception e) {e.printStackTrace();}
		} catch (Exception e) {	e.printStackTrace();}
	}

	public void Sleep(int time){
		try{
			Thread.sleep(time);
		}catch(Exception e){e.printStackTrace();}
	}


	public void onBack(){
		try {
			Main.showLoggedInScreenWorker();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	public  UpdateBookController(){
		bookList = new ArrayList<Book>();
		genresBooksList = new ArrayList<Book>();
		boolean isGenres = false;
		for(Book book : WorkerController.foundBookList)
			if(book.isGenres==false&&isGenres==false)//We have both the genresbooks and the books lists in this list, and both lists are seperated by book.isgenres
				bookList.add(book);
			else{
				isGenres = true;
				genresBooksList.add(book);
			}
		genresBooksList.remove(0);


	}

	public void initialize(){
		if(!flag){
			for(int i=0;i<bookList.size();i++)
				for(int j=0;j<genresBooksList.size();j++)
					if(bookList.get(i).getBookid() == genresBooksList.get(j).getBookid()){
						bookList.get(i).setGenre(genresBooksList.get(j).getGenre());break;
					}
			initTableView();
			ObservableList<Book> books = FXCollections.observableArrayList(bookList);
			booksTableView.setItems(books);
		}
	}


	@SuppressWarnings("unchecked")
	public void initTableView(){
		TableColumn<Book/*The type of data in the table*/,String/*The type of the data in this column*/> titleColumn =new TableColumn<Book,String>("Title");
		titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));//What attribute of class this column takes

		TableColumn<Book,String> authorColumn =new TableColumn<Book,String>("Author");
		authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

		TableColumn<Book,String> genreColumn =new TableColumn<Book,String>("Genre");
		genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));

		TableColumn<Book,Integer> IDColumn =new TableColumn<Book,Integer>("Book ID");
		IDColumn.setCellValueFactory(new PropertyValueFactory<>("bookid"));

		TableColumn<Book,String> langColumn =new TableColumn<Book,String>("Language");
		langColumn.setCellValueFactory(new PropertyValueFactory<>("language"));
 
		TableColumn<Book,String> summColumn =new TableColumn<Book,String>("Summary");
		summColumn.setCellValueFactory(new PropertyValueFactory<>("summary"));

		TableColumn<Book,String> keyWordColumn =new TableColumn<Book,String>("Key word");
		keyWordColumn.setCellValueFactory(new PropertyValueFactory<>("keyword"));

		booksTableView.getColumns().addAll(titleColumn,genreColumn,authorColumn,IDColumn,langColumn,summColumn,keyWordColumn);//Adding the columns to the table
	}

	public void onUpdateBook(){
		flag=true;
		try {Main.showSearchBookForUpdate();} catch (IOException e) {e.printStackTrace();}
	}

	public void onBackFromSearch(){

		Book book = new Book();
		book.query = "select * from books;";
		sendServer(book, "UpdateBookList");
		while(WorkerController.foundBookList ==null)
			Sleep(10);

		try{
			Main.showUpdateBookScreen();
		}catch(Exception e){e.printStackTrace();}
	}
	
	public void onGenresPress(){
		Genre genre = new Genre();
		genresComboBox.getItems().clear();
		sendServer(genre, "InitializeGenreList");
		while(WorkerController.genresList==null)
			Sleep(2);
		ObservableList<String> items = FXCollections.observableArrayList();
		items.addAll(WorkerController.genresList);
		genresComboBox.getItems().addAll(items);
	}

	public void onShowAllBooks(){
		WorkerController.foundBookList = null;
		Book book = new Book();
		book.query = "select * from books;";
		sendServer(book, "UpdateBookList");
		while(WorkerController.foundBookList==null)
			Sleep(10);
		initTableView();
		ObservableList<Book> books = FXCollections.observableArrayList(bookList);
		booksTableView.setItems(books);
		try{
			Main.showUpdateBookScreen();
		}catch(Exception e){e.printStackTrace();} 
	}
	
	public void onSearchBook(){
		flag=false;
		Book book = new Book();
		book.genreToSearch = "";
		String title = titleTextFieldR.getText(), /*author = authorTextFieldR.getText(), RETURN LATER*/ 
				language=languageTextFieldR.getText(), summary=summaryTextFieldR.getText(),
				genre = genresComboBox.getSelectionModel().getSelectedItem(), keyword = keywordTextFieldR.getText();
		//String[] authors = author.split(",");//a,b,c ->[a][b][c] RETURN LATER
		book.query = "SELECT * FROM books WHERE";
		if(!title.equals(""))
			book.query +=" title LIKE  '%" + title + "%' AND ";
		if(!language.equals(""))
			book.query+=" language LIKE '%" + language + "%' AND ";
		if(!summary.equals(""))
			book.query+=" summary LIKE '%" + summary + "%' AND ";
		if(!keyword.equals(""))
			book.query+=" keyWord LIKE '%" + keyword + "%' AND ";
		if(genre != null)
			book.genreToSearch = genre;
		String query = "";
		for(int i=0;i<book.query.length()-5;i++)
			query+=book.query.charAt(i);//Remove the AND from the end of the query
		query+=";";
		book.query=query;
		System.out.println("query before server:" + book.query);
		WorkerController.foundBookList = null;
		sendServer(book, "UpdateBookListSearch");
		while(WorkerController.foundBookList==null)
			Sleep(10);
		try{
			Main.showUpdateBookScreen();
		}catch(Exception e){e.printStackTrace();} 

	}
}
