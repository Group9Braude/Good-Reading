package Controllers;

import java.awt.TextField;

import javafx.scene.input.KeyEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;

public class ReportsController {
	@FXML public TextField id;
	ObservableList<String> obsMyBooks=FXCollections.observableArrayList();
	public ListView<String> myBooks=new ListView<String>(obsMyBooks);
		
	
	@FXML
	public void textAction(KeyEvent e){//if ive got enter

	    if(e.getCode().equals(KeyCode.ENTER))
	        System.out.println(id.getText());
	    
	}
}
