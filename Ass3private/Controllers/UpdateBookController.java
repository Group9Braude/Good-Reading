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

public class UpdateBookController {
	@FXML
	TableView<Book> booksTableView;
	public static ArrayList<Book> bookList, genresBooksList, previousBookList;
	@FXML
	private TextField titleTextFieldR, authorTextFieldR, languageTextFieldR, summaryTextFieldR, GenreTextFieldR, keywordTextFieldR;
	@FXML
	public ComboBox<String> genresComboBox, genresAddComboBox;
	
	
	
	static boolean  flag;//Make sure initialized wont be called after udpatebook
	static Book bookForEdition;

	/**
	 * This function is a general function, used all across my controllers.
	 * <p>
	 * It's main purpose is to send the server a message that it knows how to deal with.
	 * @param msg is a parameter that extends GeneralMessage and is used mainly to hold the string for the server, to get to the right case.
	 * @param actionNow is the string that contains the information for to server to get us to the right case.
	 * @author orel zilberman
	 */
	
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

	
	//Close the Current window and go back to screenWorker
/**
 * This method closes the current window and sets back the loggedinscreenworker screen
 *  @author orel zilberman
 */
	public void onBack(){
		try {
			Main.showLoggedInScreenWorker();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public UpdateBookController(){
		getBookListWithGenres();
	}

//Get the booklist joined with genres, so I will have the books with their genres in one list for the tableview
	/**
	 * This function initializes a list of books along with their genres.
	 *  @author orel zilberman
	 */
	public void  getBookListWithGenres(){
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

/**
 * This method initializes the TableView to set the books list
 *  @author orel zilberman
 */
	public void initialize(){
		if(!flag){//Meaning we just came back from a search
			for(int i=0;i<bookList.size();i++)
				for(int j=0;j<genresBooksList.size();j++)
					if(bookList.get(i).getBookid() == genresBooksList.get(j).getBookid()){
						bookList.get(i).setGenre(genresBooksList.get(j).getGenre());break;
					}
			initTableView();
			System.out.println("After init: " + (bookList.get(0)).getTitle() + "  " + bookList.get(0).getGenre());
			ObservableList<Book> books = FXCollections.observableArrayList(bookList);
			booksTableView.setItems(books);
		}
	}
/**
 * This method sets values  in the tableview
 *  @author orel zilberman
 */

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
		
		TableColumn<Book,String> themeColumn =new TableColumn<Book,String>("Theme");
		keyWordColumn.setCellValueFactory(new PropertyValueFactory<>("theme"));

		booksTableView.getColumns().addAll(titleColumn,genreColumn,authorColumn,IDColumn,langColumn,summColumn,
				keyWordColumn, themeColumn);//Adding the columns to the table
	}
/**
 * This method shows the Search book screen to update it.
 *  @author orel zilberman
 */
	public void onUpdateBook(){
		flag=true;
		try {Main.showSearchBookForUpdate();} catch (IOException e) {e.printStackTrace();}
	}
	/**
	 * This method resets the TableView with edited items
	 *  @author orel zilberman
	 */
	public void onEditBook(){
		Book book = booksTableView.getSelectionModel().getSelectedItem();
		if (book ==null)
			return;
		WorkerController.bookForEdit = null;
		sendServer(book, "GetBookForEdition");
		while(WorkerController.bookForEdit == null)
			Sleep(5);
		try {Main.showEditBookScreen();} catch (IOException e) {e.printStackTrace();}
	}


	/**
	 * This method is called when we're back from a search that we didn't commit.
	 *  @author orel zilberman
	 */
	public void onBackFromSearch(){
		Book book = new Book();
		book.query = "select * from books;";
		sendServer(book, "UpdateBookList");
		while(WorkerController.foundBookList ==null)
			Sleep(10);
		System.out.println("here");
		try{Main.showUpdateBookScreen();}catch(Exception e){e.printStackTrace();}
	}
	
	/**
	 * This method sets the options in the genres ComboBox
	 */
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
	
/**
 * This method is called when the user wants to see all the books in the TableView
 *  @author orel zilberman
 */
	public void onShowAllBooks(){
		WorkerController.foundBookList = null;
		Book book = new Book();
		book.query = "select * from books;";
		sendServer(book, "UpdateBookList");
		while(WorkerController.foundBookList==null)
			Sleep(10);
		getBookListWithGenres();
		for(int i=0;i<bookList.size();i++)
			for(int j=0;j<genresBooksList.size();j++)
				if(genresBooksList.get(j).getBookid()==bookList.get(i).getBookid()){
					bookList.get(i).setGenre(genresBooksList.get(j).getGenre());break;
				}
		initTableView();
		ObservableList<Book> books = FXCollections.observableArrayList(bookList);
		booksTableView.setItems(books);
		try{
			Main.showUpdateBookScreen();
		}catch(Exception e){e.printStackTrace();} 
	}
	/**
	 * This method is called when the user sets his search request and commits it.
	 *  @author orel zilberman
	 */
	public void onSearchBook(){
		flag=false;
		Book book = new Book();
		book.genreToSearch = "";
		String title = titleTextFieldR.getText(), /*author = authorTextFieldR.getText(), RETURN LATER*/ 
				language=languageTextFieldR.getText(), summary=summaryTextFieldR.getText(),
				genre = genresComboBox.getSelectionModel().getSelectedItem(), keyword = keywordTextFieldR.getText();
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
		/*                    END AND             */
		book.query=query;
		WorkerController.foundBookList = null;
		sendServer(book, "UpdateBookListSearch");
		while(WorkerController.foundBookList==null)
			Sleep(10);
		try{
			Main.showUpdateBookScreen();
		}catch(Exception e){e.printStackTrace();} 
	}
}