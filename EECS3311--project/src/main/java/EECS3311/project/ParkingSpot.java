package EECS3311.project;

public class ParkingSpot {
	boolean isFilled;
	ParkingRate rate;
	PaymentStatus isPaid;
	Double StartTime;
	Double ExpirationTime;
	ParkingSpotStatus status;
	/**
	 * Constructor
	 */
	public ParkingSpot() {
		isFilled=false;
		isPaid=isPaid.UNPAID;
		status=status.AVAILABLE;
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
