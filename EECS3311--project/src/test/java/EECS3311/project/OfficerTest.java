package EECS3311.project;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

public class OfficerTest {
	
	@Test
	public void Constructors() {
		Officer o2= new Officer("demo","demo");
		assertEquals(o2.getID(),"demo");
	}
	
	@Test
	public void Login() throws IOException {
		System.out.println("************************Login************************");
		Officer o2= new Officer();
		SystemAdmin admin = new SystemAdmin();
		admin.AddOfficer("demo", "demo");
		assertEquals(true,o2.LogIn("demo", "demo"));
	}
	
	@Test
	public void AddSpot() throws Exception {
		System.out.println("************************AddSpot************************");
		Officer o = new Officer();
		LocalDate date = LocalDate.now();
		ParkingSpot s = new ParkingSpot("M5A0E2","TOR2020","UNPAID", 12,30, 2,35,date, 5);
		o.AddSpot("M5A0E2", 5);
		System.out.println("s = o.AddSpot() = "+s.ID);
		List<ParkingSpot> spot = o.getSpots();
		o.RemoveSpot(s);

		assertEquals(spot.contains(s),false);
	}
	
	@Test
	public void updateBookingStatus() throws IOException {
		System.out.println("************************GRANT************************");
		LocalDate date = LocalDate.now();
		 ParkingSpot s = new ParkingSpot("M5A0E2","TOR2020","UNPAID", 12,30, 2,35,date, 5);
		 String uniqueID = UUID.randomUUID().toString().substring(0, 5);
		 Officer o = new Officer();
		 o.AddSpot(s.ID,5);
		 customer c = new customer();
		 c.bookSpot(uniqueID, s.getID(), s);
		 o.GrantRequest(s.getID(), s.getUnique());	 
	}
	
	@Test
	public void cancelRequest() throws Exception {
		System.out.println("************************CANCEL************************");
		LocalDate date = LocalDate.now();
		 ParkingSpot s = new ParkingSpot("M5A0E2","TOR2020","UNPAID", 12,30, 2,35,date, 5);
		 String uniqueID = UUID.randomUUID().toString().substring(0, 5);
		 Officer o = new Officer();
		 o.AddSpot(s.ID,5);
		 customer c = new customer();
		 c.bookSpot(uniqueID, s.getID(), s);
		 o.CancelRequest(s.getID(), s.getUnique());	 
	}
}
