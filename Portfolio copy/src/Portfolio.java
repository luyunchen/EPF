
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
    
    public Portfolio(){} //Default constructor for Portfolio class
    
    public Portfolio(List<StockQuote> stocks, List<Double> weights){ //Constructor for Portfolio class. 
        this.stocks = stocks;
        this.weights = weights;
        
        this.portfolioReturn = calculatePortfolioReturn(); //With the stocks and weights, we can call methods that will give us both return and risk.
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
        return ((portfolioReturn)*100);
    }

    public double calculatePortfolioRisk() {
        int numStocks = weights.size();
        double[][] covarianceMatrix = calculateCovarianceMatrix();

        // Using the matrix to calculate portfolio variance
        double portfolioVariance = 0.0;
        for (int i = 0; i < numStocks; i++) {
            for (int j = 0; j < numStocks; j++) {
                portfolioVariance += weights.get(i) * weights.get(j) * covarianceMatrix[i][j];
            }
        }

        // Calculate portfolio risk (standard deviation)
        return Math.sqrt((portfolioVariance-1.00)*100);
    }

    private double[][] calculateCovarianceMatrix() { //Create a matrix/array of size 3x3
        int numStocks = stocks.size();
        double[][] covarianceMatrix = new double[numStocks][numStocks];

        for (int i = 0; i < numStocks; i++) {
            for (int j = 0; j < numStocks; j++) {
                double covariance = calculateCovariance(stocks.get(i).getBeta(), stocks.get(j).getBeta());
                covarianceMatrix[i][j] = covariance;
            }
        }

        return covarianceMatrix;
    }

    private double calculateCovariance(double beta1, double beta2) {
        //Assuming that beta is sole indicator of covariance
        return beta1 * beta2;
    }
    
    
}

