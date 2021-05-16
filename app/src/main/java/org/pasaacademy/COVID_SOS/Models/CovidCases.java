package org.pasaacademy.COVID_SOS.Models;

/**
 * Model class for the covid cases database
 */
public class CovidCases {
    String activeCases, lastUpdated, recoveredCases, totalCases;

    public CovidCases(){

    }

    public CovidCases(String activeCases, String lastUpdated, String recoveredCases, String totalCases) {
        this.activeCases = activeCases;
        this.lastUpdated = lastUpdated;
        this.recoveredCases = recoveredCases;
        this.totalCases = totalCases;
    }

    public String getActiveCases() {
        return activeCases;
    }

    public void setActiveCases(String activeCases) {
        this.activeCases = activeCases;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getRecoveredCases() {
        return recoveredCases;
    }

    public void setRecoveredCases(String recoveredCases) {
        this.recoveredCases = recoveredCases;
    }

    public String getTotalCases() {
        return totalCases;
    }

    public void setTotalCases(String totalCases) {
        this.totalCases = totalCases;
    }
}
