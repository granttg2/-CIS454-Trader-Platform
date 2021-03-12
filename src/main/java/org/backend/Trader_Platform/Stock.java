package org.backend.Trader_Platform;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
//Stock Data structure
public class Stock {

	private String ticker, stockName, currentTime;
	private ArrayList<Pair<LocalDate, Double>> eodTotals;
	private Double currentPrice;
	private LocalDate start, end;
	public Stock (String tick) {
		this.ticker = tick;
		eodTotals = new ArrayList<Pair<LocalDate, Double>>();
	}
	
	//Date added to the end of the list.
	public void addNewEOD(String date, Double total) {
		eodTotals.add(new Pair<LocalDate, Double>(generateLocalDate(date), total));
	}
	
	//Takes string in the yyyy-MM-dd'T'HH:mm:ss.SSSXXX(matches with market stack api)
	public static LocalDate generateLocalDate(String date) {
		if(date.contains("T")) {
			date = date.substring(0, date.indexOf('T'));
		}
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
		LocalDate dateNew = LocalDate.parse(date, format);
		return dateNew;
	}
	
	public void setStartDate(LocalDate date){
		this.start = date;
	}
	
	public LocalDate getStartDate() {
		return start;
	}
	
	public void setEndDate(LocalDate date){
		this.end = date;
	}
	
	public LocalDate getEndDate() {
		return end;
	}
	
	public ArrayList<Pair<LocalDate, Double>> getEodTotals() {
		return eodTotals;
	}
	public void setEodTotals(ArrayList<Pair<LocalDate, Double>> eodTotals) {
		this.eodTotals = eodTotals;
	}
	public String getTicker() {
		return ticker;
	}
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	public String getStockName() {
		return stockName;
	}
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public Double getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(double d) {
		this.currentPrice = d;
	}

	public String getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(String currentTime) {
		this.currentTime = currentTime;
	}
	
}
