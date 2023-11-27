/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author sujansiva
 */
public class FXMLController implements Initializable {
    public static List<StockQuote> stocksSelected; //Empty list of StockQuotes. When user changes the first security, call setOnAction -> stocksSelected.set(0,*stockquote object*) and so on
    public static List<Portfolio> listOfPortfolios;
    static String userRiskToleranc  ; //Each time the updateInvestorProfile is clicked, this should update to it.
    static double totalEquity; //
    public static List<Integer> calculateStockQuantities(){
         List<Integer> stockQuantities = new ArrayList<>();

        // Calculate the number of stocks for each stock based on the weights
        for (int i = 0; i < bestFitPortfolio(listOfPortfolios).getStocks().size(); i++) {
            double stockWeight = bestFitPortfolio(listOfPortfolios).getWeights().get(i);
            double stockValue = bestFitPortfolio(listOfPortfolios).getStocks().get(i).getLatestPrice();
            int stockQuantity = (int) ((totalEquity*stockWeight)/stockValue);
            stockQuantities.add(stockQuantity);
        }

        return stockQuantities;   
    }
    public static Portfolio bestFitPortfolio(List<Portfolio> portfolios){
        switch(userRiskToleranc){
        
            case "conservative": {
                
                Portfolio smallestRiskPortfolio = portfolios.get(0); // Initialize with the first portfolio

                // Iterate through the rest of the portfolios
                for (int i = 1; i < portfolios.size(); i++) {
                    Portfolio currentPortfolio = portfolios.get(i);

                    // Compare the risk of the current portfolio with the smallestRiskPortfolio
                    if (currentPortfolio.getPortfolioRisk() < smallestRiskPortfolio.getPortfolioRisk()) {
                        smallestRiskPortfolio = currentPortfolio; // Update if the current portfolio has smaller risk
                    }
                }
                return smallestRiskPortfolio;
            }
            case "aggressive": {
                Portfolio highestReturnsPortfolio = portfolios.get(0);
                for (int i = 1; i < portfolios.size(); i++) {
                    Portfolio currentPortfolio = portfolios.get(i);

                    // Compare returns of the current portfolio with the highestReturnsPortfolio
                    if (currentPortfolio.getPortfolioReturn() > highestReturnsPortfolio.getPortfolioReturn()) {
                        highestReturnsPortfolio = currentPortfolio; // Update if the current portfolio has higher returns
                    }
                }

                return highestReturnsPortfolio;
            }
            default:{
                return new Portfolio();
            }
        }
    }
    
    
    
    public static List<List<Double>> weights; //These are some random weights of the stocks for each portfolio. There's gonna be 20 portfolio, meaning 20 data points.
        
        
        
        

        
    
    
    
    @FXML
    private TextField sec1;
    @FXML
    private ChoiceBox<Double> ListViewRisk;
    
    //Use following code for creating portfolios when 1. 'create graph' button is pressed OR 2. When both of these conditions are met : 2.1 A security slot has been changed 2.2 All three securities are selected. 
    // In this case, call setOnAction for security selection and then if statement to see if all three securities slots are filled.
     void sec1OnAction(ActionEvent event) {
        StockQuote stockQuote = new StockQuote();
        stocksSelected.set(0, stockQuote);
        checkAndCreatePortfolios();
    }

    void sec2OnAction(ActionEvent event) {
        StockQuote stockQuote = new StockQuote();
        stocksSelected.set(1, stockQuote);
        checkAndCreatePortfolios();
    }

    void sec3OnAction(ActionEvent event) {
        StockQuote stockQuote = new StockQuote();
        stocksSelected.set(2, stockQuote);
        checkAndCreatePortfolios();
    }

    private void checkAndCreatePortfolios() {
        // Check if all three securities are selected
        if (areAllSecuritiesSelected()) {
            createPortfolios();
        }
    }

    private boolean areAllSecuritiesSelected() {
        return stocksSelected.size() == 3 && stocksSelected.get(0) != null && stocksSelected.get(1) != null && stocksSelected.get(2) != null;
    }

    private void createPortfolios() {
        listOfPortfolios.clear(); // Clear everything that's in the list of portfolio options as we're creating new options.
        for (int i = 0; i < 20; i++) {
            listOfPortfolios.add(new Portfolio(new ArrayList<>(stocksSelected), weights.get(i)));
        }
    }
    
    /*
    for(int i = 0; i<20; i++){
        listOfPortfolios.clear(); //Clear everything that's in the list of portfolio options as we're creating new options.
        listOfPortfolios.add(new Portfolio(stocksSelected,weights.get(i));
    }

    */
    @FXML
 void generateGraphOnAction(ActionEvent event) {
          createPortfolios();

        // Clear existing data in the chart
        Graph.getData().clear();

        // Add series to the chart using portfolio data
        for (int i = 0; i < listOfPortfolios.size(); i++) {
            Portfolio portfolio = listOfPortfolios.get(i);
            double risk = portfolio.getPortfolioRisk();
            double returnVal = portfolio.getPortfolioReturn();

            // Add data points to the series
            XYChart.Series series = new XYChart.Series<>();
            series.getData().add(new XYChart.Data<>(risk, returnVal));
            

            // Add the series to the chart
            Graph.getData().add(series);
           
        }
    }
    @FXML
    private AreaChart<Double, Double> Graph;
    
    @FXML
    private AnchorPane rootPane;

    

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

    void GenerateOnAction(ActionEvent event) {

    }
    @FXML
    private Label St1;

    @FXML
    private TextField Txt1;

    @FXML
    private Label St2;

    @FXML
    private TextField Txt2;

    @FXML
    private Label St3;

    @FXML
    private TextField Txt3;

    @FXML
    private HBox Stockideal;

    @FXML
    private Button generateGraph;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        weights.add(List.of(0.25, 0.3, 0.35)); //Add random diversifications to the List of weights
        weights.add(List.of(0.1, 0.5, 0.4));
        weights.add(List.of(0.2, 0.1, 0.6));
        weights.add(List.of(0.3, 0.2, 0.4));
        weights.add(List.of(0.15, 0.4, 0.45));
        weights.add(List.of(0.4, 0.2, 0.35));
        weights.add(List.of(0.3, 0.4, 0.2));
        weights.add(List.of(0.2, 0.3, 0.4));
        weights.add(List.of(0.1, 0.3, 0.5));
        weights.add(List.of(0.35, 0.25, 0.4));
        weights.add(List.of(0.2, 0.25, 0.55));
        weights.add(List.of(0.3, 0.15, 0.55));
        weights.add(List.of(0.25, 0.35, 0.4));
        weights.add(List.of(0.15, 0.3, 0.55));
        weights.add(List.of(0.4, 0.1, 0.5));
        weights.add(List.of(0.25, 0.35, 0.4));
        weights.add(List.of(0.3, 0.1, 0.6));
        weights.add(List.of(0.1, 0.4, 0.5));
        weights.add(List.of(0.2, 0.5, 0.3));
        weights.add(List.of(0.3, 0.3, 0.35));

}

}

