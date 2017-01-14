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


	public static int flag=0;
	@FXML
	public TextField id;
	@FXML
	public ListView<String> myBooks;
	public static  ArrayList<OrderedBook> arr;

	public  ObservableList<String> obsMyBooks;


	@FXML
	public void onEnter(){
		Reader r=new Reader(id.getText(),null);
		obsMyBooks=FXCollections.observableArrayList();	
		obsMyBooks.removeAll(obsMyBooks);
		sendServer(r,"getUserBooks");
		while(flag==0){};
		flag=0;
		for(int i=0;i<arr.size();i++)
			obsMyBooks.add(arr.get(i).getTitle()+" by "+arr.get(i).getAuthor());
		System.out.println(obsMyBooks);
		myBooks.setItems(obsMyBooks);

	}


	@SuppressWarnings("unchecked")
	@Override
	protected void handleMessageFromServer(Object msg) {
		arr = new ArrayList <OrderedBook>((ArrayList <OrderedBook>)msg);
		ReportsController.flag=1;
	}




}

