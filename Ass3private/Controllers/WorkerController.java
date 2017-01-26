package Controllers;
//SEND THE WHOLE CLASS

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

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
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import ocsf.client.AbstractClient;

public class WorkerController extends AbstractClient {

	static private ArrayList<String> foundReaders, foundWorkers;
	static ArrayList<String> genresList;
	static public ArrayList<String> foundBooks, foundReviews;
	static ArrayList<Book> foundBookList;
	public static Book bookForEdit;
	private static boolean isBackFromServer/*For some methods to know if I am back from the server with answers*/
	, isReview = false,//To know if I should notify the worker that there are new reviews
	isAlive = false; //For the Thread in the constructor

	@FXML
	private Button addBookButton, removeBookButton, reviewsButton;
	@FXML
	private ImageView addedButton, catImageView, addImageView, removeImageView, checkImageView, updateImageView, searchImageView, enterImageView, logoutImageView;
	@FXML
	private Text titleText,keywordText,authorText,languageText,summaryText,tocText,genresText, removeBookTitle;
	@FXML
	private TextField titleTextFieldR, authorTextFieldR, languageTextFieldR, summaryTextFieldR, GenreTextFieldR, keywordTextFieldR,genresTextField,//TextFields for book removal
	idTextFieldR, firstNameTextFieldR, lastNameTextFieldR, readerIDTextFieldR,//For reader search
	workerIDTextFieldW, TextFieldW, lastNameTextFieldW, idTextFieldW, firstNameTextFieldW,//TextFields for Worker search
	titleTextField, authorTextField, languageTextField, summaryTextField, tocTextField, keywordTextField,//TextFields for book search/add
	idTextField, passTextField, firstNameTextField, lastNameTextField;//TextFields for user update or add
	@FXML
	public ChoiceBox<String> departmentChoiceBox, roleChoiceBox, subscriptionChoiceBoxR;
	@FXML
	public ComboBox<String> genresComboBox, genresAddComboBox;
	@FXML
	public ComboBox<Integer> subscriptionComboBox;
	@FXML
	private ListView<String> foundReadersListView, foundWorkersListView, foundBookListView;





	public WorkerController() {
		super(Main.host, Main.port);
		foundReaders = null;
		foundWorkers = null;
		foundBooks = null;
		foundReviews = null;
		genresList = null;

	}
	/**
	 * This initializer sets up a thread the will check every 30 seconds whether there is a new review/a review that requires attention.
	 * <p>
	 * The thread sleeps while the user is going through other screens other then the main worker screen.
	 * @author orel zilberman
	 */
	public void initialize(){
		if(!isReview){
			isReview=true;
			isAlive=true;
			Thread thread = new Thread(){
				public void run(){
					try{
						while(true){
							while(!isAlive)
								Sleep(10000);							
							isBackFromServer=false;
							sendServer(new Review(), "CheckReviews");
							while(!isBackFromServer){
								Sleep(50);
								Sleep(30000);
							}
						}
					}catch(Exception e){}
				}
			};thread.start();
		}
	}

	

	/**
	 * This function is a general function, used all across my controllers.
	 * <p>
	 * It's main purpose is to send the server a message that it knows how to deal with.
	 * @param msg is a parameter that extends GeneralMessage and is used mainly to hold the string for the server, to get to the right case.
	 * @param actionNow is the string that contains the information for to server to get us to the right case.
	 * @author orel zilberman
	 */
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
	/**
	 * This method gets the current running thread to sleep.
	 * @param time is a parameter that holds the time for the thread to sleep.
	 * @author orel zilberman
	 */
	public void Sleep(int time){
		try{Thread.sleep(time);}catch(Exception e){e.printStackTrace();}
	}
	/**
	 * This method is called when the user wants to edit a theme
	 * @throws IOException
	 *  @author orel zilberman
	 */
	public void onEditTheme() throws IOException{
		Main.showEditTheme();
	}
	/**
	 * This method shows a screen with found results of reviews
	 *  @author orel zilberman
	 */
	public void showFound(){
		try{
			Main.mainLayout = FXMLLoader.load(Main.class.getResource("/GUI/FoundScreen.fxml"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Main.popup.setScene(new Scene(Main.mainLayout));
		Main.popup.show();
	}

	/**
	 * This method initializes the role ChoiceBox when searching for a worker
	 */
	public void onRolePress(){
		ObservableList<String> items = FXCollections.observableArrayList();
		items.add("Librarian");items.add("Manager");
		roleChoiceBox.setItems(items);
	}


	/**
	 * This method is called when the user chooses a genre from the combo box.
	 * <p>
	 * The function sets an uneditable textfield with the user choice of genre.
	 * <p>
	 * If the user chose a genre that is already set in the textfield, the method removes it from the textfield.
	 */

	public void onGenreSelected(){
		if(genresAddComboBox.getSelectionModel().getSelectedItem() == null)
			return;
		System.out.println("newgenre:" );
		String genreSelected = genresAddComboBox.getSelectionModel().getSelectedItem();
		String genreText="";

		for(int i=0;i<genresTextField.getText().length();i++)//Deep Copy from textfield to variable genreText
			genreText += genresTextField.getText().charAt(i);

		String newGenre = "";
		if(!genresTextField.getText().contains(genreSelected)){//The genre is not there! add it!
			if(genreText.equals(""))
				newGenre = genreSelected;
			else
				newGenre = (genreText + " " + genreSelected);
		}
		else{//The genre is already there! remove it!
			int indexOf = genresTextField.getText().indexOf(genreSelected);//Where the genreSelected String begins.
			boolean isFirst = false;
			newGenre="";
			System.out.println("indexof" +indexOf);
			for(int i=0;i<genreText.length();i++){
				if(indexOf==0&&i==0){
					isFirst = true;
					i+=genreSelected.length()+1;
				}
				if(i>=indexOf-1&&!isFirst){
					i+=genreSelected.length()+1;//For the "," the " " will be taken care of next iteration with the i++
					isFirst=true;//This block should be done only once
				}
				if(i<genreText.length())
					newGenre+= genreText.charAt(i);
			}//end for
		}//end else
		genresTextField.setText(newGenre);
	}

	/**
	 * This method is called to instantiate the subscription choice box every press 
	 */
	public void onSubscriptionChoicePress(){
		if(subscriptionChoiceBoxR == null)
			return;
		ObservableList<String> items = FXCollections.observableArrayList();
		items.add("1");items.add("0");items.add("-1");
		subscriptionChoiceBoxR.setItems(items);
	}
	/**
	 * This method is called to instantiate the subscription choice box every press 
	 */
	public void onSubscriptionPress(){
		if(subscriptionComboBox == null)
			return;
		ObservableList<Integer> items = FXCollections.observableArrayList();
		items.add(1);items.add(0);items.add(-1);
		subscriptionComboBox.setItems(items);
	}

	/**
	 * This method is called to instantiate the genre combobox every press for when the user tries to add a book
	 */

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

	/**
	 * This method is called to instantiate the genre combobox every press
	 */

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

	/**
	 * This method is called when the user wants to add a book, after he sets the textfields according to his will
	 */

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
			genresText.setFill(Color.BLACK); genres=true; book.setGenre(genresTextField.getText());
		}



		if(title&&author&&language&&summary&&toc&&keyWord&&genres){//Every field is filled
			Book.bookList.add(book);//Update our ARRAYLIST!
			sendServer(book, "AddBook");


		}
	}//End onAddBook



	/****************************/
	/*public void onPressCat(){
		File file = new File("C:\\Users\\Sagi\\Desktop\\Ass3Logos\\Orel Buttons\\organizeBookCatSelected.png");
		Image image = new Image(file.toURI().toString());
		catImageView.setImage(image);
	}
=======
>>>>>>> refs/remotes/origin/master

<<<<<<< HEAD
	public void onRlsCat(){
		File file = new File("C:\\Users\\Sagi\\Desktop\\Ass3Logos\\Orel Buttons\\organizeBookCat.png");
		Image image = new Image(file.toURI().toString());
		catImageView.setImage(image);
	}

	public void onPressAdd(){
		File file = new File("C:\\Users\\Sagi\\Desktop\\Ass3Logos\\Orel Buttons\\AddBookSelected.png");
		Image image = new Image(file.toURI().toString());
		addImageView.setImage(image);
	}

	public void onRlsAdd(){
		File file = new File("C:\\Users\\Sagi\\Desktop\\Ass3Logos\\Orel Buttons\\AddBook.png");
		Image image = new Image(file.toURI().toString());
		addImageView.setImage(image);
	}

	public void onPressRemove(){
		File file = new File("C:\\Users\\Sagi\\Desktop\\Ass3Logos\\Orel Buttons\\RemoveBookSelected.png");
		Image image = new Image(file.toURI().toString());
		removeImageView.setImage(image);
	}

	public void onRlsRemove(){
		File file = new File("C:\\Users\\Sagi\\Desktop\\Ass3Logos\\Orel Buttons\\RemoveBook.png");
		Image image = new Image(file.toURI().toString());
		removeImageView.setImage(image);
	}

	public void onPressUpdate(){
		File file = new File("C:\\Users\\Sagi\\Desktop\\Ass3Logos\\Orel Buttons\\UpdateBookSelected.png");
		Image image = new Image(file.toURI().toString());
		updateImageView.setImage(image);
	}

	public void onRlsUpdate(){
		File file = new File("C:\\Users\\Sagi\\Desktop\\Ass3Logos\\Orel Buttons\\Update Book.png");
		Image image = new Image(file.toURI().toString());
		updateImageView.setImage(image);
	}

	public void onPressSearchU(){
		File file = new File("C:\\Users\\Sagi\\Desktop\\Ass3Logos\\Orel Buttons\\SearchUserSelected.png");
		Image image = new Image(file.toURI().toString());
		searchImageView.setImage(image);
	}

	public void onRlsSearchU(){
		File file = new File("C:\\Users\\Sagi\\Desktop\\Ass3Logos\\Orel Buttons\\Search User.png");
		Image image = new Image(file.toURI().toString());
		searchImageView.setImage(image);
	}

	public void onPressEnter(){	
		File file = new File("C:\\Users\\Sagi\\Desktop\\Ass3Logos\\Orel Buttons\\EnterReaderSelected.png");
		Image image = new Image(file.toURI().toString());
		enterImageView.setImage(image);
	}

	public void onRlsEnter(){
		File file = new File("C:\\Users\\Sagi\\Desktop\\Ass3Logos\\Orel Buttons\\Enter Reader.png");
		Image image = new Image(file.toURI().toString());
		enterImageView.setImage(image);
	}
	public void onPressLogout(){//C:\Users\Sagi\Desktop\Ass3Logos\Orel Buttons\organizeBookCatSelected.png
		File file = new File("C:\\Users\\Sagi\\Desktop\\Ass3Logos\\Orel Buttons\\LogoutSelected.png");
		Image image = new Image(file.toURI().toString());
		logoutImageView.setImage(image);
	}

	public void onRlsLogout(){
		File file = new File("C:\\Users\\Sagi\\Desktop\\Ass3Logos\\Orel Buttons\\Logout.png");
		Image image = new Image(file.toURI().toString());
		logoutImageView.setImage(image);
	}

	public void onPressCheck(){//C:\Users\Sagi\Desktop\Ass3Logos\Orel Buttons\organizeBookCatSelected.png
		File file = new File("C:\\Users\\Sagi\\Desktop\\Ass3Logos\\Orel Buttons\\CheckReviewsSelected.png");
		Image image = new Image(file.toURI().toString());
		checkImageView.setImage(image);
	}

	public void onRlsCheck(){
		File file = new File("C:\\Users\\Sagi\\Desktop\\Ass3Logos\\Orel Buttons\\CheckReviews.png");
		Image image = new Image(file.toURI().toString());
		checkImageView.setImage(image);
	}*/



	/**
	 * This method is called when the user wants to delete a book.
	 * <p>
	 * 	 it searches through the books in the database and displays the found books for the user to choose which one he wants to delete.
	 */

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


	/**
	 * Show the edit genre screen
	 * @throws IOException
	 */
	public void onEditGenre() throws IOException{
		Main.showEditGenre();
	}
	/**
	 * Returns the user back to the previous screen and sets the reviews thread back alive .
	 */
	public void onBack(){
		isAlive=true;
		try {
			if(Main.getCurrentUser().getType()==3)
				Main.showManagerLoggedScreen();
			else Main.showLoggedInScreenWorker();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	/**
	 * This method returns the status of the review checking thread
	 * This function returns the status of the review checking thread
	 * @return the status of the thread.
	 */
	public static boolean isAlive() {
		return isAlive;
	}

	public static void setAlive(boolean isAlive) {
		WorkerController.isAlive = isAlive;
	}
	/**
	 * This method searches  through the database for a worker, according to user's input information.
	 * @author orel zilberman
	 */
	public void onWorkerSearch(){
		String lastName=lastNameTextFieldW.getText(),firstName=firstNameTextFieldW.getText(), id=idTextFieldW.getText(),
				workerID=workerIDTextFieldW.getText(), role = "";
		if(roleChoiceBox.getSelectionModel().getSelectedItem() != null)
				role=roleChoiceBox.getSelectionModel().getSelectedItem().toString();

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
		if(!role.equals(""))
			if(role.equals("Librarian"))//	items.add("Librarian");items.add("Manager");
				worker.query+=("isManager=0 AND ");
			else
				worker.query+=("isManager=1 AND ");	
		String query = "";
		for(int i=0;i<worker.query.length()-5;i++)
			query+=worker.query.charAt(i);
		query+=";";
		worker.query="";
		worker.query += query;
		System.out.println("Worker query : " + worker.query);
		sendServer(worker, "FindWorkers");
		workerLVUpdate();		
	}//End onWorkerSearch

	/**
	 * This method sets the isLogged to 0, which means the user is offline and not connected. 
	 * <p>
	 * In addition, the method returns the user to the login screen. 
	 */

	public  void onLogout(){
		isAlive=false;
		Worker worker = new Worker();
		worker.setWorkerID(LoginScreenController.currentWorker.getWorkerID());
		sendServer(worker, "LogOutUser");
		try {Main.showMainMenu();} catch (IOException e) {e.printStackTrace();}

	}



	/**
	 * This method searches  through the database for a reader, according to user's input information.
	 * @author orel zilberman
	 */
	public void onReaderSearch(){
		String lastName=lastNameTextFieldR.getText(),firstName=firstNameTextFieldR.getText(), readerID = readerIDTextFieldR.getText();
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
		if(subscriptionChoiceBoxR.getSelectionModel().getSelectedItem()!=null &&!subscriptionChoiceBoxR.getSelectionModel().getSelectedItem().equals(""))
			reader.query +=("subscription = '" + subscriptionChoiceBoxR.getSelectionModel().getSelectedItem() + "' AND ");
		String query = "";
		for(int i=0;i<reader.query.length()-5;i++)
			query+=reader.query.charAt(i);
		query+=";";
		reader.query="";
		reader.query += query;
		sendServer(reader, "FindReaders");
		readerLVUpdate();

	}

	/**
	 * This method adds a new reader to the database, according to user's input.
	 * @author orel zilberman
	 */

	public void onAddNewReader(){
		boolean id, pass;
		Reader reader = new Reader();
		if(idTextField.getText().equals("")){
			titleText.setFill(Color.RED); id=false;
		}else{
			titleText.setFill(Color.BLACK); id=true; reader.setID(idTextField.getText());
		}
		if(passTextField.getText().equals("")){
			authorText.setFill(Color.RED); pass=false;
		}else{
			titleText.setFill(Color.BLACK); pass=true;reader.setPassword(passTextField.getText());
		}
		if(!id&&!pass)
			return;

		reader.setFirstName(firstNameTextField.getText());
		reader.setLastName(lastNameTextField.getText());
		if(subscriptionComboBox.getSelectionModel().getSelectedItem()==null)
			reader.setSubscribed(0);
		else
			reader.setSubscribed(subscriptionComboBox.getSelectionModel().getSelectedItem());
		isBackFromServer = false;
		sendServer(reader, "AddNewReader");
	}




	/**
	 * This method updates a certain reader according to specific information the user inserts.
	 * @author orel zilberman
	 */

	public void onUpdateReader(){
		Reader reader = new Reader();
		if(!firstNameTextFieldR.getText().equals(""))
			reader.setFirstName(firstNameTextFieldR.getText());
		if(!lastNameTextFieldR.getText().equals(""))
			reader.setLastName(lastNameTextFieldR.getText());
		if(subscriptionChoiceBoxR.getSelectionModel().getSelectedItem() != null && !subscriptionChoiceBoxR.getSelectionModel().getSelectedItem().equals(""))
			reader.setSubscribed(Integer.parseInt(subscriptionChoiceBoxR.getSelectionModel().getSelectedItem()));
		if(foundReadersListView.getSelectionModel().getSelectedItem()==null)
			return;
		int indexOf = foundReadersListView.getSelectionModel().getSelectedItem().indexOf("ID:");
		indexOf+=4;
		String id="";
		for(int i=indexOf;i<foundReadersListView.getSelectionModel().getSelectedItem().length();i++)
			id+=foundReadersListView.getSelectionModel().getSelectedItem().charAt(i);
		reader.setID(id);

		isBackFromServer=false;
		sendServer(reader, "UpdateReader");
		while(!isBackFromServer)
			Sleep(5);
		isBackFromServer=false;
		saveOldList(foundReadersListView, "UpdateReader", firstNameTextFieldR.getText() + " " + lastNameTextFieldR.getText() + " ID: " + id);

	}

	/**
	 * This method removes a reader from the database and updates the listview.
	 * @author orel zilberman
	 */

	public void onRemoveReader(){
		String readerString;
		if((readerString = foundReadersListView.getSelectionModel().getSelectedItem()).equals(""))
			return;
		int indexOf = readerString.indexOf("ID:");
		indexOf+=4;//First char of the id.
		if(indexOf>readerString.length())
			JOptionPane.showMessageDialog(null, "No ID for this reader");
		Reader reader = new Reader();
		String id="";
		for(int i=indexOf;i<readerString.length();i++)
			id+=readerString.charAt(i);
		reader.setID(id);
		sendServer(reader, "RemoveReader");
		isBackFromServer=false;
		while(!isBackFromServer)
			Sleep(5);
		isBackFromServer=false;
		saveOldList(foundReadersListView, "RemoveTuple", "");
	}

	/**
	 * This function uses parameters to update a listview, and its a general method. can fit to any list update.
	 * @param listView is the ListView to update
	 * @param whatToDo is a string to instruct the server what to do, according to cases
	 * @param toUpdate is a string that holds a string to insert into the viewlist.
	 * @author orel zilberman
	 */

	public void saveOldList(ListView<String> listView, String whatToDo, String toUpdate){
		if(listView == null || listView.getItems() == null)
			return;
		ObservableList<String> list = listView.getItems();
		int index = listView.getSelectionModel().getSelectedIndex();
		if(list ==null)
			return;
		switch(whatToDo){
		case "RemoveTuple":
			list.remove(index);listView.setItems(list);break;
		case "UpdateReader":
			list.set(index, toUpdate);listView.setItems(list);break;

		}
	}


	/**
	 * This method extracts the ID from the string in the ListView, sets it into a Book object and sends it to the server to update a book.
	 * @author orel zilberman
	 */
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
	/**
	 * This method updates the readers ListView with the readers with the update.
	 * @author orel zilberman
	 */

	public void readerLVUpdate(){
		while(foundReaders==null)
			Sleep(2);
		ObservableList<String> items =FXCollections.observableArrayList();
		items.addAll(foundReaders);
		foundReadersListView.setItems(items);	
	}
	/**
	 * This method updates the workers ListView with the workers with the update. 
	 * @author orel zilberman
	 */

	public void workerLVUpdate(){
		while(foundWorkers==null)
			Sleep(2);
		ObservableList<String> items =FXCollections.observableArrayList();
		items.addAll(foundWorkers);
		foundWorkersListView.setItems(items);
	}

	/**
	 * This method updates the books ListView with the books with the update. 
	 * @author orel zilberman
	 */

	public void bookLVUpdate(){
		foundBooks = new ArrayList<String>();
		ObservableList<String> items =FXCollections.observableArrayList();
		for(Book book:Book.bookList)
			foundBooks.add(book.getTitle() + "           " + book.getAuthor() + "           " + book.getBookid());
		items.addAll(foundBooks);
		foundBookListView.setItems(items);	

	}

	/**
	 * This method is connected to the static buttons in the reader search. 
	 * It finds logged in readers from the database and updates the readers listview
	 * @author orel zilberman
	 */

	public void onLoggedReaders(){
		Reader reader = new Reader();
		sendServer(reader, "FindLoggedReaders");
		readerLVUpdate();
	}

	/**
	 * This method is connected to the static buttons in the reader search. 
	 * It finds readers that are in debt from the database and updates the readers listview
	 * @author orel zilberman
	 */

	public void onDebtReaders(){
		Reader reader = new Reader();
		sendServer(reader, "FindDebtReaders");
		readerLVUpdate();
	}

	/**
	 * This method is connected to the static buttons in the reader search. 
	 * It finds readers with frozen accounts from the database and updates the readers listview
	 * @author orel zilberman
	 */

	public void onFrozenReaders(){
		Reader reader = new Reader();
		sendServer(reader, "FindFrozenReaders");
		readerLVUpdate();
	}

	/**
	 * This method is connected to the static buttons in the manager search. 
	 * It finds all the managers from the database and updates managers listview
	 * @author orel zilberman
	 */

	public void onAllManagers(){
		Worker worker = new Worker();
		sendServer(worker, "FindAllManagers");
		workerLVUpdate();
	}

	/**
	 * This method is connected to the static buttons in the worker search. 
	 * It finds the all the workers from the database and updates the workers listview
	 * @author orel zilberman
	 */

	public void onAllWorkers(){
		Worker worker = new Worker();
		sendServer(worker, "FindAllWorkers");
		workerLVUpdate();
	}

	/**
	 * This method is connected to the static buttons in the worker search. 
	 * It finds the logged in workers from the database and updates the workers listview
	 * @author orel zilberman
	 */

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
			System.out.println("book");
			bookForEdit = new Book(((Book)msg).getTitle(), ((Book)msg).getBookid(), ((Book)msg).getAuthor(), ((Book)msg).getLanguage(),
					((Book)msg).getSummary(), ((Book)msg).getToc(), ((Book)msg).getKeyword(), ((Book)msg).getisSuspend(), ((Book)msg).getNumOfPurchases());
			bookForEdit.setGenre(((Book)msg).getGenre());
			System.out.println("msg instanceof Book workercontroller");

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
				foundReaders = new ArrayList<>((ArrayList<String>)msg);foundReaders.remove(0);break;
			case "Workers":
				foundWorkers = new ArrayList<>((ArrayList<String>)msg);foundWorkers.remove(0);break;
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
			case "BookAdd":
				JOptionPane.showMessageDialog(null, "Book Added!");break;
			case "ReaderRemoved":
				JOptionPane.showMessageDialog(null, "Reader Removed!");isBackFromServer = true;break;
			case "ReaderAdded":
				JOptionPane.showMessageDialog(null, "Reader Added!");isBackFromServer = true;break;
			case "UserAlreadyInDB":
				JOptionPane.showMessageDialog(null, "This ID is taken!");isBackFromServer = true;break;
			case "UpdateReader":
				JOptionPane.showMessageDialog(null, "Reader Updated!");isBackFromServer = true;break;
			case "ReviewsToCheck":
				JOptionPane.showMessageDialog(null, "New Reviews Require Your Attention");isBackFromServer = true;reviewsButton.setVisible(false);break;
			case "NoReviewsToCheck":
				isBackFromServer = true;break;


			}
		}
	}


	/*           LoggedInWorkerController          */

	/**
	 * This method brings the review thread alive, updates the foundBook list and sets the UpdateBookScreen
	 * @author orel zilberman
	 */
	public void onUpdateBookL(){
		isAlive=false;
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

	/**
	 * This method puts the review thread to sleep and shows AddNewReaderScreen
	 * @author orel zilberman
	 */

	public void onAddNewReaderL(){
		isAlive=false;
		reviewsButton.setVisible(false);
		try{
			Main.showAddNewReaderScreen();
		}catch(IOException e){e.printStackTrace();}
	}

	/**
	 * This method puts the review thread to sleep and shows AddBook screen
	 * @author orel zilberman
	 */

	public void  onAddBookL(){
		isAlive=false;
		try {
			Main.showAddBook();
		} catch (IOException e) {e.printStackTrace();}
	}

	/**
	 * This method puts the review thread to sleep and shows RemoveBook screen
	 * @author orel zilberman
	 */

	public void onRemoveBookL(){
		isAlive=false;
		try{
			Main.showRemoveBook();
		}catch (IOException e){e.printStackTrace();}
	}

	/**
	 * This method puts the review thread to sleep and shows SearchUser screen
	 * @author orel zilberman
	 */

	public void onSearchUserL(){
		isAlive=false;
		try{
			Main.showSearchUser();
		}catch(IOException e){e.printStackTrace();}
	}

	/**
	 * This method puts the review thread to sleep and shows the main screen
	 * @author orel zilberman
	 */

	public void onLogoutL(){ 
		isAlive=false;
		sendServer(LoginScreenController.currentWorker, "Logout");
		try {Main.showMainMenu();} catch (IOException e) {e.printStackTrace();}
	}

	/**
	 * This method puts the review thread to sleep, updates the reviews list  and shows FinalReviewScreen
	 * @author orel zilberman
	 */

	public void onCheckReviewL(){
		isAlive=false;
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
}