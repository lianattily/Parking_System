package EECS3311.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.IOException;
import java.util.List;

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
		ParkingSpot s = new ParkingSpot("N5A0C1", "TOR2020", 12,15, 13,30);
		Officer o = new Officer();
		o.AddSpot(s.ID,5);
		assertEquals(customer.bookSpot(s.ID,s), true);
		assertNotEquals(customer.ViewBookings().size(),0);
	}
	
	@Test
	public void PayTest() throws Exception {
		PaymentMethod method = new Debit();
		customer.setMethod(method);
		assertEquals(customer.Pay("N5A0C1"), true);
	}
	
	@Test
	public void removeRecord() throws Exception {
		List<ParkingSpot> s = customer.ViewBookings();
		assertEquals(customer.CancelBookings(s.get(0).ID),true);
	}
	
	@Test
	public void getRate() {
		List<ParkingSpot> s = customer.ViewBookings();
		customer.getRate(s.get(0).ID);
	}
	
}
