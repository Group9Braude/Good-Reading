package Controllers;

import javax.swing.JOptionPane;

import Entities.OrderedBook;
import Entities.Reader;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class LoginReaderController {
 
	@FXML
	TextField welcomeText;
	@FXML
	TextField subscribeText;
    @FXML
    public ObservableList <OrderedBook> items = FXCollections.observableArrayList();
    public ListView <OrderedBook> bookList = new ListView<OrderedBook>(items);
	@FXML
	Button Subscribe;
	private Reader reader;
	public static OrderedBook selectedBook;
	
	@FXML
	public void initialize()
	{
		reader=((Reader)Main.getCurrentUser());
		items=FXCollections.observableArrayList(reader.getMyBooks());
		bookList.setItems(items);
		welcomeText.setText("Hello " + reader.getFirstName());
		this.setSubscribeText();
	}
	
	public void setSubscribeText()
	{
		int status = reader.getSubscribed();
		switch(status)
		{
		case 0:
			subscribeText.setText("Not subscribed");
			break;
		case 1:
			subscribeText.setText("Monthly subscription");
			break;
		case 2:
			subscribeText.setText("Yearly subscription");
			break;
		default:break;
		}
	} 
	
	public void onSubscribe()
	{
		Main.showSubscriptionScreen();
	}
	
	public void onReview()
	{
		selectedBook = bookList.getSelectionModel().getSelectedItem();
		if(selectedBook != null)
			Main.showReviewScreen();
		else JOptionPane.showMessageDialog(null, "You must first select a book!");
	}
}
