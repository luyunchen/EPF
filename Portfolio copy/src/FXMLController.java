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
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
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
    static List<List<Double>> weights = new ArrayList<>(50); //These are some random weights of the stocks for each portfolio. There's gonna be 20 portfolio, meaning 20 data points. 
    static List<StockQuote> stocksSelected = new ArrayList<>(3); //Empty list of StockQuotes. When user changes the first security, call setOnAction -> stocksSelected.set(0,*stockquote object*) and so on
    static List<Portfolio> listOfPortfolios = new ArrayList<>(50);
    static Portfolio bestFitPortfolio;
    
    static List<Integer> calculateStockQuantities(){
        setBestFitPortfolio();
        List<Integer> stockQuantities = new ArrayList<>();
        if (bestFitPortfolio != null && !bestFitPortfolio.getStocks().isEmpty() && !bestFitPortfolio.getWeights().isEmpty()) {
            
            for (int i = 0; i < bestFitPortfolio.getStocks().size(); i++) {
                double stockWeight = bestFitPortfolio.getWeights().get(i);
                double stockValue = bestFitPortfolio.getStocks().get(i).getLatestPrice();
                int stockQuantity = (int) ((totalEquity * stockWeight) / stockValue);
                stockQuantities.add(stockQuantity);
            }
        } else {
            // Handle the case where the list of stocks or weights is empty
            System.out.println("List of stocks or weights is empty");
        }
            return stockQuantities;
            }
    
    static void setBestFitPortfolio(){
        switch(userRiskTolerance){
        
            case "conservative": {
                
                    Portfolio smallestRiskPortfolio = listOfPortfolios.get(0);

                    // Iterate through the rest of the portfolios
                    for (int i = 1; i < listOfPortfolios.size(); i++) {
                        Portfolio currentPortfolio = listOfPortfolios.get(i);

                        // Compare the risk of the current portfolio with the smallestRiskPortfolio
                        if (currentPortfolio.getPortfolioRisk() < smallestRiskPortfolio.getPortfolioRisk()) {
                            smallestRiskPortfolio = currentPortfolio; // Update if the current portfolio has smaller risk
                        }
                    }

                    bestFitPortfolio = smallestRiskPortfolio;
                    break;
            }
            case "aggressive": {
                Portfolio highestReturnsPortfolio = listOfPortfolios.get(0);
                
                for (int i = 1; i < listOfPortfolios.size(); i++) {
                    Portfolio currentPortfolio = listOfPortfolios.get(i);

                    // Compare returns of the current portfolio with the highestReturnsPortfolio
                    if (currentPortfolio.getPortfolioReturn() > highestReturnsPortfolio.getPortfolioReturn()) {
                        highestReturnsPortfolio = currentPortfolio; // Update if the current portfolio has higher returns
                    }
                }

                bestFitPortfolio = highestReturnsPortfolio;
                break;
            }
            
        }
    }
    
    
    
    @FXML
    private TextField sec1;
    
@FXML
void SymbolName(KeyEvent event) {
    checkAndCreatePortfolios();
    
    if (event.getCode() == KeyCode.ENTER) {
        String stockSymbol = BarSearch.getText().toUpperCase();

        if (!stockSymbol.isEmpty()) {
            StockQuote stockQuote = getStockInfoBySymbol(stockSymbol);
            if (stockQuote != null) {
                // Check if Beta is null
                if (stockQuote.getBeta() == null) {
                    showWarningAlert("Beta is null", "Please try another stock.");
                    return; // Exit the method to prevent further processing
                }

                // Check if the new stock symbol is the same as any of the existing symbols
                if (stockSymbol.equals(sec1.getText()) || stockSymbol.equals(sec2.getText()) || stockSymbol.equals(sec3.getText())) {
                    showWarningAlert("Duplicate Stock Symbol", "Stock symbol already exists in the list.");
                    return; // Exit the method to prevent further processing
                }

                // Update sec1, sec2, sec3 based on availability
                if (sec1.getText().isEmpty()) {
                    stocksSelected.add(stockQuote);
                    sec1.setText(stockQuote.getSymbol());
                    St1.setText(stockQuote.getSymbol());
                } else if (sec2.getText().isEmpty() && !stockSymbol.equals(sec1.getText())) {
                    stocksSelected.add(1, stockQuote);
                    sec2.setText(stockQuote.getSymbol());
                    St2.setText(stockQuote.getSymbol());
                } else if (sec3.getText().isEmpty() && !stockSymbol.equals(sec1.getText())
                        && !stockSymbol.equals(sec2.getText())) {
                    stocksSelected.add(0, stockQuote);
                    sec3.setText(stockQuote.getSymbol());
                    St3.setText(stockQuote.getSymbol());
                } 
                if (stockQuote != null) {
                displayStockInformation(stockQuote);   
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Stock Added Succesfully");
                alert.setContentText(stockQuote.getFullName() +" was added sucessfully.");
                alert.show();

                
            
            } 

             
            } else {
                showWarningAlert("Invalid Stock Symbol", "Please enter a valid stock symbol.");
            }
        } else {
            System.out.println("Failed to fetch stock information.");
        }
    }
}

// Helper method to display stock information
private void displayStockInformation(StockQuote stockQuote) {
    System.out.println("Symbol: " + stockQuote.getSymbol());
    System.out.println("Latest Price: " + stockQuote.getLatestPrice());
    System.out.println("Company Name: " + stockQuote.getFullName());
    System.out.println("Beta: " + stockQuote.getBeta());
}

// Helper method to show a warning alert
private void showWarningAlert(String title, String content) {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(content);
    alert.showAndWait();
}
@FXML
void RST(ActionEvent event){
    // Clear securities
    sec1.clear();
    sec2.clear();
    sec3.clear();

    // Clear labels and text fields
    St1.setText("");
    St2.setText("");
    St3.setText("");
    Txt1.clear();
    Txt2.clear();
    Txt3.clear();

    // Clear other fields
    Asset.clear();
    TextFieldAge.clear();
    TextFieldName.clear();
    BarSearch.clear();


    // Clear the list of selected stocks
    stocksSelected.clear();

    // Clear the list of portfolios
    listOfPortfolios.clear();

    // Clear the best fit portfolio
    bestFitPortfolio = null;

    // Clear the chart data
    Chart.getData().clear();
    
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
       createPortfolios();
       setBestFitPortfolio();
       

    // Update the text fields with the best-fit portfolio information
    if (bestFitPortfolio != null && bestFitPortfolio.getStocks() != null && bestFitPortfolio.getStocks().size() >= 3) {
        Txt1.setText(String.valueOf(calculateStockQuantities().get(0)));
        Txt2.setText(String.valueOf(calculateStockQuantities().get(1)));
        Txt3.setText(String.valueOf(calculateStockQuantities().get(2)));
        
        System.out.println("Portfolio updated successfully!");
    }
}
    }
    
@FXML
void generateGraphOnAction(ActionEvent event) {
    checkAndCreatePortfolios();

    // Clear existing data on the chart
    Chart.getData().clear();

    // Create a single series for all data points
    XYChart.Series<String, Double> series = new XYChart.Series<>();

    // Add data to the series
    for (int i = 0; i < listOfPortfolios.size(); i++) {
        Portfolio portfolio = listOfPortfolios.get(i);
        double risk = portfolio.getPortfolioRisk();
        double returns = portfolio.getPortfolioReturn();

        System.out.println("Adding data to chart - Risk: " + risk + ", Returns: " + returns);

        // Add data point to the series
        XYChart.Data<String, Double> dataPoint = new XYChart.Data<>(String.valueOf(i), returns);

        series.getData().add(dataPoint);
        

    }

    // Add the series to the chart
    Chart.getData().add(series);
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
    void ChangeAsset(KeyEvent event){
    
        totalEquity = Double.parseDouble(Asset.getText());
        
        
    
    }
    @FXML
    void Clear(ActionEvent event){
    sec1.clear();
    
    }
    @FXML
    void Clear2(ActionEvent event){
    sec2.clear();
    }
    @FXML
    void Clear3(ActionEvent event){
    sec3.clear();
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
    private TextField Asset;


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
    
    @FXML
    private LineChart<String,Double>Chart;
    
    @FXML
    private CategoryAxis Xaxis;
    
    @FXML
    private NumberAxis Yaxis;
    
    @FXML
    private Button Restart;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         

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
        weights.add(List.of(0.12, 0.65, 0.23));
        weights.add(List.of(0.28, 0.08, 0.64));
        weights.add(List.of(0.42, 0.17, 0.41));
        weights.add(List.of(0.33, 0.45, 0.22));
        weights.add(List.of(0.18, 0.39, 0.43));
        weights.add(List.of(0.49, 0.12, 0.39));
        weights.add(List.of(0.31, 0.47, 0.22));
        weights.add(List.of(0.26, 0.36, 0.38));
        weights.add(List.of(0.13, 0.34, 0.53));
        weights.add(List.of(0.38, 0.21, 0.41));
        weights.add(List.of(0.23, 0.28, 0.49));
        weights.add(List.of(0.34, 0.19, 0.47));
        weights.add(List.of(0.27, 0.38, 0.35));
        weights.add(List.of(0.16, 0.31, 0.53));
        weights.add(List.of(0.46, 0.08, 0.46));
        weights.add(List.of(0.28, 0.41, 0.31));
        weights.add(List.of(0.36, 0.09, 0.55));
        weights.add(List.of(0.14, 0.45, 0.41));
        weights.add(List.of(0.25, 0.58, 0.17));
        weights.add(List.of(0.32, 0.28, 0.40));
        weights.add(List.of(0.21, 0.14, 0.65));
        weights.add(List.of(0.37, 0.26, 0.37));
        weights.add(List.of(0.29, 0.52, 0.19));
        weights.add(List.of(0.43, 0.10, 0.47));
        weights.add(List.of(0.22, 0.43, 0.35));
        weights.add(List.of(0.18, 0.61, 0.21));
        weights.add(List.of(0.35, 0.33, 0.32));
        weights.add(List.of(0.24, 0.17, 0.59));
        weights.add(List.of(0.30, 0.25, 0.45));
        
         // Add values to the ChoiceBox
// Add values to the ChoiceBox
    ObservableList<String> riskOptions = FXCollections.observableArrayList("conservative", "aggressive");
        CBR.setItems(riskOptions);

        // Add event handler for ChoiceBox selection
        CBR.setOnAction(event -> userRiskTolerance = CBR.getValue());


        
}
}

    
    


    
    

