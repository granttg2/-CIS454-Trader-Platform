package org.backend.Trader_Platform;

public class Pair<A, B> {
	public final A itemA;
	public final B itemB;
	
	public Pair(A itemA, B itemB) {
		this.itemA = itemA;
		this.itemB = itemB;
	}
	
	public A getFirst() {
		return itemA;
	}
	
	public B getSecond() {
		return itemB;
	}
	
}
