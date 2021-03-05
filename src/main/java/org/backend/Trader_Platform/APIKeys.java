package org.backend.Trader_Platform;

public class APIKeys {
	private String marketKey;
	private String coinKey;
	
	public APIKeys(String marketKey) {
		this.setMarketKey(marketKey);
	}

	public String getMarketKey() {
		return marketKey;
	}

	public void setMarketKey(String marketKey) {
		this.marketKey = marketKey;
	}

	public String getCoinKey() {
		return coinKey;
	}

	public void setCoinKey(String coinKey) {
		this.coinKey = coinKey;
	}
	
	
}
