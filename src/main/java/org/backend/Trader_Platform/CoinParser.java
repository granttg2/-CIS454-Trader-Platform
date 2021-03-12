package org.backend.Trader_Platform;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;

import org.json.*;

public class CoinParser {

	public static void parseCoinEODTotals(Stock stock){
		String start = stock.getStartDate().toString();
		String end = stock.getEndDate().toString();
		String jsonString;
		int startIndex = Period.between(stock.getStartDate(), stock.getEndDate()).getDays();
		LocalDate lDate = stock.getStartDate(); 
		
		try {
			jsonString = APICall.getCoinHistory(stock.getTicker(), start, end);
			JSONArray completeA = new JSONArray(jsonString);

			if(completeA.length() < startIndex) {
				startIndex = completeA.length()-1;
				lDate = stock.getEndDate().minusDays(completeA.length());
			}
			
			for(int i = startIndex; i >= 0; i--) {
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
	

}

