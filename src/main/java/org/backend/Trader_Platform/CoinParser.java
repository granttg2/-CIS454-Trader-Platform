package org.backend.Trader_Platform;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

import org.json.*;

public class CoinParser {

	public static void parseCoinEODTotals(Stock stock){
		String start = stock.getStartDate().toString();
		String end = stock.getEndDate().toString();
		String jsonString;
		long startIndex = ChronoUnit.DAYS.between(stock.getStartDate(), stock.getEndDate());
		LocalDate lDate = stock.getStartDate(); 
		
		try {
			jsonString = APICall.getCoinHistory(stock.getTicker(), start, end);
			JSONArray completeA = new JSONArray(jsonString);

			if(completeA.length() < startIndex) {
				startIndex = completeA.length()-1;
				lDate = stock.getEndDate().minusDays(completeA.length());
			}
			
			for(int i = (int) startIndex; i >= 0; i--) {
				JSONArray object = completeA.getJSONArray(i);
				stock.addNewEOD(lDate.toString(),object.getDouble(4));
				lDate = lDate.plusDays(1);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void parseCurrencyTickers(TickerList tickers) {
		String jsonString;
		
		try {
			jsonString = APICall.getCurrencyList();
			JSONArray currArray = new JSONArray(jsonString);
			
			for(int i = 0; i < currArray.length(); i++) {
				JSONObject object = currArray.getJSONObject(i);
				tickers.addTicker(object.getString("id"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
//	public static void parseExchangeRate(TickerList tickers) {Unused function
//		String jsonString;
//		
//		try {
//			jsonString = APICall.getExchangeReate();
//			JSONArray curArray = new JSONArray(jsonString);
//			
//			
//			JSONObject object = curArray.getJSONObject(0);
//			tickers.addTicker(object.getString("rate"));
//			
//		
//		}catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
	public static void parseSpotPrice(Stock stock) {
		String jsonString;		 
		
		try {
			jsonString = APICall.getSpotPrice(stock.getTicker());
			JSONObject wrapperObj = new JSONObject(jsonString);
			
			JSONObject object = wrapperObj.getJSONObject("data");
			
			stock.setCurrentPrice(object.getDouble("amount"));
			
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

}

