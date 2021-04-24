package EECS3311.project;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

public class OfficerTest {
	
	@Test
	public void Constructors() {
		Officer o1 = new Officer();
		Officer o2= new Officer("demo","demoPassword");
		assertEquals(o2.getID(),"demo");
	}
	
	@Test
	public void Login() throws IOException {
		System.out.println("************************Login************************");
		Officer o2= new Officer();
		assertEquals(o2.LogIn("demo", "demo"),true);
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
		assertEquals(spot.contains(s),true);
		o.RemoveSpot(s);

		assertEquals(spot.contains(s),false);
	}
}
