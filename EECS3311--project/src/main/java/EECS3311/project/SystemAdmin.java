package EECS3311.project;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class SystemAdmin implements User {
	private String email, password;
	private boolean LogInStatus;
	private Map<Integer, Officer> OFFICERS;
	private int cnt = 0;
	
	//constructor
	public SystemAdmin() {
		LogInStatus=false;
		email = "MASTER";
		password = "A365@MASTERLOGIN!";
	}
	@Override
	public void CreatAccount(String Fname, String Lname, String email, String Password) {
		//NOTHING TO DO FOR SYSTEM ADMIN --> they login using master login
		
	}
	
	public void SaveRecord(String Fname, String Lname, String email, String Password) throws IOException {
		FileWriter fw = new FileWriter("database.txt", true); 
		BufferedWriter bw = new BufferedWriter(fw); 
		PrintWriter pw = new PrintWriter(bw); 
		pw.println(Fname+","+Lname+","+email+","+Password); 
		pw.flush(); 
		pw.close();
	}
	@Override
	public boolean LogIn(String email, String password) {
		// TODO Auto-generated method stub
		if(email==this.email && password==this.password)LogInStatus=true;
		
		return LogInStatus;
	}

	public boolean AddOfficer() {
		Officer newOfficer = new Officer();
		OFFICERS.put(cnt, newOfficer);
		cnt++;
		return false;
	}

	public boolean RemoveOfficer(int ID) {
		if(OFFICERS.get(ID)!=null) {
		OFFICERS.remove(ID);
		cnt--;
		return true;
		}
		return false;
	}
	
	public void ChangePaymentStatus() {
		
	}
}
