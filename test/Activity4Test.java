import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
 
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class Activity4Test {
	static ForecastPeriod sammamish;
	static ForecastPeriod purdue;
//	static WeatherStation[] stations;
//	static ArrayList<WeatherStation> stationsAsList;
//	static ArrayList<WeatherStation> waStations;
//	static ArrayList<WeatherStation> riStations;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		sammamish = new ForecastPeriod("Saturday Night", "2025-01-25T18:00:00-08:00", "2025-01-26T06:00:00-08:00", 27,
				"F", 0, 
				"3 mph", "ENE", "https://api.weather.gov/icons/land/night/skc?size=medium",
				"Clear", "Clear, with a low around 27.");
		purdue = new ForecastPeriod("This Afternoon", "2025-01-23T14:00:00-05:00", "2025-01-23T18:00:00-05:00", 26,
				"F", 30,
				"13 mph", "NW", "https://api.weather.gov/icons/land/day/snow,30?size=medium",
				"Scattered Snow Showers",
				"Scattered snow showers before 3pm. Mostly cloudy. High near 26, with temperatures falling to around 24 in the afternoon. Northwest wind around 13 mph, with gusts as high as 20 mph. Chance of precipitation is 30%.");
	}

	@Test
	void testConstructor1of20() {
		assertEquals("Saturday Night",sammamish.getName());
	}
	@Test
	void testConstructor2of20() {
		assertEquals("This Afternoon",purdue.getName());
	}
	@Test
	void testConstructor3of20() {
		assertEquals(25,sammamish.getStart().getDayOfMonth());
	}
	@Test
	void testConstructor4of20() {
		assertEquals(23,purdue.getStart().getDayOfYear());
	}
	@Test
	void testConstructor5of20() {
		assertEquals(6,sammamish.getEnd().getHour());
	}
	@Test
	void testConstructor6of20() {
		assertEquals(DayOfWeek.THURSDAY,purdue.getEnd().getDayOfWeek());
	}
	@Test
	void testConstructor7of20() {
		assertEquals(27,sammamish.getTemp());
	}
	@Test
	void testConstructor8of20() {
		assertEquals(26,purdue.getTemp());
	}
	@Test
	void testConstructor9of20() {
		assertEquals("F",sammamish.getTempUnit());
	}
	@Test
	void testConstructor10of20() {
		assertEquals("F",purdue.getTempUnit());
	}

	@Test
	void testConstructor11of20() {
		assertEquals(3,sammamish.getWindSpeed());
	}
	@Test
	void testConstructor12of20() {
		assertEquals(13,purdue.getWindSpeed());
	}
	@Test
	void testConstructor13of20() {
		assertEquals("ENE",sammamish.getWindDirection());
	}
	@Test
	void testConstructor14of20() {
		assertEquals("NW",purdue.getWindDirection());
	}
	@Test
	void testConstructor15of20() {
		assertEquals(0,sammamish.getPrecipChancePercent());
	}
	@Test
	void testConstructor16of20() {
		assertEquals(30,purdue.getPrecipChancePercent());
	}
	@Test
	void testConstructor17of20() {
		assertEquals("Clear",sammamish.getShortForecast());
	}
	@Test
	void testConstructor18of20() {
		assertEquals("Scattered Snow Showers",purdue.getShortForecast());
	}
	@Test
	void testConstructor19of20() {
		assertEquals("Clear, with a low around 27.",sammamish.getDetailedForecast());
	}
	@Test
	void testConstructor20of20() {
		assertEquals("Scattered snow showers before 3pm. Mostly cloudy. High near 26, with temperatures falling to around 24 in the afternoon. Northwest wind around 13 mph, with gusts as high as 20 mph. Chance of precipitation is 30%.",purdue.getDetailedForecast());
	}

}
