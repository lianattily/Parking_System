package EECS3311.project;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Random;

public class customer implements User {
	Map<String, ParkingSpot> BOOKINGS;
	boolean LogInStatus;
	Payment PAYMENT;
	PaymentMethod METHOD; //maybe enum?
	private String Fname, Lname, email, password;
	public customer() {
		//constructor
		LogInStatus=false;
	}
	
	@Override
	public void CreatAccount(String Fname, String Lname, String email, String Password) {
		this.Fname=Fname;
		this.Lname=Lname;
		this.email=email;
		this.password=Password;
		try {
			SaveRecord(Fname,Lname,email,Password);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void SaveRecord(String Fname, String Lname, String email, String Password) throws IOException {
		FileWriter fw = new FileWriter("database.txt", true); 
		BufferedWriter bw = new BufferedWriter(fw); 
		PrintWriter pw = new PrintWriter(bw); 
		pw.println(Fname+","+Lname+","+email+","+Password); 
		pw.flush(); 
		pw.close();
	}

	@Override
	public boolean LogIn(String email, String password) {
		// TODO Auto-generated method stub
		if(email==this.email && password==this.password)LogInStatus=true;
		
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

	
//	public static void main(String[] args){
//		
//		customer c = new customer();
//		c.CreatAccount("Lian", "Attily", "lianattily@gmail.com", "ravi0215.");
//	}
	
	
}
