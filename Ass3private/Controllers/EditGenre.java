package Controllers;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import Entities.Genre;
import GUI.AlertBox;
import application.Main;
import ocsf.client.AbstractClient;

public class EditGenre extends AbstractClient {
	static int flag=0;
	Genre genre=new Genre();
	public ComboBox<Genre> genreBox=new ComboBox<>();
	public ObservableList<Genre> obsGenre=FXCollections.observableArrayList(Genre.genreList);
	public TextField genreTextField=new TextField();
	public TextField commentsTextField=new TextField();

	public EditGenre() {
		super(Main.host, Main.port);
		try {
			this.openConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	public void initialize(){
		if(flag==0){
			int i=0;
			genre.actionNow="InitializeGenreList";
			try {
				this.sendToServer(genre);
			} catch (IOException e) {
				// TODO Auto-generated catch block
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
		}
		obsGenre.clear();
		obsGenre=FXCollections.observableArrayList(Genre.genreList);
		genreBox.setItems(obsGenre);
		genreBox.getSelectionModel().selectedItemProperty().addListener((v,oldValue,newValue)
				->genreTextField.setText(newValue.getGenre()));
		genreBox.getSelectionModel().selectedItemProperty().addListener((v,oldValue,newValue)
				->commentsTextField.setText(Genre.genreList.get(genreBox.getSelectionModel().getSelectedIndex()).getComments()));		
	}


	public void onAddGenre(){
		if(commentsTextField.getText().equals("") && genreTextField.getText().equals("")){
			System.out.println(commentsTextField.getText());
			AlertBox.display("Error", "You must fill at least one of the fields!");
			return;
		}

		else if(genreTextField.getText().equals("")) {
			AlertBox.display("Error", "You must fill Name first!");
			return;
		}

		else if(obsGenre.contains(genreTextField.getText())){
			AlertBox.display("Error", "Genre already exists, try press Update!");
			return;
		}
		genre=new Genre();

		if(!(commentsTextField.getText().equals("")))
			genre.setComments(commentsTextField.getText());
		genre.setGenre(genreTextField.getText());

		Genre.genreList.add(genre);
		obsGenre.add(genre);
		genreBox.getSelectionModel().selectLast();
		genre.actionNow="AddGenre";
		try {
			this.openConnection();
			this.sendToServer(genre);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void onDeleteGenre(){
		try{
			if(genreBox.getValue().equals("")){
				AlertBox.display("Error", "You must fill at least one of the fields!");
				return;
			}
			else {

				genre.setGenre(genreBox.getValue().getGenre());
				Genre.genreList.remove(genre);
				obsGenre.remove(genre);
				genreBox.getSelectionModel().selectPrevious();
				genre.actionNow="DeleteGenre";
				try {
					this.openConnection();
					this.sendToServer(genre);
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


	public void onUpdateGenre() throws InterruptedException{
		int index=genreBox.getSelectionModel().getSelectedIndex();
		try{
			// if nothing has selected
			if(genreBox.getValue().equals("")){
				AlertBox.display("Error", "You must fill at least one of the fields!");
				return;
			}

			genre=Genre.genreList.get(index);
			genre.setOldGenre(genre.getGenre());

			//else if the name is still the same and the comments are the same
			if(genreTextField.getText()!=null  && commentsTextField.getText()!=null)
				if (genreBox.getValue().getGenre().equals(genreTextField.getText()) && 
						genre.getComments().equals(commentsTextField.getText())){
					AlertBox.display("Error", "You must change field to update!");
					return;
				}
			//if the name has changed
			if(genreTextField.getText()!=null)
				if(!genre.getGenre().equals(genreTextField.getText())){
					genre.setGenre(genreTextField.getText());
					genreBox.getSelectionModel().clearAndSelect(index);
					obsGenre.set(index, genre);
				}
			//if the comments has changed
			if(commentsTextField.getText()!=null)
				if(!commentsTextField.getText().equals(genre.getComments())){
					genre.setComments(commentsTextField.getText());
				}


			obsGenre=FXCollections.observableArrayList(Genre.genreList);
			genre.actionNow="UpdateGenre";

			try {
				this.openConnection();
				this.sendToServer(genre);
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
	protected void handleMessageFromServer(Object msg) {
		if(msg instanceof String)
			System.out.println((String)msg);
		if(((ArrayList<?>)msg).size() > 0)
			if(((ArrayList<?>)msg).get(0) instanceof Genre){
				for(Genre genre:(ArrayList<Genre>)msg)
					Genre.genreList.add(genre);
			}
		flag=1;
	}

}
