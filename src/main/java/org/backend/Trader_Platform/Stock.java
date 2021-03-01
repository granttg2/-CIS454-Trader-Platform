package org.backend.Trader_Platform;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
//Stock Data structure
public class Stock {

	private String ticker, stockName;
	private ArrayList<Pair<LocalDate, Float>> eodTotals;
	private Float currentPrice;
	private LocalDate start, end;
	
	public Stock (String tick, String name) {
		this.ticker = tick;
		this.stockName = name;
	}
	
	public Stock (String tick) {
		this.ticker = tick;
	}
	
	//Date added to the end of the list.
	public void addNewEOD(String date, Float total) {
		Pair<LocalDate, Float> nPair = new Pair<LocalDate, Float>(generateLocalDate(date), total);
		eodTotals.add(nPair);
	}
	
	//Takes string in the yyyy-MM-dd'T'HH:mm:ss.SSSXXX(matches with market stack api
	public LocalDate generateLocalDate(String date) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.ENGLISH);
		LocalDate dateNew = LocalDate.parse(date, format);
		return dateNew;
	}
	
	public void setStartDate(String date){
		start = generateLocalDate(date);
	}
	
	public LocalDate getStartDate() {
		return start;
	}
	
	public void setEndDate(String date){
		end = generateLocalDate(date);
	}
	
	public LocalDate getEndDate() {
		return end;
	}
	
	public ArrayList<Pair<LocalDate, Float>> getEodTotals() {
		return eodTotals;
	}
	public void setEodTotals(ArrayList<Pair<LocalDate, Float>> eodTotals) {
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

	public Float getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(Float currentPrice) {
		this.currentPrice = currentPrice;
	}
	
}
