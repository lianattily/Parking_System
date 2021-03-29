package EECS3311.project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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

	
	public boolean RemoveOfficer(String ID) {
		
		for(int i=0;i<cnt;i++) {
			if(OFFICERS.get(i).getID().equals(ID)) {
				OFFICERS.remove(i);
				cnt--;
				return true;
				}
		}
		return false;
	}
	
	public void ChangePaymentStatus() {
		
	}
	public String getID() {
		return email;
	}
	public String getPASS() {
		return password;
	}
	
	public List<Officer> getList(){
		List<Officer> temp = new ArrayList<Officer>();
		for(int i = 0;i < cnt ;i++) {
			temp.add(OFFICERS.get(i));
		}
		return temp;
	}
	
}
