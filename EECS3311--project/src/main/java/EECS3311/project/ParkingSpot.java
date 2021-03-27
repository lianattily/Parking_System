package EECS3311.project;

public class ParkingSpot {
	boolean isFilled;
	ParkingRate rate;
	PaymentStatus isPaid;
	int StartTime;
	int ExpirationTime;
	ParkingSpotStatus status;
	String LicensePlate;
	String ID;
	/**
	 * Constructor
	 */
	public ParkingSpot(String ID) {
		isFilled=false;
		isPaid=isPaid.UNPAID;
		status=status.AVAILABLE;
		this.ID=ID;
	}
	
	public ParkingSpot(int start, int end) {
		isFilled=false;
		isPaid=isPaid.UNPAID;
		status=status.AVAILABLE;
		StartTime=start;
		ExpirationTime=end;
	}
	public ParkingSpot(String spacenum, String license, int start, int end) {
		ID = spacenum;
		LicensePlate=license;
		StartTime=start;
		ExpirationTime=end;
		isFilled=true;
		
	}

	public boolean isFilled() {
		if(status==ParkingSpotStatus.AVAILABLE) {
			return false;
		}
		else return true;
		
	}
	
	public void Display() {
		
	}
	
	public double CalculatePayment() { //== getRate()
		return 0;
		
	}
	
	public boolean freeSpot() {
		isFilled=false;
		return false;
		
	}
	
	public void update() {
		
	}
	public boolean getisFilled() {
		return isFilled;
	}
	public boolean setAvail() {
		isFilled=true;
		return isFilled;
	}
	public PaymentStatus getisPaid() {
		return isPaid;
	}
	public String getID() {
		return ID;
	}
}
