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
import java.util.Scanner;

public class SystemAdmin implements User {
	private String email, password;
	private boolean LogInStatus;
	private List<Officer> OFFICERS; //initialize with entries in database

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
			br.close();
		}
		catch (Exception e) {

		}

	}

	public void SaveRecord(String ID, String Password) throws IOException {
		FileWriter fw = new FileWriter("OfficerDatabase.txt", true); 
		BufferedWriter bw = new BufferedWriter(fw); 
		PrintWriter pw = new PrintWriter(bw); 
		Officer temp = new Officer(ID,Password);
		OFFICERS.add(temp);
		System.out.println("ADDING "+ID+"   "+Password);
		pw.println(ID+","+Password); 
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

	public boolean AddOfficer(String ID, String password) throws IOException {
		Officer newOfficer = new Officer(ID, password);
		for(Officer o: OFFICERS) {
			System.out.println("checking officer "+o.getID());
			if(o.getID().equals(ID)) return false;
		}
		
			OFFICERS.add(newOfficer);
			SaveRecord(ID, password);
			return true;
	}


	public boolean RemoveOfficer(String ID) throws Exception {

		for(int i=0;i<OFFICERS.size();i++) {
			if(OFFICERS.get(i).getID().equals(ID)) {
				OFFICERS.remove(i);
				return removeRecord(ID);
			}
		}
		return false;
	}

	public boolean ChangePaymentStatus(ParkingSpot s) throws IOException {
		s.isPaid= s.isPaid.PAID;
		
		return updateDatabase(s.ID, s.getUnique());

	}

	/* 
	 * Update BookingsDatabase when payment status changes
	 */
	private boolean updateDatabase(String id, String unique) throws IOException {
		File file = new File("BookingsDatabase.txt");
		File temp = new File("TempFile.txt");
		String ID = "", PaymentStatus = "", Expiration = "", license = "", start = "", date="", rate="",uniqueID="",avail="";  
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
			//84a56,N3W0A5,TORONTO123,PENDING,PENDING,12,30,10,30,22/04/2021,12
			start = x.next()+","+x.next();
			Expiration = x.next()+","+x.next();
			date=x.next();
			rate=x.next();
			System.out.println("ID in line 176 = "+ID+" vs "+ id);
			if(ID.equals(id) && unique.equals(uniqueID)){ 
				System.out.println("RE WRITING : "+ID+" , "+"PAID"+" , "+Expiration);
				pw.println(uniqueID+","+ID+ "," + license +"," +"PAID" +","+avail+","+start+","+ Expiration+","+date+","+rate); 
			}
			else {
				System.out.println("KEEPING : "+ID+" , "+PaymentStatus+" , "+Expiration);
				pw.println(uniqueID+","+ID+ "," + license +"," +PaymentStatus+avail+"," +","+start+","+ Expiration+","+date+","+rate); 
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

		String name = "OfficerDatabase.txt";
		File file = new File("OfficerDatabase.txt");
		File temp = new File("TempFile.txt");
		String ID = "", Password = "";  
		FileWriter fw = new FileWriter("TempFile.txt",false); 
		BufferedWriter bw = new BufferedWriter(fw); 
		PrintWriter pw = new PrintWriter(bw); 
		Scanner x = new Scanner(new File(name)); 
		x.useDelimiter("[,\n]"); 
		while(x.hasNext()) { 
			ID = x.next(); 
			Password = x.next(); 

			System.out.println("ID in line 176 = "+ID+" vs "+ toRemove);
			if(!ID.equals(toRemove)){ 
				System.out.println("KEEPING OFFICER "+ID);
				pw.println(ID+ "," + Password); 
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
}
