package EECS3311.project;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

public class OfficerTest {
	
	@Test
	public void Constructors() {
		Officer o1 = new Officer();
		Officer o2= new Officer("demo","demoPassword");
		assertEquals(o2.getID(),"demo");
	}
	
	@Test
	public void Login() {
		Officer o = new Officer();
		Officer o2= new Officer("demo","demoPassword");
		assertEquals(o.LogIn("demo", "demoPassword"),true);
	}
	
	@Test
	public void AddSpot() throws IOException {
		Officer o = new Officer();
		ParkingSpot s = o.AddSpot("M9K 4K4");
		o.RemoveSpot(s);
	}
}
