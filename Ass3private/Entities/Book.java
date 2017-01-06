package Entities;

import java.io.Serializable;
import java.util.ArrayList;

public class Book implements Serializable{
 private static final long serialVersionUID = 1L;
 private String bookid, title, author, language, summary, toc, keyword;
 private static ArrayList<Book> bookList;
 private int isSuspend=0;
 
 
 
 

 public Book(){}
 
 public Book(String bookid, String title, String author, String language, String summary, String toc, String keyword, int isSuspend) {
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

public static ArrayList<Book> getBookList() {
	return bookList;
}
public static void setBookList(ArrayList<Book> bookList) {
	Book.bookList = bookList;
}
 public int getisSuspend(){
  return this.isSuspend;
 }
 public void setisSuspend(){
  this.isSuspend=0;
 }
 public String getBookid() {
  return bookid;
 }
 public void setBookid(String bookid) {
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


}