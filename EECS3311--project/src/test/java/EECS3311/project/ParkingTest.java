package EECS3311.project;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ParkingTest {
	
	@BeforeEach
	public void SetUp() {
		
		
	}
	
	@Test
	public void ParkingSpotConstructors() {
		ParkingSpot s = new ParkingSpot("M5A 0C4",8);
		assertEquals(s.isFilled, false);
		assertEquals(s.ID.equals("M5A 0C4"), true);
		
		
		//String spacenum,int startH, int startM, int endH, int endM)
		ParkingSpot s1 = new ParkingSpot("M5A 0E2","TOR2020", 12,30, 2,35);
		assertEquals(s1.getID().equals("M5A 0E2"),true);
		assertEquals(s1.isFilled(), true);
		assertEquals(s1.ExpirationTime, 2);
		
		//(String spacenum, String license, int start, int end)
		ParkingSpot s2 = new ParkingSpot("M5A 0K7", "TOR2020",12,15,21,45);
		assertEquals(s2.getID().equals("M5A 0K7"),true);
		assertEquals(s2.isFilled, true);
		assertEquals(s2.ExpirationTime, 21);
	}
	@Test
	public void SettersAndGetters() {
		ParkingSpot s = new ParkingSpot("N5A 0C1", "TOR2020", 12,30, 13,30);
		assertEquals(s.isFilled, true);
		s.freeSpot();
		assertEquals(s.isFilled, false);
		s.setAvail();
		assertEquals(s.isFilled, true);
		
		
		s.SetRate(5);
		System.out.println(s.CalculatePayment());
		int amount = (13-12)*5;
		System.out.println(amount);
		assertEquals(s.CalculatePayment(),amount);
	}
}
