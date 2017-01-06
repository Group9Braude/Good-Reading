package Controllers;

import java.io.File;
import java.io.IOException;

import Entities.Book;
import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import ocsf.client.AbstractClient;

public class WorkerController extends AbstractClient {
	/****************************************/
	@FXML
	private Button addBookButton;
	@FXML
	private ImageView addedButton;
	@FXML
	private TextField titleTextField, authorTextField, languageTextField, summaryTextField, tocTextField,keywordTextField;
	@FXML
	private Text titleText,keywordText,authorText,languageText,summaryText,tocText;
	private static Pane mainLayout;
	private CheckBox titleCheckBox, authorCheckBox, languageCheckBox, summaryCheckBox, tocCheckBox, keywordCheckBox;




	public WorkerController() {
		super(Main.host, 3336);
	}





	//label1.setTextFill(Color.web("#0076a3"));
	public void onAddBook(){
		/*try {
			mainLayout = FXMLLoader.load(Main.class.getResource("/GUI/LoginScreen.fxml"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Main.popup.setScene(new Scene(mainLayout));
	Main.popup.show();*/
	File file = null ;
		Book book = new Book();
		boolean title, author, language, summary, toc, keyWord;//Check if all the fields were filled.
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

		//(Book.getBookList()).add(book);//Adding the book to the arraylist!

		if(title&&author&&language&&summary&&toc&&keyWord){//Every field is filled
			file = new File("C:/Users/orels/Desktop/Ass3Logos/AddedBook.png");
			addedButton.setImage(new Image(file.toURI().toString()));


			/****************************************/
			WorkerController workerController = new WorkerController();
			try {
				workerController.openConnection();
				workerController.sendToServer(book);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}//End onAddBook



	/****************************/
	public void onRemoveBook(){
		System.out.println(authorCheckBox.isSelected());
	}

	/****************************/




	public void onBack(){
		try {
			Main.showLoggedInScreenWorker();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	protected void handleMessageFromServer(Object msg) {
		if(msg instanceof String)
			System.out.println((String)msg);
	}


}