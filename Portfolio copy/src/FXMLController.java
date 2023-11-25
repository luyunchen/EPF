/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;

import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author sujansiva
 */
public class FXMLController implements Initializable {
    
    public static List<double[]> weights; //These are some random weights of the stocks for each portfolio. There's gonna be 20 portfolio, meaning 20 data points.
        
        
        
        

        
    
    
    public static List<StockQuote> stocksSelected; //Empty list of StockQuotes. When user changes the first security, call setOnAction -> stocksSelected.set(0,*stockquote object*) and so on
    public static List<Portfolio> listOfPortfolios;
    
    //Use following code for creating portfolios when 1. 'create graph' button is pressed OR 2. When both of these conditions are met : 2.1 A security slot has been changed 2.2 All three securities are selected. 
    // In this case, call setOnAction for security selection and then if statement to see if all three securities slots are filled.
    
    
    /*
    for(int i = 0; i<20; i++){
        listOfPortfolios.clear(); //Clear everything that's in the list of portfolio options as we're creating new options.
        listOfPortfolios.add(new Portfolio(stocksSelected,weights.get(i));
    }

    */
    
    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField sec1;

    @FXML
    private TextField sec2;

    @FXML
    private TextField sec3;

    @FXML
    private Label LabelName;

    @FXML
    private TextField TextFieldName;

    @FXML
    private Label LabelRisk;

   

    @FXML
    private Label LabelAge;

    @FXML
    private TextField TextFieldAge;

    @FXML
    private Button Update;

  

    @FXML
    private Label LabelAsset;

    @FXML
    private CheckBox CB1;

    @FXML
    private CheckBox CB2;

    @FXML
    private CheckBox CB3;

    @FXML
    private CheckBox CB4;

    @FXML
    private Button Refresh;

    @FXML
    private TextField BarSearch;

    @FXML
    private ListView<String> ListView;

    @FXML
    void ListviewPop(MouseEvent event) {
    ListView.setOpacity(1.0);
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        weights.add(new double[]{0.25, 0.3, 0.35}); //Add random diversifications to the List of weights
        weights.add(new double[]{0.1, 0.5, 0.4});
        weights.add(new double[]{0.2, 0.1, 0.6});
        weights.add(new double[]{0.3, 0.2, 0.4});
        weights.add(new double[]{0.15, 0.4, 0.45});
        weights.add(new double[]{0.4, 0.2, 0.35});
        weights.add(new double[]{0.3, 0.4, 0.2});
        weights.add(new double[]{0.2, 0.3, 0.4});
        weights.add(new double[]{0.1, 0.3, 0.5});
        weights.add(new double[]{0.35, 0.25, 0.4});
        weights.add(new double[]{0.2, 0.25, 0.55});
        weights.add(new double[]{0.3, 0.15, 0.55});
        weights.add(new double[]{0.25, 0.35, 0.4});
        weights.add(new double[]{0.15, 0.3, 0.55});
        weights.add(new double[]{0.4, 0.1, 0.5});
        weights.add(new double[]{0.25, 0.35, 0.4});
        weights.add(new double[]{0.3, 0.1, 0.6});
        weights.add(new double[]{0.1, 0.4, 0.5});
        weights.add(new double[]{0.2, 0.5, 0.3});
        weights.add(new double[]{0.3, 0.3, 0.35});

}
}