package parking.assistant.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class DatabaseHandler {
	private static DatabaseHandler handler; 
	private static final String DB_URL = "jdbc:derby:database;create=true"; 
	private static Connection conn = null; 
	private static Statement stmt = null;

	public DatabaseHandler() {
		createConnection();
		setupParkingTable();
	}

	void createConnection() {
		try {
			Class.forName( "org.apache.derby.jdbc.EmbeddedDriver" ).newInstance();
			conn = DriverManager.getConnection(DB_URL);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	void setupParkingTable(){ 
		String TABLE_NAME = "PARKING"; 
		try { stmt = conn.createStatement(); 
		DatabaseMetaData dbm=conn.getMetaData(); 
		ResultSet tables = dbm.getTables(null,null, TABLE_NAME.toUpperCase(), null); 
		if(tables.next()){ 	System.out.println("Table "+TABLE_NAME+" already exists. Ready to go!"); }
		else { stmt.execute("CREAT TABLE "+ TABLE_NAME +" (" 
				+ "    spacenum varchar(200) primary key, \n" 
				+ "    platenum varchar(200),\n" 
				+ "    starttime varchar(100), \n"
				+ "    endtime varchar(100)\n"
				+ "    isAvail boolean default true" 
				+" )");
		}
		}
		catch(Exception e) {
		}
	}

	public ResultSet execQuery(String query){ 
		ResultSet result; 
		try{ 
			stmt = conn.createStatement(); 
			result = stmt.executeQuery(query);} 
		catch(Exception e) { return null;}  
		finally {} return result; 
	}

	public boolean execaction(String qu) {
		try { 
			stmt = conn.createStatement(); 
			stmt.execute(qu); 
			return true; 
		} catch (Exception e){ 
			JOptionPane.showMessageDialog(null, "ERROR: "+ e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			return false;} 
		finally{}
	}
}

