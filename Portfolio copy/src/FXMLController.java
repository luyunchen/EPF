/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */


import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.QuadCurve;

/**
 * FXML Controller class
 *
 * @author sujansiva
 */
public class FXMLController implements Initializable {
    IEXCloudApi api = new IEXCloudApi();
    static String userRiskTolerance  ; //Each time the updateInvestorProfile is clicked, this should update to it.
    static double totalEquity; 
    
    static List<Integer> calculateStockQuantities(){
         List<Integer> stockQuantities = new ArrayList<>();
       Portfolio bestFit = bestFitPortfolio(listOfPortfolios);

if (bestFit != null && !bestFit.getStocks().isEmpty() && !bestFit.getWeights().isEmpty()) {
    for (int i = 0; i < bestFit.getStocks().size(); i++) {
        double stockWeight = bestFit.getWeights().get(i);
        double stockValue = bestFit.getStocks().get(i).getLatestPrice();
        int stockQuantity = (int) ((totalEquity * stockWeight) / stockValue);
        stockQuantities.add(stockQuantity);
    }
} else {
    // Handle the case where the list of stocks or weights is empty
    System.out.println("List of stocks or weights is empty");
}
    return stockQuantities;
    }
    
    static Portfolio bestFitPortfolio(List<Portfolio> portfolios){
        switch(userRiskTolerance){
        
            case "conservative": {
                if (!portfolios.isEmpty()) {
                    Portfolio smallestRiskPortfolio = portfolios.get(0);

                    // Iterate through the rest of the portfolios
                    for (int i = 1; i < portfolios.size(); i++) {
                        Portfolio currentPortfolio = portfolios.get(i);

                        // Compare the risk of the current portfolio with the smallestRiskPortfolio
                        if (currentPortfolio.getPortfolioRisk() < smallestRiskPortfolio.getPortfolioRisk()) {
                            smallestRiskPortfolio = currentPortfolio; // Update if the current portfolio has smaller risk
                        }
                    }

                    return smallestRiskPortfolio;
                
            }}
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
    
    static List<List<Double>> weights; //These are some random weights of the stocks for each portfolio. There's gonna be 20 portfolio, meaning 20 data points. 
    static List<StockQuote> stocksSelected = new ArrayList<>(3); //Empty list of StockQuotes. When user changes the first security, call setOnAction -> stocksSelected.set(0,*stockquote object*) and so on
    static List<Portfolio> listOfPortfolios;
    
    @FXML
    private TextField sec1;
    
    @FXML
    void SymbolName(KeyEvent event) {
       checkAndCreatePortfolios();
        if (event.getCode() == KeyCode.ENTER) {
            String stockSymbol = BarSearch.getText().toUpperCase();

            if (!stockSymbol.isEmpty()) {
                StockQuote stockQuote = getStockInfoBySymbol(stockSymbol);

                if (sec1.getText().isEmpty()) {
                    stocksSelected.add(0, stockQuote);
                    sec1.setText(stockQuote.getSymbol());
                    St1.setText(stockQuote.getSymbol());
                }
                else if (sec2.getText().isEmpty() && !stockSymbol.equals(sec1.getText())) {
                    stocksSelected.add(1, stockQuote);
                    sec2.setText(stockQuote.getSymbol());
                    St2.setText(stockQuote.getSymbol());

                } 
                else if (sec3.getText().isEmpty() && !stockSymbol.equals(sec1.getText()) && !stockSymbol.equals(sec2.getText())) {
                    stocksSelected.add(2, stockQuote);
                    sec3.setText(stockQuote.getSymbol());
                    St3.setText(stockQuote.getSymbol());
                }
  // Display the stock information
            if (stockQuote != null) {
                System.out.println("Symbol: " + stockQuote.getSymbol());
                System.out.println("Latest Price: " + stockQuote.getLatestPrice());
                System.out.println("Company Name: " + stockQuote.getFullName());
                System.out.println("Beta: " + stockQuote.getBeta());
            } else {
                System.out.println("Failed to parse stock information.");
            }
        } 
        else {
            System.out.println("Failed to fetch stock information.");
        }       
        }            
    }
    
    private boolean areAllSecuritiesSelected() {
        return stocksSelected.size() == 3 && stocksSelected.get(0) != null && stocksSelected.get(1) != null && stocksSelected.get(2) != null;
    }

    public static void createPortfolios() {
       // Clear everything that's in the list of portfolio options as we're creating new options.
        for (int i = 0; i < 20; i++) {
            listOfPortfolios.add(new Portfolio(new ArrayList<>(stocksSelected), weights.get(i)));
        }
    }
    
    private void checkAndCreatePortfolios() {
        // Check if all three securities are selected
        if (areAllSecuritiesSelected()) {
            createPortfolios();
        }
    }
    
    @FXML
    void UP(ActionEvent event) {
        if (areAllSecuritiesSelected()) {
    // Get the best-fit portfolio
    Portfolio bestFit = bestFitPortfolio(listOfPortfolios);

    // Update the text fields with the best-fit portfolio information
    if (bestFit != null && bestFit.getStocks() != null && bestFit.getStocks().size() >= 3) {
        Txt1.setText(String.valueOf(bestFit.getStocks().get(0).getLatestPrice()));
        Txt2.setText(String.valueOf(bestFit.getStocks().get(1).getLatestPrice()));
        Txt3.setText(String.valueOf(bestFit.getStocks().get(2).getLatestPrice()));
        System.out.println("Portfolio updated successfully!");
    }
}
    }
    
    @FXML
    void generateGraphOnAction(ActionEvent event) {
        checkAndCreatePortfolios();
        // Check if portfolios have been created
        if (listOfPortfolios != null && !listOfPortfolios.isEmpty()) {
            
            // Clear existing data on the chart
            Graph.getData().clear();

            // Create a new series for the chart
            XYChart.Series<Double, Double> series = new XYChart.Series<>();

            // Iterate through the list of portfolios
            for (Portfolio portfolio : listOfPortfolios) {
                // Add a data point using portfolio risk as X and portfolio return as Y
                series.getData().add(new XYChart.Data<>(portfolio.getPortfolioRisk(), portfolio.getPortfolioReturn()));
            }

            // Add the series to the chart
            Graph.getData().add(series);

            // Set the control points for the QuadCurve based on the first and last data points
            if (!series.getData().isEmpty()) {
                double startX = series.getData().get(0).getXValue();
                double startY = series.getData().get(0).getYValue();

                double endX = series.getData().get(series.getData().size() - 1).getXValue();
                double endY = series.getData().get(series.getData().size() - 1).getYValue();

                double controlX = (startX + endX) / 2.0;
                double controlY = Math.min(startY, endY) - 50.0;

                // Set QuadCurve properties
                Curve.setStartX(startX);
                Curve.setStartY(startY);
                Curve.setEndX(endX);
                Curve.setEndY(endY);
                Curve.setControlX(controlX);
                Curve.setControlY(controlY);
            }
        } else {
            System.out.println("No portfolios available to generate the graph.");
        }
    }

    public StockQuote getStockInfoBySymbol(String stockSymbol) {
        // Call your API or data source to get stock information based on the symbol
        // Replace "AAPL" with the stock symbol you want to test

        String stockInfoJson = api.getCurrentStockInfo(stockSymbol);

        // Check if the response is not null
        if (stockInfoJson != null) {
            // Parse the JSON string using the StockDataParser
            return StockDataParser.parseStockQuote(stockInfoJson);
        } else {
            System.out.println("Failed to fetch stock information for symbol: " + stockSymbol);
            return null;
        }
    }
  
    
    @FXML
    private ChoiceBox<String> CBR;
          
    
    @FXML
    private AreaChart<Double, Double> Graph;
    
    
    @FXML
    private AnchorPane rootPane;
    
    
    @FXML
    private QuadCurve Curve;
    
    
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
         listOfPortfolios = new ArrayList<>();

    weights = new ArrayList<>();
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
        
         // Add values to the ChoiceBox
// Add values to the ChoiceBox
    ObservableList<String> riskOptions = FXCollections.observableArrayList("conservative", "aggressive");
        CBR.setItems(riskOptions);

        // Add event handler for ChoiceBox selection
        CBR.setOnAction(event -> userRiskTolerance = CBR.getValue().toLowerCase());
    
}
}

    
    


    
    

