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
		String path = "ParkingDatabase.txt"; 
		String line = ""; 
		try {
			BufferedReader br  = new BufferedReader(new FileReader(path));
			while((line = br.readLine())!=null) {
				String [] values = line.split(",");
				
				ParkingSpot s = new ParkingSpot(values[0],Integer.parseInt(values[1]));
				Spots.add(s);
				}
			br.close();
			}
		catch (Exception e) {
			
		}
	}
	
	@Override
	public void CreatAccount(String Fname, String Lname, String email, String Password) {
		//nothing to do here
		
	}
	
	public void SaveRecord(String ID, boolean b, PaymentStatus paymentStatus) throws IOException {
		for(ParkingSpot s: Spots) {
			if(s.getID().equals(ID)) return;
		}
		String avail="Booked";
		if(b) {
			avail ="Available";
		}
		FileWriter fw = new FileWriter("Parkingdatabase.txt", true); 
		BufferedWriter bw = new BufferedWriter(fw); 
		PrintWriter pw = new PrintWriter(bw); 
		pw.println(ID+","+avail+","+paymentStatus.toString()+","+"N/A"); 
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
		System.out.println(Spots.add(s));
		SaveRecord(ID, s.getisFilled(), s.getisPaid());
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
	
	public void CancelRequest(String ID) {
		for(ParkingSpot s: Spots) {
			if(s.ID.equals(ID)) {
				s.freeSpot();
				break;
			}
		}
	}
	
	public void GrantRequest(String ID) {
		for(ParkingSpot s: Spots) {
			if(s.ID.equals(ID)) {
				s.setAvail();
				break;
			}
		}
	}
	
	public void visit(customer customer) {
		customer.accept();
	}

	public List<ParkingSpot> getSpots() {
		return Spots;
	}
	
	public String getID() {
		return ID;
	}
}
