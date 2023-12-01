/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */


/**
 *
 * @author Eliyahu Cano
 */
public class API {

    /**
     * @param args the command line arguments
     */
    //The main wont be used. Its only here so that I can test the classes
    public static void main(String[] args) {
        // Replace "AAPL" with the stock symbol you want to test
        String stockSymbol = "SENS";

        // Create an instance of IEXCloudApi
        IEXCloudApi api = new IEXCloudApi();

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
