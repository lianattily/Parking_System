
package EECS3311.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

public class AdminTest {

	SystemAdmin admin = new SystemAdmin();
	@Test
	public void Login() {
		assertEquals(admin.LogIn("MASTER", "MASTERLOGIN!"), true);
		assertEquals(admin.getID().equals("MASTER"),true);
		assertEquals(admin.getPASS().equals("MASTERLOGIN!"),true);
		assertNotEquals(admin.getList().size(),0);
	}
	
	@Test
	public void AddOfficer() throws IOException {
		System.out.println("************************AddOfficer************************");
		assertEquals(admin.AddOfficer("demo", "demo"),true);
	}
	
	@Test
	public void removeOff() throws Exception {
		assertEquals(admin.RemoveOfficer("demo"),true);
	}
	
	@Test
	public void UpdatePaymentStatus() throws IOException {
		System.out.println("************************UpdatePayment************************");
		LocalDate date = LocalDate.now();
		ParkingSpot s = new ParkingSpot("M5A0E2","TOR2020","UNPAID", 12,30, 2,35,date, 5);
		Officer off = new Officer();
		off.AddSpot(s.getID(), 9);
		List<ParkingSpot> spot = off.getSpots();
		assertEquals(admin.ChangePaymentStatus(s),true);
	}
}
