package Controllers;

import java.io.IOException;
import java.util.ArrayList;

import Entities.GeneralMessage;
import Entities.Review;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import ocsf.client.AbstractClient;

public class SearchReviewScreenController extends AbstractClient
{
	private ArrayList<Review> allReviews = null;
	private ArrayList<Review> updatedReviewList = null;
	private int origin = -1; // 0 - initialize(), 1 - onSearch();
	@FXML
	public ObservableList <Review> items = FXCollections.observableArrayList();
	public ListView <Review> reviewList = new ListView <Review>(items);
	public ComboBox<String> action = new ComboBox<String>();
	public TextField titleField,reviewerField,keyWordsField;
	
	public void initialize()
	{
		action.getItems().addAll("AND","OR");
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
