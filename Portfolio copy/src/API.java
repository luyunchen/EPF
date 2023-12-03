/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */


/**
 *
 * @author Eliyahu Cano
 */
/**
/**
 *This class is used purely for testing if the api functions properly
 */
public class API {

    public static void main(String[] args) {
        
        String apiKey = "NUNW7MUCXTACICR6";

        // Replace "AAPL" with the stock symbol you want to test
        String stockSymbol = "AAPL";

        // Create an instance of IEXCloudApi (assuming it has been modified for Alpha Vantage)
        AlphaVantageApi api = new AlphaVantageApi(apiKey);

        // Grab stock information as a JSON string
        String stockInfoJson = api.getCurrentStockInfo(stockSymbol);

        // Print the raw JSON response
        System.out.println("Raw JSON Response: " + stockInfoJson);

        // Check if the response is not null
        if (stockInfoJson != null) {
            // Parse the JSON string using the StockDataParser
            StockQuote stockQuote = StockDataParser.parseStockQuote(stockInfoJson);

            // Display the stock information
            if (stockQuote != null) {
                System.out.println("Symbol: " + stockQuote.getSymbol());
                System.out.println("Latest Price: " + stockQuote.getLatestPrice());
                System.out.println("Company Name: " + stockQuote.getFullName());
                System.out.println("Beta: " + stockQuote.getBeta());
            } else {
                System.out.println("Failed to parse stock information.");
            }
        } else {
            System.out.println("Failed to fetch stock information.");
        }
    }
}
