package Entities;

import java.io.Serializable;
import java.util.ArrayList;

public class Book extends GeneralMessage implements Serializable{
	private static final long serialVersionUID = 1L;
	private String title, author, language, summary, toc, keyword;
	public static ArrayList<Book> bookList;
	private int isSuspend=0 , bookid;
	public static int bookCnt=10;



	public Book(){}

	public Book(String title,int bookid, String author, String language, String summary, String toc, String keyword, int isSuspend) {
		super();
		this.bookid = bookid;
		this.title = title;
		this.author = author;
		this.language = language;
		this.summary = summary;
		this.toc = toc;
		this.keyword = keyword;
		this.isSuspend = isSuspend;
	}

	public int getisSuspend(){
		return this.isSuspend;
	}
	public void setisSuspend(){
		this.isSuspend=0;
	}
	public int getBookid() {
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static int getBookCnt() {
		return bookCnt;
	}

	public static void setBookCnt(int bookCnt) {
		Book.bookCnt = bookCnt;
	}


}