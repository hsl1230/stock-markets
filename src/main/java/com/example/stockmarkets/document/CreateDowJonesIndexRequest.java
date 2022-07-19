package com.example.stockmarkets.document;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

public class CreateDowJonesIndexRequest {
    private int quarter; // the yearly quarter (1 = Jan-Mar; 2 = Apr=Jun).
    private String stock; // the stock symbol (see above)
    @Schema(description = "date", example = "1/30/2020", format = "M/d/yyyy", required = true, nullable = false)
    @JsonFormat(pattern = "M/d/yyyy")
    private LocalDate date; // the last business day of the work (this is typically a Friday)
    private double open; // the price of the stock at the beginning of the week
    private double high; // the highest price of the stock during the week
    private double low; // the lowest price of the stock during the week
    private double close; // the price of the stock at the end of the week
    private long volume; // the number of shares of stock that traded hands in the week
    private Double percentChangePrice; // the percentage change in price throughout the week
    private Double percentChangeVolumeOverLastWeek; // the percentage change in the number of shares of stock that
                                                    // traded hands for this week compared to the previous week
    private Long previousWeeksVolume; // the number of shares of stock that traded hands in the previous week
    private Double nextWeeksOpen; // the opening price of the stock in the following week
    private Double nextWeeksClose; // the closing price of the stock in the following week
    private Double percentChangeNextWeeksPrice; // the percentage change in price of the stock in the following week
                                                // days_to_next_dividend; //the number of days until the next dividend
    private Integer daysToNextDividend;
    private Double percentReturnNextDividend; // the percentage of return on the next dividend

    public int getQuarter() {
        return quarter;
    }

    public void setQuarter(int quarter) {
        this.quarter = quarter;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    public Double getPercentChangePrice() {
        return percentChangePrice;
    }

    public void setPercentChangePrice(Double percentChangePrice) {
        this.percentChangePrice = percentChangePrice;
    }

    public Double getPercentChangeVolumeOverLastWeek() {
        return percentChangeVolumeOverLastWeek;
    }

    public void setPercentChangeVolumeOverLastWeek(Double percentChangeVolumeOverLastWeek) {
        this.percentChangeVolumeOverLastWeek = percentChangeVolumeOverLastWeek;
    }

    public Long getPreviousWeeksVolume() {
        return previousWeeksVolume;
    }

    public void setPreviousWeeksVolume(Long previousWeeksVolume) {
        this.previousWeeksVolume = previousWeeksVolume;
    }

    public Double getNextWeeksOpen() {
        return nextWeeksOpen;
    }

    public void setNextWeeksOpen(Double nextWeeksOpen) {
        this.nextWeeksOpen = nextWeeksOpen;
    }

    public Double getNextWeeksClose() {
        return nextWeeksClose;
    }

    public void setNextWeeksClose(Double nextWeeksClose) {
        this.nextWeeksClose = nextWeeksClose;
    }

    public Double getPercentChangeNextWeeksPrice() {
        return percentChangeNextWeeksPrice;
    }

    public void setPercentChangeNextWeeksPrice(Double percentChangeNextWeeksPrice) {
        this.percentChangeNextWeeksPrice = percentChangeNextWeeksPrice;
    }

    public Double getPercentReturnNextDividend() {
        return percentReturnNextDividend;
    }

    public void setPercentReturnNextDividend(Double percentReturnNextDividend) {
        this.percentReturnNextDividend = percentReturnNextDividend;
    }

    public Integer getDaysToNextDividend() {
        return daysToNextDividend;
    }

    public void setDaysToNextDividend(Integer daysToNextDividend) {
        this.daysToNextDividend = daysToNextDividend;
    }
}
