package Controllers;


import java.io.IOException;
import java.util.ArrayList;

import Entities.GeneralMessage;
import Entities.OrderedBook;
import Entities.Reader;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import ocsf.client.AbstractClient;

public class ReportsController extends AbstractClient {

	public ReportsController() {
		super(Main.host,Main.port);
		// TODO Auto-generated constructor stub
	}

	public void sendServer(Object msg, String actionNow){/******************************/
		((GeneralMessage)msg).actionNow = actionNow;
		ReportsController client = new ReportsController();
		try {
			client.openConnection();
			client.sendToServer(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	static int flag=0;
	@FXML
	public TextField id;
	@FXML
	public ListView<String> myBooks;


	public ObservableList<String> obsMyBooks=FXCollections.observableArrayList();


	@FXML
	public void onEnter(){
		Reader r=new Reader(id.getText(),null);
		sendServer(r,"getUserBooks");
		try {
			Thread.sleep(400);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(obsMyBooks);
		myBooks.setItems(obsMyBooks);
	}


	@SuppressWarnings("unchecked")
	@Override
	protected void handleMessageFromServer(Object msg) {
		
		ArrayList <OrderedBook> userbooks=new ArrayList<OrderedBook>();
		userbooks.addAll(((ArrayList<OrderedBook>)msg));		
		for(int i=0;i<((ArrayList<OrderedBook>)msg).size();i++)
			obsMyBooks.add(userbooks.get(i).getTitle()+" by "+userbooks.get(i).getAuthor()+"id: "+userbooks.get(i).getBookid());


	}




}

