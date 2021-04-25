
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
		assertEquals(admin.LogIn("MASTER", "MASTER"), true);
		assertEquals(admin.getID().equals("MASTER"),true);
		assertEquals(admin.getPASS().equals("MASTER"),true);
		assertNotEquals(admin.getList().size(),0);
	}

	@Test
	public void AddOfficer() throws IOException {
		System.out.println("************************AddOfficer************************");
		admin.AddOfficer("demoTest", "demoTest");
	}

	@Test
	public void removeOff() throws Exception {
		System.out.println("************************RemoveOfficer************************");
		admin.AddOfficer("LIAN", "TEST");
		admin.RemoveOfficer("LIAN");
	}

	@Test
	public void UpdatePaymentStatus() throws IOException {
		System.out.println("************************UpdatePayment************************");
		LocalDate date = LocalDate.now();
		ParkingSpot s = new ParkingSpot("abcs" ,"M5A0E2","TOR2020","PENDING","UNPAID", 12,30, 2,35,date, 5);
		Officer off = new Officer();
		off.AddSpot(s.getID(), 9);
		customer c = new customer();
		c.bookSpot(s.getUnique(), s.getID(), s);
		assertEquals(admin.ChangePaymentStatus(s),true);
	}
}
