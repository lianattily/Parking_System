package EECS3311.project;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
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
		SystemAdmin admin = new SystemAdmin();
		admin.AddOfficer("demo", "demopassword");
		Officer o2= new Officer();
		assertEquals(o2.LogIn("demo", "demoPassword"),true);
	}
	
	@Test
	public void AddSpot() throws Exception {
		Officer o = new Officer();
		ParkingSpot s = o.AddSpot("M9K4K4",8);
		List<ParkingSpot> spot = o.getSpots();
		assertEquals(spot.contains(s),true);
		o.RemoveSpot(s);
	}
}
