package Controllers;

import java.io.IOException;

import Entities.Book;
import Entities.GeneralMessage;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import ocsf.client.AbstractClient;

public class FoundClass{
	@FXML
	private ListView<String> foundListView;

	public void sendServer(Object msg, String actionNow){/******************************/
		((GeneralMessage)msg).actionNow = actionNow;
		WorkerController client = new WorkerController();
		try {
			client.openConnection();
			client.sendToServer(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void onRemove(){
		String selected = foundListView.getSelectionModel().getSelectedItem();
		if(selected==null || selected == "")
			return;
		int chosen = foundListView.getSelectionModel().getSelectedIndex();
		String ID="";
		Book book = new Book();
		for(int i=0;i<selected.length();i++)
			if(selected.charAt(i)-'0'<=9 &&selected.charAt(i)-'0'>=0)//Its the ID!
				ID+=selected.charAt(i);
		book.setBookid(Integer.parseInt(ID));
		WorkerController.foundBooks=null;
		for(int i=0;i<Book.bookList.size();i++)
			if(Book.bookList.get(i).getBookid()==book.getBookid()){
				Book.bookList.remove(i);break;
			}//Update global public books arraylist
		sendServer(book, "DeleteBook");
		foundListView.getItems().remove(chosen);
		

	}

	public void initialize(){
		System.out.println("init");
		WorkerController.foundBooks.set(0, "Name:                    Author:                       ID:");
		ObservableList<String> items =FXCollections.observableArrayList();
		items.addAll(WorkerController.foundBooks);
		foundListView.setItems(items);
	}

	public void onBack(){
		Main.popup.close();
	}


}
