package EECS3311.project;

public class ParkingRate {
	private int rate;
	
	public void setRate(int rate) {	//set by officer when they add the spot
		this.rate=rate;
	}
	public int getRate() { //hour*rate
		return rate;
	}
}
