package Controllers;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JOptionPane;

import Entities.FileDetails;
import Entities.OrderedBook;
import Entities.Reader;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import ocsf.client.AbstractClient;

public class LoginReaderController extends AbstractClient{

	public LoginReaderController() 
	{
		super(Main.host, Main.port);
	}
	private boolean back = false;
	@FXML
	TextField welcomeText;
	@FXML
	TextField subscribeText;
	@FXML
	public ObservableList <OrderedBook> items = FXCollections.observableArrayList();
	public ListView <OrderedBook> bookList = new ListView<OrderedBook>(items);
	@FXML
	Button Subscribe;
	public ComboBox<String> format;
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
		format.getItems().setAll("DOC","PDF","FB2");
		try {
			this.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	public void onGenerate()
	{

		if( bookList.getSelectionModel().getSelectedItem() == null)
			JOptionPane.showMessageDialog(null, "You must first select a book to download!!");
		else if(format.getSelectionModel().getSelectedItem() == null)
			JOptionPane.showMessageDialog(null, "You must first select a format!!");
		else
		{
			selectedBook = bookList.getSelectionModel().getSelectedItem();
			String chosen = format.getSelectionModel().getSelectedItem();
			FileDetails file = new FileDetails(selectedBook.getBookid(),chosen);
			file.actionNow = "CreateAndSendFile";
			try {
				this.sendToServer(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void onLogOut()
	{
		reader.actionNow="Logout";
		try {
			this.sendToServer(reader);
			while(!back)
				Thread.sleep(10);
			back = false;
			Main.showMainMenu();
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void popUpCredit()
	{

		try {
			Pane mainLayout = FXMLLoader.load(Main.class.getResource("/GUI/CreditCardScreen.fxml"));
			Main.popup.setScene(new Scene(mainLayout));
			Main.popup.showAndWait();
		} catch (IOException e1) {
			e1.printStackTrace();
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

	public void onSearch()
	{
		Main.showSearchScreen();
	}

	public void onSearchReviews()
	{
		Main.showSearchReviewScreen();
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		if(msg instanceof String)
		{
			System.out.println((String)msg);
			back=true;
		}
		else{
			OutputStream out;
			try {
				out = new FileOutputStream(selectedBook + "."+format.getSelectionModel().getSelectedItem());
				out.write((byte[])msg);
				out.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}


	}
}
