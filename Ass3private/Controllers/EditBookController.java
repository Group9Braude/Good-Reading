package Controllers;

import java.io.IOException;

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
	TextField titleTextField, languageTextField, summaryTextField, authorTextField, keyWordTextField, tocTextField;
	@FXML
	ComboBox<String> genreComboBox;
	public static Book book;
	public static boolean isBackFromServer;
	public static String genre;

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
		genreComboBox.setPromptText(WorkerController.bookForEdit.getGenre());
		genre = WorkerController.bookForEdit.getGenre();
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
	}//end sendserver

	public void Sleep(int time){
		try{
			Thread.sleep(time);
		}catch(Exception e){e.printStackTrace();}
	}//endsleep

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


	public void onEditBook(){
		isBackFromServer=false;
		Book book = new Book();
		book.setAuthor(authorTextField.getText());
		book.setTitle(titleTextField.getText());
		book.setLanguage(languageTextField.getText());
		book.setSummary(summaryTextField.getText());
		book.setKeyword(keyWordTextField.getText());
		book.setToc(tocTextField.getText());
		if(genreComboBox.getSelectionModel().getSelectedItem()!=null)
			book.setGenre(genreComboBox.getSelectionModel().getSelectedItem());
		else{
			book.setGenre(genre);
			System.out.println("in the else " + genre);
		}
		book.setBookid(this.book.getBookid());
		sendServer(book,"EditBookPlz");
	/*	while(!isBackFromServer)
			Sleep(5);
		UpdateBookController.flag=true;
		WorkerController.foundBookList = null;
		Book book1 = new Book();
		book.query = "select * from books;";
		sendServer(book1, "UpdateBookList");
		while(WorkerController.foundBookList==null)
			Sleep(10);*/
		try {Main.showUpdateBookScreen();} catch (IOException e) {e.printStackTrace();}
		/*                CHANGE IT FOR THE SCREEN UPDATE          */
	}

}
