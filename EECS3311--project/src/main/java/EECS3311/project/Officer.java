package EECS3311.project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.opencsv.CSVWriter;

import javafx.scene.control.Alert;

public class Officer implements User {
	private String ID;
	private String password;
	List<ParkingSpot> Spots;
	boolean LogInStatus;
	
	
	public Officer() {
		LogInStatus=false;
		Spots = new ArrayList<ParkingSpot>();
		fill();
	}
	public Officer(String ID, String password) {
		this.ID=ID;
		this.password=password;
	}
	
	private void fill() {
		//System.out.println("officer.java 36 HERE");
		String path = "ParkingDatabase.txt"; 
		String line = ""; 
		try {
			BufferedReader br  = new BufferedReader(new FileReader(path));
			while((line = br.readLine())!=null) {
				String [] values = line.split(",");
				System.out.println(values.length);
				System.out.println("line = "+line+"   "+Integer.parseInt(values[3]));
				ParkingSpot s = new ParkingSpot(values[0],Integer.parseInt(values[3]));
				Spots.add(s);
				System.out.println("spot = "+s.ID+"  "+s.getRate());
				}
			br.close();
			}
		catch (Exception e) {
			System.out.println("EXCEPTION");
		}
		System.out.println("in spots size = "+Spots.size());
	}
	
	@Override
	public void CreatAccount(String Fname, String Lname, String email, String Password) {
		//nothing to do here
		
	}
	
	public void SaveRecord(String ID,  String paymentStatus,String Rate) throws IOException {
		System.out.println("In save records");
		
		String	avail ="Available";
		
		System.out.println(avail);
		FileWriter fw = new FileWriter("Parkingdatabase.txt", true); 
		BufferedWriter bw = new BufferedWriter(fw); 
		PrintWriter pw = new PrintWriter(bw); 
		pw.println(ID+","+avail+","+paymentStatus+","+Rate); 
		System.out.println("Adding -> "+ID);
		pw.flush(); 
		pw.close();
		bw.close();
		fw.close();
		
	}
	@Override
	public boolean LogIn(String ID, String password) {
		// TODO Auto-generated method stub
		File file = new File("OfficerDatabase.txt");
		String line = ""; 
		try {
			BufferedReader br  = new BufferedReader(new FileReader("OfficerDatabase.txt"));
			while((line = br.readLine())!=null) {
				String [] values = line.split(",");
				if(values[0].equals(ID) && values[1].equals(password)) {
					LogInStatus = true;
				}
				}
			br.close();
			}
		catch (Exception e) {
			
		}
		//if(this.ID.equals(ID) && this.password.equals(password))LogInStatus=true;
		
		return LogInStatus;
	}
	
	public ParkingSpot AddSpot(String ID, Integer rate) throws IOException {
		
		ParkingSpot s = new ParkingSpot(ID,rate);
		for(ParkingSpot sp: Spots) {
			System.out.println("sp.getID = "+sp.getID());
			if(sp.getID().equals(ID)) return null;
			System.out.println(sp.getID()+" , "+sp.getRate());
		}
		Spots.add(s);
		SaveRecord(ID, s.getisPaid(), rate.toString());
		
		return s;
	}
	
	public void RemoveSpot(ParkingSpot s) throws Exception {
		Spots.remove(s);
		removeRecord(s.ID);
		//must remove from database
	}
	
	private boolean removeRecord(String toRemove) throws Exception {
		for (int i = 0; i < Spots.size(); i++) {
			ParkingSpot filter = Spots.get(i);
			if (filter.ID.equalsIgnoreCase(toRemove)){//equalsIgnoreCase("bbb")) {
				System.out.println("removing: "+filter.ID);
				Spots.remove(i);

			}
		}
		String name = "ParkingDatabase.txt";
		File file = new File("ParkingDatabase.txt");
		File temp = new File("TempFile.txt");
		String ID = "", PaymentStatus = "", Avail = "", rate="";  
		FileWriter fw = new FileWriter("TempFile.txt",false); 
		BufferedWriter bw = new BufferedWriter(fw); 
		PrintWriter pw = new PrintWriter(bw); 
		String line = ""; 
		try {
			BufferedReader br  = new BufferedReader(new FileReader("ParkingDatabase.txt"));
			while((line = br.readLine())!=null) {
				String [] values = line.split(",");

				ID =values[0]; 
				Avail=values[1];
				rate=values[2];
				System.out.println("ID in line 176 = "+ID+" vs "+ toRemove);
				if(!values[0].equals(toRemove)){ 
					System.out.println("RE WRITING : "+ID+" , "+PaymentStatus+" , "+Avail);
					pw.println(ID+ "," +Avail+"," +PaymentStatus+","+rate ); 
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
		
		File dump = new File(name);
		

		return temp.renameTo(dump);
	}
	
	
	private boolean removeBookingRecord(String toRemove,String unique) throws Exception {
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
				System.out.println("ID in line 176 = "+ID+" vs "+ toRemove);
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
	
	public boolean CancelRequest(String ID, String unique) throws Exception {
//		customer c = new customer();
//		for(ParkingSpot s: c.ViewBookings()) {
//			if(s.getUnique().equals(unique)) return c.CancelBookings(ID, unique);
//		}
//		for(ParkingSpot s: Spots) {
//			if(s.ID.equals(ID)) {
//				s.freeSpot();
//				System.out.println("cancelling request with ID = "+s.ID);
//				
//			}
//		}
//		return false;
		return removeBookingRecord(ID, unique);
	}
	
	private boolean updateBookingStatus(String toChange, String unique) throws IOException {
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
				if(values[0].equals(unique)){ 
					System.out.println("RE WRITING : "+ID+" , "+"BOOKED"+" , "+Expiration);
					pw.println(uniqueID+","+ID+ "," + license +"," +PaymentStatus +","+"FILLED"+","+start+","+ Expiration+","+date+","+rate); 
				}
				else {
					System.out.println("KEEPING : "+ID+" , "+PaymentStatus+" , "+Expiration);
					pw.println(uniqueID+","+ID+ "," + license +"," +PaymentStatus +","+avail+","+start+","+ Expiration+","+date+","+rate); 
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
	
	public boolean GrantRequest(String ID, String unique) throws IOException {
		for(ParkingSpot s: Spots) {
			if(s.ID.equals(ID)) {
				s.setAvail();
				return updateBookingStatus(ID, unique);
			}
		}
		return false;
	}
	
	public List<ParkingSpot> visit(customer customer) {
		return customer.accept(this);
	}

	public List<ParkingSpot> getSpots() {
		return Spots;
	}
	
	public String getID() {
		return ID;
	}
	
	public String getPassword() {
		return password;
	}
}
