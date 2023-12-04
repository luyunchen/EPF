import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class FXMLControllerTest {

    private FXMLController controller;

    @BeforeEach
    public void setUp() {
        controller = new FXMLController();
    
    }

    @Test
    public void testCalculateStockQuantities() {
        // Set up necessary data for testing
        FXMLController.totalEquity = 100000; // Set some value
        FXMLController.userRiskTolerance = "aggressive"; // Set some value

        // Add stocks to FXMLController.stocksSelected and portfolios to FXMLController.listOfPortfolios
        List<StockQuote> stocks = new ArrayList<>();
        stocks.add(new StockQuote());
        stocks.add(new StockQuote());
        stocks.add(new StockQuote());

        List<Double> weights = List.of(0.4, 0.3, 0.3);
        Portfolio portfolio = new Portfolio(stocks, weights);
        FXMLController.listOfPortfolios.add(portfolio);

        // Call the method to be tested
        List<Integer> result = FXMLController.calculateStockQuantities();

        // Assert the result
        assertNotNull(result);
        assertEquals(3, result.size());
    
    }

    @Test
    public void testSetBestFitPortfolio() {
        // Set up necessary data for testing
        List<StockQuote> stocks = new ArrayList<>();
        stocks.add(new StockQuote());
        stocks.add(new StockQuote());
        stocks.add(new StockQuote());

        List<Double> weightsConservative = List.of(0.4, 0.3, 0.3);
        List<Double> weightsAggressive = List.of(0.6, 0.2, 0.2);

        Portfolio portfolioConservative = new Portfolio(stocks, weightsConservative);
        Portfolio portfolioAggressive = new Portfolio(stocks, weightsAggressive);

        FXMLController.listOfPortfolios.add(portfolioConservative);
        FXMLController.listOfPortfolios.add(portfolioAggressive);

        FXMLController.userRiskTolerance = "conservative"; // Set the risk tolerance

        // Call the method to be tested
        FXMLController.setBestFitPortfolio();

        // Assert the result
        assertNotNull(FXMLController.bestFitPortfolio);
        assertEquals(portfolioConservative, FXMLController.bestFitPortfolio);
   
    }

    @Test
    public void testGenerateGraphOnAction() {
        // Set up necessary data for testing
        List<StockQuote> stocks = new ArrayList<>();
        stocks.add(new StockQuote());
        stocks.add(new StockQuote());
        stocks.add(new StockQuote());

        List<Double> weights = List.of(0.4, 0.3, 0.3);
        Portfolio portfolio = new Portfolio(stocks, weights);
        FXMLController.listOfPortfolios.add(portfolio);

        FXMLController.userRiskTolerance = "conservative"; // Set the risk tolerance

        // Call the method to be tested
        controller.generateGraphOnAction(null);

        // Assert the result
        assertNotNull(controller.Chart.getData());
    
    }

    @Test
    public void testSymbolName() {
        // Set up necessary data for testing
        KeyEvent enterEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.ENTER, false, false, false, false);

        FXMLController.BarSearch.setText("AAPL"); // Set a valid stock symbol

        controller.SymbolName(enterEvent);

        assertNotNull(FXMLController.stocksSelected);
        assertEquals(1, FXMLController.stocksSelected.size());
        assertNotNull(FXMLController.stocksSelected.get(0));
       
    }
}
