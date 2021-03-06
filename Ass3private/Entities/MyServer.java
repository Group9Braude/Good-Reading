package Entities;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import application.Main;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;





public class MyServer extends AbstractServer {    
	Connection conn;
	public static void main(String[] args) {
		int port =5555;
		try {
			port = Integer.parseInt(args[0]);
		}
		catch (Throwable t) {
			port = 5555;
		}
		@SuppressWarnings("unused")
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
	/**
	 * This method taking care of the clients accesses to the server
	 * <p>
	 * 1.Data is been brought	from DB according to the clients data
	 * 2.After getting the right data, the server sends back to the client adequate data
	 * <p>
	 * This method calls the right method, according to the message which is represented as a String(Switch-case)
	 */
	public void handleMessageFromClient(Object msg, ConnectionToClient client) {
		try{
			switch(((GeneralMessage)msg).actionNow){

			case "ranking":
				ranking(msg,client);break;
			case "getReaders":
				getReaders(client);break;
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
			case "AddTheme":
				addTheme((Theme)msg,client);break;
			case "DeleteTheme":
				deleteTheme((Theme)msg,client);break;
			case "UpdateTheme":
				updateTheme((Theme)msg,client);break;	
			case "InitializeThemeList":
				initializeThemeList((Theme)msg, client);break;
			case "AddGenre":
				addGenre((Genre)msg,client);break;
			case "DeleteGenre":
				deleteGenre((Genre)msg,client);break;
			case "UpdateGenre":
				updateGenre((Genre)msg,client);break;
			case "InitializeGenreList":
				initializeGenreList(client);break;
			case "InitializeBookList":
				initializeBookList(client);break;
			case "InitializeWorkerList":
				initializeWorkerList(client);break;
			case "getUserBooks":
				getUserBooks((Reader)msg, client);break;
			case "TempRemoveAbook":
				tempremoveabook((Book)msg,client);break;
			case "Logout":
				LogoutUser((User)msg,client);break;
			case "CreditCard":
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
				addReview((Review)msg,client); break;
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
			case "NewOrder":
				addNewOrder((OrderedBook)msg,client); break;
			case "GetReviewsForReader":
				getReviewsForReader(client); break;
			case "updateBookList":
				updateBookList((Book)msg,client);break;
			case "HoldReview":
				examineReview((Review)msg, 0, client);break;
			case "EditReview":
				editReview((Review)msg, client); break;
			case "UpdateBookList":
				UpdateBookList(client); break;
			case "InitializeGenresBooksList":
				InitializeGenresBooksList(client); break;
			case "UpdateBookListSearch":
				UpdateBookListSearch((Book)msg, client); break;
			case "updateReviewList":
				updateReviewList((Review)msg,client); break;
			case "GetBookForEdition":
				getBookForEdition((Book)msg, client);break;
			case "EditBookPlz":
				editBook((Book)msg, client);break;
			case "CreateAndSendFile":
				createAndSendFile((FileDetails)msg,client); break;
			case "RemoveReader":
				removeReader((Reader)msg, client);break;
			case "AddNewReader":
				addNewReader((Reader)msg, client);break;
			case "UpdateReader":
				UpdateReader((Reader)msg, client);break;
			case "CheckNewReviews":break;
			case "CheckReviews":
				CheckReviews(client);break;
			default:
				break;
			}
		}catch(Exception e){System.out.println("Exception at:" + ((GeneralMessage)msg).actionNow);e.printStackTrace();}
	}

	/**
	 *  This method checks if there are any reviews that are awaiting a check.
	 * @param client To send back to the client
	 * @author orel zilberman
	 */

	public void CheckReviews(ConnectionToClient client){
		try{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM reviews WHERE isApproved = 0");
			if(rs.next()){
				ArrayList<String> s = new ArrayList<String>();
				s.add("ReviewsToCheck");
				client.sendToClient(s);
			}
			else{
				ArrayList<String> s = new ArrayList<String>();
				s.add("NoReviewsToCheck");
				client.sendToClient(s);
			}
		}catch(Exception e){e.printStackTrace();}
	}
	/**
	 * This method updates a certain reader with certain information.
	 * 
	 * @param reader is an object that holds the crucial information for the update..
	 * @param client To send back to the client
	 * @author orel zilberman
	 */

	public void UpdateReader(Reader reader, ConnectionToClient client){
		try{
			Statement stmt = conn.createStatement();
			String query = "UPDATE readers SET firstName = '" + reader.getFirstName() + "', lastNAme = '" + reader.getLastName() + 
					"', Subscription = " + reader.getSubscribed() + " WHERE readerID = '" + reader.getID() + "';";
			System.out.println(query);
			stmt.executeUpdate(query);
			ArrayList<String> s = new ArrayList<String>();
			s.add("UpdateReader");
			client.sendToClient(s);return;
		}catch(Exception e){e.printStackTrace();}
	}

	/**
	 * This method adds a new reader to the database.
	 * @param reader Holds the information to add the new reader
	 * @param client
	 * @author orel zilberman
	 */

	public void addNewReader(Reader reader, ConnectionToClient client){
		try{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT readerID FROM readers WHERE readerID = '"+reader.getID() + "';");
			if(rs.next()){
				ArrayList<String> s = new ArrayList<String>();
				s.add("UserAlreadyInDB");
				client.sendToClient(s);return;
			}

			String query = "INSERT INTO readers VALUES ('" + reader.getID() + "', '" + reader.getPassword() + "','" + reader.getFirstName() 
			+ "','" + reader.getLastName() + "'," + reader.getSubscribed() + ", 0, 0, NULL, 0, 0, 0, NULL, NULL, NULL);";
			System.out.println("addNewReader query : " + query);
			stmt.executeUpdate(query);
			ArrayList<String> s = new ArrayList<String>();
			s.add("ReaderAdded");
			System.out.println("done.addReader");
			client.sendToClient(s);
		}catch(Exception e){ e.printStackTrace();}
	}

	/**
	 * This method removes a certain reader from the database.
	 * @param reader Holds the crucial information to functionalize this method.
	 * @param client
	 * @author orel zilberman
	 */

	public void removeReader(Reader reader, ConnectionToClient client){
		String query = "DELETE FROM readers WHERE readerID = '" + reader.getID() + "';";
		System.out.println("removeReader query : " + query);
		try{
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(query);
			ArrayList<String> s = new ArrayList<String>();
			s.add("ReaderRemoved");
			client.sendToClient(s);
		}catch(Exception e){e.printStackTrace();}
	}
	
	/**
	 * This method initializes the themes list in order to economize the access to the server.
	 * @param client
	 * @author sagi vaknin
	 */

	public void initializeThemeList(Theme theme, ConnectionToClient client){/*******************************************/
		try {
			Statement stmt = conn.createStatement();
			String query = "SELECT * FROM theme";
			ResultSet rs = stmt.executeQuery(query);
			ArrayList<Theme> themeList = new ArrayList<Theme>();	
			while(rs.next()){
				System.out.println("theme");
				themeList.add( new Theme (rs.getString(1),rs.getString(2)));
			}
			System.out.println(themeList.size());

			client.sendToClient(themeList);
		} catch (Exception  e) {
			e.printStackTrace();
		}	
	}

	/**
	 * This method adds theme to the database, with specific genre that he belongs to.
	 * @param theme holds information about the theme we want to delete.
	 * @param client
	 * @author sagi vaknin
	 */

	public void addTheme(Theme theme, ConnectionToClient client){
		Statement stmt;
		String query = "insert into theme values ('"+theme.getTheme()+"', '"+theme.getGenre()+"');";
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

	/**
	 * This method deletes a specific theme, according to the user's input
	 * @param theme holds information about the theme we want to delete
	 * @param client
	 * @author sagi vaknin
	 */

	public void deleteTheme(Theme theme,ConnectionToClient client){
		try{
			Statement stmt=conn.createStatement();
			String query = "DELETE FROM theme WHERE name= '"+theme.getTheme()+"';";
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
		}catch (SQLException e) {
			System.out.println("Error deleting theme.");
			e.printStackTrace();
		}
		try {
			client.sendToClient("Deleted!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	

	/**
	 * This method updates a specific theme with specific information, according to the user's input.
	 * @param theme hold the essntial information of the specific theme we want to update.
	 * @param client
	 * @author sagi vaknin
	 */

	public void updateTheme(Theme theme,ConnectionToClient client){
		try{
			Statement stmt=conn.createStatement();
			String query = "UPDATE theme SET name='"+theme.getTheme()+"', genre= '"+theme.getGenre().getGenre()+
					"' WHERE name='"+theme.getOldTheme()+"';";
			System.out.println(query);
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

	
	
	/**
	 * 
	 * @param fileDetails holds the format the client selected and the bookid of the selected book
	 * @param client holds the connection to the client that sent the request
	 * @author Eran Simhon
	 */

	private void createAndSendFile(FileDetails fileDetails, ConnectionToClient client)
	{
		Statement stmt;
		String content="";
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select title,author from books where bookid="+fileDetails.getBookid()+";");//To mimic the behavior of real servers, where the content of the book is kept in the DB
			rs.next();
			content = rs.getString(1)+" by "+rs.getString(2);
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		switch(fileDetails.getType())
		{
		case "PDF":
			try
			{
				Document document = new Document();
				PdfWriter writer = PdfWriter.getInstance(document, out);
				document.open();
				document.add(new Paragraph(content));
				document.close();
				writer.close();
			} catch (DocumentException e)
			{
				e.printStackTrace();
			}
			break;
		case "DOC":
			XWPFDocument document = new XWPFDocument();
			XWPFParagraph paragraph = document.createParagraph();
			XWPFRun run = paragraph.createRun();
			run.setText(content);
			try {
				document.write(out);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		case "FB2":
			try{
				DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder icBuilder= icFactory.newDocumentBuilder();
				org.w3c.dom.Document doc = icBuilder.newDocument();
				Element rootElement = doc.createElement("main");
				doc.appendChild(rootElement);
				Element story = doc.createElement("content");
				story.appendChild(doc.createTextNode(content));
				rootElement.appendChild(story);     
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource((Node) doc);

				StreamResult result = new StreamResult(out);
				transformer.transform(source, result);	
			}catch(Exception e){}
		default: break;
		}
		byte[] fileBytes = out.toByteArray();
		try {
			client.sendToClient(fileBytes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	/**
	 * This function updates the reviews list that is presented to the client according to the parameters that were entered
	 * @param review holding the query which represents the search parameters entered by the client
	 * @param client holds the connection to the client that sent the request
	 * @author Eran Simhon
	 */


	private void updateReviewList(Review review, ConnectionToClient client)
	{
		try{
			Statement stmt = conn.createStatement();
			System.out.println(review.query);
			ResultSet rs = stmt.executeQuery(review.query);
			ArrayList<Review> res = new ArrayList<Review>();
			Statement stmt1 = conn.createStatement();
			ResultSet rs1;
			while(rs.next())
			{
				rs1 = stmt1.executeQuery("select readerID from orderedbook where bookid=" + rs.getInt(1));
				rs1.next();
				OrderedBook reviewBook = new OrderedBook(rs1.getString(1),rs.getInt(1),rs.getString(2),rs.getString(3));
				res.add(new Review(reviewBook,rs.getString(4),rs.getString(6),rs.getString(8),rs.getInt(5),rs.getInt(7)));
			}
			client.sendToClient(res);
		}
		catch(Exception e){e.printStackTrace();}
	}
	/**
	 * Get all the approved reviews and return them to the reader
	 * @param client connection to the client that sent the request
	 */

	private void getReviewsForReader(ConnectionToClient client)
	{
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from reviews where isApproved=1;");
			ArrayList<Review> reviewList = new ArrayList<Review>();
			ResultSet rs1;
			Statement stmt1 = conn.createStatement();
			while(rs.next())
			{
				rs1 = stmt1.executeQuery("select readerID from orderedbook where bookid=" + rs.getInt(1)+";");
				if(rs1.next())
				{
					OrderedBook reviewBook = new OrderedBook(rs1.getString(1),rs.getInt(1),rs.getString(2),rs.getString(3));
					reviewList.add(new Review(reviewBook,rs.getString(4),rs.getString(6),rs.getString(8),rs.getInt(5),rs.getInt(7)));
				}
				rs1.close();
			}
			client.sendToClient(reviewList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * This method reinitializes the readers list to economize the access to the server.
	 * @param client
	 * @author orel zilberman
	 */
	private void getReaders(ConnectionToClient client) {
		try{
			ArrayList<Reader> arr=new ArrayList<Reader>();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM readers;");
			while(rs.next())
				arr.add(new Reader(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)));
			client.sendToClient(arr);
		}catch(Exception e){}
	}

	/**
	 * This method edits a specific book with specific information.
	 * @param book Holds the information of the book we want to edit and it's ID in order to do so.
	 * @param client
	 * @author orel zilberman
	 */
	public void editBook(Book book, ConnectionToClient client){

		try{
			Statement stmt = conn.createStatement();
			String query = "UPDATE books SET title = '" + book.getTitle() + "'," + "language = '" + book.getLanguage() + "'," + "summary = '" + book.getSummary()
			+ "'," + "author = '" + book.getAuthor()+ "'," + "keyWord = '" + book.getKeyword() + "'," + "tableOfContents = '" + book.getToc() +"' "
			+ "WHERE bookid = " + book.getBookid() + ";";
			stmt.executeUpdate(query);
			String genreToAdd="";
			String queryForRemoval = "DELETE FROM genresbooks WHERE genre != '" ;
			System.out.println("Genres : " + book.getGenre());
			for(int i=0;i<book.getGenre().length();i++)
				if((book.getGenre().charAt(i) == ' '||i==book.getGenre().length()-1) && !genreToAdd.equals("") && !genreToAdd.equals(" ")){
					if(i==book.getGenre().length()-1)
						genreToAdd+=book.getGenre().charAt(i);
					query = "INSERT INTO genresbooks(genre, bookid) SELECT * FROM(SELECT '" + genreToAdd + "', " +book.getBookid() + ") AS TMP"
							+ " WHERE NOT EXISTS ( SELECT * FROM genresbooks WHERE genre = '" + genreToAdd + "' AND bookid = " + book.getBookid() + ") LIMIT 1;";
					System.out.println("\neditBook MyServer:" + query );
					stmt.executeUpdate(query);
					queryForRemoval += genreToAdd + "' AND genre != '" ;
					genreToAdd="";
				}
				else if(! (book.getGenre().charAt(i) == ' ')){
					genreToAdd+=book.getGenre().charAt(i);
					System.out.print(book.getGenre().charAt(i));
				}
			query = queryForRemoval.substring(0, queryForRemoval.length()-15);
			query += " AND bookid = " + book.getBookid() + ";";
			System.out.println("editBook MyServer genres to remove from genresbooks:\n" + query );
			stmt.executeUpdate(query);

			//end for!
			/*String genreToRemove = "";
			for(int i=0;i<book.removeGenres.length();i++)
				if(book.removeGenres.charAt(i) == ' '){
					query = "DELETE FROM genresbooks WHERE (genre = '" + genreToRemove + "' AND bookid = " + book.getBookid() + ");";
					System.out.println("editBook MyServer:" + query );
					stmt.executeQuery(query);
					genreToRemove = "";
				}else
					genreToRemove+=book.removeGenres.charAt(i);*/


		}catch(Exception e){e.printStackTrace();}
	}

	/**
	 * This method returns the client the book in order to edit it accordingly.
	 * @param book holds the ID of the book we want to edit
	 * @param client
	 * @author orel zilberman
	 */
	public void getBookForEdition(Book book, ConnectionToClient client){
		System.out.println("getBookForEdition");
		try{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM books where bookid = " + book.getBookid() + ";");
			rs.next();
			Book book1 =  new Book (rs.getString(1),rs.getInt(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7), rs.getInt(8), rs.getInt(9));


			rs = stmt.executeQuery("SELECT genre FROM genresbooks where bookid = " + book.getBookid() + ";");
			book1.setGenre("");
			while(rs.next()){
				String genre = book1.getGenre();
				genre+=rs.getString(1) + " ";
				book1.setGenre(genre);
			}
			System.out.println("GetBookForEdition: " + book1.getGenre());
			client.sendToClient(book1);
			System.out.println("GetBookForEdition: " + book1.getGenre());


		}catch(Exception e){e.printStackTrace();}

	}
	/**
	 * This method updates  a booklist according to the user's search fields.
	 * @param book holds the query to search the books.
	 * @param client
	 * @author orel zilberman
	 */
	public void UpdateBookListSearch(Book book, ConnectionToClient client){
		ArrayList<Book> bookList = new ArrayList<Book>();
		Book b = new Book();
		try{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(book.query);
			System.out.println("query:" + book.query);
			b.query = "UpdateBookList";
			bookList.add(b);
			while(rs.next())
				bookList.add( new Book (rs.getString(1),rs.getInt(2),rs.getString(3)
						,rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7), rs.getInt(8), rs.getInt(9)));
			Book b1 = new Book();
			b1.isGenres=true;
			bookList.add(b1);
			ResultSet rs1 = stmt.executeQuery("SELECT * FROM genresbooks;");
			while(rs1.next())
				bookList.add(new Book(rs1.getString(1), rs1.getInt(2)));
			client.sendToClient(bookList);
		}catch(Exception e){}
	}



	public void UpdateBookList(ConnectionToClient client)
	{
		ArrayList<Book> bookList = new ArrayList<Book>();
		Book book = new Book();
		System.out.println("UpdateBookList");
		book.query = "UpdateBookList";
		bookList.add(book);
		try{
			Statement stmt = conn.createStatement();
			String query = "SELECT * FROM books";
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next())
				bookList.add( new Book (rs.getString(1),rs.getInt(2),rs.getString(3)
						,rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7), rs.getInt(8), rs.getInt(9)));
			Book b2 = new Book();b2.isGenres = true;
			bookList.add(b2);
			for(int i=0;i<bookList.size();i++){
				Book b  = bookList.get(i);
				query = "SELECT * FROM genresbooks WHERE bookid = " + b.getBookid() + ";";
				ResultSet rs1 = stmt.executeQuery(query);
				bookList.get(i).setGenre("");
				while(rs1.next())
					bookList.get(i).GenreAdd(rs1.getString(1));
			}

			client.sendToClient(bookList);


		}catch(Exception e){e.printStackTrace();}
	}
	/*				while(rs1.next()){
					System.out.println("rs.getString(1)");
					book1.setBookid(rs1.getInt(2));
					String genre = book1.getGenre();
					genre+=rs1.getString(1) + " ";
					book1.setGenre(genre);
				}*/
	/**
	 * This method initialized the genresbooks list to economize the access to the server.
	 * @param client
	 * @author orel zilberman
	 */

	public void InitializeGenresBooksList(ConnectionToClient client){
		try {
			Statement stmt = conn.createStatement();
			String query = "SELECT * FROM genresbooks";
			ResultSet rs = stmt.executeQuery(query);
			ArrayList<Book> bookList = new ArrayList<Book>();	
			while(rs.next())
				bookList.add( new Book (rs.getString(1),rs.getInt(2)));
			client.sendToClient(bookList);


		} catch (Exception  e) {e.printStackTrace();}	
	}
	/**
	 * This method creates a new entry in the ordered books table
	 * @param book the book selected by the client
	 * @param client holds the connection to the client that sent the request
	 * @author Eran Simhon
	 */


	private void addNewOrder(OrderedBook book,ConnectionToClient client)
	{
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("insert into orderedbook values('" + book.getReaderID()+ "'," + book.getBookid() + ",'" + book.getTitle() + "','" + book.getAuthor() + "','" +  book.getPurchaseDate() + "');" );
			ResultSet rs = stmt.executeQuery("SELECT numofpurchases FROM books where bookid="+book.getBookid()+";");
			rs.next();
			int num = rs.getInt(1);
			stmt.executeUpdate("Update books set numofpurchases=" + (num+1) + " where bookid=" + book.getBookid()+";");
			client.sendToClient(book);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * This method initializes the genres list in order to economize the access to the server.
	 * @param client
	 * @author orel zilberman
	 */

	public void initializeGenreList(ConnectionToClient client){/*******************************************/
		try {
			Statement stmt = conn.createStatement();
			String query = "SELECT * FROM genre";
			ResultSet rs = stmt.executeQuery(query);
			ArrayList<Genre> genreList = new ArrayList<Genre>();	
			while(rs.next())
				genreList.add( new Genre (rs.getString(1),rs.getString(2)));
			client.sendToClient(genreList);

		} catch (Exception  e) {}	
	}

	/**
	 * 	This method adds a new genre to the genre table
	 * @param genre holds the information for the new genre to add
	 * @param client
	 * @author orel zilberman
	 */
	public void addGenre(Genre genre, ConnectionToClient client){
		Statement stmt;
		String query = "insert into genre values ('"+genre.getGenre()+"', '"+genre.getComments()+"');";
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
	/**
	 * This method updates a specific genre with specific information, according to the user's input.
	 * @param genre hold the essntial information of the specific genre we want to update.
	 * @param client
	 * @author orel zilberman
	 */

	public void updateGenre(Genre genre,ConnectionToClient client){
		try{
			Statement stmt=conn.createStatement();
			String query = "UPDATE genre SET name='"+genre.getGenre()+"', comments= '"+genre.getComments()+
					"' WHERE name='"+genre.getOldGenre()+"';";
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
			query = "UPDATE genresbooks SET genre = '" + genre.getGenre() + "' WHERE genre = '" + genre.getOldGenre() + "';";
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


	/**
	 * This method deletes a specific genre, according to the user's input
	 * @param genre holds information about the genre we want to delete
	 * @param client
	 * @author orel zilberman
	 */

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
				arr.add(new Book_NumOfPurchases(rs.getInt(2),rs.getInt(9)));
			Collections.sort(arr);
			int i=0;
			for(i=0;i<arr.size();i++)
				if(arr.get(i).bookid==b.getBookid())
					break;
			for(int j=i;j<arr.size();j++)
				try{
					if(arr.get(j).numofpurchases==arr.get(j+1).numofpurchases)
						i++;
				}
			catch(Exception e){};
			System.out.println("place:"+(arr.size()-i));
			System.out.println("total:"+arr.size());
			client.sendToClient(new Book(0,Integer.toString((arr.size()-i))+"/"+Integer.toString(arr.size())));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	/*class that helps for organizing and sorting to get ranking of a book*/
	/**
	 * This class represents books and their number of purchases. 
	 * It helps for sorting Book arrays, by the number of purchases, so we can send to the client the books popularity.
	 * @author ozdav
	 *
	 */
	public class Book_NumOfPurchases implements Comparable<Book_NumOfPurchases> {
		public int bookid;
		public int numofpurchases;
		/**
		 * Constructs according to specific bookid and numofpurchases
		 * @param bookid Book's id in DB
		 * @param numofpurchases Book's number of purchases in DB
		 */
		public Book_NumOfPurchases(int bookid,int numofpurchases){
			this.bookid=bookid;
			this.numofpurchases=numofpurchases;
		}
		/**
		 * Sets book's number of purchases
		 * @param x
		 */
		public void setnumofpurchases(int x){
			this.numofpurchases=x;
		}
		/**
		 * Overrides the Object's compareTo, to compare values according to their number of purchases.
		 * <p>
		 * Used for sorting books that been reached from server.
		 */
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
	/**
	 * This method gets a String(not really a book) which represents a specific genre of a book, and its id.
	 * <p>
	 * @return A string which represents the book popularity in the specific genre
	 * @param b handles a BookTitle which is actually a string, represents the wanted genre, and the bookid
	 * @param client
	 */
	private void gettingGenrePlace(Book b, ConnectionToClient client) {
		ArrayList <Book_NumOfPurchases> arr=new ArrayList<Book_NumOfPurchases>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("Select *  FROM genresbooks WHERE genre='"+ b.getTitle() + "';");
			while(rs.next())
				arr.add(new Book_NumOfPurchases(rs.getInt(2),6));//Updating books id's
			for(int i=0;i<arr.size();i++){//updating their number of purchases
				Statement stmt1 = conn.createStatement();
				ResultSet rs1 = stmt1.executeQuery("Select *  FROM books WHERE bookid="+ arr.get(i).bookid + ";");
				while(rs1.next())
					arr.get(i).setnumofpurchases(rs1.getInt(9));
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
			for(int j=i;j<arr.size();j++)
				try{
					if(arr.get(j).numofpurchases==arr.get(j+1).numofpurchases)
						i++;
				}
			catch(Exception e){};
			client.sendToClient(new Book(0,Integer.toString(arr.size()-i)+"/"+Integer.toString(arr.size())));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * This method returns an array of the general popularity of the books in the library
	 * @param msg dummy
	 * @param client 
	 */
	private void ranking(Object msg, ConnectionToClient client) {
		ArrayList<Book_NumOfPurchases>arr=new ArrayList<Book_NumOfPurchases>();
		ArrayList<Integer>array=new ArrayList<Integer>();

		try{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from books;");
			while(rs.next())
				arr.add(new Book_NumOfPurchases(rs.getInt(2),rs.getInt(9)));
			Collections.sort(arr);	
			for(int i=0;i<arr.size();i++)
				array.add(arr.get(i).bookid);
			client.sendToClient(array);
		}
		catch(Exception e){e.printStackTrace();}
	}
	private void getBookGenres(Book b,ConnectionToClient client){
		ArrayList<String> arr=new ArrayList<String>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("Select *  FROM genresbooks WHERE bookid="+ b.getBookid() + ";");
			while(rs.next())
				arr.add(rs.getString(1));//Adding genres to array
			System.out.println(arr);
			client.sendToClient(arr);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * This method updates the book list that is presented to the reader according to the search parameters entered
	 * @param book holding the 2 queries: one that gets data form the book table, and the other
	 * that gets data from the genresbooks table. It was necessary to write 2 queries because
	 * the book table doesn't contain any data regarding the genres of the books stored in it.
	 * The two results are being processed according to the operation selected: "AND" / "OR"
	 * @param client client holds the connection to the client that sent the request
	 * @author Eran Simhon
	 */


	private void updateBookList(Book book, ConnectionToClient client)
	{
		ArrayList<Integer> IDs = new ArrayList<Integer>();
		ArrayList<Book> res = new ArrayList<Book>();
		int i;

		try{
			Statement stmt = conn.createStatement();
			System.out.println(book.query);
			System.out.println(book.genreQuery);
			ResultSet rs = stmt.executeQuery(book.query);

			while(rs.next())
				res.add(new Book(rs.getString(1),rs.getInt(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getInt(8),rs.getInt(9)));
			if(!book.genreQuery.equals(""))
			{
				Statement stmt1 = conn.createStatement();
				ResultSet rs1 = stmt1.executeQuery(book.genreQuery);//All books that belong to the selected genre
				while(rs1.next())
					IDs.add(rs1.getInt(2));
				if(book.getSearchOperand().equals("AND"))
				{
					if(IDs.size()==0)
						res.clear();
					else{
						System.out.println(IDs.contains(2));
						for(i=0;i<res.size();i++)
							if(!IDs.contains(res.get(i).getBookid()))
								res.remove(i);
					}
				}
				else
				{
					Statement stmt2 = conn.createStatement();
					for(i=0;i<IDs.size();i++)
					{
						ResultSet rs2 = stmt2.executeQuery("select * from books where bookid="+IDs.get(i)+";");
						rs2.next();
						Book b = new Book(rs2.getString(1)
								,rs2.getInt(2)
								,rs2.getString(3)
								,rs2.getString(4)
								,rs2.getString(5)
								,rs2.getString(6)
								,rs2.getString(7)
								,rs2.getInt(8)
								,rs2.getInt(9));
						//Book b = new Book(rs2.getString(1),rs2.getInt(2),rs2.getString(3),rs2.getString(4),rs2.getString(5),rs2.getString(6),rs2.getString(7),rs2.getInt(8),rs2.getInt(9));
						if(!res.contains(b))
							res.add(b);
					}
				}

			}
			//Increasing the number of searches for all the books that passed the checks
			rs = stmt.executeQuery("select searchID from searchbook;");
			int searchID=0;
			if(rs.last())//Moving the cursor, if possible, to the last row
				searchID = rs.getInt(1)+1;
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
			LocalDate x = LocalDate.now();
			String y = x.format(formatter);
			for(i=0;i<res.size();i++)
			{
				stmt.executeUpdate("insert into searchbook values("+res.get(i).getBookid()+",'"+y+"',"+searchID+");");
				searchID++;
			}
			//End increasing the number of searches for all the books that passed the checks

			//Get the genre of each book
			for(i=0;i<res.size();i++)
			{
				rs = stmt.executeQuery("select genre from genresbooks where bookid="+res.get(i).getBookid());
				String temp="";
				while(rs.next())
					temp+=rs.getString(1)+",";
				String genre="";
				for(int j=0;j<temp.length()-1;j++)
					genre+=temp.charAt(j);
				res.get(i).setGenre(genre);
			}
			client.sendToClient(res);
		}
		catch(Exception e){e.printStackTrace();}
	}
	/**
	 * This method edits a specific review with specific information, according to the user's input.
	 * @param review holds the vital information of the review we want to edit.
	 * @param client
	 * @author orel zilberman
	 */

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
	/**
	 *Getting statistics of a book: #of purchases & #of searches of a book by date
	 *<p>
	 * Input: Bookid, from-(date), until(date)
	 * @return: Array which handles number of purchases and number of searches from date until date.
	 * @param s Search s- includes: bookid, from-(date), until(date)
	 * @param client
	 * @author orel zilberman
	 */
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

	/**
	 * This method gets the all the books in the DB that are not suspended 
	 * @param client client holds the connection to the client that sent the request
	 * @author Eran Simhon
	 */

	private void getBooks(ConnectionToClient client)
	{
		try {
			ArrayList <Book> books = new ArrayList<Book>();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("Select * from books where isSuspend=0");
			while(rs.next())
				books.add( new Book (rs.getString(1),rs.getInt(2),rs.getString(3)
						,rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7), rs.getInt(8), rs.getInt(9)));
			//Get the genre of each book in books
			int j;
			String temp,genre;
			for(int i=0;i<books.size();i++)
			{
				rs = stmt.executeQuery("select genre from genresbooks where bookid="+books.get(i).getBookid()+";");
				temp="";
				while(rs.next())
					temp+=rs.getString(1)+",";
				//Remove the comma(,) at the end of the string
				genre="";
				for(j=0;j<temp.length()-1;j++)
					genre+=temp.charAt(j);
				books.get(i).setGenre(genre);	
			}	
			client.sendToClient(books);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * This method examines the reviews list to find the tuples with that are either
	 *  approved, not approved or not checked and update the isApproved field, according to the isApproved param input.
	 * @param review holds the review ID to differentiate the review we want from the others.
	 * @param isApproved holds an integer that says what is the review status.  -1 >NOT APPROVED, 0 - NOT CHECKED, 1 - APPROVED
	 * @param client
	 * @author orel zilberman
	 */

	public void examineReview(Review review,int isApproved, ConnectionToClient client){//-1 >NOT APPROVED, 0 - NOT CHECKED YET, 1 - APPROVED
		Statement stmt;
		try{
			stmt = conn.createStatement();
			stmt.executeUpdate("UPDATE reviews SET isApproved = '" + isApproved + "' WHERE reviewid = '" + review.getReviewID() + "';");
		}catch(Exception e){e.printStackTrace();}
	}

	/**
	 * This method gets the reviews the user's was searching for.
	 * @param select holds a string for the SELECT clause
	 * @param from holds a string for the FROM clause
	 * @param where holds a string for the WHERE clause
	 * @param client
	 * @author orel zilberman
	 */

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

	/**
	 * This method deletes a certain book from the DB, according to the user's input.
	 * @param book holds some crucial information about the book the user wants to delete.
	 * @param client
	 * @author orel zilberman
	 */

	public void deleteBook(Book book, ConnectionToClient client){
		ArrayList<String> bookList = new ArrayList<String>();
		bookList.add("BookSearch");
		try{
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("DELETE FROM books WHERE bookid = '" + book.getBookid() + "';");
			stmt.executeUpdate("DELETE FROM genresbooks WHERE bookid = '" + book.getBookid() + "';");
		}catch(Exception e){e.printStackTrace();}

	}

	/**
	 * This method inserts a new row into the review table
	 * @param review the new review to be inserted
	 * @param client client holds the connection to the client that sent the request
	 * @author Eran Simhon
	 */
	private void addReview(Review review, ConnectionToClient client)
	{
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select reviewID from reviews;");
			int reviewID = 0;
			if(rs.last())
				reviewID = rs.getInt(1)+1;
			//System.out.println("insert into reviews values('" + review.getReviewBook().getBookid() + "','" + review.getReviewBook().getTitle() + "','" + review.getReviewBook().getAuthor() + "','" + review.getKeyword() + "',0,'" + review.getReview() + "'," + reviewID + ",'" + review.getSignature() + "');");
			stmt.executeUpdate("insert into reviews values('" + review.getReviewBook().getBookid() + "','" + review.getReviewBook().getTitle() + "','" + review.getReviewBook().getAuthor() + "','" + review.getKeyword() + "',0,'" + review.getReview() + "'," + reviewID + ",'" + review.getSignature() + "');" );
			client.sendToClient("Thank you for submitting a review! If your review will be approved by one of our workers, it will be published.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * This method returns all the books that the logged in reader ordered
	 * @param msg the currently logged in reader
	 * @param client client holds the connection to the client that sent the request
	 * @author Eran Simhon
	 */


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
			userbooks.add(new OrderedBook("",0,"","No books were been ordered by user."));
			client.sendToClient(userbooks);
		} catch (Exception  e) {
			e.printStackTrace();
		}                        
	}
	/**
	 * Activate suspended books and return them to the reader's search
	 * @param b a Book that the worker/manager would like to activate
	 * @param client
	 */
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
	/**
	 * Temporarily suspending a book from reader's search
	 * @param b Book manager/worker would like to suspend
	 * @param client
	 */
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
	/**
	 * This method searches through the readers in the database and finds readers
	 * <p>
	 * according to user's input.
	 * @param reader holds the query for the search.
	 * @param client
	 * @author orel zilberman
	 */

	public void findReaders(Reader reader, ConnectionToClient client){
		ArrayList<String> readersList = new ArrayList<String>();
		readersList.add("Readers");
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(reader.query);

			while(rs.next())
				readersList.add(rs.getString(3) + "   " + rs.getString(4) + " ID: " + rs.getString(1));
			client.sendToClient(readersList);
		} catch (Exception e) {e.printStackTrace();}
	}

	/**
	 * This method searches through the readers in the database and finds workers
	 * according to user's input.
	 * @param worker holds the query for the search.
	 * @param client
	 * @author orel zilberman
	 */

	public void findWorkers(Worker worker, ConnectionToClient client){
		ArrayList<String> workersList = new ArrayList<String>();
		workersList.add("Workers");
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(worker.query);
			while(rs.next())
				workersList.add(rs.getString(3) + " " + rs.getString(4) + " ID: " + rs.getString(1));
			client.sendToClient(workersList);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method searches through the database with specific information it gets as parameters.
	 * This function is generally used when you have a fixed query to send the server.
	 * @param from is a string for the FROM clause 
	 * @param where is a string for the WHERE clause 
	 * @param isWhat is a string that holds crucial information for when we send an array back to the server. 
	 * <p>
	 * It is so vital because it's a general method for many uses for many options, so in order to diffrentiate one request from another we'll use this param.
	 * @param client
	 * @author orel zilberman
	 */

	public void find(String from, String where,String isWhat, ConnectionToClient client){
		ArrayList<String> arr = new ArrayList<String>();
		arr.add(isWhat);
		Statement stmt;
		try{
			stmt = conn.createStatement();
			String query = "SELECT * FROM " + from + " WHERE " + where;
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				arr.add(rs.getString(3) + " " + rs.getString(4) + " ID: " + rs.getString(1));
			}
			client.sendToClient(arr);
		}catch(Exception e){e.printStackTrace();}
	}
	/**
	 * This method updates the readers table according to the selected subscription period
	 * @param reader the currently logged in reader
	 * @param type the type of the subscription
	 * @param client client holds the connection to the client that sent the request
	 * @author Eran Simhon
	 */

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
	/**
	 * This method removes a specific book from the database.
	 * @param book holds a query that was prepared in advance
	 * @param client
	 * @author orel zilberman
	 */

	public void removeBook(Book book, ConnectionToClient client){/**********************************/
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<String> authorList = new ArrayList<String>();
		ArrayList<String> titleList = new ArrayList<String>();
		ArrayList<Integer> bookIDS = new ArrayList<Integer>();
		ArrayList<Integer> genresIDS = new ArrayList<Integer>();//Contains the indexes that fit the search for bookIDS
		list.add("RemoveBook");
		try{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(book.query);
			System.out.println("MyServer removebook query : " + book.query);
			while(rs.next()){
				bookIDS.add(rs.getInt(3));authorList.add(rs.getString(2));titleList.add(rs.getString(1));//Get all the info about the book
				//At this point I know that if bookid X is in index i in the bookIDS, its also index i in the other lists.
			}
			int i=0;
			for(int id : bookIDS){
				ResultSet rs1 = stmt.executeQuery("SELECT bookid FROM genresbooks WHERE bookid = "+id+" AND genre LIKE '%" + book.genreToSearch + "%';");
				if(rs1.next())
					genresIDS.add(i);
				i++;
			}

			for(int index: genresIDS){
				String genre="";
				ResultSet rs1 = stmt.executeQuery("SELECT genre FROM genresbooks WHERE bookid = "+bookIDS.get(index) + ";");
				while(rs1.next())
					genre+=rs1.getString(1) + ", ";
				if(!genre.equals(""))
					list.add(titleList.get(index) + " by " + authorList.get(index) + " Genre: " + genre.substring(0, genre.length()-2) + "  With Book ID: " + bookIDS.get(index));
				i++;
			}


			client.sendToClient(list);
		}catch(Exception e){e.printStackTrace();}
	}

	/**
	 * This function initializes the worker's list to economize the access to the server
	 * @param client
	 * @author orel zilberman
	 */

	public void initializeWorkerList(ConnectionToClient client){
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

	/**
	 * Initializes a list of books which are in DB while software boots
	 * <p>
	 * Useful around all the code. Economizes accesses to server.
	 * @param client
	 */

	public void initializeBookList(ConnectionToClient client){/*******************************************/
		try {
			Statement stmt = conn.createStatement();
			String query = "SELECT * FROM books";
			ResultSet rs = stmt.executeQuery(query);
			ArrayList<Book> bookList = new ArrayList<Book>();
			while(rs.next())
				bookList.add( new Book (rs.getString(1),rs.getInt(2),rs.getString(3)
						,rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7), rs.getInt(8), rs.getInt(9)));

			client.sendToClient(bookList);
		} catch (Exception  e) {
			e.printStackTrace();
		}	
	}


	/**
	 * This method enters the credit card info into the DB
	 * @param card the credit card
	 * @param client client holds the connection to the client that sent the request
	 * @author Eran Simhon
	 */
	private void addCreditCard(CreditCard card,ConnectionToClient client)
	{
		Statement stmt;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate("Update readers set creditcardnum = '" + card.getCardNum() + "',expdate = '" + card.getExpDate() + "',securitycode='" + card.getSecCode() +"' where readerID="+ card.getId()+";");
			client.sendToClient(card);
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}


	}


	/**
	 * Updating client's status to offline.
	 * @param user represents the user that is currently online and that is asking to logout.
	 * @param client
	 */
	private void LogoutUser(User user,ConnectionToClient client)
	{
		try {
			Statement stmt = conn.createStatement();
			if(user instanceof Reader)
				stmt.executeUpdate("UPDATE readers SET isLoggedIn=0 WHERE readerID='" + user.getID() + "';");
			if(user instanceof Worker){
				System.out.println("worker logout");
				Worker worker = (Worker)user;
				stmt.executeUpdate("UPDATE workers SET 	isLoggedIn=0 WHERE workerID='" + worker.getWorkerID()+"';");
			}
			client.sendToClient("You've logged out successfully");

		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}



	/**
	 * This method adds a specific book to the database, with specific information that is held in the book parameter.
	 * @param book holds information about the new book the user would like to add.
	 * @param client
	 * @author orel zilberman
	 */


	public void addBook(Book book, ConnectionToClient client){
		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM books");
			int cnt = 0;
			while(rs.next()){
				if(rs.getInt(2)==cnt)
					cnt++;
			}	
			String query = "insert into books values ('" + book.getTitle() + "','" + cnt + "','" + book.getAuthor() + "','" + 
					book.getLanguage() + "','" + book.getSummary() + "','" + book.getToc() + "','" + book.getKeyword() + "','0', '0');";
			stmt.executeUpdate(query);
			while(!book.getGenre().equals("")){
				String genre = "", genreNew="";
				int counter=0;//Number of chars of the next genre
				for(int i=0;i<book.getGenre().length()&&!((book.getGenre().charAt(i))==' ');i++){
					counter++;
					genre+=book.getGenre().charAt(i);
				}
				for(int i=counter+1;i<book.getGenre().length();i++)
					genreNew +=book.getGenre().charAt(i);
				query = "insert into genresbooks values('" + genre + "'," + cnt + ");";
				book.setGenre(genreNew);
				stmt.executeUpdate(query);
			}
		} catch (SQLException e) {e.printStackTrace();}
		try {
			ArrayList<String> s = new ArrayList<String>();
			s.add("BookAdd");
			client.sendToClient(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * This method initializes the code's connection to the DB
	 */

	public void connectToDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		}
		catch (Exception var1_1) {
		}
		try {

			//this.conn = DriverManager.getConnection("jdbc:mysql://sql11.freesqldatabase.com/sql11153849", "sql11153849", "TlZbvGxXKu");
			//this.conn = DriverManager.getConnection("jdbc:mysql://localhost/mydb", "root", "Braude");
			this.conn = DriverManager.getConnection("jdbc:mysql://sql11.freesqldatabase.com/sql11153849", "sql11153849", "TlZbvGxXKu");
			//this.conn = DriverManager.getConnection("jdbc:mysql://localhost/mydb", "root", "Braude");
			System.out.println("MySQL Login Successful!");
		}
		catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}






	/**
	 * This method checks the login details that were entered by the user
	 * @param user contains the entered details
	 * @param client client holds the connection to the client that sent the request
	 * @author Eran Simhon
	 */

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
					worker = new  Worker();
					if(rs.getString(2).equals(password))
					{
						if(rs.getInt(10)==1)//isLoggedIn
							client.sendToClient("You're already signed in!");
						else{
							if(rs.getInt(9)==1){//It is a manager! rs.getInt(9) -> get the isManager
								worker.setType(3);
								worker.setWorkerID(rs.getString(1));
								stmt1.executeUpdate("UPDATE workers SET isLoggedIn=1 WHERE workerID='" + worker.getWorkerID() + "';");
							}
							else{
								worker.setType(2);
								worker.setWorkerID(rs.getString(1));
								stmt1.executeUpdate("UPDATE workers SET isLoggedIn=1 WHERE workerID='" + worker.getWorkerID() + "'");
								client.sendToClient(worker);
							}
							client.sendToClient(worker);
						}
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
							stmt.executeUpdate("UPDATE readers SET isLoggedIn=1 WHERE readerID='" + reader.getID() + "';");
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



/*REMOVE BOOK INCASE OF FAILURE
 * 
 * 
	public void removeBook(Book book, ConnectionToClient client){
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<String> authorList = new ArrayList<String>();
		ArrayList<String> titleList = new ArrayList<String>();
		ArrayList<Integer> bookIDS = new ArrayList<Integer>();
		list.add("RemoveBook");
		try{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(book.query);
			System.out.println("MyServer removebook query : " + book.query);
			while(rs.next()){
				bookIDS.add(rs.getInt(3));authorList.add(rs.getString(2));titleList.add(rs.getString(1));//Get all the info about the book
				//At this point I know that if bookid X is in index i in the bookIDS, its also index i in the other lists.
			}
			int i=0;
			for(int id : bookIDS){
				String genre="";
				ResultSet rs1 = stmt.executeQuery("SELECT genre, bookid FROM genresbooks WHERE bookid = "+id+" AND genre LIKE '%" + book.genreToSearch + "%';");
				while(rs1.next())
					genre+=rs1.getString(1) + ", ";
				if(!genre.equals(""))
				list.add(titleList.get(i) + " by " + authorList.get(i) + " Genre: " + genre.substring(0, genre.length()-2) + "  With Book ID: " + id);
				i++;
			}		
			client.sendToClient(list);
		}catch(Exception e){e.printStackTrace();}
	}
 */