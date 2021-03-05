package org.openjfx.Trader_Platform;

import java.time.LocalDate;
import java.util.Random;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.*;

public class Main_Page {
	@FXML private TextField textEnterTicker;
	@FXML private DatePicker calSelectorStart, calSelectorEnd;
	@FXML private Button buttonHistoricData;
	@FXML private LineChart<String, Double> lineChartHistoric;
	
	@FXML
	public void clickButtonHistoricData() {
		//todo No api calls to make as of yet.
		testFunc1();
		testFunc2();
	}
	
	//Purely test function before API implementation
	private void testFunc1() {
		LocalDate startDate = calSelectorStart.getValue();
		LocalDate endDate = calSelectorEnd.getValue();
		String ticker = textEnterTicker.getText().replaceAll("\\s", "");
		
		System.out.println("Ticker: " + ticker + 
				"\nStart Date: " + startDate.toString() +
				"\nEnd Date: " + endDate.toString());
	}
	
	//Purely test function before API implementation
	private void testFunc2() {
		Random r = new Random();
		LocalDate startDate = calSelectorStart.getValue();
		LocalDate endDate = calSelectorEnd.getValue();
				
		lineChartHistoric.getData().clear();
		lineChartHistoric.setAnimated(false);

		
		final Series<String, Double> series = new XYChart.Series<String, Double>();
		
		for(LocalDate i = startDate; i.isBefore(endDate); i = i.plusDays(1)) {
			Double rand = 200 + (250 - 200) * r.nextDouble();
			series.getData().add(new XYChart.Data<String, Double>(i.toString(), rand));
		}
		
		lineChartHistoric.getData().add(series);
		lineChartHistoric.autosize();
	}
	
	
	
}
