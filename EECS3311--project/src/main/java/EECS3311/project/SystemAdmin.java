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
			br.close();
		}
		catch (Exception e) {

		}

	}

	public void SaveRecord(String ID, String Password) throws IOException {
		for(Officer o: OFFICERS) {
			if(o.getID().equals(ID)) return;
		}
		FileWriter fw = new FileWriter("OfficerDatabase.txt", true); 
		BufferedWriter bw = new BufferedWriter(fw); 
		PrintWriter pw = new PrintWriter(bw); 
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
		boolean success = updateDatabase(s.getID(), s.isPaid.toString(), s.ExpirationTime.toString());
		return success;

	}

	/* 
	 * Update BookingsDatabase when payment status changes
	 */
	private boolean updateDatabase(String id,  String PaymentStatus, String Expiration) throws IOException {
		File file = new File("BookingsDatabase.txt");
		File temp = new File("TempFile.txt");
		String ID = "", Status = "", Exp = "";  
		FileWriter fw = new FileWriter("TempFile.txt",false); 
		BufferedWriter bw = new BufferedWriter(fw); 
		PrintWriter pw = new PrintWriter(bw); 
		Scanner x = new Scanner(new File("BookingsDatabase.txt")); 
		x.useDelimiter("[,\n]"); 
		while(x.hasNext()) { 
			ID = x.next(); 
			Status = x.next(); 
			Exp = x.next();
			System.out.println("ID in line 176 = "+ID+" vs "+ id);
			if(ID.equals(id)){ 
				System.out.println("RE WRITING : "+ID+" , "+PaymentStatus+" , "+Expiration);
				pw.println(ID+ "," + PaymentStatus +"," + Expiration); 
			}
			else {
				pw.println(ID+ "," + Status +"," + Exp); 
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
