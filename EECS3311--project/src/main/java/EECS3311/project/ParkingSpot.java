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
	public ParkingSpot() {
		isFilled=false;
		isPaid=isPaid.UNPAID;
		status=status.AVAILABLE;
	}
	
	public ParkingSpot(int start, int end) {
		StartTime=start;
		ExpirationTime=end;
	}
	public ParkingSpot(String spacenum, String license, int start, int end, Boolean avail) {
		ID = spacenum;
		LicensePlate=license;
		StartTime=start;
		ExpirationTime=end;
		isFilled=avail;
	}

	public boolean isFilled() {
		if(status==ParkingSpotStatus.AVAILABLE) {
			return false;
		}
		else return true;
		
	}
	
	public void Display() {
		
	}
	
	public double CalculatePayment() {
		return 0;
		
	}
	
	public boolean freeSpot() {
		isFilled=false;
		return false;
		
	}
	
	public void update() {
		
	}
}
