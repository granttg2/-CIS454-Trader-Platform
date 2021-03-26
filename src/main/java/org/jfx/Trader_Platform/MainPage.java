package org.jfx.Trader_Platform;

import java.time.LocalDate;
import java.util.ArrayList;

import org.backend.Trader_Platform.CoinParser;
import org.backend.Trader_Platform.MarketParser;
import org.backend.Trader_Platform.Pair;
import org.backend.Trader_Platform.Simulator;
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
		}else if (stock.getEndDate().isAfter(LocalDate.now().minusDays(1))) {
			labelError.setText("Dates must be before " + LocalDate.now());
		}else if(stock.getEndDate().equals(null) || stock.getStartDate().equals(null)){
			labelError.setText("Dates must be entered");
		}else if(stock.getTicker() == ""){
			labelError.setText("Ticker must be entered");
		}else{
			if(coinTickers.hasTicker(stock.getTicker())) {
				CoinParser.parseCoinEODTotals(stock);
				graph(stock.getEodTotals(), lineChartHistoric, labelError, labelGraphInfo);
			}else {
				MarketParser.parseMarketEODTotals(stock);
				graph(stock.getEodTotals(), lineChartHistoric, labelError, labelGraphInfo);
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
	
	/*
	 * Simulation Viewer
	 */
	@FXML private TextField textSimTicker, textBudget, textTakeProfit, textShares, 
							textAddPercent, textAdd, textStopLoss, textDynamicStop;
	@FXML private Label labelSimGraph, labelSimError, labelStartPrice, labelStartValue, labelEndValue;
	@FXML private DatePicker calSimStart, calSimEnd;
	@FXML private LineChart<String, Double> lineChartSim;
	@FXML private CheckBox checkTakeProfit, checkAdditionalShares, checkStopLoss, checkDynamicStop;
	@FXML private Button buttonRunSimulation;
	
	
	//Gathers parameters, runs simulation, calls function to display results
	@FXML
	private void clickButtonRunSimulation() {
		//Declaring data fields
		Stock stock;
		TickerList coinTickers;
		Simulator sim;
		Double budget = 0.0;
		int initBuy = 0;
		
		labelSimError.setText("");
		labelStartValue.setText("$000000");
		labelEndValue.setText("$000000");
		lineChartSim.getData().clear();;
		
		//Getting input data
		stock = new Stock(textSimTicker.getText());
		coinTickers = App.getTickerList();
		try {
			stock.setStartDate(calSimStart.getValue());
			stock.setEndDate(calSimEnd.getValue());
		}catch(Exception e) {
			System.out.println("Null calendar values");
		}
		
		//Error checking and calling parser
		if(stock.getStartDate().isAfter(stock.getEndDate())) {
			labelSimError.setText("Date Error");
		}else if (stock.getEndDate().isAfter(LocalDate.now().minusDays(1))) {
			labelSimError.setText("Dates must be before " + LocalDate.now());
		}else if(stock.getEndDate().equals(null) || stock.getStartDate().equals(null)){
			labelSimError.setText("Dates must be entered");
		}else if(stock.getTicker() == ""){
			labelSimError.setText("Ticker must be entered");
		}else{
			if(coinTickers.hasTicker(stock.getTicker())) {
				CoinParser.parseCoinEODTotals(stock);
			}else {
				MarketParser.parseMarketEODTotals(stock);
			}
		}
		
		if(textBudget.getText().isBlank()|| textShares.getText().isBlank()) {
			labelSimError.setText("Must enter both Budget, and Initial Shares to buy");
			return;
		}else {
			budget = Double.parseDouble(textBudget.getText());
			initBuy = Integer.parseInt(textShares.getText());
		}
		
		try {
			sim = new Simulator(stock, budget, initBuy);
		}catch(Exception e) {
			System.out.println("Not a number");
			return;
		}
		
		
		
		if(checkStopLoss.isSelected()) {
			sim.setFlag(0, Double.parseDouble(textStopLoss.getText()));
		}
		
		if(checkTakeProfit.isSelected()) {
			sim.setFlag(1, Double.parseDouble(textTakeProfit.getText()));
		}
		
		if(checkDynamicStop.isSelected()) {
			sim.setFlag(2, Double.parseDouble(textDynamicStop.getText()));
		}
		
		if(checkAdditionalShares.isSelected()) {
			sim.setFlag(3, Double.parseDouble(textAddPercent.getText()), Double.parseDouble(textAdd.getText()));
		}
		
		sim.simulate(); //Runs the simulation, populating needed data.
		
		graph(sim.getUserPortfolio(), lineChartSim, labelSimError, labelSimGraph);
		
		labelStartValue.setText("$" + sim.getStartingMoney());
		labelEndValue.setText("$" + sim.getEndingMoney());
		
	}
	
	//Showing most recent price for stock on given start date
	@FXML
	private void startingDateUpdate() {
		Stock stock = new Stock(textSimTicker.getText());
		TickerList coinTickers = App.getTickerList();
		try {
			stock.setStartDate(calSimStart.getValue());
			stock.setEndDate(calSimEnd.getValue());
		}catch(Exception e) {
			System.out.println("Null calendar values");
		}
		
		if (stock.getEndDate().isAfter(LocalDate.now().minusDays(1))) {
			labelSimError.setText("Dates must be before " + LocalDate.now());
		}else if(coinTickers.hasTicker(stock.getTicker())) {
			CoinParser.parseCoinEODTotals(stock);
			labelStartPrice.setText("APPROX Start Price: " + stock.getEodTotals().get(0).getSecond().toString());
		}else {
			MarketParser.parseMarketEODTotals(stock);
			labelStartPrice.setText("APPROX Start Price: " + stock.getEodTotals().get(0).getSecond().toString());
		}
	}
	
/*
 * Generic Helper functions
 */
	//Graphing EOD data in Stock
	private void graph(ArrayList<Pair<LocalDate, Double>> prices, LineChart<String, Double> graph, Label error, Label info) {
		
		LocalDate dateEstimate = LocalDate.now().minusDays(100);
		
		if(prices.size() == 100 || prices.get(0).getFirst().isBefore(dateEstimate)) {
			error.setText("*Warning: API only supports variable historic data");
		}else {
			error.setText("*Some API plans do not support data up until previous day");
		}
		
		graph.getData().clear();
		graph.setAnimated(false);
		
		final Series<String, Double> series = new XYChart.Series<String, Double>();
		
		for(Pair<LocalDate, Double> i:prices) {
			series.getData().add(new XYChart.Data<String, Double>(i.getFirst().toString(), i.getSecond()));
		}
		
		graph.getData().add(series);
		graph.autosize();
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
				setGraphLabel(info, startValue, endValue, lineData.getXValue());
			});
		});
	}
	
	private void setGraphLabel(Label info, Double startValue, Double endValue, String date) {
		if(endValue > startValue) {
			Double percentageChange = (double) (Math.round(((endValue - startValue) / startValue) * 10000))/100;
			info.setText("Up: " + percentageChange.toString()+"%" +"\nPrice: $" + endValue + "\nOn: " + date);
			info.setStyle("-fx-background-color: green;");
		}else if (endValue < startValue){
			Double percentageChange = (double) (Math.round(((startValue - endValue) / startValue) * 10000))/100;
			info.setText("Down: " + percentageChange.toString() +"%" +"\nPrice: $" + endValue + "\nOn: " + date);
			info.setStyle("-fx-background-color: red;");
		}else {
			info.setText("-: 0.00%" +" Price: $" + endValue + "\nOn: " + date);
			info.setStyle("-fx-background-color: transparent;");
		}
	}
	
	
	
	
	
}
