package application;

import java.io.IOException;

import com.sun.prism.Image;

import Controllers.LoginScreenController;
import Entities.User;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;



public class Main extends Application {
	@FXML
	private TextField idfield;
	private static Stage primaryStage;
	private static Parent mainLayout;
	public static int port=3307;
	public static String host = "localhost";
	public static Stage popup;
	private static User currentUser;


	@Override
	public void start(Stage primaryStage) throws IOException {
		Main.primaryStage = primaryStage;
		popup = new Stage();
		popup.initModality(Modality.APPLICATION_MODAL);
		popup.initOwner(primaryStage);
		showMainMenu();
	}

	public static Stage getStage()
	{ 
		return primaryStage;
	}

	public static void showSearchUser() throws IOException{
		FXMLLoader loader = new FXMLLoader(); 
		loader.setLocation(Main.class.getResource("/GUI/SearchUser.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.show();
	}
	public static void showSuspendSuccess() throws IOException{
		FXMLLoader loader = new FXMLLoader(); 
		loader.setLocation(Main.class.getResource("/GUI/SuspendSuccess.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.show();
	}

	public static void showMainMenu() throws IOException{
		FXMLLoader loader = new FXMLLoader(); 
		loader.setLocation(Main.class.getResource("/GUI/LoginScreen.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		//primaryStage.getIcons().add(new Image("C:\\Users\\orels\\Desktop\\Reader")
		primaryStage.show();
	}


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


	public static void showLoggedInScreenWorker() throws IOException{
		FXMLLoader loader = new FXMLLoader(); 
		loader.setLocation(Main.class.getResource("/GUI/LoggedInScreenWorker.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.show();
	}



	public static void showUserDetails(){
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("GUI/UserDetails.fxml"));
		try {
			mainLayout = loader.load();
			//   idfield.setText();
			primaryStage.setScene(new Scene(mainLayout));
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public static void showAddBook() throws IOException{
		FXMLLoader loader = new FXMLLoader(); 
		loader.setLocation(Main.class.getResource("/GUI/AddBook.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.show();
	}

	public static void showManagerLoggedScreen() throws IOException{
		FXMLLoader loader = new FXMLLoader(); 
		loader.setLocation(Main.class.getResource("/GUI/ManagerLoggedScreen.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.setTitle("Good Reading System");
		primaryStage.show();
	}

	public static void showTempRemoveAbook() throws IOException{
		FXMLLoader loader = new FXMLLoader(); 
		loader.setLocation(Main.class.getResource("/GUI/TempRemoveAbook.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.show();
	}

	public static void showRemoveBook() throws IOException{
		FXMLLoader loader = new FXMLLoader(); 
		loader.setLocation(Main.class.getResource("/GUI/RemoveBook.fxml"));
		mainLayout = loader.load();
		primaryStage.setScene(new Scene(mainLayout));
		primaryStage.show();
	}

	public void stop()
	{
		if(currentUser != null)
		{
			LoginScreenController sender = new LoginScreenController();
			User toLogOut = currentUser;
			sender.sendServer(toLogOut, "Logout");
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	public static User getCurrentUser() {
		return currentUser;
	}

	public static void setCurrentUser(User currentUser) {
		Main.currentUser = currentUser;
	}
}
