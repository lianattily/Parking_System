package EECS3311.project;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Officer implements User {
	private String Fname, Lname, email, password;

	boolean LogInStatus;
	public Officer() {
		LogInStatus=false;
	}
	@Override
	public void CreatAccount(String Fname, String Lname, String email, String Password) {
		this.Fname=Fname;
		this.Lname=Lname;
		this.email=email;
		this.password=Password;
		try {
			SaveRecord(Fname,Lname,email,Password);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
	
	public void AddSpot() {
		
	}
	
	public void RemoveSpot() {
		
	}
	
	public void CancelRequest() {
		
	}
	
	public void GrantRequest() {
		
	}
	
	public void visit(customer customer) {
		
	}

}
