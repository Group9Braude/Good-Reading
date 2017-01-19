package Controllers;
import java.io.IOException;
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
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
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
	public TextField id,bookidd;
	@FXML
	public ListView<String> myBooks;
	@FXML
	public DatePicker from;
	@FXML
	public DatePicker until;

	@FXML
	public BarChart<?,?> mybar;
	ObservableList<String> genres = FXCollections.observableArrayList();	
	@FXML
	public ChoiceBox genre;
	@FXML
	public TextField genrebookid;
	@FXML 
	public Button enter1;
	@FXML
	public Button checkpop;
	@FXML
	public Text please,notfound;
	@FXML
	public TextField result;
	public int found=0;
	public static  ArrayList<OrderedBook> arr;
	public static  ArrayList<Integer> arrint;
	public static  ArrayList<String> arrstring;
	public static String pop;
	public  ObservableList<String> obsMyBooks;



	@FXML
	private void initialize(){
		mybar.setVisible(false);
	}


	@FXML
	public TextField bookid;
	@FXML
	public Text notfound1,generalpop;

	/*check in general popularity*/
	public void onCheckk(){
		sendServer(new Book(Integer.parseInt(bookidd.getText())),"getGeneralPop");//getting book's genres
		while(flag==0){
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
		if(!pop.equals("0/5")){
			notfound1.setVisible(false);
			generalpop.setText(pop);
			generalpop.setVisible(true);
		}
		else {
			generalpop.setVisible(false);
			notfound1.setVisible(true);
		}
		flag=0;

	}
	/*enter in genre popularity*/
	public void onEnter1(){
		notfound.setVisible(false);
		result.clear();
		genres.clear();
		result.setVisible(false);
		sendServer(new Book(Integer.parseInt(genrebookid.getText())),"getBookGenres");//getting book's genres
		while(flag==0){
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
		System.out.println("arrstring:"+arrstring);
		if(!arrstring.get(0).equals("end")){
			please.setVisible(true);
			genre.setVisible(true);
			checkpop.setVisible(true);
			for(int i=0;i<arrstring.size()-1;i++)
				genres.add(arrstring.get(i));
			genre.setItems(genres);
		}
		else{
			notfound.setVisible(true);
		}
		flag=0;
		arrstring.removeAll(arrstring);
	}
	/*check pop in genre pop*/
	public void onCheckpop(){
		sendServer(new Book(Integer.parseInt(genrebookid.getText()),genre.getSelectionModel().getSelectedItem().toString()), "gettingGenrePlace");//getting how many books in the selected genre
		while(flag==0){
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
		result.setText(pop);
		result.setVisible(true);
		genres.removeAll();
		flag=0;
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
				//author=Book.bookList.get(i).getAuthor();
				System.out.println(Book.bookList.get(i).getBookid());
				found=1;
				break;}
		}
		if(found==1)
			mybar.setTitle("Statistics of "+title+" by "+author);
		else{ 
			mybar.setTitle("Book is not found");
		}
		XYChart.Series set=new XYChart.Series();
		set.getData().add(new XYChart.Data("#Purchases",arrint.get(0)));
		set.getData().add(new XYChart.Data("#Searches",arrint.get(1)));
		mybar.getData().addAll(set);
		flag=0;
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
			}
			else if (((ArrayList<?>)msg).get(0) instanceof Integer) {
				arrint = new ArrayList <Integer>((ArrayList <Integer>)msg);
				System.out.println("handle message from server:"+arrint);

			}
			else if (((ArrayList<?>)msg).get(0) instanceof String){
				arrstring = new ArrayList <String>((ArrayList <String>)msg);
				System.out.println("handle message from server:"+arrstring);
			}
		}
		else{
			pop=((Book)msg).getTitle();	/*its not really type of book, it is just to get a string*/
			System.out.println("pop: "+pop);
			System.out.println("handle message from server:"+pop);
		}
		ReportsController.flag=1;

	}

}

