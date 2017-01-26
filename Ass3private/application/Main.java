package application;

import java.io.IOException;

import Controllers.LoginScreenController;
import Entities.User; 
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * The main function handles the whole functions which are present the different screens
 * @author ozdav
 *
 */
public class Main extends Application {
	@FXML
	private TextField idfield;
	private static Stage primaryStage;
	public static Parent mainLayout;
	public static int port=3307;
	public static String host = "localhost";
	public static Stage popup;
	private static User currentUser;

/**
 * Shorten the writing of sleep
 * @param time
 */
	public static void Sleep(int time){
		try {Thread.sleep(time);} catch (InterruptedException e) {e.printStackTrace();}
	}
	
	@Override
	/**
	 * This method creates a stage and initializes it
	 */
	public void start(Stage primaryStage) throws IOException {
		Main.primaryStage = primaryStage;
		popup = new Stage();
        primaryStage.getIcons().add(new Image("/src/41.png"));
        primaryStage.setTitle("Good Reading System");
		popup.initModality(Modality.APPLICATION_MODAL);
		popup.initOwner(primaryStage);
		showMainMenu();
	}
	
/**
 * Presents the 'Edit Book' screen
 * @throws IOException
 */
	public static void showAddNewReaderScreen()throws IOException{
		FXMLLoader loader = new FXMLLoader(); 
		loader.setLocation(Main.class.getResource("/GUI/AddNewUser.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.show();
	}
	
	public static void showEditBookScreen()throws IOException{
		FXMLLoader loader = new FXMLLoader(); 
		loader.setLocation(Main.class.getResource("/GUI/EditBook.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.show();
	}
/**
 *  Presents the 'Update Book' screen	
 * @throws IOException
 */
	public static void showUpdateBookScreen()throws IOException{
		FXMLLoader loader = new FXMLLoader(); 
		loader.setLocation(Main.class.getResource("/GUI/UpdateBookScreen.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.show();
	}
/**
 *  Presents the 'Search Book' screen
 * @throws IOException
 */
	public static void showSearchBookForUpdate() throws IOException{
		FXMLLoader loader = new FXMLLoader(); 
		loader.setLocation(Main.class.getResource("/GUI/SearchBookForUpdate.fxml"));
			mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.show();
	}
/**
 * Closes the currently screen
 */
	public static void onExit(){
		primaryStage.close();
	}
	
/**
 * 	Presents 'Edit Reviews' screen
 * @throws IOException
 */
	public static void showFinalReviewScreen() throws IOException{
		FXMLLoader loader = new FXMLLoader(); 
		loader.setLocation(Main.class.getResource("/GUI/FinalReviewScreen.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.show();
	}
/**
 * Presents the 'Search user' screen
 * @throws IOException
 */
	public static void showSearchUser() throws IOException{
		FXMLLoader loader = new FXMLLoader(); 
		loader.setLocation(Main.class.getResource("/GUI/SearchUser.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.show();
	}


/**
 * Presents the 'LOGIN' screen
 * @throws IOException
 */
	public static void showMainMenu() throws IOException{
		FXMLLoader loader = new FXMLLoader(); 
		loader.setLocation(Main.class.getResource("/GUI/LoginScreen.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.show();
	}
/**
 * Presents the 'Search Book' screen	
 */
	public static void showSearchScreen()
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/GUI/SearchBookScreen.fxml"));
		try {    
			mainLayout =  loader.load();
			primaryStage.setScene(new Scene(mainLayout));
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
/**
 * Presents the 'Searching a review' screen	
 */
	public static void showSearchReviewScreen()
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/GUI/SearchReviewScreen.fxml"));
		try {    
			mainLayout =  loader.load();
			primaryStage.setScene(new Scene(mainLayout));
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

/**
 * Presents the 'Subscription screen' to user
 */
	public static void showSubscriptionScreen()
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/GUI/chooseSubscriptionScreen.fxml"));
		try {    
			mainLayout =  loader.load();
			primaryStage.setScene(new Scene(mainLayout));
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
/**
 * Presents the 'Edit genre' screen
 */
	public static void showEditGenre()
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/GUI/EditGenre.fxml"));
		try {    
			mainLayout =  loader.load();
			primaryStage.setScene(new Scene(mainLayout));
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

/**
 * Presents the Reader's main screen	
 */
	public static void showEditTheme()
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/GUI/EditTheme.fxml"));
		try {    
			mainLayout =  loader.load();
			primaryStage.setScene(new Scene(mainLayout));
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void showReaderLoginScreen(){
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/GUI/LoginScreenReader.fxml"));
		try {
			mainLayout =  loader.load();
			primaryStage.setScene(new Scene(mainLayout));
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}  

/**
 * Presents the Worker's main screen
 * @throws IOException
 */
	public static void showLoggedInScreenWorker() throws IOException{
		FXMLLoader loader = new FXMLLoader(); 
		loader.setLocation(Main.class.getResource("/GUI/LoggedInScreenWorker.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.show();
	}


/**
 * Presents the 'Add book' screen
 * @throws IOException
 */
	public static void showAddBook() throws IOException{
		FXMLLoader loader = new FXMLLoader(); 
		loader.setLocation(Main.class.getResource("/GUI/AddBook.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.show();
	}
/**
 * Presents the Manager's main screen
 * @throws IOException
 */
	public static void showManagerLoggedScreen() throws IOException{
		FXMLLoader loader = new FXMLLoader(); 
		loader.setLocation(Main.class.getResource("/GUI/ManagerLoggedScreen.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.setTitle("Good Reading System");
		primaryStage.show();
	}
/**
 * Presents the 'Temporarily remove a book' option screen
 * @throws IOException
 */
	public static void showTempRemoveAbook() throws IOException{
		FXMLLoader loader = new FXMLLoader(); 
		loader.setLocation(Main.class.getResource("/GUI/TempRemoveAbook.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.show();
	}
/**
 * Presents the 'Activate suspended books' option screen
 * @throws IOException
 */
	public static void showActiveBooks() throws IOException{
		FXMLLoader loader = new FXMLLoader(); 
		loader.setLocation(Main.class.getResource("/GUI/ActiveBooks.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.show();
	}
/**
 * Presents the 'Remove book' option screen
 * @throws IOException
 */
	public static void showRemoveBook() throws IOException{
		FXMLLoader loader = new FXMLLoader(); 
		loader.setLocation(Main.class.getResource("/GUI/RemoveBook.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.show();
	}
/**
 * Presents the 'Review' screen, which lets the reader write a comment on a book
 */
	public static void showReviewScreen()
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/GUI/ReviewScreen.fxml"));
		try 
		{
			mainLayout = loader.load();
			primaryStage.setScene(new Scene(mainLayout));
			primaryStage.show();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
/**
 * Presents the manager the 'Get periodical reports' option on his screen
 * @throws IOException
 */
	public static void showReports() throws IOException {
		FXMLLoader loader = new FXMLLoader(); 
		loader.setLocation(Main.class.getResource("/GUI/Reports.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.show();  
	}
	
/**
 * This function happens when the client closes the stage, by the 'X'
 * <p>
 * Changes the user's status to offline in DB
 */
	public void stop()
	{
		if(currentUser != null)
		{
			LoginScreenController sender = new LoginScreenController();
			User toLogOut = currentUser;
			sender.sendServer(toLogOut, "Logout");
		}
	} 
/**
 * Main function
 * @param args
 */
	public static void main(String[] args) {
		launch(args);
	}
/**
 * @return the current client details
 */
	public static User getCurrentUser() {
		return currentUser;
	}
/**
 * Sets current user
 * @param currentUser A specific client
 */
	public static void setCurrentUser(User currentUser) {
		Main.currentUser = currentUser;
	}
/**
 * Gets primary stage
 * @return
 */
	public static Stage getPrimaryStage() {
		return primaryStage;
	}

/**
 * Sets primary stage
 * @param primaryStage
 */
	public static void setPrimaryStage(Stage primaryStage) {
		Main.primaryStage = primaryStage;
	}

}

