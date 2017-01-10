package Controllers;
//SEND THE WHOLE CLASS

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import Entities.Book;
import Entities.GeneralMessage;
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
	@FXML//TextFields for book search/add
	private TextField titleTextField, authorTextField, languageTextField, summaryTextField, tocTextField, keywordTextField;
	@FXML
	private Text titleText,keywordText,authorText,languageText,summaryText,tocText;
	@FXML//TextFields for book removal
	private TextField titleTextFieldR, authorTextFieldR, languageTextFieldR, summaryTextFieldR, tocTextFieldR, keywordTextFieldR;
	@FXML//TextFields for Worker search
	private TextField workerIDTextFieldW, TextFieldW, lastNameTextFieldW, idTextFieldW, firstNameTextFieldW;
	@FXML
	private CheckBox titleCheckBox, authorCheckBox, languageCheckBox, summaryCheckBox, tocCheckBox, keywordCheckBox;
	@FXML 
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
		//WorkerController.flag=1;
		Book deleteBook = new Book();
		boolean flag=false; // Tell me if I should use the deleteBook.deleteBookList arraylist or the main arraylist
		deleteBook.deleteBookList = new ArrayList<Book>();
		int j=0;


		//isTitleCheckBox
		if(titleCheckBox.isSelected()){
			flag=true;
			for(Book book:Book.bookList){
				if(book.getTitle().equals(titleTextFieldR.getText()))
					deleteBook.deleteBookList.add(book);
			}
		}//isTitleCheckBox end

		//isAuthorCheckBox
		if(authorCheckBox.isSelected()){
			if(flag){
				for(int i=0;i<deleteBook.deleteBookList.size();i++)
					if(!deleteBook.deleteBookList.get(i).getAuthor().equals(authorTextFieldR.getText()))//If the author does not match
						deleteBook.deleteBookList.remove(i);	
			}//end if
			else{
				for(Book book:Book.bookList){
					if(book.getAuthor().equals(authorTextFieldR.getText()))
						deleteBook.deleteBookList.add(book);
				}
			}//end else
		}//isAuthorCheckBox end

		//isLanguageCheckBox
		if(languageCheckBox.isSelected()){
			if(flag){
				for(int i=0;i<deleteBook.deleteBookList.size();i++)
					if(!deleteBook.deleteBookList.get(i).getLanguage().equals(languageTextFieldR.getText()))//If the author does not match
						deleteBook.deleteBookList.remove(i);	
			}//end if
			else{
				flag=true;
				for(Book book:Book.bookList){
					if(book.getLanguage().equals(languageTextFieldR.getText()))
						deleteBook.deleteBookList.add(book);
				}
			}//end else
		}//isLanguageCheckBox end

		//isSummaryCheckBox
		if(summaryCheckBox.isSelected()){
			if(flag){
				for(int i=0;i<deleteBook.deleteBookList.size();i++)
					if(!deleteBook.deleteBookList.get(i).getSummary().contains(summaryTextFieldR.getText()))//If the author does not match
						//Contains because it might be part of
						deleteBook.deleteBookList.remove(i);	
			}//end if
			else{
				flag=true;
				for(Book book:Book.bookList){
					if(book.getSummary().equals(summaryTextFieldR.getText()))
						deleteBook.deleteBookList.add(book);
				}
			}//end else
		}//isSummaryCheckBox end

		//isTocCheckBox
		if(tocCheckBox.isSelected()){
			if(flag){
				for(int i=0;i<deleteBook.deleteBookList.size();i++)
					if(!deleteBook.deleteBookList.get(i).getToc().contains(tocTextFieldR.getText()))//If the author does not match
						//Contains because it might be part of
						deleteBook.deleteBookList.remove(i);	
			}//end if
			else{
				flag=true;
				for(Book book:Book.bookList){
					if(book.getToc().equals(tocTextFieldR.getText()))
						deleteBook.deleteBookList.add(book);
				}
			}
		}//isTocCheckBox end

		//isKeywordCheckBox
		if(keywordCheckBox.isSelected()){
			if(flag){
				for(int i=0;i<deleteBook.deleteBookList.size();i++)
					if(!deleteBook.deleteBookList.get(i).getKeyword().equals(keywordTextFieldR.getText()))//If the author does not match
						deleteBook.deleteBookList.remove(i);	
			}//end if
			else{
				for(Book book:Book.bookList){
					if(book.getKeyword().equals(keywordTextFieldR.getText()))
						deleteBook.deleteBookList.add(book);
				}
			}
		}//isKeywordCheckBox end
		sendServer(deleteBook, "RemoveBook");


	}//End onRemoveBook





	public void onBack(){
		try {
			Main.showLoggedInScreenWorker();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}




	public void onWorkerSearch(){
		ArrayList<Worker> workers = new ArrayList<Worker>();
		boolean flag=false;//To know if I should remove from the new arraylist or the main into the new


		if((idTextFieldW.getText()=="")){
			for(Worker worker:Worker.workerList)
				if(worker.getId().equals(idTextFieldW.getText())){
					workers.add(worker);
					break;
				}
		}

		else if((workerIDTextFieldW.getText()=="")){

			for(Worker worker:Worker.workerList)
				if(worker.getId().contains(workerIDTextFieldW.getText())){
					workers.add(worker);
					break;
				}
		}

		else{
			if((firstNameTextFieldW.getText()!="")){
				System.out.println("thefuck");
				flag=true;
				for(Worker worker:Worker.workerList)
					if(worker.getFirstName().contains(firstNameTextFieldW.getText()))
						workers.add(worker);
			}



			if((lastNameTextFieldW.getText()==""))				
				if(!flag){
					flag=true;
					for(Worker worker:Worker.workerList)
						if(worker.getLastName().contains(lastNameTextFieldW.getText()))
							workers.add(worker);
				}
				else{
					for(int i=0;i<workers.size();i++)
						if(!workers.get(i).getLastName().contains(lastNameTextFieldW.getText()))
							workers.remove(i);
				}


			/*if((roleChoiceBox.getSelectionModel().getSelectedItem().toString()!=""))				
				if(!flag){
					flag=true;
					for(Worker worker:Worker.workerList)
						if(worker.getRole()==roleChoiceBox.getSelectionModel().getSelectedItem().toString())
							workers.add(worker);
				}
				else{
					for(int i=0;i<workers.size();i++)
						if(workers.get(i).getRole()!=roleChoiceBox.getSelectionModel().getSelectedItem().toString())
							workers.remove(i);
				}


			if((departmentChoiceBox.getSelectionModel().getSelectedItem().toString()!=""))				
				if(!flag){
					flag=true;
					for(Worker worker:Worker.workerList)
						if(worker.getDepartment()==departmentChoiceBox.getSelectionModel().getSelectedItem().toString())
							workers.add(worker);
				}
				else{
					for(int i=0;i<workers.size();i++)
						if(workers.get(i).getDepartment()!=departmentChoiceBox.getSelectionModel().getSelectedItem().toString())
							workers.remove(i);
				}*/
			for(Worker worker:workers)
				System.out.println("Workers:\n" + worker.getFirstName());

		}

	}//End onWorker

	public void onReaderSearch(){

	}










	public void onLoggedReaders(){

	}

	public void onDebtReaders(){

	}

	public void onFrozenReaders(){

	}

	public void onAllManagers(){

	}


	public void onAllWorkers(){

	}

	public void onLoggedWorkers(){

	}











	protected void handleMessageFromServer(Object msg) {
		if(msg instanceof String)
			System.out.println((String)msg);
		else if(msg instanceof ArrayList){
			for(Book book:(ArrayList<Book>)msg)
				Book.bookList.add(book);

		}
	}


}