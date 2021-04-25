package EECS3311.project;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ParkingTest {
	ParkingSpot s;
	LocalDate date;
	@BeforeEach
	public void SetUp() {

		date = LocalDate.now();
		s = new ParkingSpot("M5A0E2","TOR2020","UNPAID", 12,30, 13,30,date, 5);

	}

	@Test
	public void ParkingSpotConstructors() {
		System.out.println("**********************ParkingTests constructors*********************** ");
		ParkingSpot s = new ParkingSpot("M5A0C4",8);
		assertEquals(s.isFilled, false);
		assertEquals(s.ID.equals("M5A0C4"), true);
		System.out.println(s.getID());
		assertEquals(s.getID().equals("M5A0C4"),true);
		assertEquals(s.isFilled(), false);
		assertEquals(s.ExpirationTime,null);

	}
	@Test
	public void SettersAndGetters() {
		System.out.println("**********************ParkingTests setters/getters*********************** ");
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
