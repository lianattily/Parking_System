package EECS3311.project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SystemAdmin implements User {
	private String email, password;
	private boolean LogInStatus;
	private List<Officer> OFFICERS; //initialize with entries in database
	private Integer cnt = 0;
	
	//constructor
	public SystemAdmin() {
		LogInStatus=false;
		email = "MASTER";
		password = "MASTERLOGIN!";
		OFFICERS = new LinkedList<Officer>();
		init();
	}
	@Override
	public void CreatAccount(String Fname, String Lname, String email, String Password) {
		//NOTHING TO DO FOR SYSTEM ADMIN --> they login using master login
		
	}
	
	private void init() {
		String path = "OfficerDatabase.txt"; 
		String line = ""; 
		try {
			BufferedReader br  = new BufferedReader(new FileReader(path));
			while((line = br.readLine())!=null) {
				String [] values = line.split(",");
				Officer s = new Officer(values[0], values[1]);
				OFFICERS.add(s);
				}
			}
		catch (Exception e) {
			
		}
		
	}
	
	public void SaveRecord(String ID, String Password) throws IOException {
		FileWriter fw = new FileWriter("OfficerDatabase.txt", true); 
		BufferedWriter bw = new BufferedWriter(fw); 
		PrintWriter pw = new PrintWriter(bw); 
		pw.println(ID+","+Password); 
		pw.flush(); 
		pw.close();
	}
	@Override
	public boolean LogIn(String email, String password) {
		// TODO Auto-generated method stub
		if(email==this.email && password==this.password)LogInStatus=true;
		
		return LogInStatus;
	}

	public boolean AddOfficer(String ID, String password) throws IOException {
		Officer newOfficer = new Officer(ID, password);
		if(!OFFICERS.contains(newOfficer)) {
		OFFICERS.add(newOfficer);
		cnt++;
		SaveRecord(ID, password);
		return true;
		}
		return false;
	}

	
	public boolean RemoveOfficer(String ID) throws Exception {
		
		for(int i=0;i<cnt;i++) {
			if(OFFICERS.get(i).getID().equals(ID)) {
				OFFICERS.remove(i);
				cnt--;
				return removeRecord(ID);
				}
		}
		return false;
	}
	
	public boolean ChangePaymentStatus(ParkingSpot s) throws IOException {
		s.isPaid= s.isPaid.PAID;
		//boolean success = updateDatabase(s.getID(), s.isPaid.toString(), s.ExpirationTime.toString());
		return true;
		
	}
	
	/* 
	 * Update BookingsDatabase when payment status changes
	 */
	private boolean updateDatabase(String id,  String PaymentStatus, String Expiration) throws IOException {
//		customer cust = new customer();
//		List<ParkingSpot> list = cust.ViewBookings();
//		for(ParkingSpot s: list) {
//			if(s.getID().equals(id)) {
//				FileWriter fw = new FileWriter("BookingsDatabase.txt", true); 
//				BufferedWriter bw = new BufferedWriter(fw); 
//				PrintWriter pw = new PrintWriter(bw); 
//				pw.println(id+","+PaymentStatus+","+Expiration); 
//				pw.flush(); 
//				pw.close();
//				return true;
//			}
//		}
		
		
		return false;
	}
	
	public String getID() {
		return email;
	}
	public String getPASS() {
		return password;
	}
	
	public List<Officer> getList(){
		return OFFICERS;
	}
	
	private boolean removeRecord(String toRemove) throws Exception {
		//OFFICERS
//		FileWriter fw = new FileWriter("OfficerDatabase.txt", true); 
//		BufferedWriter bw = new BufferedWriter(fw); 
//		PrintWriter pw = new PrintWriter(bw); 
//		pw.println(ID+","+Password); 
//		pw.flush(); 
//		pw.close();
		
		File inputFile = new File("OfficerDatabase.txt");
		File tempFile = new File("TempFile.txt");

		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

		String currentLine;

		while((currentLine = reader.readLine()) != null) {
		    // trim newline when comparing with lineToRemove
		    //String trimmedLine = currentLine.trim();
		    if(currentLine.equals(toRemove)) {
		    	continue;
		    }
		    
		    writer.write(currentLine + System.getProperty("line.separator"));
		}
		writer.close(); 
		reader.close(); 
		boolean successful = tempFile.renameTo(inputFile);
		return successful;
	}
}
