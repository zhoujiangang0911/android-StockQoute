package com.cn.StockQuote;

/**
 * Created by 建刚 on 2014/10/28.
 */
public class StockInfo {
    private String daysLow="";
    private String daysHigh="";
    private String yearLow="";
    private String yearHigh="";
    private String name="";
    private String lastTradePriceonly="";
    private String change="";
    private String dayRange;


    public StockInfo( String daysLow,  String daysHigh,  String yearLow,  String yearHigh,  String name,  String lastTradePriceonly,  String change,  String dayRange) {
        this.daysLow = daysLow;
        this.daysHigh = daysHigh;
        this.yearLow = yearLow;
        this.yearHigh = yearHigh;
        this.name = name;
        this.lastTradePriceonly = lastTradePriceonly;
        this.change = change;
        this.dayRange = dayRange;
    }

    public String getDaysLow() {
        return daysLow;
    }

    public void setDaysLow(final String daysLow) {
        this.daysLow = daysLow;
    }

    public String getDaysHigh() {
        return daysHigh;
    }

    public void setDaysHigh(final String daysHigh) {
        this.daysHigh = daysHigh;
    }

    public String getYearLow() {
        return yearLow;
    }

    public void setYearLow(final String yearLow) {
        this.yearLow = yearLow;
    }

    public String getYearHigh() {
        return yearHigh;
    }

    public void setYearHigh(final String yearHigh) {
        this.yearHigh = yearHigh;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getLastTradePriceonly() {
        return lastTradePriceonly;
    }

    public void setLastTradePriceonly(final String lastTradePriceonly) {
        this.lastTradePriceonly = lastTradePriceonly;
    }

    public String getChange() {
        return change;
    }

    public void setChange(final String change) {
        this.change = change;
    }

    public String getDayRange() {
        return dayRange;
    }

    public void setDayRange(final String dayRange) {
        this.dayRange = dayRange;
    }
}
