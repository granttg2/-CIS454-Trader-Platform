package org.backend.Trader_Platform;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Scanner;

public class APICall{
	
	public static String getHistoricData(String key, String ticker, String startDay, String endDay) throws IOException {
		String url = "http://api.marketstack.com/v1/eod?access_key=" + key + "&symbols=" + ticker + "&date_from=" + startDay + "&date_to=" + endDay;
		return getURLResult(url);
	}
	
	private static String getURLResult(String url) throws IOException {
		try(Scanner scanner = new Scanner(new URL(url).openStream(), StandardCharsets.UTF_8.toString())){
			scanner.useDelimiter("\\A");
			return scanner.hasNext() ? scanner.next() : "";
		}
	}
}
      
   