package EECS3311.project;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.Random;

public class customer implements User {
	Map<String, ParkingSpot> BOOKINGS;
	boolean LogInStatus;
	Payment PAYMENT;
	PaymentMethod METHOD; //maybe enum?
	
	public customer() {
		//constructor
		LogInStatus=false;
	}
	
	@Override
	public void CreatAccount() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean LogIn() {
		// TODO Auto-generated method stub
		LogInStatus=true;
		return LogInStatus;
	}
	
	public boolean bookSpot() {
		if(BOOKINGS.size()>=3) return false;
		byte[] array = new byte[7]; // length is bounded by 7
	    new Random().nextBytes(array);
	    String BookingID = new String(array, Charset.forName("UTF-8"));
		ParkingSpot booking=new ParkingSpot();
		BOOKINGS.put(BookingID, booking);
		return true;
	}
	
	public boolean Pay() {
		PAYMENT.SetPaymentMethod(METHOD);
		return false;
	}
	
	public Map<String, ParkingSpot> ViewBookings() {
		return BOOKINGS;
	}
	
	public boolean CancelBookings(String ID) {
		/**
		 * Booking does not exist
		 */
		if(BOOKINGS.get(ID)==null) return false;
		
		/*
		 * 1) free spot
		 * 2) remove entry from bookings map
		 */
		BOOKINGS.get(ID).freeSpot();
		BOOKINGS.remove(ID);
		return true;
	}
	
	public void accept() {
		
	}
}
