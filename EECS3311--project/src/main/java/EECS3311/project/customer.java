package EECS3311.project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class customer implements User {
	List<ParkingSpot> BOOKINGS;
	boolean LogInStatus;
	Payment PAYMENT;
	static PaymentMethod METHOD; //maybe enum?
	private String Fname, Lname, email, password;
	public customer() {
		//constructor
		BOOKINGS = new ArrayList<ParkingSpot>();
		PAYMENT = new Payment();
		LogInStatus=false;
		init();
	}
	private void init() {
		String path = "BookingsDatabase.txt"; 
		String line = ""; 
		try {
			BufferedReader br  = new BufferedReader(new FileReader(path));
			while((line = br.readLine())!=null) {
				String [] values = line.split(",");
				ParkingSpot s = new ParkingSpot(values[0]);
				BOOKINGS.add(s);
			}
		}
		catch (Exception e) {

		}

	}

	public boolean getStatus() {
		return LogInStatus;
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
	/*
	 * Record customer's info in CustomerDatabase.txt
	 */
	private void SaveRecord(String Fname, String Lname, String email, String Password) throws IOException {
		FileWriter fw = new FileWriter("CustomerDatabase.txt", true); 
		BufferedWriter bw = new BufferedWriter(fw); 
		PrintWriter pw = new PrintWriter(bw); 
		pw.println(Fname+","+Lname+","+email+","+Password); 
		pw.flush(); 
		pw.close();
	}
	/*
	 * Record booking in BookingsDatabase.txt
	 */
	private void SaveBooking(String ID, String PaymentStatus, String Expiration) throws IOException {
		FileWriter fw = new FileWriter("BookingsDatabase.txt", true); 
		BufferedWriter bw = new BufferedWriter(fw); 
		PrintWriter pw = new PrintWriter(bw); 
		pw.println(ID+","+PaymentStatus+","+Expiration); 
		pw.flush(); 
		pw.close();


	}

	@Override
	public boolean LogIn(String email, String password) {
		// TODO Auto-generated method stub
		if(email==this.email && password==this.password)LogInStatus=true;

		return LogInStatus;
	}

	public boolean bookSpot(ParkingSpot spot) throws IOException {
		if(BOOKINGS.size()>=3) return false;
		Officer o = new Officer();
		List<ParkingSpot> s = o.getSpots();
		for(ParkingSpot sp: s) {
			if(sp.getID().equals(spot.getID())) {
				BOOKINGS.add(spot);
				SaveBooking(spot.getID(),spot.isPaid.toString(), spot.ExpirationTime.toString());
				return true;
			}
		}
		return false;
	}

	public boolean Pay() {
		PAYMENT.SetPaymentMethod(METHOD);
		PAYMENT.METHOD=METHOD;
		return false;
	}

	public static void setMethod(PaymentMethod m) {
		METHOD=m;
	}

	public List<ParkingSpot> ViewBookings() {
		return BOOKINGS;
	}

	public boolean CancelBookings(String ID) throws Exception {
		/**
		 * Booking does not exist
		 */
		for(ParkingSpot s: BOOKINGS) {
			if(s.ID.equals(ID)) {
				s.isFilled=false;
				BOOKINGS.remove(s);
				return removeRecord(ID);
			}
		}
		return false;
	}
	private boolean removeRecord(String toRemove) throws Exception {
		for (int i = 0; i < BOOKINGS.size(); i++) {
			ParkingSpot filter = BOOKINGS.get(i);

			System.out.println("SIZE OF LIST BEFORE REMOVING  = "+BOOKINGS.size());
			if (filter.ID.equalsIgnoreCase(toRemove)){//equalsIgnoreCase("bbb")) {
				System.out.println("removing: "+filter.ID);
				BOOKINGS.remove(i);

				System.out.println("SIZE OF LIST AFTER REMOVING = "+BOOKINGS.size());
			}
		}
		System.out.println("SIZE OF LIST = "+BOOKINGS.size());
		FileWriter fw = new FileWriter("BookingsDatabase.txt", true); 
		BufferedWriter bw = new BufferedWriter(fw); 
		PrintWriter pw = new PrintWriter(bw); 
		for(ParkingSpot s: BOOKINGS) {
			System.out.println("RE WRITING : "+s.ID);
			pw.println(s.ID+","+s.stat+","+s.EndHour); 
		}

		pw.flush(); 
		pw.close();
		return true;

		//		File inputFile = new File("BookingsDatabase.txt");
		//		File tempFile = new File("TempFile.txt");
		//
		//		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		//		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
		//
		//		String currentLine;
		//
		//		while((currentLine = reader.readLine()) != null) {
		//		    // trim newline when comparing with lineToRemove
		//		    String trimmedLine = currentLine.trim();
		//		    if(trimmedLine.equals(toRemove)) continue;
		//		    writer.write(currentLine + System.getProperty("line.separator"));
		//		}
		//		writer.close(); 
		//		reader.close(); 
		//		boolean successful = tempFile.renameTo(inputFile);
		//return successful;
	}
	public void accept() {

	}

	public Integer getRate() {
		ParkingSpot s = BOOKINGS.get(0);
		return s.CalculatePayment();
	}


}
