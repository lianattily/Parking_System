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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class customer implements User {
	private List<ParkingSpot> BOOKINGS;
	boolean LogInStatus;
	Payment PAYMENT;
	static PaymentMethod METHOD;
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
		String date;
		try {
			BufferedReader br  = new BufferedReader(new FileReader(path));
			while((line = br.readLine())!=null) {
				String [] values = line.split(",");
				date = values[9];
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				//convert String to LocalDate
				LocalDate localDate = LocalDate.parse(date, formatter);
				//090a0,L9A5G2,TORONTO124,UNPAID,PENDING,12,30,2,30,24/04/2021,8
				// String uniqueID, String spacenum,String license,String payStat,String avail, int startH, int startM, int endH, int endM, LocalDate localDate, int rate
				ParkingSpot s = new ParkingSpot(values[0],values[1],values[2],values[3], values[4],Integer.parseInt(values[5]),Integer.parseInt(values[6]),Integer.parseInt(values[7]),Integer.parseInt(values[8]), localDate,Integer.parseInt(values[10]));
				System.out.println("adding "+BOOKINGS.add(s));
			}
			br.close();
		}
		catch (Exception e) {

		}System.out.println("customers booking initializing size = "+BOOKINGS.size());


	}

	public boolean getStatus() {
		return LogInStatus;
	}

	private boolean isExists(String ID) {
		String path = "CustomerDatabase.txt"; 
		String line = ""; 
		try {
			BufferedReader br  = new BufferedReader(new FileReader(path));
			while((line = br.readLine())!=null) {
				String [] values = line.split(",");
				if(values[2].equals(ID)) { System.out.println("FOUND CUSTOMER "+values[2]);return true;}
			}
			br.close();
		}
		catch (Exception e) {
		}


		return false;
	}

	@Override
	public void CreatAccount(String Fname, String Lname, String email, String Password) {

		if(isExists(email)) return;
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
	private void SaveBooking(String id, ParkingSpot spot) throws IOException {
		FileWriter fw = new FileWriter("BookingsDatabase.txt", true); 
		BufferedWriter bw = new BufferedWriter(fw); 
		PrintWriter pw = new PrintWriter(bw); 
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String strDate = dateFormat.format(spot.date);  
		System.out.println("in save booking date = "+strDate);
		spot.status = spot.status.PENDING;
		pw.println(id+","+spot.ID+","+spot.LicensePlate+","+spot.getisPaid()+","+spot.getStat()+","+spot.StartHour+","+spot.StartMin+","+spot.EndHour+","+spot.EndMinute+","+strDate+","+spot.rate.getRate()); 
		pw.flush(); 
		pw.close();
		bw.close();
		fw.close();
	}

	@Override
	public boolean LogIn(String email, String password) {
		LogInStatus = true;
		return true;
	}

	public boolean bookSpot(String uniqueID,String ID,ParkingSpot spot) throws IOException {
		if(BOOKINGS.size()>=3) return false;
		System.out.println("LOOKING FOR SPOT : "+ID);
		Officer o = new Officer();
		for(ParkingSpot s: BOOKINGS) {
			if(s.getID().equals(ID) && s.getUnique().equals(uniqueID)) return false;
		}
		List<ParkingSpot> s = o.getSpots();

		for(ParkingSpot sp: s) {
			if(sp.getID().equals(ID)) {
				System.out.println("BOOKING in customer : "+sp.getID());
				BOOKINGS.add(spot);
				SaveBooking(uniqueID, spot);
				//SaveBooking(ID,spot.isPaid.toString(), spot.LicensePlate, spot.ExpirationTime.toString(), String.valueOf(spot.EndMinute),spot.date.toString());
				return true;
			}
		}
		System.out.println("could not find spot");
		return false;
	}

	public boolean Pay(ParkingSpot ID) throws Exception {
		PAYMENT.SetPaymentMethod(METHOD);
		PAYMENT.METHOD=METHOD;
		return updateUNPAID(ID.ID, ID.getUnique());
	}

	public static void setMethod(PaymentMethod m) {
		METHOD=m;
	}

	public List<ParkingSpot> ViewBookings() {
		return BOOKINGS;
	}

	private boolean updateUNPAID(String toChange, String unique) throws Exception {

		File file = new File("BookingsDatabase.txt");
		File temp = new File("TempFile.txt");
		String ID = "", PaymentStatus = "", Expiration = "", license = "", start = "", date="", rate="", uniqueID="",avail="";  
		FileWriter fw = new FileWriter("TempFile.txt",false); 
		BufferedWriter bw = new BufferedWriter(fw); 
		PrintWriter pw = new PrintWriter(bw); 
		String line = ""; 
		try {
			BufferedReader br  = new BufferedReader(new FileReader("BookingsDatabase.txt"));
			while((line = br.readLine())!=null) {
				String [] values = line.split(",");
				//090a0,L9A5G2,TORONTO124,UNPAID,PENDING,12,30,2,30,24/04/2021,8
				// String uniqueID, String spacenum,String license,String payStat,String avail, int startH, int startM, int endH, int endM, LocalDate localDate, int rate
				uniqueID=values[0];
				ID =values[1]; 
				license = values[2];
				PaymentStatus =values[3];
				avail=values[4];
				//N3W0A5,TORONTO123,UNPAID,12,30,22,45,22/04/2021,12
				start = values[5]+","+values[6];
				Expiration = values[7]+","+values[8];
				date=values[9];
				rate=values[10];
				System.out.println("ID in line 176 = "+ID+" vs "+ toChange);
				if((!unique.equals(values[0]))){ 
					System.out.println("RE WRITING : "+ID+" , "+PaymentStatus+" , "+Expiration);
					pw.println(uniqueID+","+ID+ "," + license +"," +PaymentStatus+","+avail +","+start+","+ Expiration+","+date+","+rate); 
				}
				else {
					System.out.println("UPDATING: "+ID+" , "+"PENDING"+" , "+Expiration);
					pw.println(uniqueID+","+ID+ "," + license +"," +"PENDING"+","+avail +","+start+","+ Expiration+","+date+","+rate); 
	
				}
			}
			br.close();
		}
		catch (Exception e) {

		}
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

	public boolean CancelBookings(String ID,String unique) throws Exception {
		for(ParkingSpot s: BOOKINGS) {
			System.out.println("230"+s.getUnique()+"  vs "+unique);
			if(s.getUnique().equals(unique)) {
				s.isFilled=false;
				BOOKINGS.remove(s);
				return removeRecord(ID, s.uniqueID);
			}
		}
		return false;
	}

	private boolean removeRecord(String toRemove, String unique) throws Exception {
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
		String ID = "", PaymentStatus = "", Expiration = "" , license = "", start = "", date="", rate="",uniqueID="",avail="";   
		FileWriter fw = new FileWriter("TempFile.txt",false); 
		BufferedWriter bw = new BufferedWriter(fw); 
		PrintWriter pw = new PrintWriter(bw); 
		String line = ""; 
		try {
			BufferedReader br  = new BufferedReader(new FileReader("BookingsDatabase.txt"));
			while((line = br.readLine())!=null) {
				String [] values = line.split(",");
				//090a0,L9A5G2,TORONTO124,UNPAID,PENDING,12,30,2,30,24/04/2021,8
				// String uniqueID, String spacenum,String license,String payStat,String avail, int startH, int startM, int endH, int endM, LocalDate localDate, int rate
				System.out.println("line = "+line);
				uniqueID=values[0];
				ID =values[1]; 
				license = values[2];
				PaymentStatus =values[3];
				avail=values[4];
				//N3W0A5,TORONTO123,UNPAID,12,30,22,45,22/04/2021,12
				start = values[5]+","+values[6];
				Expiration = values[7]+","+values[8];
				date=values[9];
				rate=values[10];
				System.out.println("ID in line 176 = "+ID+" vs "+ toRemove+ "   unique "+unique+" vs "+uniqueID);
				System.out.println(toRemove.equals(values[1]));
				if(!unique.equals(values[0])){ 
					System.out.println("RE WRITING : "+ID+" , "+PaymentStatus+" , "+Expiration);
					pw.println(uniqueID+","+ID+ "," + license +"," +PaymentStatus+","+avail +","+start+","+ Expiration+","+date+","+rate); 
				}		
			}
			br.close();
		}
		catch (Exception e) {

		}
		
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
	public List<ParkingSpot> accept(Officer o) {
		return BOOKINGS;
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
