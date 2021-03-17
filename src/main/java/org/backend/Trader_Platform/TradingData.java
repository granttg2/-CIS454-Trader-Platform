package org.backend.Trader_Platform;

public class TradingData {
	private boolean flag;
	private Double arg1, arg2;
	
	TradingData(){
		flag = false;
		arg1 = null;
		arg2 = null;
	}
	
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public Double getArg1() {
		return arg1;
	}
	public void setArg1(Double arg1) {
		this.arg1 = arg1;
	}
	public Double getArg2() {
		return arg2;
	}
	public void setArg2(Double arg2) {
		this.arg2 = arg2;
	}
	
	
	
	
	
}
