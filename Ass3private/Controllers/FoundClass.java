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

	/**
	 * This function is a general function, used all across my controllers.
	 * <p>
	 * It's main purpose is to send the server a message that it knows how to deal with.
	 * @param msg is a parameter that extends GeneralMessage and is used mainly to hold the string for the server, to get to the right case.
	 * @param actionNow is the string that contains the information for to server to get us to the right case.
	 * @author orel zilberman
	 */
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

	/**
	 * This method is called whenever the user wants to delete a certain book.
	 * @author orel zilberman
	 */
	public void onRemove(){
		String selected = foundListView.getSelectionModel().getSelectedItem();
		if(selected==null || selected == "")
			return;
		int chosen = foundListView.getSelectionModel().getSelectedIndex();
		String ID="";
		Book book = new Book();
		
		for(int i=0;i<selected.length();i++)
			if(selected.charAt(i)-'0'<=9 &&selected.charAt(i)-'0'>=0)//It might be the id.
				ID+=selected.charAt(i);
			else
				ID="";

		book.setBookid(Integer.parseInt(ID));
		WorkerController.foundBooks=null;
		for(int i=0;i<Book.bookList.size();i++)
			if(Book.bookList.get(i).getBookid()==book.getBookid()){
				Book.bookList.remove(i);break;
			}//Update global public books arraylist
		sendServer(book, "DeleteBook");
		foundListView.getItems().remove(chosen);


	}
	/**
	 * This method initializes the listview for user comfot.
	 * @author orel zilberman
	 */

	public void initialize(){
		ObservableList<String> items =FXCollections.observableArrayList();
		items.addAll(WorkerController.foundBooks);
		foundListView.setItems(items);
	}
	
	/**
	 * Closes the current window, which is just a pop up.
	 * @author orel zilberman
	 */

	public void onBack(){
		Main.popup.close();
	}


}
