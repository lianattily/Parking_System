package EECS3311.project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Alert;

public class Officer implements User {
	private String ID, password;
	List<ParkingSpot> Spots;
	boolean LogInStatus;
	
	
	public Officer() {
		LogInStatus=false;
		Spots = new ArrayList<ParkingSpot>();
		fill();
	}
	
	private void fill() {
		String path = "ParkingDatabase.txt"; 
		String line = ""; 
		try {
			BufferedReader br  = new BufferedReader(new FileReader(path));
			while((line = br.readLine())!=null) {
				String [] values = line.split(",");
				ParkingSpot s = new ParkingSpot(values[0]);
				Spots.add(s);
				}
			}
		catch (Exception e) {
			
		}
	}
	
	@Override
	public void CreatAccount(String Fname, String Lname, String email, String Password) {
		//nothing to do here
		
	}
	
	public void SaveRecord(String ID, boolean b, PaymentStatus paymentStatus) throws IOException {
		FileWriter fw = new FileWriter("Parkingdatabase.txt", true); 
		BufferedWriter bw = new BufferedWriter(fw); 
		PrintWriter pw = new PrintWriter(bw); 
		pw.println(ID+","+b+","+paymentStatus); 
		pw.flush(); 
		pw.close();
	}
	@Override
	public boolean LogIn(String ID, String password) {
		// TODO Auto-generated method stub
		if(this.ID.equals(ID) && this.password.equals(password))LogInStatus=true;
		
		return LogInStatus;
	}
	
	public ParkingSpot AddSpot(String ID) throws IOException {
		
		ParkingSpot s = new ParkingSpot(ID);
		System.out.println(Spots.add(s));
		SaveRecord(ID, s.getisFilled(), s.getisPaid());
		return s;
	}
	
	public void RemoveSpot(ParkingSpot s) {
		Spots.remove(s);
	}
	
	public void CancelRequest(String ID) {
		for(ParkingSpot s: Spots) {
			if(s.ID.equals(ID)) {
				s.freeSpot();
				break;
			}
		}
	}
	
	public void GrantRequest(int ID) {
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
		System.out.println("IN OFFICER");
		for(ParkingSpot s: Spots) {
			System.out.println(s.getID());
		}
		return Spots;
	}
}
