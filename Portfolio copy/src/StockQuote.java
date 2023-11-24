/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author Marce
 */


public class StockQuote implements securityReturn{
    private String symbol;
    private Double latestPrice;
    private String fullName; 
    private Double beta;     
    
    private static Double expectedMarketReturn;
    private static Double riskFreeRate;
    
    
    // Getters and setters
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Double getLatestPrice() {
        return latestPrice;
    }

    public void setLatestPrice(Double latestPrice) {
        this.latestPrice = latestPrice;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Double getBeta() {
        return beta;
    }

    public void setBeta(Double beta) {
        this.beta = beta;
    }
    
    
    @Override
    public double calculateExpectedSecurityReturn(){
        return ((riskFreeRate+beta)*(expectedMarketReturn-riskFreeRate));
    }
    public static void main(String[] args) {
        //Try : Creating a new stockQuote and then returning its securityReturn as a double
    }
}

