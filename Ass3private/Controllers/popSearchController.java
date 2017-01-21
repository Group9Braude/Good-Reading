package Controllers;

import java.util.ArrayList;

import Entities.Book;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class popSearchController {
	@FXML
	public ComboBox<String> action = new ComboBox<String>();
	public TextField title,author,lang,genre,keyWord;
	/*public void initialize()
	{
		if(action==null)
			System.out.println("null22");
		action.getItems().addAll("AND","OR");
	}*/
	
	/*public void onSearch()
	{
		ArrayList <String> authors = new ArrayList<String>();
		String[] str = author.getText().split(",");
		for(int i=0;i<str.length;i++)
			authors.add(str[i]);
		
		ArrayList <String> genres = new ArrayList<String>();
		str = genre.getText().split(",");
		for(int i=0;i<str.length;i++)
			genres.add(str[i]);
		
		ArrayList <String> keyWords = new ArrayList<String>();
		str = keyWord.getText().split(",");
		for(int i=0;i<str.length;i++)
			keyWords.add(str[i]);
		
		int i;
		ArrayList<Book> titleRes = new ArrayList<Book>(SearchBookScreenController.getAllBook());
		for(i=0;i<titleRes.size();i++)
			if(!titleRes.get(i).getTitle().equals(title.getText()))
				titleRes.remove(i);
		
		ArrayList<Book> authorRes = new ArrayList<Book>(SearchBookScreenController.getAllBook());
		for(i=0;i<authorRes.size();i++)
		{
			String[]  temp = authorRes.get(i).getAuthor().split(",");
			ArrayList<String> bookAuthors = new ArrayList<String>();
			for(int j=0;j<temp.length;j++)
				bookAuthors.add(temp[j]);
			if(action.getSelectionModel().getSelectedItem().equals("AND"))
			{
				if(!authors.containsAll(bookAuthors))
					authorRes.remove(i);
			}
			else
			{
				boolean found = false;
				for(int j=0;j<bookAuthors.size();j++)
					if(authors.contains(bookAuthors.get(j)))
						found=true;
				if(!found)
					authorRes.remove(i);
			}
		}
		
		ArrayList<Book> langRes = new ArrayList<Book>(SearchBookScreenController.getAllBook());
		for(i=0;i<langRes.size();i++)
			if(!langRes.get(i).getLanguage().equals(lang.getText()))
				langRes.remove(i);
		
		
		//NEED TO ADD THIS
		//ArrayList<Book> genreRes = new ArrayList<Book>(SearchBookScreenController.getAllBook());
		
		ArrayList<Book> keyWordRes = new ArrayList<Book>(SearchBookScreenController.getAllBook());
		for(i=0;i<keyWordRes.size();i++)
		{
			String[]  temp = keyWordRes.get(i).getAuthor().split(",");
			ArrayList<String> bookKeyWord = new ArrayList<String>();
			for(int j=0;j<temp.length;j++)
				bookKeyWord.add(temp[j]);
			if(action.getSelectionModel().getSelectedItem().equals("AND"))
			{
				if(!keyWords.containsAll(bookKeyWord))
					keyWordRes.remove(i);
			}
			else
			{
				boolean found = false;
				for(int j=0;j<bookKeyWord.size();j++)
					if(keyWords.contains(bookKeyWord.get(j)))
						found=true;
				if(!found)
					keyWordRes.remove(i);
			}
		}
		ArrayList<Book> finalRes = SearchBookScreenController.getAllBook();
		if(action.getSelectionModel().getSelectedItem().equals("AND"))
		{
			finalRes.retainAll(titleRes);
			finalRes.retainAll(authorRes);
			finalRes.retainAll(langRes);
			finalRes.retainAll(keyWordRes);
		}
		
		else{
			for(i=0;i<finalRes.size();i++)
				if(!titleRes.contains(finalRes.get(i)) && !authorRes.contains(finalRes.get(i)) && !langRes.contains(finalRes.get(i)) && !keyWordRes.contains(finalRes.get(i)))
					titleRes.remove(i);
		}
		
	}*/
	
	
}
		
				

		
		
		
		

