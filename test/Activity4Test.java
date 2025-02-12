import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class Activity4Test {
	static ForecastPeriod sammamish;
	static ForecastPeriod purdue;

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

	@Test
	void testWeatherStationGetForecast1of8() throws JSONException, IOException {
		WeatherStation kLAF= new WeatherStation("Lafayette, Purdue University Airport (KLAF)","KLAF","IN", 40.41,-86.95); 
		ForecastPeriod[] periods = kLAF.getForecast();
		assertTrue(periods.length > 0);
	}
	@Test
	void testWeatherStationGetForecast2of8() throws JSONException, IOException {
		WeatherStation kUUU= new WeatherStation("Newport, Newport State Airport","KUUU","RI", 41.53,-71.28); 
		ForecastPeriod[] periods = kUUU.getForecast();
		assertTrue(periods.length > 0);
	}

	@Test
	void testWeatherStationGetForecast3of8() throws JSONException, IOException {
		WeatherStation kSEA= new WeatherStation("Seattle, Seattle-Tacoma International Airport","KSEA","WA", 47.44472,-122.31361); 
		ForecastPeriod[] periods = kSEA.getForecast();
		assertEquals(LocalDateTime.now(ZoneId.of("America/Los_Angeles")).getDayOfMonth(),periods[0].getStart().getDayOfMonth());
	}
	@Test
	void testWeatherStationGetForecast4of8() throws JSONException, IOException {
		WeatherStation kSMP= new WeatherStation("Stampede Pass","KSMP","WA", 47.427,-121.418); 
		ForecastPeriod[] periods = kSMP.getForecast();
		assertEquals(LocalDateTime.now(ZoneId.of("America/Los_Angeles")).getDayOfMonth(),periods[0].getStart().getDayOfMonth());
	}
	@Test
	void testWeatherStationGetForecast5of8() throws JSONException, IOException {
		WeatherStation kLAF= new WeatherStation("Lafayette, Purdue University Airport (KLAF)","KLAF","IN", 40.41,-86.95); 
		ForecastPeriod[] periods = kLAF.getForecast();
		//checking precipChance for all periods
		int percent = -1;
		for (ForecastPeriod period : periods) {
			percent = period.getPrecipChancePercent();
		}
		assertNotEquals(-1, percent);
	}
	@Test
	void testWeatherStationGetForecast6of8() throws JSONException, IOException {
		WeatherStation kTTN= new WeatherStation("Trenton, Mercer County Airport (KTTN)","KTTN","NJ", 40.28,-74.82); 
		ForecastPeriod[] periods = kTTN.getForecast();
		//checking precipChance for all periods
		int percent = -1;
		for (ForecastPeriod period : periods) {
			percent = period.getPrecipChancePercent();
		}
		assertNotEquals(-1, percent);
	}
	@Test
	void testWeatherStationGetForecast7of8() throws JSONException, IOException {
		WeatherStation kTTN= new WeatherStation("Trenton, Mercer County Airport (KTTN)","KTTN","NJ", 40.28,-74.82); 
		ForecastPeriod[] periods = kTTN.getForecast();
		//checking precipChance for all periods
		boolean isValidRange = true;
		for (ForecastPeriod period : periods) {
			int percent = period.getPrecipChancePercent();
			isValidRange = percent >= 0 && percent <= 100;
		}
		assertTrue(isValidRange);
	}
	@Test
	void testWeatherStationGetForecast8of8() throws JSONException, IOException {
		WeatherStation kSEA= new WeatherStation("Seattle, Seattle-Tacoma International Airport","KSEA","WA", 47.44472,-122.31361); 
		ForecastPeriod[] periods = kSEA.getForecast();
		//checking precipChance for all periods
		boolean isValidRange = true;
		for (ForecastPeriod period : periods) {
			int percent = period.getPrecipChancePercent();
			isValidRange = percent >= 0 && percent <= 100;
		}
		assertTrue(isValidRange);
	}

}
