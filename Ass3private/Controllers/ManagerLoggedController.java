package Controllers;

import java.io.IOException;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ManagerLoggedController {
 private Main main;
 @FXML
 private static Button temporarilyRemoveABook,getAbsolutePopularityofBook,getRelativePopularityofBook,getPurchaseNumPerBook,getSearchNumPerBook,getUserBookList,getPeriodicReports;
 
 /**
  * @throws IOException
  */
 private void goTempRemoveAbook() throws IOException{
  temporarilyRemoveABook.setStyle("-fx-background-color: #CCFF99");
  main.showTempRemoveAbook();
  
 }
}