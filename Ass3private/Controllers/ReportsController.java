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
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.util.Callback;
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
	public ListView<String> myBooks,rank;
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
	public Button enter1;
	@FXML
	public Button checkpop;
	@FXML
	public Text please;
	@FXML
	public TextField result;
	public int found=0;
	public static  ArrayList<OrderedBook> arr;
	public static  ArrayList<Integer> arrint;
	public static  ArrayList<String> arrstring;
	public static String pop;
	public  ObservableList<String> obsMyBooks;
	public  ObservableList<String> ranking;

	public  ObservableList<Book> books;
	public  ObservableList<Book> books1;
	public  ObservableList<Book> books2;


	public ObservableList <Reader> obsMyreaders = FXCollections.observableArrayList();
	@FXML
	public TableView <Reader> mytable = new TableView <Reader>();
	public TableView <Book> booktable = new TableView <Book>();
	public TableView <Book> booktable1 = new TableView <Book>();
	public TableView <Book> booktable2 = new TableView <Book>();


	@FXML
	public TableColumn readerid,firstname,lastname,bookcol,authorcol,titlecol,bookcol1,authorcol1,titlecol1,bookcol2,authorcol2,titlecol2;
	@FXML
	private void initialize(){
		mybar.setVisible(false);
		try {
			this.openConnection();
			GeneralMessage dummy = new GeneralMessage();
			dummy.actionNow="getReaders";
			this.sendToServer(dummy);
			readerid.setCellValueFactory(new PropertyValueFactory<Reader,String>("id") );
			firstname.setCellValueFactory(new PropertyValueFactory<Reader,String>("lastName") );
			lastname.setCellValueFactory(new PropertyValueFactory<Reader,String>("firstName") );

			while(flag==0)
				try {
					Thread.sleep(5);
				} catch (InterruptedException e){
					e.printStackTrace();
				}

			obsMyreaders = FXCollections.observableArrayList(arrreader);
			mytable.setItems(obsMyreaders);
			flag=0;

			/*BOOKS TABLEs*/	
			bookcol.setCellValueFactory(new PropertyValueFactory<Book,Integer>("bookid") );
			titlecol.setCellValueFactory(new PropertyValueFactory<Book,String>("title") );
			authorcol.setCellValueFactory(new PropertyValueFactory<Book,String>("author") );

			books = FXCollections.observableArrayList(Book.bookList);
			books1 = FXCollections.observableArrayList(Book.bookList);
			books2 = FXCollections.observableArrayList(Book.bookList);

			bookcol1.setCellValueFactory(new PropertyValueFactory<Book,Integer>("bookid") );
			titlecol1.setCellValueFactory(new PropertyValueFactory<Book,String>("title") );
			authorcol1.setCellValueFactory(new PropertyValueFactory<Book,String>("author") );

			bookcol2.setCellValueFactory(new PropertyValueFactory<Book,Integer>("bookid") );
			titlecol2.setCellValueFactory(new PropertyValueFactory<Book,String>("title") );
			authorcol2.setCellValueFactory(new PropertyValueFactory<Book,String>("author") );
			booktable.setItems(books);
			booktable1.setItems(books1);
			booktable2.setItems(books2);
			/*ranking*/
			Book r=new Book(7);
			sendServer(r,"ranking");
			while(flag==0){
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
			ranking=FXCollections.observableArrayList();	
			ranking.removeAll(ranking);
			System.out.println("reached");
			flag=0;

			for(int i=arrint.size()-1;i>=0;i--)
				for(int j=0;j<Book.bookList.size();j++)
					if(arrint.get(i)==Book.bookList.get(j).getBookid()){
						int x=arrint.size()-i;
						ranking.add("#"+x+"    "+Book.bookList.get(j).getTitle()+" by "+Book.bookList.get(j).getAuthor()+" <"+Integer.toString(Book.bookList.get(j).getBookid())+">");
						break;
					}

						rank.setItems(ranking);

					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}	

		}


		@FXML
		public TextField bookid;
		@FXML
		public Text generalpop;

		/*check in general popularity*/
		public void onReportsBack() throws IOException{
			Main.showManagerLoggedScreen();
		}
		public void onCheckk(){
			sendServer(new Book(booktable2.getSelectionModel().getSelectedItem().getBookid()),"getGeneralPop");//getting book's genres
			while(flag==0){
				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
			generalpop.setText(pop);
			generalpop.setVisible(true);

			flag=0;

		}
		/*enter in genre popularity*/
		public void onEnter1(){
			result.clear();
			genres.clear();
			result.setVisible(false);
			sendServer(new Book(booktable1.getSelectionModel().getSelectedItem().getBookid()),"getBookGenres");//getting book's genres
			while(flag==0){
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
			System.out.println("arrstring:"+arrstring);
			please.setVisible(true);
			genre.setVisible(true);
			checkpop.setVisible(true);
			for(int i=0;i<arrstring.size();i++)
				genres.add(arrstring.get(i));
			genre.setItems(genres);

			flag=0;
			arrstring.removeAll(arrstring);
		}
		/*check pop in genre pop*/
		public void onCheckpop(){
			sendServer(new Book(booktable1.getSelectionModel().getSelectedItem().getBookid(),genre.getSelectionModel().getSelectedItem().toString()), "gettingGenrePlace");//getting how many books in the selected genre
			while(flag==0){
				try {
					Thread.sleep(5);
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
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
			String fromm=from.getValue().format(formatter);
			String untill=until.getValue().format(formatter);
			Search s=new Search(booktable.getSelectionModel().getSelectedItem().getBookid(),fromm,untill);
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
				if(Book.bookList.get(i).getBookid()==booktable.getSelectionModel().getSelectedItem().getBookid()){
					title=Book.bookList.get(i).getTitle();
					author=Book.bookList.get(i).getAuthor();
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

		public ArrayList<Reader> arrreader=new ArrayList<Reader>();



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
				else if (((ArrayList<?>)msg).get(0) instanceof Reader){
					arrreader = new ArrayList <Reader>((ArrayList <Reader>)msg);
					for(int i=0;i<arrreader.size();i++)
						System.out.println(arrreader.get(i).getLastName()+"readerid:"+arrreader.get(i).getID());
				}
			}
			else{
				pop=((Book)msg).getTitle().toString();	/*its not really type of book, it is just to get a string*/
				System.out.println("pop: "+pop);
				System.out.println("handle message from server:"+pop);
			}
			ReportsController.flag=1;

		}

	}

