package EECS3311.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CustomerTest {

	customer customer = new customer();
	@BeforeEach
	public void SetUp() {
		customer.CreatAccount("Lian", "Attily", "yorku@gmail.com", "testpassword");
	}
	
	@Test
	public void LogInTest() {
		customer.LogIn("yorku@gmail.com", "testpassword");
		assertEquals(customer.getStatus(),true);
	}
	
	@Test
	public void BookSpot() throws IOException {
		System.out.println("************************BookSpot************************");
		LocalDate date = LocalDate.now();
		ParkingSpot s = new ParkingSpot("M5A0E2","TOR2020","UNPAID", 12,30, 2,35,date, 5);
		Officer o = new Officer();
		o.AddSpot(s.ID,5);
		String uniqueID = UUID.randomUUID().toString().substring(0, 5);
		assertEquals(customer.bookSpot(uniqueID,s.ID,s), true);
		assertNotEquals(customer.ViewBookings().size(),0);
	}
	
	@Test
	public void PayTest() throws Exception {
		System.out.println("************************PayTest************************");
		PaymentMethod method = new Debit();
		customer.setMethod(method);
		LocalDate date = LocalDate.now();
		ParkingSpot s = new ParkingSpot("M5A0E2","TOR2020","UNPAID", 12,30, 2,35,date, 5);
		assertEquals(customer.Pay(s), true);
	}
	
	@Test
	public void removeRecord() throws Exception {
		System.out.println("************************RemoveRecord************************");
		LocalDate date = LocalDate.now();
		ParkingSpot s = new ParkingSpot("M5A0E2","TOR2020","UNPAID", 12,30, 2,35,date, 5);
		String uniqueID = UUID.randomUUID().toString().substring(0, 5);
		customer.bookSpot(uniqueID,"M5A0E2", s);
		System.out.println("s.get(0).ID"+s.ID);
		assertEquals(customer.CancelBookings(s.ID),true);
	}
	
	@Test
	public void getRate() throws IOException {
		System.out.println("************************GetRate************************");
		LocalDate date = LocalDate.now();
		ParkingSpot s = new ParkingSpot("M5A0E2","TOR2020","UNPAID", 12,30, 2,35,date, 5);
		String uniqueID = UUID.randomUUID().toString().substring(0, 5);
		customer.bookSpot(uniqueID,"M5A0E2", s);
		customer.getRate(s.ID);
	}
	
}
