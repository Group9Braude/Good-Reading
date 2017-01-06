package Controllers;

import java.io.IOException;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ManagerLoggedController {
 @FXML
 private static Button temporarilyRemoveABook,getAbsolutePopularityofBook,getRelativePopularityofBook,getPurchaseNumPerBook,getSearchNumPerBook,getUserBookList,getPeriodicReports;
 
 /**
  * @throws IOException
  */
 public void goTempRemoveAbook() throws IOException{
  Main.showTempRemoveAbook();
  
 }
}