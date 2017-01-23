package Controllers;

import java.io.IOException;
import java.util.ArrayList;

import Entities.Genre;
import Entities.Theme;
import GUI.AlertBox;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import ocsf.client.AbstractClient;

public class EditTheme extends AbstractClient {
	static int flag=0;
	Theme theme=new Theme();
	Genre genre=new Genre();
	//TextField 
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

		//listeners
		themeBox.getSelectionModel().selectedItemProperty().addListener((v,oldValue,newValue)
				->{
					if(newValue!=null){
						themeTextField.setText(newValue.getTheme());
						genreBox.setValue(newValue.getGenre());
					}
					else{ themeTextField.setText("");
					genreBox.getSelectionModel().clearSelection();
					}

				});

	}



	public void initTheme(){
		flag=0;
		theme.actionNow="InitializeThemeList";

		try {
			this.openConnection();
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


	@SuppressWarnings("unused")
	public void onAddTheme(){
		if(themeTextField.getText().equals("")){
			AlertBox.display("Error", "You must fill theme text field in order theme!");
			return;
		}

		//if the theme already exists!
		else
			for(int i=0;i<obsTheme.size();i++)
			{
				if(obsTheme.get(i).getTheme().equals(themeTextField.getText())){
					AlertBox.display("Error", "Theme already exists, try press Update!");
					return;
				}
			}

		if(genreBox.getValue()==null)
		{
			AlertBox.display("Error", "You have to choose a genre the theme will be added to!");
			return;
		}

		//creating new theme with the elements
		theme=new Theme();
		theme.setTheme(themeTextField.getText());
		theme.setGenre(genreBox.getValue());
		//add theme to the genre's theme list
		genreBox.getValue().themeList.add(theme);

		Theme.themeList.add(theme);
		obsTheme.add(theme);
		themeBox.getSelectionModel().selectLast();


		theme.actionNow="AddTheme";

		try {
			this.openConnection();
			this.sendToServer(theme);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void onDeleteTheme(){
		try{
			if(themeBox.getValue()==null){
				AlertBox.display("Error", "You must select one from the themes in combo box!");
				return;
			}
			else {
				//setting the theme we want to delete
				theme.setTheme(themeBox.getValue().getTheme());
				//delete from lists
				obsTheme.remove(themeBox.getSelectionModel().getSelectedIndex());
				theme.getGenre().themeList.remove(theme);
				Theme.themeList.remove(theme);

				themeBox.getSelectionModel().selectPrevious();
				theme.actionNow="DeleteTheme";
				try {
					this.openConnection();
					this.sendToServer(theme);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (NullPointerException e)
		{
			AlertBox.display("Error", "You must select item from the list!");
			return;
		}
	}


	public void onUpdateTheme(){
		int index=themeBox.getSelectionModel().getSelectedIndex();
		try{
			// if nothing has selected
			if(themeBox.getValue()==null || genreBox.getValue()==null){
				AlertBox.display("Error", "you must choose theme and genre from combo box!");
				return;
			}

			theme=Theme.themeList.get(index);
			theme.setOldTheme(theme.getTheme());				

			//else if the name is still the same and the comments are the same
			if(themeTextField.getText()!=null)
				if (themeBox.getValue().getTheme().equals(themeTextField.getText()) && 
						genreBox.getValue().equals(theme.getGenre())){
					AlertBox.display("Error", "You must change at least one field to update!");
					return;
				}

			//if the name has changed
			if(themeTextField.getText()!=null)
				if(!theme.getTheme().equals(themeTextField.getText())){
					theme.setTheme(themeTextField.getText());
					themeBox.getSelectionModel().clearAndSelect(index);
					obsTheme.set(index, theme);
				}
			//if the genre has changed
			if(genreBox.getValue()!=theme.getGenre()) {
				theme.getGenre().themeList.remove(theme);
				theme.setGenre(genreBox.getValue());
				theme.getGenre().themeList.add(theme);
			}


			obsTheme=FXCollections.observableArrayList(Theme.themeList);
			theme.actionNow="UpdateTheme";

			try {
				this.openConnection();
				this.sendToServer(theme);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (NullPointerException e)
		{
			AlertBox.display("Error", "You must select item from the list!");
			return;
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
		if(((ArrayList<?>)msg).size() > 0){
			if(((ArrayList<?>)msg).get(0) instanceof Genre){
				flag=1;
				Genre.genreList.clear();
				for(Genre genre:(ArrayList<Genre>)msg)
					Genre.genreList.add(genre);
			}
			if(((ArrayList<?>)msg).get(0) instanceof Theme){
				flag=1;
				Theme.themeList.clear();
				for(Theme theme:(ArrayList<Theme>)msg)
					Theme.themeList.add(theme);
			}
		}
		flag=1;
	}


}