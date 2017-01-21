package Controllers;

import java.io.IOException;
import java.util.ArrayList;

import Entities.Genre;
import Entities.Theme;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import ocsf.client.AbstractClient;

public class EditTheme extends AbstractClient {
	int flag=0, initTheme=0;
	Theme theme=new Theme();
	Genre genre=new Genre();
	//Genres ComboBox
	public ComboBox<Genre> genreBox=new ComboBox<>();
	public ObservableList<Genre> obsGenre=FXCollections.observableArrayList(Genre.genreList);
	//Themes ComboBox
	public ComboBox<Theme> themeBox=new ComboBox<>();
	public ObservableList<Theme> obsTheme=FXCollections.observableArrayList(Theme.themeList);
	public TextField themeTextField=new TextField();



	public EditTheme() {
		super(Main.host, Main.port);
		try {
			this.openConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@FXML
	public void initialize(){
		flag=0;
		genre.actionNow="InitializeGenreList";
		try {
			this.sendToServer(genre);
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(flag==0){
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		obsGenre=FXCollections.observableArrayList(Genre.genreList);
		genreBox.setItems(obsGenre);
		
	}
	public void initTheme(){
		if(initTheme==0){
			initTheme=1;
			flag=0;
			theme.actionNow="InitializeThemeList";
			
			try {
				this.sendToServer(theme);
			} catch (IOException e) {
				e.printStackTrace();
			}
			while(flag==0){
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			obsTheme=FXCollections.observableArrayList(Theme.themeList);
			themeBox.setItems(obsTheme);
		}
	}


	public void onBack(){
		try {
			Main.showLoggedInScreenWorker();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	protected void handleMessageFromServer(Object msg) {
		if(msg instanceof String)
			System.out.println((String)msg);

		if(((ArrayList<?>)msg).get(0) instanceof Genre){
			System.out.println("h");
			flag=1;
			for(Genre genre:(ArrayList<Genre>)msg)
				Genre.genreList.add(genre);
		}
		if(((ArrayList<?>)msg).get(0) instanceof Theme){
			flag=1;
			for(Theme theme:(ArrayList<Theme>)msg)
				Theme.themeList.add(theme);
		}
		flag=1;
	}


}
