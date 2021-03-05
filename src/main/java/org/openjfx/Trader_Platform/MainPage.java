package org.openjfx.Trader_Platform;

import java.time.LocalDate;
import java.util.ArrayList;

import org.backend.Trader_Platform.MarketParser;
import org.backend.Trader_Platform.Pair;
import org.backend.Trader_Platform.Stock;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.*;

public class MainPage {
	@FXML private TextField textEnterTicker;
	@FXML private DatePicker calSelectorStart, calSelectorEnd;
	@FXML private Button buttonHistoricData;
	@FXML private LineChart<String, Double> lineChartHistoric;
	@FXML private Label labelError;
	
	@FXML
	public void clickButtonHistoricData() {
		//Getting input data
		Stock stock = new Stock(textEnterTicker.getText());
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
			MarketParser.parseMarketEODTotals(stock);
		}
		//Graphing
		graph(stock);
		
	}
	
	
	//Purely test function before API implementation
	private void graph(Stock stock) {
		ArrayList<Pair<LocalDate, Double>> prices = stock.getEodTotals();
		
		if(prices.size() == 100) {
			labelError.setText("*Warning: API only supports prices from past hundred trading days");
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
