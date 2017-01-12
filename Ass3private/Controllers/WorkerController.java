package Controllers;
//SEND THE WHOLE CLASS

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import Entities.Book;
import Entities.GeneralMessage;
import Entities.Reader;
import Entities.User;
import Entities.Worker;
import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import ocsf.client.AbstractClient;

public class WorkerController extends AbstractClient {

	@FXML
	private Button addBookButton;
	@FXML
	private ImageView addedButton;
	@FXML
	private Text titleText,keywordText,authorText,languageText,summaryText,tocText;
	@FXML
	private TextField titleTextFieldR, authorTextFieldR, languageTextFieldR, summaryTextFieldR, tocTextFieldR, keywordTextFieldR,//TextFields for book removal
	idTextFieldR, firstNameTextFieldR, lastNameTextFieldR, readerIDTextFieldR,//For reader search
	workerIDTextFieldW, TextFieldW, lastNameTextFieldW, idTextFieldW, firstNameTextFieldW,//TextFields for Worker search
	titleTextField, authorTextField, languageTextField, summaryTextField, tocTextField, keywordTextField;//TextFields for book search/add
	private ChoiceBox<String> departmentChoiceBox, roleChoiceBox;





	public WorkerController() {
		super(Main.host, Main.port);

	}


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




	/*try {
	mainLayout = FXMLLoader.load(Main.class.getResource("/GUI/LoginScreen.fxml"));
} catch (IOException e1) {
	e1.printStackTrace();
}
Main.popup.setScene(new Scene(mainLayout));
Main.popup.show();*/
	//label1.setTextFill(Color.web("#0076a3"));




	public void onAddBook(){
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


			if(title&&author&&language&&summary&&toc&&keyWord){//Every field is filled
				//set picture
				file = new File("C:/Users/orels/Desktop/Ass3Logos/AddedBook.png");
				addedButton.setImage(new Image(file.toURI().toString()));
				Book.bookList.add(book);//Update our ARRAYLIST!

				sendServer(book, "AddBook");
			}
		}
	}//End onAddBook



	/****************************/


	public void onRemoveBook(){
		Book book = new Book();
		String title = titleTextFieldR.getText(), author = authorTextFieldR.getText(),
				language=languageTextFieldR.getText(), summary=summaryTextFieldR.getText(),
				toc = tocTextFieldR.getText(), keyword = keywordTextFieldR.getText();
		book.query = "DELETE FROM books WHERE ";
		if(!title.equals(""))
			book.query +="title = '" + title + "' AND ";
		if(!author.equals(""))
			book.query +="author = '" + author + "' AND ";
		if(!language.equals(""))
			book.query+="language = '" + language + "' AND ";
		if(!summary.equals(""))
			book.query+="summary LIKE '%" + summary + "%' AND ";
		if(!toc.equals(""))
			book.query+="tableOfContents = '" + toc + "' AND ";
		if(!keyword.equals(""))
			book.query+="keyWord = '" + keyword + "' AND ";
		String query = "";
		for(int i=0;i<book.query.length()-5;i++)
			query+=book.query.charAt(i);
		query+=";";
		book.query=query;
		//System.out.println(query);
		sendServer(book, "RemoveBook");
		}//end onRemoveBook




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
	}//End onWorkerSearch
	
	
	public  void onLogout(){
		System.out.println(User.currentWorker.getWorkerID());
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
		
		
	}
	public void onLoggedReaders(){
		Reader reader = new Reader();
		sendServer(reader, "FindLoggedReaders");
	}
	public void onDebtReaders(){
		Reader reader = new Reader();
		sendServer(reader, "FindDebtReaders");
	}
	public void onFrozenReaders(){
		Reader reader = new Reader();
		sendServer(reader, "FindFrozenReaders");
	}
	public void onAllManagers(){
		Worker worker = new Worker();
		sendServer(worker, "FindAllManagers");
	}
	public void onAllWorkers(){
		Worker worker = new Worker();
		sendServer(worker, "FindAllWorkers");
	}
	public void onLoggedWorkers(){
		Worker worker=  new Worker();
		sendServer(worker, "FindLoggedWorkers");
	}


	protected void handleMessageFromServer(Object msg) {
		if(msg instanceof String)
			System.out.println((String)msg);
		else if(((ArrayList<?>)msg).get(0) instanceof Book){
			for(Book book:(ArrayList<Book>)msg)
				Book.bookList.add(book);
		}
		else if(((ArrayList<?>)msg).get(0) instanceof String){
			for(String str:(ArrayList<String>)msg)
				System.out.println(str);
		}
	}


}