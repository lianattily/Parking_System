package EECS3311.project;

public class ParkingSpot {
	//private final int rate= 5;
	boolean isFilled;
	ParkingRate rate = new ParkingRate();
	PaymentStatus isPaid;
	int StartHour, StartMin, EndHour, EndMinute;
	Integer ExpirationTime;
	ParkingSpotStatus status;
	String LicensePlate;
	public String ID;
	public String avail, stat;
	/**
	 * Constructor for adding by ID (default settings, no expiration date)
	 */
	public ParkingSpot(String ID, int rate) {
		isFilled=false;
		isPaid=isPaid.UNPAID;
		avail = isPaid.toString();
		status=status.AVAILABLE;
		stat=status.toString();
		this.rate.setRate(rate);
		this.ID=ID;
	}
	/*
	 * Constructor for customer with start and end times, license plate
	 */
	public ParkingSpot(String spacenum,String license,int startH, int startM, int endH, int endM) {
		ID = spacenum;
		isFilled=true;
		isPaid=isPaid.UNPAID;
		status=status.FILLED;
		StartHour=startH;
		StartMin = startM;
		EndHour  = endH ;
		EndMinute = endM;
		ExpirationTime = endH;
		stat=status.toString();
		avail = isPaid.toString();
		ID = spacenum;
		LicensePlate=license;
		System.out.println("END HR = "+ExpirationTime);
		
	}

	public ParkingSpot(String id, String status, int endH, int endM) {
		ID=id;
		if(status.equals("UNPAID")) {
			isPaid = isPaid.UNPAID;
		}else isPaid = isPaid.PAID;
		EndHour  = endH ;
		EndMinute = endM;
		ExpirationTime = endH;
		
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
		stat = status.toString();
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
		stat = status.toString();
		return isFilled;
	}
	public String getisPaid() {
		return isPaid.toString();
	}
	public String getID() {
		return ID;
	}
	public String getStat() {
		return stat;
	}
	public String getAvail() {
		return avail;
	}
	public String getExp() {
		System.out.println(String.valueOf(EndHour)+":"+String.valueOf(EndMinute));
		String s = String.valueOf(EndHour)+":"+String.valueOf(EndMinute);
		return s;
	}
	
	public String getRate() {
		String s = "$"+ String.valueOf(rate.getRate());
		return s;
	}
}
