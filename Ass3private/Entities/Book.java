package Entities;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * Book class describes the whole book's details and its functions
 * @author ozdav
 *
 */
public class Book extends GeneralMessage implements Serializable{
	private static final long serialVersionUID = 1L;

	private String title, language, summary, toc,genre, theme;
	public String removeGenres;//past genres for removal
	private String author;
	private String keyword;

	/*             Book Properties          */
	private int isSuspend=0 , bookid, numOfPurchases;
	/*             Book Properties          */

	public static ArrayList<Book> bookList;
	public ArrayList<Book> deleteBookList;
	public String query, genreToSearch;
	public boolean isGenres ;


/**
 * Constructs a book
 */
	public Book(){}
/**
 * Constructs a new book with specific genre and bookid
 * @param genre
 * @param bookid
 */
	public Book(String genre, int bookid){
		this.setGenre(genre);
		this.bookid=bookid;
	}
/**
 * Constructs a new book with a specific bookid
 * @param bookid 
 */
	public Book(int bookid){
		this.bookid=bookid;
	}
/**
 * Constructs a new book with specific id and title
 * @param bookid
 * @param title
 */
	public Book(int bookid,String title){
		this.bookid=bookid;
		this.title=title;
	}
/**
 * Constructs a book with specific title,id,author,language, summary, table of contents, keyword, isSuspend and number of purchases
 * @param title Book's title
 * @param bookid Book's id in DB
 * @param author Book's authors
 * @param language 
 * @param summary 
 * @param toc Table of contents 
 * @param keyword
 * @param isSuspend is this book would be presented to the reader?
 * @param numOfPurchases number of purchases BY readers
 */
	public Book(String title,int bookid, String author, String language, String summary, String toc, String keyword, int isSuspend,int numOfPurchases) 
	{
		super();
		this.numOfPurchases = numOfPurchases;
		this.bookid = bookid;
		this.title = title;
		this.language = language;
		this.summary = summary;
		this.toc = toc;
		this.keyword = keyword;
		this.isSuspend = isSuspend;
		this.author = author;
 
	}
/**
 * Adding a genre to a book
 * @param genre Name of the genre
 */
	public void GenreAdd(String genre){
		if(this.genre.equals(""))
			this.genre = genre;
		else
			this.genre+=" " + genre;
	}
/**
 * Is this book suspended?
 * <p>
 * Book that has been suspended would be presented in reader's search
 * @return 0-active 1-suspended
 */
	public int getIsSuspend() {
		return isSuspend;
	}
/**
 * Sets 'suspend status' of a book
 * @param isSuspend
 */
	public void setIsSuspend(int isSuspend) {
		this.isSuspend = isSuspend;
	}
/**
 * Get the number of purchases of this book
 * @return
 */
	public int getNumOfPurchases() {
		return numOfPurchases;
	}
/**
 * Sets book's number of purchases
 * @param numOfPurchases
 */
	public void setNumOfPurchases(int numOfPurchases) {
		this.numOfPurchases = numOfPurchases;
	}
/**
 * @return An ArrayList which handles the whole books in DB 
 */
	public static ArrayList<Book> getBookList() {
		return bookList;
	}
/**
 * Sets an ArrayList of books in booklist
 * @param bookList 
 */
	public static void setBookList(ArrayList<Book> bookList) {
		Book.bookList = bookList;
	}
/**
 * @return deleteBookList
 */
	public ArrayList<Book> getDeleteBookList() {
		return deleteBookList;
	}
/**
 * Sets the deleteBookList
 * @param deleteBookList an ArrayList of books
 */
	public void setDeleteBookList(ArrayList<Book> deleteBookList) {
		this.deleteBookList = deleteBookList;
	}
/**
 * Gets the query
 * @return query
 */
	public String getQuery() {
		return query;
	}
/**
 * Sets query
 * @param query
 */
	public void setQuery(String query) {
		this.query = query;
	}
/**
 * Gets books 'suspended' status
 * <p>
 * Suspended book wouldn't be presented to the readers
 * @return 0-active 1-suspended
 */
	public int getisSuspend(){
		return this.isSuspend;
	}
/**
 * Sets book's 'status suspend'
 * @param x 0-active 1-suspend 
 */
	public void setisSuspend(int x){
		this.isSuspend=x;
	}
/**
 * Gets the book's ID in DB
 * @return
 */
	public int getBookid() 
	{
		return bookid;
	}
/**
 * Sets the book's ID in DB
 * @param bookid
 */
	public void setBookid(int bookid) {
		this.bookid = bookid;
	}
/**
 * Gets book's title
 * @return Book's title
 */
	public String getTitle() {
		return title;
	}
/**
 * Sets the book's title
 * @param title Title input
 */
	public void setTitle(String title) {
		this.title = title;
	}
/**
 * Gets the book's authors
 * @return Book's authors
 */
	public String getAuthor() {
		return author;
	}
/**
 * Sets the book's authors
 * @param author A String of authors
 */
	public void setAuthor(String author) {
		this.author = author;
	}
/**
 * Gets the book's language
 * @return Book's language
 */
	public String getLanguage() {
		return language;
	}
/**
 * Sets Book's language
 * @param language the new language
 */
	public void setLanguage(String language) {
		this.language = language;
	}
/**
 * Get book's summary
 * @return A string which presents the book's summary
 */
	public String getSummary() {
		return summary;
	}
/**
 * Sets book's summary	
 * @param summary The new book's summary
 */
	public void setSummary(String summary) {
		this.summary = summary;
	}
/**
 * Get book's table of contents
 * @return Book's table of contents
 */
	public String getToc() {
		return toc;
	}
/**
 * Sets the book's table of contents 
 * @param toc The new book's table of contents 
 */
	public void setToc(String toc) {
		this.toc = toc;
	}
/**
 * Get book's keyword
 * @return The book's keyword
 */
	public String getKeyword() {
		return keyword;
	}
/**
 * Sets the book's keyword
 * @param keyword Book's new keyword
 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public static long getSerialversionuid() 
	{
		return serialVersionUID;
	}



/**
 * Overriding the toString of Object class, presents the book's title
 */
	public String toString()
	{
		return title;
	}
/**
 * Gets books genres
 * @return A Strig of the book's genres
 */
	public String getGenre() {
		return genre;
	}
/**
 * Sets the book's genres
 * @param genre A string of genres 
 */
	public void setGenre(String genre) {
		this.genre = genre;
	}
/**
 * Get book's themes
 * @return A String of the book's themes
 */
	public String getTheme() {
		return theme;
	}
/**
 * Sets the book's themes
 * @param theme A String which represented the book's themes, separated with commas
 */
	public void setTheme(String theme) {
		this.theme = theme;
	}


}