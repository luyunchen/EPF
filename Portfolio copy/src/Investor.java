/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author musta
 */
public class Investor {
    private String name;
    private double equity;
    private String riskTolerance; //riskTolerance can be conservative or aggressive
    
    public Investor(){};
    
    public Investor(String name, double equity, String riskTolerance){
        this.name = name;
        this.equity = equity;
        this.riskTolerance = riskTolerance;
    }

    public String getName() {
        return name;
    }

    public double getEquity() {
        return equity;
    }

    public String getRiskTolerance() {
        return riskTolerance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEquity(double equity) {
        this.equity = equity;
    }

    public void setRiskTolerance(String riskTolerance) {
        this.riskTolerance = riskTolerance;
    }
    
    
}
