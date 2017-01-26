package Controllers;

import java.io.IOException;

import javax.swing.JOptionPane;

import Entities.Book;
import Entities.GeneralMessage;
import Entities.Genre;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class EditBookController {

	@FXML
	TextField titleTextField, languageTextField, summaryTextField, authorTextField, keyWordTextField, tocTextField, 
	genresTextField;
	@FXML
	ComboBox<String> genreComboBox;
	public static Book book;
	public static String genre, firstGenre;

	public EditBookController(){
	}

	public void initialize(){
		book = new Book();
		book.setBookid(WorkerController.bookForEdit.getBookid());
		titleTextField.setText(WorkerController.bookForEdit.getTitle());
		authorTextField.setText(WorkerController.bookForEdit.getAuthor());
		languageTextField.setText(WorkerController.bookForEdit.getLanguage());
		summaryTextField.setText(WorkerController.bookForEdit.getSummary());
		keyWordTextField.setText(WorkerController.bookForEdit.getKeyword());
		tocTextField.setText(WorkerController.bookForEdit.getToc());
		genresTextField.setText(WorkerController.bookForEdit.getGenre());
		genreComboBox.setPromptText("Genres");
		genre = WorkerController.bookForEdit.getGenre();
	} 
	/**
	 * This function is a general function, used all across my controllers.
	 * <p>
	 * It's main purpose is to send the server a message that it knows how to deal with.
	 * @param msg is a parameter that extends GeneralMessage and is used mainly to hold the string for the server, to get to the right case.
	 * @param actionNow is the string that contains the information for to server to get us to the right case.
	 * @author orel zilberman
	 */
	public void sendServer(Object msg, String actionNow){
		try {
			((GeneralMessage)msg).actionNow = actionNow;
			WorkerController client = new WorkerController();
			try {
				client.openConnection();
				client.sendToServer(msg);
			} catch (Exception e) {e.printStackTrace();}
		} catch (Exception e) {	e.printStackTrace();}
	}//end sendserver
	/**
	 * This method gets the current running thread to sleep.
	 * @param time is a parameter that holds the time for the thread to sleep.
	 * @author orel zilberman
	 */
	public void Sleep(int time){
		try{
			Thread.sleep(time);
		}catch(Exception e){e.printStackTrace();}
	}//endsleep
	/**
	 * This method initializes the genres combo box in the book edit screen
	 *  @author orel zilberman
	 */
	public void onGenrePress(){
		Genre genre = new Genre();
		genreComboBox.getItems().clear();
		sendServer(genre, "InitializeGenreList");
		while(WorkerController.genresList==null)
			Sleep(2);
		ObservableList<String> items = FXCollections.observableArrayList();
		items.addAll(WorkerController.genresList);
		genreComboBox.setItems(items);
	}

	/**
	 * This method initializes the books tableview if the user decided not to search eventually and pressed back, and changes the screen.
	 *  @author orel zilberman
	 */

	public void onBackFromSearch(){

		Book book1 = new Book();
		book1.query = "select * from books;";
		sendServer(book1, "UpdateBookList");
		while(WorkerController.foundBookList ==null)
			Sleep(10);

		try{
			Main.showUpdateBookScreen();
		}catch(Exception e){e.printStackTrace();}
	}//End onbackfromsearch

	/**
	 * This method updates the textfield in the edit book screen, to faciliate the user and let him see what he has chosen so far.
	 *  @author orel zilberman
	 */

	public void onNewGenreChosen(){
		if(genreComboBox.getSelectionModel().getSelectedItem() == null)
			return;
		String genreSelected = genreComboBox.getSelectionModel().getSelectedItem();
		String genreText="";
		/* The following lines in this method are written to add and remove genres from the textfield */
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
	}//end onNewGenreAdd


	/**
	 * This method is called when the user wants to edit a book and after he entered the information he wants to update.
	 *  @author orel zilberman
	 */

	public void onEditBook(){//titleTextField, languageTextField, summaryTextField, authorTextField, keyWordTextField, tocTextField, 
		if(genresTextField.equals("")){
			JOptionPane.showMessageDialog(null, "No genre chosen!");
			return;
		}
		else if(titleTextField.equals("")){
			JOptionPane.showMessageDialog(null, "No title chosen!");
			return;
		}
		else if(languageTextField.equals("")){
			JOptionPane.showMessageDialog(null, "No language chosen!");
			return;
		}
		else if(summaryTextField.equals("")){
			JOptionPane.showMessageDialog(null, "No summary chosen!");
			return;
		}
		else if(authorTextField.equals("")){
			JOptionPane.showMessageDialog(null, "No author chosen!");
			return;
		}
		else if(keyWordTextField.equals("")){
			JOptionPane.showMessageDialog(null, "No key word !");
			return;
		}
		else if (tocTextField.equals("")){
			JOptionPane.showMessageDialog(null, "No table of contents !");
			return;
		}
		Book book = new Book();
		//The book will contain genres that are not in the WorkerController.bookForEdit

		book.setGenre("");
		String genreToAdd="";
		book.removeGenres = "";
		for(int i=0;i<genresTextField.getText().length();i++)
			if(genresTextField.getText().charAt(i) == ' '){
				book.GenreAdd(genreToAdd);
				genreToAdd="";
			}else
				genreToAdd+=genresTextField.getText().charAt(i);




		book.setAuthor(authorTextField.getText());
		book.setTitle(titleTextField.getText());
		book.setLanguage(languageTextField.getText());
		book.setSummary(summaryTextField.getText());
		book.setKeyword(keyWordTextField.getText());
		book.setToc(tocTextField.getText());
		book.setGenre(genresTextField.getText());
		book.setBookid(WorkerController.bookForEdit.getBookid());
		System.out.println("edit:" + WorkerController.bookForEdit.getBookid());
		for(int i=0;i<Book.bookList.size();i++)//Update for the Book.booklist for sir
			if (Book.bookList.get(i).getBookid() == WorkerController.bookForEdit.getBookid()){
				Book.bookList.set(i, book);break;
			}

		sendServer(book,"EditBookPlz");
		/*           Show the screen after edit book    */          
		WorkerController.foundBookList = null;
		Book book1 = new Book();
		book1.query = "select * from books;";
		sendServer(book1, "UpdateBookList");
		while(WorkerController.foundBookList==null)
			Sleep(5);
		/*           Show the screen after edit book              */
		try {Main.showUpdateBookScreen();} catch (IOException e) {e.printStackTrace();}
	}//end onbookedit




	public void onBack(){
		try {
			if(Main.getCurrentUser().getType()==3)
				Main.showManagerLoggedScreen();
			else Main.showLoggedInScreenWorker();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}//end class

