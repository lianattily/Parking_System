package EECS3311.project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

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
				ParkingSpot s = new ParkingSpot(values[0],values[1],Integer.parseInt(values[2]),Integer.parseInt(values[3]));
				BOOKINGS.add(s);
			}
			br.close();
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
		bw.close();
		fw.close();
	}
	/*
	 * Record booking in BookingsDatabase.txt
	 */
	private void SaveBooking(String ID, String PaymentStatus, String Expiration, String min) throws IOException {
		FileWriter fw = new FileWriter("BookingsDatabase.txt", true); 
		BufferedWriter bw = new BufferedWriter(fw); 
		PrintWriter pw = new PrintWriter(bw); 
		pw.println(ID+","+PaymentStatus+","+Expiration+","+min); 
		pw.flush(); 
		pw.close();
		bw.close();
		fw.close();


	}

	@Override
	public boolean LogIn(String email, String password) {
		// TODO Auto-generated method stub
		if(email==this.email && password==this.password)LogInStatus=true;

		return LogInStatus;
	}

	public boolean bookSpot(String ID,ParkingSpot spot) throws IOException {
		if(BOOKINGS.size()>=3) return false;
		System.out.println("LOOKING FOR SPOT : "+ID);
		Officer o = new Officer();
		List<ParkingSpot> s = o.getSpots();
		for(ParkingSpot sp: s) {
			if(sp.getID().equals(ID)) {
				System.out.println("BOOKING in customer : "+sp.getID());
				BOOKINGS.add(spot);
				SaveBooking(ID,spot.isPaid.toString(), spot.ExpirationTime.toString(), String.valueOf(spot.EndMinute));
				return true;
			}
		}
		System.out.println("could not find spot");
		return false;
	}

	public boolean Pay(String ID) throws Exception {
		PAYMENT.SetPaymentMethod(METHOD);
		PAYMENT.METHOD=METHOD;
		
		return updateUNPAID(ID);
	}

	public static void setMethod(PaymentMethod m) {
		METHOD=m;
	}

	public List<ParkingSpot> ViewBookings() {
		return BOOKINGS;
	}
	
	private boolean updateUNPAID(String toChange) throws Exception {
		File file = new File("BookingsDatabase.txt");
		File temp = new File("TempFile.txt");
		String ID = "", PaymentStatus = "", Expiration = "";  
		FileWriter fw = new FileWriter("TempFile.txt",false); 
		BufferedWriter bw = new BufferedWriter(fw); 
		PrintWriter pw = new PrintWriter(bw); 
		Scanner x = new Scanner(new File("BookingsDatabase.txt")); 
		x.useDelimiter("[,\n]"); 
		while(x.hasNext()) { 
			ID = x.next(); 
			PaymentStatus = x.next(); 
			Expiration = x.next();
			System.out.println("ID in line 176 = "+ID+" vs "+ toChange);
			if(ID.equals(toChange)){ 
				System.out.println("RE WRITING : "+ID+" , "+"PAID"+" , "+Expiration);
				pw.println(ID+ "," + "PAID" +"," + Expiration); 
			}
			else {
				System.out.println("KEEPING : "+ID+" , "+PaymentStatus+" , "+Expiration);
				pw.println(ID+ "," + PaymentStatus +"," + Expiration); 
			}
			x.next();
		}
		x.close();
		pw.flush();
		pw.close();
		fw.close();
		bw.close();
		if(file.exists()) {
			System.out.println("FILE EXISTS"+file.delete());
		}else System.out.println("file dont exist");

		File dump = new File("BookingsDatabase.txt");


		return temp.renameTo(dump);
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

		File file = new File("BookingsDatabase.txt");
		File temp = new File("TempFile.txt");
		String ID = "", PaymentStatus = "", Expiration = "";  
		FileWriter fw = new FileWriter("TempFile.txt",false); 
		BufferedWriter bw = new BufferedWriter(fw); 
		PrintWriter pw = new PrintWriter(bw); 
		Scanner x = new Scanner(new File("BookingsDatabase.txt")); 
		x.useDelimiter("[,\n]"); 
		while(x.hasNext()) { 
			ID = x.next(); 
			PaymentStatus = x.next(); 
			Expiration = x.next();
			System.out.println("ID in line 176 = "+ID+" vs "+ toRemove);
			if(!ID.equals(toRemove)){ 
				System.out.println("RE WRITING : "+ID+" , "+PaymentStatus+" , "+Expiration);
				pw.println(ID+ "," + PaymentStatus +"," + Expiration); 
			}
		}
		x.close();
		pw.flush();
		pw.close();
		fw.close();
		bw.close();
		if(file.exists()) {
			System.out.println("FILE EXISTS"+file.delete());
		}else System.out.println("file dont exist");

		File dump = new File("BookingsDatabase.txt");


		return temp.renameTo(dump);
	}
	public void accept() {

	}

	public Integer getRate(String ID) {
		Officer o = new Officer();
		List<ParkingSpot> list = o.getSpots();
		for(ParkingSpot sp: list) {
			if(sp.getID().equals(ID)) {
				return sp.CalculatePayment();
			}
		}
		return 0;
	}


}
