package org.backend.Trader_Platform;

import java.time.LocalDate;
import java.util.ArrayList;

public class Simulator {
	private final static int NumFlags = 4;
	private Stock simulatedStock;
	private TradingData[] tradingFlags;
	private Double budget, startingMoney, endingMoney;
	private ArrayList<Pair<LocalDate, Double>> userPortfolio;
	private int initialBuy;//Number of stocks to buy at the start of simulation

	/*
	 * Flags: 
	 * 0 Stop Loss-- When the price of the stock is <= to the stop loss value sell the stock
	 * 1 Take Profit-- When the price of the stock is >= to the take profit value sell the stock
	 * 2 Moving Stop Loss-- Sells when the price of the stock is n dollars below the highest stock price so far
	 * 3 Additional buying-- When a stock drops x% buy an additional n shares
	 * 
	 * If a TradingData argument (arg1 or arg2) is null it has not been used.
	 * Not every flag uses every arg.
	 * 
	 * If a flag is true you must perform the specified operation.
	 * 
	 * Once all shares have been sold the simulation is over, set endingMoney to the (budget + value of held assets)
	 *  and date at the end of the simulation.
	 * 
	 * Cannot buy partial stock, as stock is bought budget decreases, as stock is sold budget increases
	 * 
	 * For every data point that is simulated from the Stock EOD Totals, a corresponding date & money valuation
	 * must be added to the userPortfolio. This value corresponds to the budget + value of held assets.
	 */
	public Simulator(Stock stock, Double budget, int initBuy){
		simulatedStock = stock;
		tradingFlags = new TradingData[NumFlags];
		startingMoney = budget;
		this.budget = budget;
		initialBuy = initBuy;
		userPortfolio = new ArrayList<Pair<LocalDate,Double>>();
		for(int i=0; i<tradingFlags.length; i++) {
			tradingFlags[i] = new TradingData();
		}
	}
	
	/*
	 * Simulation function, returns true if success, false if failure.
	 */
	
	public boolean simulate() {
		Double currentStockPrice;
		LocalDate currentDate;
		ArrayList<Pair<LocalDate, Double>> historicData = simulatedStock.getEodTotals();


		
		//Initializing data for loop
		int numStock = initialBuy;
		Double currentStockValue = initialBuy * historicData.get(0).getSecond();
		Double peakPrice = historicData.get(0).getSecond();
		boolean flag = false;
		
		
		startingMoney = budget;
		budget = budget - currentStockValue;
		if(budget < 0) {
			return false;
		}
		else {
			userPortfolio.add(new Pair<LocalDate, Double>(historicData.get(0).getFirst(), budget + currentStockValue));
		}

		
		for(int i = 1; i<historicData.size(); i++) {
			currentDate = historicData.get(i).getFirst();
			currentStockPrice = historicData.get(i).getSecond();
			
			if(tradingFlags[0].isFlag() && tradingFlags[0].getArg1() >= currentStockPrice) {
				flag = true;
			}
			if(tradingFlags[1].isFlag() && tradingFlags[1].getArg1() <= currentStockPrice) {
				flag = true;
			}
			if(tradingFlags[2].isFlag()) {
				if(peakPrice < currentStockPrice) {
					peakPrice = currentStockPrice;
				}
				if((peakPrice - currentStockPrice) >= tradingFlags[2].getArg1()) {
					flag = true;
				}
			}
			if(tradingFlags[3].isFlag()) {
				Double lastPrice = historicData.get(i-1).getSecond();
				Double percentChange = ((lastPrice - historicData.get(i).getSecond())/lastPrice) * 100;
				if(percentChange >= tradingFlags[3].getArg1()) {
					int toBuy = tradingFlags[3].getArg2().intValue();
					Double addedValue = toBuy * currentStockPrice;
					if(budget > addedValue) {
						numStock += toBuy;
						budget -= addedValue;
					}
				}
			}
			//Checking if the ending flag has been set, if so end the simulation, otherwise continue after adding a data point
			if(flag) {
				currentStockValue = numStock * currentStockPrice;
				endingMoney = budget + currentStockValue;
				userPortfolio.add(new Pair<LocalDate, Double>(currentDate, endingMoney));
				return true;
			}else {
				currentStockValue = numStock * currentStockPrice;
				userPortfolio.add(new Pair<LocalDate, Double>(currentDate, budget + currentStockValue));
			}
			
		}
		endingMoney = currentStockValue + budget;
		return true;
	}
	
	public void addNewDataPoint(LocalDate date, Double value) {
		userPortfolio.add(new Pair<LocalDate, Double>(date, value));
	}
	
	public void setFlag(int index, Double arg1) {
		tradingFlags[index].setFlag(true);
		tradingFlags[index].setArg1(arg1);
		tradingFlags[index].setArg2(null);
	}
	
	public void setFlag(int index, Double arg1, Double arg2) {
		tradingFlags[index].setFlag(true);
		tradingFlags[index].setArg1(arg1);
		tradingFlags[index].setArg2(arg2);
	}
	
	public void unSetFlag(int index) {
		tradingFlags[index].setFlag(false);
		tradingFlags[index].setArg1(null);
		tradingFlags[index].setArg2(null);
	}

	public Stock getSimulatedStock() {
		return simulatedStock;
	}

	public void setSimulatedStock(Stock simulatedStock) {
		this.simulatedStock = simulatedStock;
	}

	public Double getBudget() {
		return budget;
	}

	public void setBudget(Double budget) {
		this.budget = budget;
	}

	public Double getStartingMoney() {
		return startingMoney;
	}

	public void setStartingMoney(Double startingMoney) {
		this.startingMoney = startingMoney;
	}

	public Double getEndingMoney() {
		return endingMoney;
	}

	public void setEndingMoney(Double endingMoney) {
		this.endingMoney = endingMoney;
	}

	public int getInitialBuy() {
		return initialBuy;
	}

	public void setInitialBuy(int initialBuy) {
		this.initialBuy = initialBuy;
	}

	public ArrayList<Pair<LocalDate, Double>> getUserPortfolio() {
		return userPortfolio;
	}

	public void setUserPortfolio(ArrayList<Pair<LocalDate, Double>> userPortfolio) {
		this.userPortfolio = userPortfolio;
	}
	
	
	
}
