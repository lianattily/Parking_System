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
		System.out.println(Spots.add(s)+"  current spots:");
		for(ParkingSpot sp: Spots) {
			if(sp.getID().equals(ID)) return null;
			System.out.println(sp.getID()+" , "+sp.getRate());
		}
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
		String ID = "", PaymentStatus = "", Avail = "";  
		FileWriter fw = new FileWriter("TempFile.txt",false); 
		BufferedWriter bw = new BufferedWriter(fw); 
		PrintWriter pw = new PrintWriter(bw); 
		Scanner x = new Scanner(new File(name)); 
		x.useDelimiter("[,\n]"); 
		while(x.hasNext()) { 
			ID = x.next(); 
			Avail = x.next();
			PaymentStatus = x.next(); 
			
			System.out.println("ID in line 176 = "+ID+" vs "+ toRemove);
			if(!ID.equals(toRemove)){ 
				System.out.println("RE WRITING : "+ID+" , "+PaymentStatus+" , "+Avail);
				pw.println(ID+ "," +Avail+"," +PaymentStatus ); 
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
		Scanner x = new Scanner(new File("BookingsDatabase.txt")); 
		x.useDelimiter("[,\n]"); 
		while(x.hasNext()) { 
			uniqueID=x.next();
			ID = x.next(); 
			license = x.next();
			PaymentStatus = x.next(); 
			avail=x.next();
			//N3W0A5,TORONTO123,UNPAID,12,30,22,45,22/04/2021,12
			start = x.next()+","+x.next();
			Expiration = x.next()+","+x.next();
			date=x.next();
			rate=x.next();
			System.out.println("ID in line 176 = "+ID+" vs "+ toRemove);
			if(!ID.equals(toRemove) && !uniqueID.equals(unique)){ 
				System.out.println("RE WRITING : "+ID+" , "+PaymentStatus+" , "+Expiration);
				pw.println(uniqueID+","+ID+ "," + license +"," +PaymentStatus+","+avail +","+start+","+ Expiration+","+date+","+rate); 

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
	
	public boolean CancelRequest(String ID, String unique) throws Exception {
		for(ParkingSpot s: Spots) {
			if(s.ID.equals(ID) && s.getUnique().equals(unique)) {
				s.freeSpot();
				return removeBookingRecord(ID, unique);
			}
		}
		return false;
	}
	
	private boolean updateBookingStatus(String toChange) throws IOException {
		File file = new File("BookingsDatabase.txt");
		File temp = new File("TempFile.txt");
		String ID = "", PaymentStatus = "", Expiration = "", license = "", start = "", date="", rate="", uniqueID="",avail="";  
		FileWriter fw = new FileWriter("TempFile.txt",false); 
		BufferedWriter bw = new BufferedWriter(fw); 
		PrintWriter pw = new PrintWriter(bw); 
		Scanner x = new Scanner(new File("BookingsDatabase.txt")); 
		x.useDelimiter("[,\n]"); 
		while(x.hasNext()) { 
			uniqueID=x.next();
			ID = x.next(); 
			license = x.next();
			PaymentStatus = x.next(); 
			avail = x.next();
			//84a56,N3W0A5,TORONTO123,PENDING,PENDING,12,30,10,30,22/04/2021,12
			start = x.next()+","+x.next();
			Expiration = x.next()+","+x.next();
			date=x.next();
			rate=x.next();
			System.out.println("ID in line 176 = "+ID+" vs "+ toChange);
			if(ID.equals(toChange)){ 
				System.out.println("RE WRITING : "+ID+" , "+"BOOKED"+" , "+Expiration);
				pw.println(uniqueID+","+ID+ "," + license +"," +PaymentStatus +","+"FILLED"+","+start+","+ Expiration+","+date+","+rate); 
			}
			else {
				System.out.println("KEEPING : "+ID+" , "+PaymentStatus+" , "+Expiration);
				pw.println(uniqueID+","+ID+ "," + license +"," +PaymentStatus +","+avail+","+start+","+ Expiration+","+date+","+rate); 
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
	
	public boolean GrantRequest(String ID) throws IOException {
		for(ParkingSpot s: Spots) {
			if(s.ID.equals(ID)) {
				s.setAvail();
				return updateBookingStatus(ID);
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
