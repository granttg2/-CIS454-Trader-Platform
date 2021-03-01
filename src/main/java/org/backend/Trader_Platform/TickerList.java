package org.backend.Trader_Platform;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class TickerList {
	private ArrayList<String> tickerList;
	
	public TickerList(ArrayList<String> list) {
		tickerList = new ArrayList<String>(list);
	}
	
	public ArrayList<String> searchTickers(String regex) {
		ArrayList<String> matches = new ArrayList<String>();
		
		Pattern pat = Pattern.compile(regex);
		
		for(String s:tickerList) {
			if(pat.matcher(s).matches()) {
				matches.add(s);
			}
		}
		return matches;
	}
	
	public void addTicker(String ticker) {
		tickerList.add(ticker);
	}
	
}
