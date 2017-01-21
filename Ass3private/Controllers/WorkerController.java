package Controllers;
//SEND THE WHOLE CLASS

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import Entities.Book;
import Entities.GeneralMessage;
import Entities.Genre; 
import Entities.Reader;
import Entities.Review;
import Entities.Worker;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import ocsf.client.AbstractClient;

public class WorkerController extends AbstractClient {

	static private ArrayList<String> foundReaders, foundWorkers;
	static ArrayList<String> genresList;
	static public ArrayList<String> foundBooks, foundReviews;
	static public String windowNow; 
	@FXML
	private Button addBookButton, removeBookButton;
	@FXML
	private ImageView addedButton, catImageView, addImageView, removeImageView, checkImageView, updateImageView, searchImageView, enterImageView, logoutImageView;
	@FXML
	private Text titleText,keywordText,authorText,languageText,summaryText,tocText,genresText, removeBookTitle;
	@FXML
	private TextField titleTextFieldR, authorTextFieldR, languageTextFieldR, summaryTextFieldR, GenreTextFieldR, keywordTextFieldR,//TextFields for book removal
	idTextFieldR, firstNameTextFieldR, lastNameTextFieldR, readerIDTextFieldR,//For reader search
	workerIDTextFieldW, TextFieldW, lastNameTextFieldW, idTextFieldW, firstNameTextFieldW,//TextFields for Worker search
	titleTextField, authorTextField, languageTextField, summaryTextField, tocTextField, keywordTextField;//TextFields for book search/add
	@FXML
	public ChoiceBox<String> departmentChoiceBox, roleChoiceBox;
	@FXML
	public ComboBox<String> genresComboBox, genresAddComboBox;
	@FXML
	private ListView<String> foundReadersListView, foundWorkersListView, foundBookListView;
	static boolean initGBL=true, flag=true, isUpdate=false;
	static ArrayList<Book> foundBookList;
	public static Book bookForEdit;



	public WorkerController() {
		super(Main.host, Main.port);
		foundReaders = null;
		foundWorkers = null;
		foundBooks = null;
		foundReviews = null;
		genresList = null;

	}





	public void sendServer(Object msg, String actionNow){/******************************/
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


	public void onCheckReview(){


	}



	public void showFound(){//POPUP
		try{
			Main.mainLayout = FXMLLoader.load(Main.class.getResource("/GUI/FoundScreen.fxml"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Main.popup.setScene(new Scene(Main.mainLayout));
		Main.popup.show();
	}



	public void changeRemoveButton(){
		if(titleTextFieldR.getText()!="" || authorTextFieldR.getText()!="" ||  languageTextFieldR.getText()!="" || 
				summaryTextFieldR.getText()!="" ||  GenreTextFieldR.getText()!="" ||  keywordTextFieldR.getText()!="")
			removeBookButton.setText("FIND BOOKS");
		else
			removeBookButton.setText("ALL BOOKS");

	}

	public void onGenresPressAdd(){
		Genre genre = new Genre();
		genresAddComboBox.getItems().clear();
		sendServer(genre, "InitializeGenreList");
		while(genresList==null)
			Sleep(2);
		ObservableList<String> items = FXCollections.observableArrayList();
		items.addAll(genresList);
		genresAddComboBox.getItems().addAll(items);
	}

	public void onGenresPress(){
		Genre genre = new Genre();
		genresComboBox.getItems().clear();
		sendServer(genre, "InitializeGenreList");
		while(genresList==null)
			Sleep(2);
		ObservableList<String> items = FXCollections.observableArrayList();
		items.addAll(genresList);
		genresComboBox.getItems().addAll(items);
	}


	public void onAddBook(){
		Book book = new Book();
		boolean title, author, language, summary, toc, keyWord, genres;//Check if all the fields were filled.
		//Check if any of the fields empty
		if(titleTextField.getText().equals("")){
			titleText.setFill(Color.RED); title=false;
		}
		else{
			titleText.setFill(Color.BLACK); title=true; book.setTitle(titleTextField.getText());
		}
		if(authorTextField.getText().equals("")){
			authorText.setFill(Color.RED); author=false;
		}
		else{
			authorText.setFill(Color.BLACK); author=true; book.setAuthor(authorTextField.getText());
		}
		if(languageTextField.getText().equals("")){
			languageText.setFill(Color.RED); language=false;
		}
		else{
			languageText.setFill(Color.BLACK); language=true; book.setLanguage(languageTextField.getText());
		}
		if(summaryTextField.getText().equals("")){
			summaryText.setFill(Color.RED); summary=false;
		}
		else{
			summaryText.setFill(Color.BLACK); summary=true; book.setSummary(summaryTextField.getText());
		}
		if(tocTextField.getText().equals("")){
			tocText.setFill(Color.RED); toc=false;
		}
		else{
			tocText.setFill(Color.BLACK); toc=true; book.setToc(tocTextField.getText());
		}
		if(keywordTextField.getText().equals("")){
			keywordText.setFill(Color.RED); keyWord=false;
		}
		else{
			keywordText.setFill(Color.BLACK); keyWord=true; book.setKeyword(keywordTextField.getText());
		}
		if(genresAddComboBox ==null || genresAddComboBox.getSelectionModel().getSelectedItem()==null){
			genresText.setFill(Color.RED);genres=false;
		}
		else{
			genresText.setFill(Color.BLACK); genres=true; book.setGenre(genresAddComboBox.getSelectionModel().getSelectedItem());
		}



		if(title&&author&&language&&summary&&toc&&keyWord&&genres){//Every field is filled
			Book.bookList.add(book);//Update our ARRAYLIST!
			sendServer(book, "AddBook");

		}
	}//End onAddBook


	/****************************/
	public void onPressCat(){
		File file = new File("C:\\Users\\orels\\Desktop\\Ass3Logos\\Orel Buttons\\organizeBookCatSelected.png");
		Image image = new Image(file.toURI().toString());
		catImageView.setImage(image);
	}

	public void onRlsCat(){
		File file = new File("C:\\Users\\orels\\Desktop\\Ass3Logos\\Orel Buttons\\organizeBookCat.png");
		Image image = new Image(file.toURI().toString());
		catImageView.setImage(image);
	}

	public void onPressAdd(){
		File file = new File("C:\\Users\\orels\\Desktop\\Ass3Logos\\Orel Buttons\\AddBookSelected.png");
		Image image = new Image(file.toURI().toString());
		addImageView.setImage(image);
	}

	public void onRlsAdd(){
		File file = new File("C:\\Users\\orels\\Desktop\\Ass3Logos\\Orel Buttons\\AddBook.png");
		Image image = new Image(file.toURI().toString());
		addImageView.setImage(image);
	}

	public void onPressRemove(){
		File file = new File("C:\\Users\\orels\\Desktop\\Ass3Logos\\Orel Buttons\\RemoveBookSelected.png");
		Image image = new Image(file.toURI().toString());
		removeImageView.setImage(image);
	}

	public void onRlsRemove(){
		File file = new File("C:\\Users\\orels\\Desktop\\Ass3Logos\\Orel Buttons\\RemoveBook.png");
		Image image = new Image(file.toURI().toString());
		removeImageView.setImage(image);
	}

	public void onPressUpdate(){
		File file = new File("C:\\Users\\orels\\Desktop\\Ass3Logos\\Orel Buttons\\UpdateBookSelected.png");
		Image image = new Image(file.toURI().toString());
		updateImageView.setImage(image);
	}

	public void onRlsUpdate(){
		File file = new File("C:\\Users\\orels\\Desktop\\Ass3Logos\\Orel Buttons\\Update Book.png");
		Image image = new Image(file.toURI().toString());
		updateImageView.setImage(image);
	}

	public void onPressSearchU(){
		File file = new File("C:\\Users\\orels\\Desktop\\Ass3Logos\\Orel Buttons\\SearchUserSelected.png");
		Image image = new Image(file.toURI().toString());
		searchImageView.setImage(image);
	}

	public void onRlsSearchU(){
		File file = new File("C:\\Users\\orels\\Desktop\\Ass3Logos\\Orel Buttons\\Search User.png");
		Image image = new Image(file.toURI().toString());
		searchImageView.setImage(image);
	}

	public void onPressEnter(){	
		File file = new File("C:\\Users\\orels\\Desktop\\Ass3Logos\\Orel Buttons\\EnterReaderSelected.png");
		Image image = new Image(file.toURI().toString());
		enterImageView.setImage(image);
	}

	public void onRlsEnter(){
		File file = new File("C:\\Users\\orels\\Desktop\\Ass3Logos\\Orel Buttons\\Enter Reader.png");
		Image image = new Image(file.toURI().toString());
		enterImageView.setImage(image);
	}
	public void onPressLogout(){//C:\Users\orels\Desktop\Ass3Logos\Orel Buttons\organizeBookCatSelected.png
		File file = new File("C:\\Users\\orels\\Desktop\\Ass3Logos\\Orel Buttons\\LogoutSelected.png");
		Image image = new Image(file.toURI().toString());
		logoutImageView.setImage(image);
	}

	public void onRlsLogout(){
		File file = new File("C:\\Users\\orels\\Desktop\\Ass3Logos\\Orel Buttons\\Logout.png");
		Image image = new Image(file.toURI().toString());
		logoutImageView.setImage(image);
	}

	public void onPressCheck(){//C:\Users\orels\Desktop\Ass3Logos\Orel Buttons\organizeBookCatSelected.png
		File file = new File("C:\\Users\\orels\\Desktop\\Ass3Logos\\Orel Buttons\\CheckReviewsSelected.png");
		Image image = new Image(file.toURI().toString());
		checkImageView.setImage(image);
	}

	public void onRlsCheck(){
		File file = new File("C:\\Users\\orels\\Desktop\\Ass3Logos\\Orel Buttons\\CheckReviews.png");
		Image image = new Image(file.toURI().toString());
		checkImageView.setImage(image);
	}
	/****************************/
	


/*                MAKE SURE REMOVE REMOVES FROM GENRESBOOKS ASWELL !!!!!!!!    */
	public void onRemoveBook(){
		Book book = new Book();
		book.genreToSearch = "";
		String title = titleTextFieldR.getText(), author = authorTextFieldR.getText(),
				language=languageTextFieldR.getText(), summary=summaryTextFieldR.getText(),
				genre = genresComboBox.getSelectionModel().getSelectedItem(), keyword = keywordTextFieldR.getText();
		System.out.println("G" + genre);
		String[] authors = author.split(",");//a,b,c ->[a][b][c]
		for(String str:authors)
			System.out.println(str);
		book.query = "SELECT title, author, bookid FROM books WHERE";
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
		System.out.println(book.genreToSearch);
		String query = "";
		for(int i=0;i<book.query.length()-5;i++)
			query+=book.query.charAt(i);//Remove the AND from the end of the query
		query+=";";
		book.query=query;
		sendServer(book, "RemoveBook");
		while(foundBooks==null)
			Sleep(5);
		showFound();
	}//end onRemoveBook



	public void onEditGenre() throws IOException{
		System.out.println("lol");
		Main.showEditGenre();
	}

	public void onBack(){
		try {
			Main.showLoggedInScreenWorker();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}




	public void onWorkerSearch(){
		String lastName=lastNameTextFieldW.getText(),firstName=firstNameTextFieldW.getText(), id=idTextFieldW.getText(),
				workerID=workerIDTextFieldW.getText();
		// role=roleChoiceBox.getSelectionModel().getSelectedItem().toString(),
		//department=roleChoiceBox.getSelectionModel().getSelectedItem().toString();

		Worker worker = new Worker();
		worker.query="SELECT * FROM workers WHERE ";
		if(!firstName.equals(""))
			worker.query+=("firstName LIKE '%"+firstName +"%' AND ");
		if(!lastName.equals(""))
			worker.query +=("lastName LIKE '%"+lastName+"%' AND ");
		if(!workerID.equals(""))
			worker.query +=("workerID='"+workerID+"' AND ");
		if(!id.equals(""))
			worker.query +=("id='"+id+"' AND ");
		String query = "";
		for(int i=0;i<worker.query.length()-5;i++)
			query+=worker.query.charAt(i);
		query+=";";
		worker.query="";
		worker.query += query;
		sendServer(worker, "FindWorkers");
		workerLVUpdate();		
	}//End onWorkerSearch


	public  void onLogout(){
		Worker worker = new Worker();
		worker.setWorkerID(LoginScreenController.currentWorker.getWorkerID());
		sendServer(worker, "LogOutUser");
		try {Main.showMainMenu();} catch (IOException e) {e.printStackTrace();}

	}




	public void onReaderSearch(){
		String lastName=lastNameTextFieldR.getText(),firstName=firstNameTextFieldR.getText(), id=idTextFieldR.getText(),
				readerID = readerIDTextFieldR.getText();
		// role=roleChoiceBox.getSelectionModel().getSelectedItem().toString(),
		//department=roleChoiceBox.getSelectionModel().getSelectedItem().toString();

		Reader reader = new Reader();
		reader.query="SELECT * FROM readers WHERE ";
		if(!firstName.equals(""))
			reader.query+=("firstName LIKE '%"+firstName +"%' AND ");
		if(!lastName.equals(""))
			reader.query +=("lastName LIKE '%"+lastName+"%' AND ");
		if(!readerID.equals(""))
			reader.query +=("readerID='"+readerID+"' AND ");
		if(!id.equals(""))
			reader.query +=("id='"+id+"' AND ");
		String query = "";
		for(int i=0;i<reader.query.length()-5;i++)
			query+=reader.query.charAt(i);
		query+=";";
		reader.query="";
		reader.query += query;
		sendServer(reader, "FindReaders");
		readerLVUpdate();

	}



	public void onBookChosen(){
		String str="";
		int ID;
		String chosen = foundBookListView.getSelectionModel().getSelectedItem();
		for(int i=0;i<chosen.length();i++)
			if((chosen.charAt(i)-'0'<=9 && chosen.charAt(i)-'0'>=0))//INTEGER! THE ID IS HERE!
				str+=chosen.charAt(i);
			else
				str="";
		ID=Integer.parseInt(str);
		Book book = new Book();
		book.setBookid(ID);
		sendServer(book, "UpdateBook");

	}


	public void readerLVUpdate(){
		while(foundReaders==null)
			Sleep(2);
		ObservableList<String> items =FXCollections.observableArrayList();
		items.addAll(foundReaders);
		foundReadersListView.setItems(items);	
	}

	public void workerLVUpdate(){
		while(foundWorkers==null)
			Sleep(2);
		ObservableList<String> items =FXCollections.observableArrayList();
		items.addAll(foundWorkers);
		foundWorkersListView.setItems(items);
	}

	public void bookLVUpdate(){
		foundBooks = new ArrayList<String>();
		ObservableList<String> items =FXCollections.observableArrayList();
		for(Book book:Book.bookList)
			foundBooks.add(book.getTitle() + "           " + book.getAuthor() + "           " + book.getBookid());
		items.addAll(foundBooks);
		foundBookListView.setItems(items);	

	}


	public void onLoggedReaders(){
		Reader reader = new Reader();
		sendServer(reader, "FindLoggedReaders");
		readerLVUpdate();
	}
	public void onDebtReaders(){
		Reader reader = new Reader();
		sendServer(reader, "FindDebtReaders");
		readerLVUpdate();
	}
	public void onFrozenReaders(){
		Reader reader = new Reader();
		sendServer(reader, "FindFrozenReaders");
		readerLVUpdate();
	}
	public void onAllManagers(){
		Worker worker = new Worker();
		sendServer(worker, "FindAllManagers");
		workerLVUpdate();
	}
	public void onAllWorkers(){
		Worker worker = new Worker();
		sendServer(worker, "FindAllWorkers");
		workerLVUpdate();
	}
	public void onLoggedWorkers(){
		Worker worker=  new Worker();
		sendServer(worker, "FindLoggedWorkers");
		workerLVUpdate();
	}





	@SuppressWarnings("unchecked")
	protected void handleMessageFromServer(Object msg) {

		if(msg instanceof String)
			System.out.println((String)msg);
		
		if(msg instanceof Book){
			bookForEdit = new Book(((Book)msg).getTitle(), ((Book)msg).getBookid(), ((Book)msg).getAuthor(), ((Book)msg).getLanguage(),
					((Book)msg).getSummary(), ((Book)msg).getToc(), ((Book)msg).getKeyword(), ((Book)msg).getisSuspend(), ((Book)msg).getNumOfPurchases());
			bookForEdit.setGenre(((Book)msg).getGenre());

		}
		
		//	public Book(String title,int bookid, String author, String language, String summary, String toc, String keyword, int isSuspend,int numOfPurchases) 

		else if(((ArrayList<?>)msg).get(0) instanceof Book){
			if((((ArrayList<Book>)msg).get(0)).query.equals("UpdateBookList")){
				foundBookList = new ArrayList<Book>((ArrayList<Book>)msg);
				foundBookList.remove(0);
			}		
			else 
				Book.bookList = new ArrayList<Book>((ArrayList<Book>)msg);
		}
		
		else if(((ArrayList<?>)msg).get(0) instanceof Review){
			for(Book book:(ArrayList<Book>)msg)
				Book.bookList.add(book);
		}
		else if(((ArrayList<?>)msg).get(0) instanceof Genre){
			Genre.genreList  = new ArrayList<Genre>(((ArrayList<Genre>)msg));
			genresList = new ArrayList<String>();
			for(int i=0;i<Genre.genreList.size();i++)
				genresList.add(Genre.genreList.get(i).getGenre());		
		}

		else{
			switch((String)((ArrayList<?>)msg).get(0)){
			case "Readers":
				foundReaders = new ArrayList<>((ArrayList<String>)msg);break;
			case "Workers":
				foundWorkers = new ArrayList<>((ArrayList<String>)msg);break;
			case "LoggedReaders":
				foundReaders = new ArrayList<>((ArrayList<String>)msg);break;
			case "LoggedWorkers":
				foundWorkers = new ArrayList<>((ArrayList<String>)msg);break;
			case "AllManagers":
				foundWorkers = new ArrayList<>((ArrayList<String>)msg);break;
			case "AllWorkers":
				foundWorkers = new ArrayList<>((ArrayList<String>)msg);break;
			case "DebtReaders":
				foundReaders = new ArrayList<>((ArrayList<String>)msg);break;
			case "FrozenReaders":
				foundReaders = new ArrayList<>((ArrayList<String>)msg);break;
			case "BookSearch":
				foundBooks = new ArrayList<>(((ArrayList<String>)msg));break;
			case "RemoveBook":
				foundBooks = new ArrayList<>(((ArrayList<String>)msg));break;
			case "SearchReviews":
				foundReviews = new ArrayList<>(((ArrayList<String>)msg));break;
			case "EditReview":
				EditReviewController.backOn=true;break;
			case "GoToUpdateScreen":
				EditBookController.isBackFromServer = true;break;
			}
		}
	}


	/*           LoggedInWorkerController          */

	public void onUpdateBookL(){
		foundBookList = null;
		Book book = new Book();
		book.query = "select * from books;";
		sendServer(book, "UpdateBookList");
		while(foundBookList==null)
			Sleep(10);
		try{
			Main.showUpdateBookScreen();
		}catch(Exception e){e.printStackTrace();}
	}

	public void  onAddBookL(){
		onRlsAdd();
		try {
			Main.showAddBook();
		} catch (IOException e) {e.printStackTrace();}
	}

	public void onRemoveBookL(){
		onRlsRemove();
		try{
			Main.showRemoveBook();
		}catch (IOException e){e.printStackTrace();}
	}

	public void onSearchUserL(){
		onRlsSearchU();
		try{
			Main.showSearchUser();
		}catch(IOException e){e.printStackTrace();}
	}

	public void onLogoutL(){ 
		onRlsLogout();
		sendServer(LoginScreenController.currentWorker, "Logout");
		try {Main.showMainMenu();} catch (IOException e) {e.printStackTrace();}
	}
	public void onCheckReviewL(){
		Review review = new Review();
		sendServer(review, "GetReviews");
		while(foundReviews == null)
			Sleep(2);
		try {
			Main.showFinalReviewScreen();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}




	/*           LoggedInWorkerController          */




}

/*try {
mainLayout = FXMLLoader.load(Main.class.getResource("/GUI/LoginScreen.fxml"));
} catch (IOException e1) {
e1.printStackTrace();
}
Main.popup.setScene(new Scene(mainLayout));
Main.popup.show();
//label1.setTextFill(Color.web("#0076a3"));

public void onUpdateBookL(){
	Book book = new Book();
	book.query = "SELECT * FROM books";
	if(flag){
		Book.bookList=new ArrayList<Book>();
		Genre.genresBooksList = new ArrayList<Book>();	
		flag=false;
	}
	sendServer(book, "UpdateBookList");

	while(Book.bookList==null)
		Sleep(5);

	sendServer(book, "InitializeGenresBooksList");

	while(Genre.genresBooksList==null)
		Sleep(5);


	WorkerController.initGBL = true;
	for(Book book1 : Book.bookList)
		System.out.println(book1.getTitle());
	for(Book book2 : Genre.genresBooksList)
		System.out.println(book2.getGenre());


	
	try {
		Main.showUpdateBookScreen();
	} catch (IOException e) {
		e.printStackTrace();
	}
}*/