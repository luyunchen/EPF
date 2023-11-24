
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author musta
 */
public class EfficientFrontierGenerator {
     private static List<Portfolio> generatePortfolios(StockQuote stock1, StockQuote stock2, StockQuote stock3) {
        List<Portfolio> portfolios = new ArrayList<>();

        // Generate various portfolios with different weights
        for (double weight1 = 0.0; weight1 <= 1.0; weight1 += 0.1) {
            for (double weight2 = 0.0; weight2 <= 1.0 - weight1; weight2 += 0.1) {
                double weight3 = 1.0 - weight1 - weight2;

                Portfolio portfolio = new Portfolio();
                portfolio.setStocks(Arrays.asList(stock1, stock2, stock3));
                portfolio.setWeights(Arrays.asList(weight1, weight2, weight3));

                portfolios.add(portfolio);
            }
        }

        return portfolios;
    }

    public static void main(String[] args) {
        // Create StockQuote objects (replace this with your actual data)
        StockQuote stock1 = new StockQuote();
        StockQuote stock2 = new StockQuote();
        StockQuote stock3 = new StockQuote();

        // Create portfolios with different combinations of weights
        List<Portfolio> portfolios = generatePortfolios(stock1, stock2, stock3);

        // Find the portfolio with the maximum return
        Portfolio maxReturnPortfolio = findMaxReturnPortfolio(portfolios);

        // Find the portfolio with the minimum risk
        Portfolio minRiskPortfolio = findMinRiskPortfolio(portfolios);

        // Generate 5 data points between the max return and min risk portfolios
        List<Portfolio> interpolatedPortfolios = interpolatePortfolios(maxReturnPortfolio, minRiskPortfolio, 5);

        // Now, you can use the interpolatedPortfolios list to plot the efficient frontier
        // (X-axis: Risk, Y-axis: Return)
        for (Portfolio portfolio : interpolatedPortfolios) {
            double risk = portfolio.calculatePortfolioRisk(/* your covariance matrix */);
            double returnVal = portfolio.calculatePortfolioReturn();
            // Plot the data points on your graph or perform further analysis
            System.out.println("Risk: " + risk + ", Return: " + returnVal);
        }
    }

    private static Portfolio findMaxReturnPortfolio(List<Portfolio> portfolios) {
        return portfolios.stream()
                .max(Comparator.comparing(Portfolio::calculatePortfolioReturn))
                .orElseThrow(() -> new RuntimeException("No portfolios found"));
    }

    private static Portfolio findMinRiskPortfolio(List<Portfolio> portfolios) {
        return portfolios.stream()
                .min(Comparator.comparing(Portfolio::calculatePortfolioRisk))
                .orElseThrow(() -> new RuntimeException("No portfolios found"));
    }

    private static List<Portfolio> interpolatePortfolios(Portfolio startPortfolio, Portfolio endPortfolio, int numPoints) {
        List<Portfolio> interpolatedPortfolios = new ArrayList<>();

        for (int i = 0; i <= numPoints; i++) {
            double weightRatio = (double) i / numPoints;
            List<Double> interpolatedWeights = interpolateWeights(startPortfolio.getWeights(), endPortfolio.getWeights(), weightRatio);

            Portfolio interpolatedPortfolio = new Portfolio();
            interpolatedPortfolio.setStocks(startPortfolio.getStocks());
            interpolatedPortfolio.setWeights(interpolatedWeights);

            interpolatedPortfolios.add(interpolatedPortfolio);
        }

        return interpolatedPortfolios;
    }

    private static List<Double> interpolateWeights(List<Double> startWeights, List<Double> endWeights, double ratio) {
        List<Double> interpolatedWeights = new ArrayList<>();

        for (int i = 0; i < startWeights.size(); i++) {
            double interpolatedWeight = startWeights.get(i) + ratio * (endWeights.get(i) - startWeights.get(i));
            interpolatedWeights.add(interpolatedWeight);
        }

        return interpolatedWeights;
    }

    // ... (other methods)
}