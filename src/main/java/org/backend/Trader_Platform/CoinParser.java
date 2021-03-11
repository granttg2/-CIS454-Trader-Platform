package org.backend.Trader_Platform;

import java.io.IOException;
import java.time.LocalDate;

import org.json.*;

public class CoinParser {

	public static void parseCoinEODTotals(Stock stock){
		String start = stock.getStartDate().toString();
		String end = stock.getEndDate().toString();
		String jsonString;
		LocalDate lDate = stock.getEndDate(); 
		
		try {
			jsonString = APICall.getCoinHistory(stock.getTicker(), start, end);
			JSONArray completeA = new JSONArray(jsonString);

			for(int i = completeA.length() - 1; i >= 0; i--) {
				JSONArray object = completeA.getJSONArray(i);
				stock.addNewEOD(lDate.toString(),object.getDouble(4));
				lDate.minusDays(1);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	

}

