package Controllers;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import Entities.Book;
import Entities.GeneralMessage;
import Entities.OrderedBook;
import Entities.Reader;
import Entities.Search;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;
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
	@FXML
	public DatePicker from;
	@FXML
	public DatePicker until;
	@FXML
	public TextField bookid;
	@FXML
	public BarChart<?,?> mybar;
	@FXML
	public CategoryAxis x;
	@FXML
	public NumberAxis y;
	public int found=0;
	public static  ArrayList<OrderedBook> arr;
	public static  ArrayList<Integer> arrint;

	public  ObservableList<String> obsMyBooks;

	
	@FXML
	private void initialize(){
		mybar.setVisible(false);
	}
	@SuppressWarnings("unchecked")
	@FXML
	public void onCheck(){
		mybar.setVisible(true);
		mybar.getData().clear();
		found=0;
		int bookidd=Integer.parseInt(bookid.getText());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		String fromm=from.getValue().format(formatter);
		String untill=until.getValue().format(formatter);
		Search s=new Search(Integer.parseInt(bookid.getText()),fromm,untill);
		sendServer(s,"getStatistics");
		while(flag==0){
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
		String title="",author="";
		for(int i=0;i<Book.bookList.size();i++){
			if(Book.bookList.get(i).getBookid()==bookidd){
				title=Book.bookList.get(i).getTitle();
				author=Book.bookList.get(i).getAuthor();
				System.out.println(Book.bookList.get(i).getBookid());
				found=1;
				break;}
		}
		if(found==1){
			mybar.setTitle("Statistics of "+title+" by "+author);
			mybar.setAnimated(true);
			XYChart.Series set=new XYChart.Series();
			set.getData().add(new XYChart.Data("#Purchases",arrint.get(0)));
			set.getData().add(new XYChart.Data("#Searches",arrint.get(1)));
			mybar.getData().addAll(set);
		}
		else{ 
			mybar.setAnimated(false);
			mybar.setTitle("Book is not found");
			XYChart.Series set=new XYChart.Series();
			set.getData().add(new XYChart.Data("#Purchases",0));
			set.getData().add(new XYChart.Data("#Searches",0));
			mybar.getData().addAll(set);
		}
	}
	@FXML
	public void onEnter(){
		Reader r=new Reader(id.getText(),null);
		obsMyBooks=FXCollections.observableArrayList();	
		obsMyBooks.removeAll(obsMyBooks);
		sendServer(r,"getUserBooks");
		while(flag==0){
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
		flag=0;
		for(int i=0;i<arr.size();i++)
			obsMyBooks.add(arr.get(i).getTitle()+" by "+arr.get(i).getAuthor());
		System.out.println(obsMyBooks);
		myBooks.setItems(obsMyBooks);

	}



	@Override
	protected void handleMessageFromServer(Object msg) {
		if(msg instanceof ArrayList){
			if(((ArrayList<?>)msg).get(0) instanceof OrderedBook){
				arr = new ArrayList <OrderedBook>((ArrayList <OrderedBook>)msg);
				ReportsController.flag=1;
			}
			else {
				arrint = new ArrayList <Integer>((ArrayList <Integer>)msg);
				ReportsController.flag=1;
				System.out.println("handle message from server:"+arrint);

			}
		}


	}

}

