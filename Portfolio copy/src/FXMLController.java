/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author sujansiva
 */

public class FXMLController implements Initializable {
    private static Double totalEquity; //Everytime that the investor profile is updated. This should be set to the investor's equity via setOnAction for UpdateInvestorProfileButton
    public static Double getTotalEquity(){
        return totalEquity;
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
