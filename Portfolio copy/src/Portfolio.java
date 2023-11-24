
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author musta
 */
public class Portfolio {
    private List<StockQuote> stocks;
    private List<Double> weights;
    private double portfolioReturn;
    private double calculatePortfolioRisk;
    
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

    public double getCalculatePortfolioRisk() {
        return calculatePortfolioRisk;
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
        this.calculatePortfolioRisk = calculatePortfolioRisk;
    }
    
    

    public double calculatePortfolioReturn() {
        double portfolioReturn = 0.0;
        for (int i = 0; i < stocks.size(); i++) {
            portfolioReturn += weights.get(i) * stocks.get(i).calculateExpectedSecurityReturn();
        }
        return portfolioReturn;
    }

    public double calculatePortfolioRisk() {
        
        // For simplicity, let's assume a linear relationship between the cofactors of risk
        double portfolioRisk = 0.0;
        for (int i = 0; i < stocks.size(); i++) {
            portfolioRisk += weights.get(i) * stocks.get(i).getBeta();
        }
        return portfolioRisk;
    }
    
    public String toString(){
        returnFXMLController.getTotalEquity()
    }
}
