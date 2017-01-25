package Entities;

import java.io.Serializable;
import java.util.ArrayList;

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



	public Book(){}
	public Book(String genre, int bookid){
		this.setGenre(genre);
		this.bookid=bookid;
	}

	public Book(int bookid){
		this.bookid=bookid;
	}
	public Book(int bookid,String title){
		this.bookid=bookid;
		this.title=title;
	}
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

	public void GenreAdd(String genre){
		if(this.genre.equals(""))
			this.genre = genre;
		else
			this.genre+=" " + genre;
	}
	public int getIsSuspend() {
		return isSuspend;
	}

	public void setIsSuspend(int isSuspend) {
		this.isSuspend = isSuspend;
	}

	public int getNumOfPurchases() {
		return numOfPurchases;
	}

	public void setNumOfPurchases(int numOfPurchases) {
		this.numOfPurchases = numOfPurchases;
	}

	public static ArrayList<Book> getBookList() {
		return bookList;
	}

	public static void setBookList(ArrayList<Book> bookList) {
		Book.bookList = bookList;
	}

	public ArrayList<Book> getDeleteBookList() {
		return deleteBookList;
	}

	public void setDeleteBookList(ArrayList<Book> deleteBookList) {
		this.deleteBookList = deleteBookList;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public int getisSuspend(){
		return this.isSuspend;
	}
	public void setisSuspend(int x){
		this.isSuspend=x;
	}
	public int getBookid() 
	{
		return bookid;
	}
	public void setBookid(int bookid) {
		this.bookid = bookid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getToc() {
		return toc;
	}
	public void setToc(String toc) {
		this.toc = toc;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public static long getSerialversionuid() 
	{
		return serialVersionUID;
	}




	public String toString()
	{
		return title;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}


}