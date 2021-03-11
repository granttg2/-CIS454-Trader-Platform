package org.backend.Trader_Platform;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.util.Scanner;

public class APICall{
	/*
	 * Market Stack methods
	 */
	//Gets historical data for market stack api with given parameters
	public static String getHistoricData(String key, String ticker, String startDay, String endDay) throws IOException {
		String url = "http://api.marketstack.com/v1/eod?access_key=" + key + "&symbols=" + ticker + "&date_from=" + startDay + "&date_to=" + endDay;
		return getURLResult(url);
	}
	
	public static String getRealTimeData(String key, String ticker) throws IOException {
		String url = "http://api.marketstack.com/v1/intraday/latest?access_key=" + key + "&symbols=" + ticker ;
		return getURLResult(url);
	}
	/*
	 * Coinbase methods
	 */
	//Gets historical Data for Coin with Parameters Ticker, startDay, endDay. *1 Day Granularity = 86400*
	public static String getCoinHistory(String ticker, String startDay, String endDay)throws IOException{
		String url = "https://api.pro.coinbase.com/products/" 
					  + ticker + "-USD" + "/candles" + "?dateFrom=" + startDay + "&date_to=" + endDay + "&granularity=86400";
		return getURLResult(url);
	}
	
	/*
	 * Utility methods
	 */
	private static String getURLResult(String url) throws IOException {
		try(Scanner scanner = new Scanner(new URL(url).openStream(), StandardCharsets.UTF_8.toString())){
			scanner.useDelimiter("\\A");
			return scanner.hasNext() ? scanner.next() : "";
		}
	}
	
	
}
      
   