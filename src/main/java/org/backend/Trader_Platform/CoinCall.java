package org.backend.Trader_Platform;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.util.Scanner;

public class CoinCall {
	
	//Gets historical Data for Coin with Parameters Ticker, startDay, endDay. *1 Day Granularity = 86400*
	public static String getCoinHistory(String ticker, String startDay, String endDay)throws IOException{
		String url = "https://api.pro.coinbase.com/products/" 
					  + ticker + "-USD" + "/candles" + "?dateFrom=" + startDay + "&date_to=" + endDay + "&granularity=86400";
		return getURLResult(url);
	}
		
	private static String getURLResult(String url) throws IOException {
		try(Scanner scanner = new Scanner(new URL(url).openStream(), StandardCharsets.UTF_8.toString())){
			scanner.useDelimiter("\\A");
			return scanner.hasNext() ? scanner.next() : "";
		}	
	}
	
	/*
	 //FOR TESTING
	public static void main(String[] args) throws IOException {
		System.out.println(getCoinHistory("BTC","3/1/2021","3/1/2021"));
		}
	*/
	
	
}//end class