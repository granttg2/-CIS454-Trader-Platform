package org.jfx.Trader_Platform;

import java.time.LocalDate;
import java.util.ArrayList;

import org.backend.Trader_Platform.CoinParser;
import org.backend.Trader_Platform.MarketParser;
import org.backend.Trader_Platform.Pair;
import org.backend.Trader_Platform.Stock;
import org.backend.Trader_Platform.TickerList;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.*;

public class MainPage {
	@FXML private TextField textEnterTicker;
	@FXML private DatePicker calSelectorStart, calSelectorEnd;
	@FXML private Button buttonHistoricData, buttonRealTimeData;
	@FXML private LineChart<String, Double> lineChartHistoric;
	@FXML private Label labelError, labelPrice, labelRealTime;
	
	@FXML
	public void clickButtonHistoricData() {
		//Getting input data
		Stock stock = new Stock(textEnterTicker.getText());
		TickerList coinTickers = App.getTickerList();
		try {
			stock.setStartDate(calSelectorStart.getValue());
			stock.setEndDate(calSelectorEnd.getValue());
		}catch(Exception e) {
			System.out.println("Null calendar values");
		}
		
		//Error checking and calling parser
		if(stock.getStartDate().isAfter(stock.getEndDate())) {
			labelError.setText("Date Error");
		}else if (stock.getEndDate().isAfter(LocalDate.now())) {
			labelError.setText("Dates must be before " + LocalDate.now());
		}else if(stock.getEndDate().equals(null) || stock.getStartDate().equals(null)){
			labelError.setText("Dates must be entered");
		}else if(stock.getTicker() == ""){
			labelError.setText("Ticker must be entered");
		}else{
			if(coinTickers.hasTicker(stock.getTicker())) {
				CoinParser.parseCoinEODTotals(stock);
				graph(stock);
			}else {
				MarketParser.parseMarketEODTotals(stock);
				graph(stock);
			}
		}
	}
	
	@FXML
	public void clickButtonRealTimeData() {
		Stock stock = new Stock(textEnterTicker.getText());
		if(stock.getTicker() == "") {
			labelRealTime.setText("Ticker must be entered");
		}else {
			MarketParser.parseMarketRealTime(stock);
			labelPrice.setText("$" + stock.getCurrentPrice().toString());
			labelRealTime.setText("*Most recent data from: " + stock.getCurrentTime());
		}
	}
	
	
	//Graphing EOD data in Stock
	private void graph(Stock stock) {
		ArrayList<Pair<LocalDate, Double>> prices = stock.getEodTotals();
		
		LocalDate dateEstimate = LocalDate.now().minusDays(100);
		
		if(prices.size() == 100 || stock.getStartDate().isBefore(dateEstimate)) {
			labelError.setText("*Warning: API only supports variable historic data");
		}else {
			labelError.setText("Some API plans do not support data up until previous day");
		}
		
		lineChartHistoric.getData().clear();
		lineChartHistoric.setAnimated(false);
		
		final Series<String, Double> series = new XYChart.Series<String, Double>();
		
		for(Pair<LocalDate, Double> i:prices) {
			series.getData().add(new XYChart.Data<String, Double>(i.getFirst().toString(), i.getSecond()));
		}
		
		lineChartHistoric.getData().add(series);
		lineChartHistoric.autosize();
	}
	
	
	
}
