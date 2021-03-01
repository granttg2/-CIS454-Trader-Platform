package org.backend.Trader_Platform;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class TickerList {
	private ArrayList<Stock> tickerList;
	
	public TickerList(ArrayList<Stock> list) {
		tickerList = new ArrayList<Stock>(list);
	}
	
	public ArrayList<String> searchTickers(String regex) {
		ArrayList<String> matches = new ArrayList<String>();
		
		Pattern pat = Pattern.compile(regex);
		
		for(Stock s:tickerList) {
			String string = s.getTicker();
			if(pat.matcher(string).matches()) {
				matches.add(string);
			}
		}
		return matches;
	}
	
	public void addTicker(String ticker) {
		Stock s = new Stock(ticker);
		tickerList.add(s);
	}
	
}
