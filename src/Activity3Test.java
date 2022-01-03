import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Activity3Test {

	@Test
	void testWeatherStation1of3() {
		WeatherStation kSEA= new WeatherStation("Seattle, Seattle-Tacoma International Airport","KSEA","WA", 47.44472,122.31361); 
		assertEquals("KSEA",kSEA.getId());
	}
	@Test
	void testWeatherStation2of3() {
		WeatherStation kSMP= new WeatherStation("Stampede Pass","KSMP","WA", 47.427,121.418); 
		assertEquals("Stampede Pass",kSMP.getName());
	}
	@Test
	void testWeatherStation3of3() {
		WeatherStation kUUU= new WeatherStation("Newport, Newport State Airport","KUUU","RI", 41.53,71.28); 
		assertTrue(kUUU.isLocatedInState("RI"));
	}

	@Test
	void testGetAllStationsArray() {
		fail("Not yet implemented");
	}

	@Test
	void testGetAllStationsList() {
		fail("Not yet implemented");
	}

	@Test
	void testGetStationsInState() {
		fail("Not yet implemented");
	}

	@Test
	void testGetColdestInState() {
		fail("Not yet implemented");
	}

	@Test
	void testGetStationsInStateSortedByName() {
		fail("Not yet implemented");
	}

	@Test
	void testInsertionSort() {
		fail("Not yet implemented");
	}

}
