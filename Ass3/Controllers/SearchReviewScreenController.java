package Controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Entities.Book;
import Entities.GeneralMessage;
import Entities.Review;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import ocsf.client.AbstractClient;

public class SearchReviewScreenController extends AbstractClient
{
	private ArrayList<Review> allReviews = null;
	private ArrayList<Review> updatedReviewList = null;
	private int origin = -1; // 0 - initialize(), 1 - onSearch();
	@FXML
	public ObservableList <Review> items = FXCollections.observableArrayList();
	public TableView <Review> reviewList = new TableView <Review>(items);
	public ComboBox<String> action = new ComboBox<String>();
	public TextField titleField,reviewerField,keyWordsField;
	public TextArea reviewText;
	
	@SuppressWarnings("unchecked")
	public void initialize()
	{
		action.getItems().addAll("AND","OR");
		TableColumn<Review,String> titleColumn =new TableColumn<Review,String>("Title");
		titleColumn.setCellValueFactory(new PropertyValueFactory<>("reviewBook"));//What attribute of class this column takes

		TableColumn<Review,Integer> IDColumn =new TableColumn<Review,Integer>("Review ID");
		IDColumn.setCellValueFactory(new PropertyValueFactory<>("ReviewID"));

		TableColumn<Review,String> reviewerColumn =new TableColumn<Review,String>("Reviewer");
		reviewerColumn.setCellValueFactory(new PropertyValueFactory<>("signature"));

		TableColumn<Review,String> keyWordColumn =new TableColumn<Review,String>("Key words");
		keyWordColumn.setCellValueFactory(new PropertyValueFactory<>("keyword"));

		reviewList.getColumns().addAll(titleColumn,IDColumn,reviewerColumn,keyWordColumn);//Adding the columns to the table
		try {
			this.openConnection();
			GeneralMessage dummy = new GeneralMessage();	
			dummy.actionNow="GetReviewsForReader";
			origin = 0;
			this.sendToServer(dummy);
			while(allReviews == null)
				Thread.sleep(10);
			items = FXCollections.observableArrayList(allReviews);
			reviewList.setItems(items);
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public SearchReviewScreenController() {
		super(Main.host, Main.port);
		// TODO Auto-generated constructor stub
	}
	
	public void onBack()
	{
		Main.showReaderLoginScreen();
	}
	
	public void showReview()
	{
		Review review = reviewList.getSelectionModel().getSelectedItem();
		if(review != null)
			reviewText.setText(review.getReview());
	}
	
	public void onSearch()
	{
		String userTitle = titleField.getText(), reviewer = reviewerField.getText(),
				keyWords = keyWordsField.getText();
		int i;	
		String query = "SELECT * FROM reviews WHERE isApproved=1 AND (";
		String op = action.getSelectionModel().getSelectedItem();
		if(op==null)
			JOptionPane.showMessageDialog(null, "You must first select the operation");
		else if(userTitle.equals("") && reviewer.equals("") &&  keyWords.equals(""))
			JOptionPane.showMessageDialog(null, "You must enter at least one search parameter");
		else{
			if(!userTitle.equals(""))
				query +=" title LIKE '%" + userTitle + "%' " + op;

			if(!reviewer.equals(""))
			{
				String[] temp = reviewer.split(",");
				for(i=0; i<temp.length;i++)
					query +=" signature LIKE '%" + temp[i] + "%' " + op;
			}


			if(!keyWords.equals(""))
			{
				String[] temp = keyWords.split(",");
				for(i=0; i<temp.length;i++)
					query+=" keyword LIKE '%" + temp[i] + "%' " + op;
			}
			String finalQuery="";
			for(i=0;i<query.length()-op.length();i++)//Remove the operation from the end of the query
				finalQuery+=query.charAt(i);
			finalQuery+=");";
			System.out.println(finalQuery);
			origin = 1;
			updatedReviewList = null;
			Review dummy = new Review();
			dummy.query = finalQuery;
			dummy.actionNow = "updateReviewList";
			try {
				this.sendToServer(dummy);
				while(updatedReviewList==null)
					Thread.sleep(10);
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			items = FXCollections.observableArrayList(updatedReviewList);
			reviewList.setItems(items);
		}
	}
	
	public void onReset()
	{
		items = FXCollections.observableArrayList(allReviews);
		reviewList.setItems(items);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void handleMessageFromServer(Object msg) {
		if(msg instanceof ArrayList)
		{
			if(origin == 0)
				allReviews = (ArrayList<Review>)msg;
			else updatedReviewList = (ArrayList<Review>)msg;
			origin = -1;
		}
		
	}

}
