package org.backend.Trader_Platform;

import java.io.IOException;

import org.json.*;
import org.jfx.Trader_Platform.App;

public class MarketParser {

	public static void parseMarketEODTotals(Stock stock){
		String key = App.getKeys().getMarketKey();
		String start = stock.getStartDate().toString();
		String end = stock.getEndDate().toString();
		String jsonString;
		
		
		try {
			jsonString = APICall.getHistoricData(key, stock.getTicker(), start, end);
			JSONObject completeJ = new JSONObject(jsonString);
			JSONArray dataArray = completeJ.getJSONArray("data");

			for(int i = dataArray.length() - 1; i >= 0; i--) {
				JSONObject object = dataArray.getJSONObject(i);
				stock.addNewEOD(object.getString("date"),object.getDouble("close"));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
}