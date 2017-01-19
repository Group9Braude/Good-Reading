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
	int flag=0;
	Genre genre=new Genre();
	public ComboBox<String> genreBox=new ComboBox<>();
	public ObservableList<String> obsGenre=FXCollections.observableArrayList();
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
		flag=0;
		int j=0;
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
			//	System.out.println(" "+j++);
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for(i=0;i<Genre.genreList.size();i++)
			obsGenre.add(Genre.genreList.get(i).getGenre());
		genreBox.setItems(obsGenre);
		genreBox.getSelectionModel().selectedItemProperty().addListener((v,oldValue,newValue)
				->genreTextField.setText(newValue));
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

		if(!(commentsTextField.getText().equals("")))
			genre.setComments(commentsTextField.getText());
		genre.setGenre(genreTextField.getText());

		Genre.genreList.add(genre);
		obsGenre.add(genre.getGenre());
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
				genre.setGenre(genreBox.getValue());

				Genre.genreList.remove(genre);
				obsGenre.remove(genre.getGenre());
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


	public void onUpdateGenre(){
		
		try{
			if(genreBox.getValue().equals("")){
				AlertBox.display("Error", "You must fill at least one of the fields!");
				return;
			}
			else if (genreBox.getValue().equals(genreTextField.getText()) && 
					Genre.genreList.get(genreBox.getSelectionModel().getSelectedIndex()).getComments().equals(commentsTextField.getText())){
					AlertBox.display("Error", "You must change field to update!");
					return;
			}		


			else {
				Genre.genreList.get(genreBox.getSelectionModel().getSelectedIndex())
			.setGenre(genreTextField.getText());
			Genre.genreList.get(genreBox.getSelectionModel().getSelectedIndex())
			.setComments(commentsTextField.getText());
			genre.setGenre(genreTextField.getText());
			genre.setComments(commentsTextField.getText());
			genre.setOldGenre(genreBox.getValue());
			}

			obsGenre.remove(genre.getOldGenre());
			obsGenre.add(genre.getGenre());
			
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


	protected void handleMessageFromServer(Object msg) {
		if(msg instanceof String)
			System.out.println((String)msg);

		if(((ArrayList<?>)msg).get(0) instanceof Genre){
			System.out.println("h");
			flag=1;
			for(Genre genre:(ArrayList<Genre>)msg)
				Genre.genreList.add(genre);
		}

	}

}
