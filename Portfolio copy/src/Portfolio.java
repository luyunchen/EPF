
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author musta
 */
public class Portfolio { //Multiple portfolio objects will be used. Each portfolio object will contain these relevant information: The 3 stocks in the portfolio, the weight of each, the portfolio return and the portfolio risk
    private List<StockQuote> stocks;
    private List<Double> weights;
    private double portfolioReturn;
    private double portfolioRisk;
    
    public Portfolio(List<StockQuote> stocks, List<Double> weights){
        this.stocks = stocks;
        this.weights = weights;
        
        this.portfolioReturn = calculatePortfolioReturn();
        this.portfolioRisk = calculatePortfolioRisk();
    }
    
    //Getters for all instant variables
    public List<StockQuote> getStocks() {
        return stocks;
    }

    public List<Double> getWeights() {
        return weights;
    }

    public double getPortfolioReturn() {
        return portfolioReturn;
    }

    public double getPortfolioRisk() {
        return portfolioRisk;
    }
    //Setters for all instant variables
    public void setStocks(List<StockQuote> stocks) {
        this.stocks = stocks;
    }

    public void setWeights(List<Double> weights) {
        this.weights = weights;
    }

    public void setPortfolioReturn(double portfolioReturn) {
        this.portfolioReturn = portfolioReturn;
    }

    public void setCalculatePortfolioRisk(double calculatePortfolioRisk) {
        this.portfolioRisk = calculatePortfolioRisk;
    }
    
    

    public double calculatePortfolioReturn() { //Obtaining total return by adding weighted returns
        double portfolioReturn = 0.0;
        for (int i = 0; i < stocks.size(); i++) {
            portfolioReturn += weights.get(i) * stocks.get(i).calculateExpectedSecurityReturn();
        }
        return portfolioReturn;
    }

    public double calculatePortfolioRisk() {
        
        // For simplicity, let's assume a linear relationship between the cofactors of risk. Usually, a covariance matrix is used.
        double portfolioRisk = 0.0; 
        for (int i = 0; i < stocks.size(); i++) {
            portfolioRisk += weights.get(i) * stocks.get(i).getBeta(); 
        }
        return portfolioRisk;   //For each portfolio, we can obtain the total covariance of the portfolio with the market by adding the beta weighted.
    }
    
    
}
