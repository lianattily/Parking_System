
package EECS3311.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

public class AdminTest {

	SystemAdmin admin = new SystemAdmin();
	@Test
	public void Login() {
		assertEquals(admin.LogIn("MASTER", "MASTERLOGIN!"), true);
		assertEquals(admin.getID(),"MASTER");
		assertEquals(admin.getPASS(),"MASTERLOGIN!");
		assertNotEquals(admin.getList().size(),0);
	}
	
	@Test
	public void AddOfficer() throws IOException {
		assertEquals(admin.AddOfficer("demo", "demo"),true);
	}
	
	@Test
	public void removeOff() throws Exception {
		assertEquals(admin.RemoveOfficer("demo"),true);
	}
	
	@Test
	public void UpdatePaymentStatus() throws IOException {
		ParkingSpot s = new ParkingSpot("L9A5G2",5);
		Officer off = new Officer();
		List<ParkingSpot> spot = off.getSpots();
		assertEquals(admin.ChangePaymentStatus(spot.get(0)),true);
	}
}
