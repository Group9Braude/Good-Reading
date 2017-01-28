package Controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Entities.Book;
import Entities.GeneralMessage;
import Entities.OrderedBook;
import Entities.Reader;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import ocsf.client.AbstractClient;

public class SearchBookScreenController extends AbstractClient
{
	private ArrayList <Book> allBooks = null;
	private OrderedBook returnedBook= null;
	private ArrayList <Book> updatedBookList = null;
	private int origin/*From where the message to the server was sent*/ = -1;//0 - setAllBooks(), 1 - onSearch()
	@FXML
	public ObservableList <Book> items = FXCollections.observableArrayList();
	public TableView <Book> bookList = new TableView <Book>(items);
	public ComboBox<String> action = new ComboBox<String>();
	public TextField title,author,lang,genre,keyWord;

	@SuppressWarnings("unchecked")
	/**
	 * initialize the table and get the list from the DB
	 */
	@FXML
	public void initialize()
	{
		action.getItems().addAll("AND","OR");
		try {
			this.openConnection();

			TableColumn<Book/*The type of data in the table*/,String/*The type of the data in this column*/> titleColumn =new TableColumn<Book,String>("Title");
			titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));//What attribute of class this column takes

			TableColumn<Book,Integer> IDColumn =new TableColumn<Book,Integer>("Book ID");
			IDColumn.setCellValueFactory(new PropertyValueFactory<>("bookid"));

			TableColumn<Book,String> authorColumn =new TableColumn<Book,String>("Author");
			authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

			TableColumn<Book,String> langColumn =new TableColumn<Book,String>("Language");
			langColumn.setCellValueFactory(new PropertyValueFactory<>("language"));

			TableColumn<Book,String> summColumn =new TableColumn<Book,String>("Summary");
			summColumn.setCellValueFactory(new PropertyValueFactory<>("summary"));

			TableColumn<Book,String> keyWordColumn =new TableColumn<Book,String>("Key word");
			keyWordColumn.setCellValueFactory(new PropertyValueFactory<>("keyword"));

			TableColumn<Book,String> genreColumn =new TableColumn<Book,String>("Genre");
			genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));

			bookList.getColumns().addAll(titleColumn,IDColumn,authorColumn,langColumn,summColumn,keyWordColumn,genreColumn);//Adding the columns to the table
		}catch(Exception e){e.printStackTrace();}
		setAllBooks();
	}
		/**
		 * get all the books that are not suspended
		 */
		private void setAllBooks()
		{
			origin = 0;
			GeneralMessage dummy = new GeneralMessage();
			dummy.actionNow="getBooks";
			try {
				this.sendToServer(dummy);
				while(allBooks==null)
					Thread.sleep(10);
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			items = FXCollections.observableArrayList(allBooks);
			bookList.setItems(items);
		}
		/**
		 * go back to main reader login screen
		 */

			public void onBack()
			{
				Main.showReaderLoginScreen();
			}
			/**
			 * open the connection to the server
			 */
			public SearchBookScreenController()     
			{
				super(Main.host, Main.port);
				try {
					this.openConnection();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			/**
			 * construct queries to find books according to the search parameters
			 */
			public void onSearch()
			{
				String userTitle = title.getText(), userAuthor = author.getText(),
						userLang=lang.getText(), genreUser = genre.getText(), keyWordUser = keyWord.getText();
				int i;	
				String finalQuery="";
				String query = "SELECT * FROM books WHERE isSuspend=0 AND (";
				String op = action.getSelectionModel().getSelectedItem();
				if(op==null)
					JOptionPane.showMessageDialog(null, "You must first select the operation");
				else if(userTitle.equals("") && userAuthor.equals("") && userLang.equals("") && genreUser.equals("") && keyWordUser.equals(""))
					JOptionPane.showMessageDialog(null, "You must enter at least one search parameter");
				else{
					if(userTitle.equals("") && userAuthor.equals("") && userLang.equals("") && keyWordUser.equals(""))
						finalQuery = "SELECT * FROM books WHERE isSuspend=0";
					else{
						if(!userTitle.equals(""))
							query +=" title LIKE '%" + userTitle + "%' " + op;

						if(!userAuthor.equals(""))
						{
							String[] temp = userAuthor.split(",");
							for(i=0; i<temp.length;i++)
								query +=" author LIKE '%" + temp[i] + "%' " + op;
						}

						if(!userLang.equals(""))
							query+=" language LIKE '%" + userLang + "%' " + op;


						if(!keyWordUser.equals(""))
						{
							String[] temp = keyWordUser.split(",");
							for(i=0; i<temp.length;i++)
								query+=" keyWord LIKE '%" + temp[i] + "%' " + op;
						}

						for(i=0;i<query.length()-op.length();i++)//Remove the operation from the end of the query
							finalQuery+=query.charAt(i);
						finalQuery+=");";
					}
					String finalGenreQuery = "";
					if(!genreUser.equals(""))
					{
						String genreQuery = "select * from genresbooks where";
						String[] temp = genreUser.split(",");
						for(i=0; i<temp.length;i++)
							genreQuery+=" genre LIKE '%" + temp[i] + "%'" + op;

						for(i=0;i<genreQuery.length()-op.length();i++)//Remove the operation from the end of the query
							finalGenreQuery+=genreQuery.charAt(i);
						finalGenreQuery+=";";
					}
					System.out.println(finalQuery);
					System.out.println(finalGenreQuery);
					origin = 1;
					updatedBookList = null;
					Book dummy = new Book();
					dummy.query = finalQuery;
					dummy.genreQuery = finalGenreQuery;
					dummy.setSearchOperand(op);
					dummy.actionNow = "updateBookList";
					try {
						this.sendToServer(dummy);
						while(updatedBookList==null)
							Thread.sleep(10);
					} catch (IOException | InterruptedException e) 
					{
						e.printStackTrace();
					}
					items = FXCollections.observableArrayList(updatedBookList);
					bookList.setItems(items);
				}
			}
			/**
			 * Reset the book table that is presented to the user
			 */

			public void onReset()
			{
				this.setAllBooks();
			}
			/**
			 * Order the selected book
			 */
			public void onOrder()
			{
				if(bookList.getSelectionModel().getSelectedItem() == null)
					JOptionPane.showMessageDialog(null, "You must first select a book to purchase!");
				else{
					Book selectedBook = bookList.getSelectionModel().getSelectedItem();
					Reader reader = (Reader)Main.getCurrentUser();
					OrderedBook newBook = new OrderedBook(reader.getID(),selectedBook.getBookid(),selectedBook.getTitle(),selectedBook.getAuthor());
					if(reader.getMyBooks().contains(newBook))
						JOptionPane.showMessageDialog(null, "You already own this book!!");
					else{
						if(reader.getCardnum().equals(""))
						{
							LoginReaderController temp = new LoginReaderController();
							temp.popUpCredit();
						}
						newBook.actionNow = "NewOrder";
						try {
							this.sendToServer(newBook);
							while(returnedBook == null)
								Thread.sleep(10);
						} catch (IOException | InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						ArrayList<OrderedBook> temp = reader.getMyBooks();
						temp.add(returnedBook);
						reader.setMyBooks(temp);
						JOptionPane.showMessageDialog(null, "Thank you for your order! Your new book has been added to your book list.");
					}
				}

			}
			/**
			 * Handle the message from the server
			 */

			@SuppressWarnings("unchecked")
			@Override
			protected void handleMessageFromServer(Object msg) 
			{
				if(msg instanceof ArrayList)
				{
					if(origin==0)
						allBooks = (ArrayList<Book>)msg;
					else if(origin==1) updatedBookList = (ArrayList<Book>)msg;
					origin = -1;
				}
				if(msg instanceof OrderedBook)
					returnedBook = (OrderedBook)msg;
			}

		}
