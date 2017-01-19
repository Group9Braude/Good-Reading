package Entities;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

import com.mysql.jdbc.PreparedStatement;

import java.sql.Date;
import application.Main;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

public class MyServer extends AbstractServer {
	Connection conn;
	private static int bookCnt;

	public static void main(String[] args) {
		int port = 0;
		try {
			port = Integer.parseInt(args[0]);
		}
		catch (Throwable t) {
			port = Main.port;
		}
		MyServer s1 = new MyServer(port);
	}

	public MyServer(int port) {
		super(port);
		this.connectToDB();
		try {
			this.listen();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void handleMessageFromClient(Object msg, ConnectionToClient client) {
		try{
			switch(((GeneralMessage)msg).actionNow){
			case "getGeneralPop":
				getGeneralPop((Book)msg,client);break;
			case "gettingGenrePlace":
				gettingGenrePlace((Book)msg,client);break;
			case "getBookGenres":
				getBookGenres((Book)msg,client);break;
			case "getStatistics":
				getStatistics((Search)msg,client);break;
			case "AddBook":
				addBook((Book)msg, client);break;
			case "RemoveBook":
				removeBook((Book) msg, client);break;
			case "CheckUser":
				checkUser((User)msg,client);break;
			case "AddGenre":
				addGenre((Genre)msg,client);break;
			case "DeleteGenre":
				deleteGenre((Genre)msg,client);break;
			case "UpdateGenre":
				updateGenre((Genre)msg,client);break;
			case "InitializeGenreList":
				initializeGenreList((Genre)msg, client);break;
			case "InitializeBookList":
				initializeBookList((Book)msg, client);break;
			case "InitializeWorkerList":
				initializeWorkerList((Worker)msg, client);break;
			case "getUserBooks":
				getUserBooks((Reader)msg, client);break;
			case "TempRemoveAbook":
				tempremoveabook((Book)msg,client);break;
			case "Logout":
				LogoutUser((User)msg,client);break;
			case "creditCard":
				addCreditCard((CreditCard)msg,client); break;
			case "FindLoggedReaders":
				find("readers", "isLoggedIn='1';", "LoggedReaders",client);break;
			case "FindLoggedWorkers":
				find("workers", "isLoggedIn='1';", "LoggedWorkers",client);break;
			case "FindAllManagers":
				find("workers", "isManager='1';", "AllManagers",client);break;
			case "FindAllWorkers":
				find("workers", "isManager='0';", "AllWorkers",client);break;
			case "FindDebtReaders":
				find("readers", "debt is not null;", "DebtReaders",client);break;
			case "FindFrozenReaders":
				find("readers", "isFrozen='1';", "FrozenReaders",client);break;
			case "FindWorkers":
				findWorkers((Worker)msg, client);break;
			case "FindReaders":
				findReaders((Reader)msg, client);break;
			case "Monthly":
				subscribe((Reader)msg,1,client); break;
			case "Yearly":
				subscribe((Reader)msg,2,client); break;
			case "AddReview":
				addReview((Review)msg,client);
			case "activeBooks":
				activeBooks((Book)msg, client);break;
			case "getBooks":
				getBooks(client); break;
			case "DeleteBook":
				deleteBook((Book)msg, client);break;
			case "GetReviews":
				getReviews("title, author, reviewid, review", "reviews" , "isApproved='0'",client);break;
			case "FindDeclinedReviews":
				getReviews("title, author, reviewid, review", "reviews" , "isApproved='-1'",client);break;
			case "FindAcceptedReviews":
				getReviews("title, author, reviewid, review", "reviews" , "isApproved='1'",client);break;
			case "AcceptReview":
				examineReview((Review)msg,1, client);break;
			case "DenyReview":
				examineReview((Review)msg, -1 , client);break;
			case "HoldReview":
				examineReview((Review)msg, 0, client);break;
			case "EditReview":
				editReview((Review)msg, client);
			case "GetAllGenres":
				getAllGenres(client);
			default:
				break;
			}
		}catch(Exception e){System.out.println("Exception at:" + ((GeneralMessage)msg).actionNow);e.printStackTrace();}
	}

	
	public void initializeGenreList(Genre genre, ConnectionToClient client){/*******************************************/
		try {
			Statement stmt = conn.createStatement();
			String query = "SELECT * FROM genre";
			ResultSet rs = stmt.executeQuery(query);
			ArrayList<Genre> genreList = new ArrayList<Genre>();	
			while(rs.next()){
				System.out.println("test");
				genreList.add( new Genre (rs.getString(1),rs.getString(2)));
			}
			client.sendToClient(genreList);
		} catch (Exception  e) {
			e.printStackTrace();
		}	
	}

public void addGenre(Genre genre, ConnectionToClient client){
		Statement stmt;
		String query = "insert into genre values ('"+genre.getGenre()+"', '"+genre.getBookNum()+"', '"+genre.getComments()+"');";
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			client.sendToClient("Added!");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

public void updateGenre(Genre genre,ConnectionToClient client){
	try{
		Statement stmt=conn.createStatement();
		String query = "UPDATE genre SET name='"+genre.getGenre()+"', comments= '"+genre.getComments()+
				"' WHERE name='"+genre.getOldGenre()+"';";
		stmt = conn.createStatement();
		stmt.executeUpdate(query);
		}catch (SQLException e) {
			System.out.println("Error update genre.");
			e.printStackTrace();
		}
		try {
			client.sendToClient("Updated!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



public void deleteGenre(Genre genre,ConnectionToClient client){
		try{
			Statement stmt=conn.createStatement();
			String query = "DELETE FROM genre WHERE name= '"+genre.getGenre()+"';";
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
			}catch (SQLException e) {
				System.out.println("Error deleting genre.");
				e.printStackTrace();
			}
			try {
				client.sendToClient("Deleted!");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	


	private void getGeneralPop(Book b, ConnectionToClient client) {
		ArrayList <Book_NumOfPurchases> arr=new ArrayList<Book_NumOfPurchases>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("Select *  FROM books;");
			while(rs.next())
				arr.add(new Book_NumOfPurchases(rs.getInt(2),rs.getInt(10)));
			Collections.sort(arr);
			for(int i=0;i<arr.size();i++)
				System.out.println("place: "+(arr.size()-i)+" bookid:"+arr.get(i).bookid+" num of purchases:"+arr.get(i).numofpurchases);
			int i=0;
			for(i=0;i<arr.size();i++)
				if(arr.get(i).bookid==b.getBookid())
					break;
			System.out.println("place:"+(arr.size()-i));
			System.out.println("total:"+arr.size());
			client.sendToClient(new Book(0,Integer.toString((arr.size()-i))+"/"+Integer.toString(arr.size())));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	/*class that helps for organizing and sorting to get ranking of a book*/
	public class Book_NumOfPurchases implements Comparable<Book_NumOfPurchases> {
		public int bookid;
		public int numofpurchases;
		public Book_NumOfPurchases(int bookid,int numofpurchases){
			this.bookid=bookid;
			this.numofpurchases=numofpurchases;
		}
		public void setnumofpurchases(int x){
			this.numofpurchases=x;
		}
		public int compareTo(Book_NumOfPurchases info) {
			if (this.numofpurchases < info.numofpurchases) {
				return -1;
			} else if (this.numofpurchases > info.numofpurchases) {
				return 1;
			} else {
				return 0;
			}
		}

	}
	private void gettingGenrePlace(Book b, ConnectionToClient client) {
		ArrayList <Book_NumOfPurchases> arr=new ArrayList<Book_NumOfPurchases>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("Select *  FROM genresbooks WHERE genre='"+ b.getTitle() + "';");
			while(rs.next())
				arr.add(new Book_NumOfPurchases(rs.getInt(2),6));//Updating books id's
			for(int i=0;i<arr.size();i++){//updating their number of purchases
				Statement stmt1 = conn.createStatement();
				ResultSet rs1 = stmt1.executeQuery("Select numofpurchases  FROM books WHERE bookid="+ arr.get(i).bookid + ";");
				while(rs1.next())
					arr.get(i).setnumofpurchases(rs1.getInt(1));
			}
			Collections.sort(arr);
			for(int i=0;i<arr.size();i++)
				System.out.println("place: "+(arr.size()-i)+" bookid:"+arr.get(i).bookid+" num of purchases:"+arr.get(i).numofpurchases);
			int i=0;
			for(i=0;i<arr.size();i++)
				if(arr.get(i).bookid==b.getBookid())
					break;
			System.out.println("place:"+(arr.size()-i));
			System.out.println("total:"+arr.size());
			System.out.println(Integer.toString((arr.size()-i))+"/"+Integer.toString(arr.size()));
			client.sendToClient(new Book(0,Integer.toString(i+1)+"/"+Integer.toString(arr.size())));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getBookGenres(Book b,ConnectionToClient client){
		ArrayList<String> arr=new ArrayList<String>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("Select *  FROM genresbooks WHERE bookid="+ b.getBookid() + ";");
			while(rs.next())
				arr.add(rs.getString(1));//Adding genres to array
			Statement stmt1 = conn.createStatement();
			arr.add("end");
			client.sendToClient(arr);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void editReview(Review review, ConnectionToClient client){
		try {
			Statement stmt = conn.createStatement();
			String query = "UPDATE reviews SET review = '" + review.getReview() +  "' WHERE reviewid = '" + review.getReviewID() + "';";
			System.out.println("Query: " + query);
			stmt.executeUpdate(query);
			ArrayList<String> arr = new ArrayList<String>();
			arr.add("EditReview");
			getReviews("title, author, reviewid, review", "reviews" , "isApproved='0'",client);
		} catch (Exception e) {e.printStackTrace();}
	}

	private void getStatistics(Search s,ConnectionToClient client){
		System.out.println(s.getFrom()+"  "+s.getUntil());

		ArrayList<Integer> arr=new ArrayList<Integer>();
		arr.add(0);
		arr.add(0);
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("Select *  FROM orderedbook WHERE bookid="+ s.getBookid() +
					" AND purchasedate BETWEEN '" + s.getFrom() +"' AND '" + s.getUntil() + "';");
			while(rs.next())
				arr.set(0, arr.get(0)+1);
			Statement stmt1 = conn.createStatement();
			ResultSet rs1 = stmt1.executeQuery("Select *  FROM searchbook WHERE bookid=" + s.getBookid() +" AND searchdate BETWEEN '" + s.getFrom() +"' AND '" + s.getUntil() + "';");
			while(rs1.next())
				arr.set(1, arr.get(1)+1);
			System.out.println("getstatistifcs in server"+arr);
			client.sendToClient(arr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getBooks(ConnectionToClient client)
	{
		try {
			ArrayList <Book> books = new ArrayList<Book>();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("Select * from books where isSuspend=0");
			while(rs.next())
				books.add( new Book (rs.getString(1),rs.getInt(2),rs.getString(3)
						,rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7), rs.getString(8), rs.getInt(9), rs.getInt(10), rs.getString(11)));
			client.sendToClient(books);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}



	public void examineReview(Review review,int isApproved, ConnectionToClient client){//-1 >NOT APPROVED, 0 - NOT CHECKED YET, 1 - APPROVED
		Statement stmt;
		try{
			stmt = conn.createStatement();
			stmt.executeUpdate("UPDATE reviews SET isApproved = '" + isApproved + "' WHERE reviewid = '" + review.getReviewID() + "';");
		}catch(Exception e){e.printStackTrace();}
	}


	public void getReviews(String select, String from, String where, ConnectionToClient client){
		ArrayList<String> reviewList = new ArrayList<String>(); 
		reviewList.add("SearchReviews");
		try{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT " + select + " FROM " + from + " WHERE " + where + ";");
			while(rs.next()){
				reviewList.add(rs.getString(1) + " by " + rs.getString(2) + " _ID:" + rs.getInt(3) + "\nReview:\n" + rs.getString(4));
			}
			client.sendToClient(reviewList);
		}catch(Exception e){e.printStackTrace();}
	}


	public void deleteBook(Book book, ConnectionToClient client){
		ArrayList<String> bookList = new ArrayList<String>();
		bookList.add("BookSearch");
		try{
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("DELETE FROM books WHERE bookid = '" + book.getBookid() + "';");
		}catch(Exception e){e.printStackTrace();}

	}


	/*	public void bookSearch(Book book, ConnectionToClient client){/***********NEEDED???*/
	/*	ArrayList<String> bookList = new ArrayList<String>();
		bookList.add("BookSearch");
		try{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM books;");
			while(rs.next())
				bookList.add(rs.getString(1) + " " + rs.getString(3));
			client.sendToClient(bookList);

		}catch(Exception e){e.printStackTrace();}

	}*/

	private void addReview(Review review, ConnectionToClient client)
	{
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("insert into reviews values('" + review.getReviewBook().getBookid()+ "','" + review.getReviewBook().getReaderID() + "','" + review.getReviewBook().getTitle() + "','" + review.getReviewBook().getAuthor() + "','" + review.getKeyword() + "',0,'" + review.getReview() + "');" );
			client.sendToClient("Thank you for submitting a review! If your review will be approved by one of our workers, it will be published.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void getUserBooks(Reader msg, ConnectionToClient client) {
		try {
			Statement stmt = conn.createStatement();
			String query = "SELECT * FROM orderedbook WHERE readerID= '"+((Reader)msg).getID()+"';";
			ResultSet rs = stmt.executeQuery(query);
			ArrayList<OrderedBook> userbooks = new ArrayList<OrderedBook>();
			while(rs.next()){
				userbooks.add( new OrderedBook (rs.getString(1),rs.getInt(2),rs.getString(3)
						,rs.getString(4)));

			}
			client.sendToClient(userbooks);
		} catch (Exception  e) {
			e.printStackTrace();
		}                        
	}

	private void activeBooks(Book b, ConnectionToClient client){
		Statement stmt;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate("Update books set isSuspend= 0 where bookid = '"+b.getBookid()+"';");
			client.sendToClient("Book has been suspended successfuly");//The subscription succeeded
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}
	private void tempremoveabook(Book b, ConnectionToClient client){
		Statement stmt;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate("Update books set isSuspend= 1 where bookid = '"+b.getBookid()+"';");
			client.sendToClient("Book has been suspended successfuly");//The subscription succeeded
		} catch (SQLException | IOException e) {
			e.printStackTrace();

		}      
	}

	public void findReaders(Reader reader, ConnectionToClient client){
		ArrayList<String> readersList = new ArrayList<String>();
		readersList.add("Readers");
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(reader.query);

			while(rs.next())
				readersList.add(rs.getString(1) + "  " + rs.getString(3) + "   " + rs.getString(4));
			client.sendToClient(readersList);
		} catch (Exception e) {e.printStackTrace();}
	}


	public void findWorkers(Worker worker, ConnectionToClient client){
		ArrayList<String> workersList = new ArrayList<String>();
		workersList.add("Workers");
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(worker.query);
			while(rs.next())
				workersList.add(rs.getString(3) + " " + rs.getString(4));
			client.sendToClient(workersList);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void find(String from, String where,String isWhat, ConnectionToClient client){
		ArrayList<String> arr = new ArrayList<String>();
		System.out.println(isWhat);
		arr.add(isWhat);
		Statement stmt;
		try{
			stmt = conn.createStatement();
			String query = "SELECT * FROM " + from + " WHERE " + where;
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				arr.add(rs.getString(3) + " " + rs.getString(4));
			}
			client.sendToClient(arr);
		}catch(Exception e){e.printStackTrace();}
	}

	private void subscribe(Reader reader,int type, ConnectionToClient client)//type is the type of subscription
	{
		Statement stmt;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate("Update readers set subscription= "+type+" where readerID = '"+reader.getID()+"';");
			client.sendToClient(type);//The subscription succeeded
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}



	public void removeBook(Book book, ConnectionToClient client){/**********************************/
		ArrayList<String> list = new ArrayList<String>();
		list.add("RemoveBook");
		try{
			Statement stmt = conn.createStatement();
			System.out.println(book.query);
			ResultSet rs = stmt.executeQuery(book.query);
			while(rs.next())
				list.add(rs.getString(1) + "                                        " + rs.getString(3) + "                            " + Integer.toString(rs.getInt(2)));
			client.sendToClient(list);
		}catch(SQLException | IOException e){e.printStackTrace();}
	}



	@SuppressWarnings( "static-access" )
	public void initializeWorkerList(Worker worker, ConnectionToClient client){
		try {
			Statement stmt = conn.createStatement();
			String query = "SELECT * FROM workers";
			ResultSet rs = stmt.executeQuery(query);
			ArrayList<Worker> workerList = new ArrayList<Worker>();
			while(rs.next()){
				workerList.add( new Worker (rs.getString(1),rs.getString(3)
						,rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8), rs.getInt(9),rs.getInt(10)));
			}
			client.sendToClient(workerList);
		} catch (Exception  e) {
			e.printStackTrace();
		}	

	}


	public void initializeBookList(Book book, ConnectionToClient client){/*******************************************/
		try {
			Statement stmt = conn.createStatement();
			String query = "SELECT * FROM books";
			ResultSet rs = stmt.executeQuery(query);
			ArrayList<Book> bookList = new ArrayList<Book>();
			while(rs.next()){
				bookList.add( new Book (rs.getString(1),rs.getInt(2),rs.getString(3)
						,rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8), rs.getInt(9)));

			}
			client.sendToClient(bookList);
		} catch (Exception  e) {
			e.printStackTrace();
		}	
	}




	private void addCreditCard(CreditCard card,ConnectionToClient client)
	{
		Statement stmt;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate("Update readers set creditcardnum = '" + card.getCardNum() + "',expdate = '" + card.getExpDate() + "',securitycode='" + card.getSecCode() +"';");
			//To add credit card checks
			client.sendToClient("Credit card added successfully");
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}


	}




	private void LogoutUser(User user,ConnectionToClient client)
	{
		try {
			Statement stmt = conn.createStatement();
			if(user instanceof Reader)
				stmt.executeUpdate("UPDATE readers SET isLoggedIn=0 WHERE readerID='" + user.getID() + "';");
			if(user instanceof Worker){
				System.out.println("worker logout");
				Worker worker = (Worker)user;
				stmt.executeUpdate("UPDATE workers SET isLoggedIn=0 WHERE workerID='" + worker.getWorkerID()+"';");
			}
			client.sendToClient("You've logged out successfully");

		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}






	public void addBook(Book book, ConnectionToClient client){
		Statement stmt;
		Book.bookCnt++;
		String query = "insert into books values ('" + book.getTitle() + "','" + Book.bookCnt + "','" + book.getAuthor() + "','" + 
				book.getGenre() + "','" + book.getLanguage() + "','" + book.getSummary() + "','" + book.getToc() + "','" + book.getKeyword() + "','0', '0', 'lol');";
		System.out.println(query);
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException e) {e.printStackTrace();}
		try {
			client.sendToClient("Added!");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void connectToDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		}
		catch (Exception var1_1) {
		}
		try {
			this.conn = DriverManager.getConnection("jdbc:mysql://sql11.freesqldatabase.com/sql11153849", "sql11153849", "TlZbvGxXKu");
			System.out.println("MySQL Login Successful!");
		}
		catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}









	private void checkUser(User user,ConnectionToClient client)
	{
		String id = user.getID();
		String password = user.getPassword();
		Statement stmt,stmt1;
		Reader reader;
		Worker worker;
		try {
			stmt = conn.createStatement();
			stmt1 = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM workers WHERE workerID='" + id + "';");
			ResultSet rs1 = stmt1.executeQuery("SELECT * FROM readers WHERE readerID='" + id + "';");
			if (rs.next())//The ID was found in the workers table
				try {
					User.currentWorker = new Worker();
					if(rs.getString(2).equals(password))
					{
						if(rs.getInt(9)==1){//It is a manager!
							User.currentWorker.setType(3);
							worker = new Worker();
							user.setType(3);
							worker = new Worker();
							worker.setWorkerID(rs.getString(1));
							stmt1.executeUpdate("UPDATE workers SET isLoggedIn=1 WHERE workerID='" + worker.getWorkerID() + "'");

						}
						else{
							user.setType(2);//It is a worker!
							User.currentWorker.setType(2);
							worker = new Worker();
							worker.setWorkerID(rs.getString(1));
							stmt1.executeUpdate("UPDATE workers SET isLoggedIn=1 WHERE workerID='" + worker.getWorkerID() + "'");
							client.sendToClient(worker);
						}
						client.sendToClient(user);
					}
					else
						client.sendToClient("Wrong password!");
				}
			catch (IOException e) {
				e.printStackTrace();
			}
			else if(rs1.next())//The ID was found in the readers table
				try {
					if(rs1.getString(2).equals(password))
					{
						if(rs1.getInt(11)==1)
							client.sendToClient("You're already signed in!");
						else
						{
							reader = new Reader(rs1.getString(1),password);
							reader.setFirstName(rs1.getString(3));
							System.out.println(reader.getFirstName());
							reader.setLastName(rs1.getString(4));
							reader.setSubscribed(rs1.getInt(5));
							reader.setIBookValid(rs1.getInt(6));
							reader.setAllowed(rs1.getInt(7));
							reader.setDebt(rs1.getInt(8));
							reader.setIsFrozen(rs1.getInt(9));
							reader.setPremission(rs1.getInt(10));
							reader.setIsLogged(1);
							reader.setCardnum(rs1.getString(12));
							reader.setExpDate(rs1.getString(13));
							reader.setSecCode(rs1.getString(14));
							//Getting the list of books the current user has ordered
							Statement stmt2 = conn.createStatement();
							ResultSet rs2 = stmt2.executeQuery("select * from orderedbook where readerID='"+reader.getID()+"';");
							ArrayList<OrderedBook> books = new ArrayList<OrderedBook>();
							while(rs2.next())
								books.add(new OrderedBook(rs2.getString(1),rs2.getInt(2),rs2.getString(3),rs2.getString(4)));
							reader.setMyBooks(books);
							//Getting the list of books the current user has ordered
							stmt1.executeUpdate("UPDATE readers SET isLoggedIn=1 WHERE readerID='" + reader.getID() + "'");
							System.out.println(reader.getFirstName());
							client.sendToClient(reader);
						}
					}
					else
						client.sendToClient("Wrong password!");
				}
			catch (IOException e) {
				e.printStackTrace();
			}
			else
				try {
					client.sendToClient("User does not exist in the DB");
				} catch (IOException e) {
					e.printStackTrace();
				} 

			return;
		}
		catch (SQLException e1) {
			e1.printStackTrace();
		}
	}



}







