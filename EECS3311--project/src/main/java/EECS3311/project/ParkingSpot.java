package EECS3311.project;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ParkingSpot {
	//private final int rate= 5;
	boolean isFilled;
	public ParkingRate rate = new ParkingRate();
	PaymentStatus isPaid;
	public int StartHour, StartMin, EndHour, EndMinute;
	Integer ExpirationTime;
	ParkingSpotStatus status;
	String LicensePlate;
	public String ID;
	LocalDate date;
	String uniqueID = "";
	/**
	 * Constructor for adding by ID (default settings, no expiration date)
	 */
	public ParkingSpot(String ID, int rate) {
		isFilled=false;
		isPaid=isPaid.UNPAID;
		//avail = isPaid.toString();
		status=status.AVAILABLE;
		this.rate.setRate(rate);
		this.ID=ID;
	}
	/*
	 * Constructor for customer with start and end times, license plate
	 */
	public ParkingSpot(String spacenum,String license,String status, int startH, int startM, int endH, int endM, LocalDate localDate, int rate) {
		
		ID = spacenum;
		this.status=this.status.PENDING;
		isFilled=true;
		if(status.equals("UNPAID")) {
			isPaid = isPaid.UNPAID;
		}else isPaid = isPaid.PAID;
		StartHour=startH;
		StartMin = startM;
		EndHour  = endH ;
		EndMinute = endM;
		ExpirationTime = endH;
		//avail = isPaid.toString();
		LicensePlate=license;
		System.out.println("END HR = "+ExpirationTime);
		this.date=localDate;
		this.rate.setRate(rate);
	}
	
	public ParkingSpot(String uniqueID, String spacenum,String license,String payStat,String avail, int startH, int startM, int endH, int endM, LocalDate localDate, int rate) {
		this.uniqueID=uniqueID;
		ID = spacenum;
		if(avail.equals("AVAILABLE"))
			this.status=this.status.AVAILABLE;
		else if(avail.equals("PENDING")) this.status=this.status.PENDING;
		else this.status=this.status.FILLED;
		isFilled=true;
		if(payStat.equals("UNPAID")) {
			isPaid = isPaid.UNPAID;
		}else isPaid = isPaid.PENDING;
		StartHour=startH;
		StartMin = startM;
		EndHour  = endH ;
		EndMinute = endM;
		ExpirationTime = endH;
		avail = isPaid.toString();
		LicensePlate=license;
		System.out.println("END HR = "+ExpirationTime);
		this.date=localDate;
		this.rate.setRate(rate);
	}

	public boolean isFilled() {
		if(status==ParkingSpotStatus.AVAILABLE) {
			return false;
		}
		else return true;
		
	}
	
	public void SetRate(int r) {
		rate.setRate(r);
	}
	
	public int CalculatePayment() { 
		int diff = Math.abs(EndHour - StartHour);
		return (diff*rate.getRate());
		
	}
	
	public boolean freeSpot() {
		isFilled=false;
		status= status.AVAILABLE;
		return false;
		
	}
	
	public void update() {
		
	}
	public boolean getisFilled() {
		return isFilled;
	}
	public boolean setAvail() {
		isFilled=true;
		status= status.FILLED;
		return isFilled;
	}
	public String getisPaid() {
		return isPaid.toString();
	}
	public String getID() {
		return ID;
	}
	public String getStat() {
		return status.toString();
	}
	public String getAvail(){
		return isPaid.toString();
	}
	public String getExp() {
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String strDate = dateFormat.format(date);
		System.out.println(String.valueOf(EndHour)+":"+String.valueOf(EndMinute));
		String s = String.valueOf(EndHour)+":"+String.valueOf(EndMinute)+"   "+ strDate;
		return s;
	}
	
	public String getRate() {
		String s = "$"+ String.valueOf(rate.getRate());
		return s;
	}
	
	public String getUnique() {
		System.out.println("uniqueID = "+uniqueID);
		return uniqueID;
	}
	
	public String getLicense() {
		return LicensePlate;
	}
	
}
