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
		password = "MASTER";
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

		for(Officer o: OFFICERS) {

			System.out.println("Officer = "+o.getID()+" vs "+ID);
			if(o.getID().equals(ID)) {
				System.out.println("FOUND OFFICER TO REMOVE");
				return removeRecord(ID);
			}
		}
		return false;

	}

	public boolean ChangePaymentStatus(ParkingSpot s) throws IOException {
		s.isPaid= s.isPaid.PAID;
		System.out.println("unique id = "+s.getUnique());
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
				System.out.println("ID in line 176 = "+unique+" vs "+ uniqueID);
				if(values[0].equals(unique)){ 
					System.out.println("RE WRITING : "+uniqueID+","+ID+ "," + license +"," +"PAID" +","+avail+","+start+","+ Expiration+","+date+", rate = "+rate);
					pw.println(uniqueID+","+ID+ "," + license +"," +"PAID" +","+avail+","+start+","+ Expiration+","+date+","+rate); 
				}
				else {
					System.out.println("KEEPING : "+uniqueID+","+ID+ "," + license +"," +PaymentStatus +","+avail+","+start+","+ Expiration+","+date+",  rate = "+rate);
					pw.println(uniqueID+","+ID+ "," + license +"," +PaymentStatus+","+avail+"," +start+","+ Expiration+","+date+","+rate); 
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
		String line = ""; 
		try {
			BufferedReader br  = new BufferedReader(new FileReader(name));
			while((line = br.readLine())!=null) {
				String [] values = line.split(",");
				ID =values[0]; 
				Password=values[1];

				System.out.println("ID in line 176 = "+ID+" vs "+ toRemove);
				if(!ID.equals(toRemove)){ 
					System.out.println("KEEPING OFFICER "+ID);
					pw.println(ID+ "," + Password); 
				}
			}br.close();
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
}
