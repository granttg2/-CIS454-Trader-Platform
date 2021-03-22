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
import javafx.scene.input.MouseEvent;

public class MainPage {
	@FXML private TextField textEnterTicker;
	@FXML private DatePicker calSelectorStart, calSelectorEnd;
	@FXML private Button buttonHistoricData, buttonRealTimeData;
	@FXML private LineChart<String, Double> lineChartHistoric;
	@FXML private Label labelError, labelPrice, labelRealTime, labelGraphInfo;
	
	/*
	 * Data Viewer Implementation
	 */
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
				graphHistoric(stock);
			}else {
				MarketParser.parseMarketEODTotals(stock);
				graphHistoric(stock);
			}
		}
	}
	
	@FXML
	public void clickButtonRealTimeData() {
		Stock stock = new Stock(textEnterTicker.getText());
		if(stock.getTicker() == "") {
			labelRealTime.setText("Ticker must be entered");
		}else {
			if(App.getTickerList().hasTicker(stock.getTicker())) {
				CoinParser.parseSpotPrice(stock);
				labelPrice.setText("$" + stock.getCurrentPrice().toString());
				labelRealTime.setText("*Most up to date price for CoinBase \n(Refreshes every ~1min)");
			}else {
				MarketParser.parseMarketRealTime(stock);
				labelPrice.setText("$" + stock.getCurrentPrice().toString());
				labelRealTime.setText("*Most recent data from: " + stock.getCurrentTime());
			}
		}
	}
	
	
	//Graphing EOD data in Stock
	private void graphHistoric(Stock stock) {
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
		series.getData().stream().forEach(lineData ->{
			lineData.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
				lineData.getNode().setStyle("-fx-background-color: #00FFFF , white;\n"
	                    + "-fx-background-insets: 0, 2;\n"
	                    + "-fx-background-radius: 5px;\n"
	                    + "-fx-padding: 5px;");
			});
			lineData.getNode().addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
				lineData.getNode().setStyle("-fx-background-color: #f3622d, white;\n"
	                    + "-fx-background-insets: 0, 2;\n"
	                    + "-fx-background-radius: 5px;\n"
	                    + "-fx-padding: 5px;");
			});
			lineData.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
				Double endValue = lineData.getYValue();
				Double startValue = series.getData().get(0).getYValue();
				setGraphLabel(startValue, endValue);
			});
		});
	}
	
	private void setGraphLabel(Double startValue, Double endValue) {
		if(endValue > startValue) {
			Double percentageChange = (double) (Math.round(((endValue - startValue) / startValue) * 10000))/100;
			labelGraphInfo.setText("Up: " + percentageChange.toString()+"%" +" Price: $" + endValue);
			labelGraphInfo.setStyle("-fx-background-color: green;");
		}else if (endValue < startValue){
			Double percentageChange = (double) (Math.round(((startValue - endValue) / startValue) * 10000))/100;
			labelGraphInfo.setText("Down: " + percentageChange.toString() +"%" +" Price: $" + endValue);
			labelGraphInfo.setStyle("-fx-background-color: red;");
		}else {
			labelGraphInfo.setText("-: 0.00%" +" Price: $" + endValue);
			labelGraphInfo.setStyle("-fx-background-color: transparent;");
		}
	}
	
	/*
	 * Simulation Viewer
	 */
	@FXML private TextField textSimTicker, textBudget, textTakeProfit, textShares, 
							textAddPercent, textAdd, textStopLoss, textDynamicStop;
	@FXML private Label labelSimGraph, labelSimError;
	@FXML private DatePicker calSimStart, calSimEnd;
	@FXML private LineChart<String, Double> lineChartSim;
	@FXML private CheckBox checkTakeProfit, checkAdditionalShares, checkStopLoss, checkDynamicStop;
	@FXML private Button buttonRunSimulation;
	

	
	
}
