import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import core.data.DataSource;

import org.junit.jupiter.api.BeforeAll;

public class Activity1Test {
	static Activity2 botSEA;
	static Observation obSEA;
	static Activity2 botBOS;
	static Observation obBOS;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		obSEA = new Observation("KSEA");
		obBOS = new Observation("KBOS");
	}

	@Test
	void testConstructor1of4() {
		Observation ob = new Observation("KSEA","Light Rain",38.0,170,11.0,997.6,89, "http://www.iconstore.com/", "cloudy.ico");
		assertEquals(38.0,ob.getTemp());
	}
	@Test
	void testConstructor2of4() {
		Observation ob = new Observation("KSEA","Light Rain",28.0,170,11.0,997.6,89, "http://www.iconstore.com/", "cloudy.ico");
		assertEquals(89,ob.getHumidity());
	}
	@Test
	void testConstructor3of4() {
		Observation ob = new Observation("KSEA","Partly Cloudy",28.0,170,11.0,997.6,89, "http://www.iconstore.com/", "cloudy.ico");
		assertEquals(11.0,ob.getWindSpeed());
	}

	@Test
	void testConstructor4of4() {
		Observation ob = new Observation("KSEA","Light Rain",38.0,170,11.0,997.6,89, "http://www.iconstore.com/", "rain.ico");
		assertEquals(170,ob.getWindDir());
	}


	@Test
	void testToString1of2() {
		Observation ob = new Observation("KSEA","Light Rain",38.0,170,10.0,997.6,89, "http://www.iconstore.com/", "cloudy.ico");
		assertEquals("KSEA: 38.0 degrees; Light Rain (wind: 10.0 knots @ 170 degrees); barometric pressure: 997.6; relativity humidity: 89",ob.toString());
	}
	@Test
	void testToString2of2() {
		Observation obUUU = new Observation("KUUU","Sailing Time",74.0,170,23.9,998.4,49, "http://www.iconstore.com/", "cloudy.ico");
		assertEquals("KUUU: 74.0 degrees; Sailing Time (wind: 23.9 knots @ 170 degrees); barometric pressure: 998.4; relativity humidity: 49",obUUU.toString());
	}
	// accessor methods
	@Test
	void testColderThan1of2() {
		Observation obSEA = new Observation("KSEA","Light Rain Fog/Mist",39.0,190,11.0,997.6,89, "http://www.iconstore.com/", "rain.ico");
		Observation obRNT = new Observation("KRNT","Light Rain",37.0,0,11.0,997.6,89, "http://www.iconstore.com/", "rain.ico");
		assertFalse(obSEA.colderThan(obRNT));
	}
	@Test
	void testColderThan2of2() {
		Observation obSEA = new Observation("KSEA","Light Rain Fog/Mist",37.0,190,11.0,998.4,86, "http://www.iconstore.com/", "rain.ico");
		Observation obSMP = new Observation("KSMP","Light snow",29.0,0,0,998.4,89, "http://www.iconstore.com/", "rain.ico");
		assertTrue(obSMP.colderThan(obSEA));
	}
	@Test
	void testGetId1of2() {
		Observation ob = new Observation("KS52","Light Rain",28.0,170,11.0,997.6,89, "http://www.iconstore.com/", "rain.ico");
		assertEquals("KS52",ob.getId());
	}

	@Test
	void testGetId2of2() {
		Observation ob = new Observation("KSMP","Light Rain",28.0,170,11.0,997.6,89, "http://www.iconstore.com/", "rain.ico");
		assertEquals("KSMP",ob.getId());
	}
	@Test
	void testGetTemp1of2() {
		Observation ob = new Observation("KSEA","Light Rain",28.0,170,11.0,997.6,89, "http://www.iconstore.com/", "rain.ico");
		assertEquals(28.0,ob.getTemp());

	}

	@Test
	void testGetTemp2of2() {
		Observation ob = new Observation("KSEA","Light Rain",14.0,170,11.0,997.6,89, "http://www.iconstore.com/", "rain.ico");
		assertEquals(14.0,ob.getTemp());

	}
	
	@Test
	void testGetDescription1of2() {
		Observation ob = new Observation("KSEA","Light Rain",14.0,170,11.0,997.6,89, "http://www.iconstore.com/", "rain.ico");
		assertEquals("Light Rain",ob.getDescription());
		
	}
	@Test
	void testGetDescription2of2() {
		Observation obSMP = new Observation("KSMP","Light snow",29.0,170,48,998.4,89, "http://www.iconstore.com/", "rain.ico");
		assertEquals("Light snow",obSMP.getDescription());
	}

	@Test
	void testGetHumidity1of2() {
		Observation ob = new Observation("KSEA","Light Rain",14.0,170,11.0,997.6,89, "http://www.iconstore.com/", "rain.ico");
		assertEquals(89,ob.getHumidity());
		
	}
	@Test
	void testGetHumidity2of2() {
		Observation obSMP = new Observation("KSMP","Light snow",29.0,170,48,998.4,49, "http://www.iconstore.com/", "rain.ico");
		assertEquals(49,obSMP.getHumidity());
	}
	@Test
	void testGetIconURL1of2() {
		Observation ob = new Observation("KSEA","Heavy Snow",28.0,170,11.0,997.6,89, "http://www.iconstore.com/", "snow.ico");
		assertEquals("http://www.iconstore.com/snow.ico",ob.getIconURL());
		
	}
	@Test
	void testGetIconURL2of2() {
		Observation ob = new Observation("KSEA","Partly Cloudy",28.0,170,11.0,997.6,89, "http://www.iconstore.com/", "cloudy.ico");
		assertEquals("http://www.iconstore.com/cloudy.ico",ob.getIconURL());
	}

	@Test
	void testGetPressure1of2() {
		Observation ob = new Observation("KSEA","Light Rain",14.0,170,11.0,997.6,89, "http://www.iconstore.com/", "rain.ico");
		assertEquals(997.6,ob.getPressure());
		
	}
	@Test
	void testGetPressure2of2() {
		Observation obSMP = new Observation("KSMP","Light snow",29.0,170,48,998.4,49, "http://www.iconstore.com/", "rain.ico");
		assertEquals(998.4,obSMP.getPressure());
	}
	@Test
	void testGetWindDir1of2() {
		Observation ob = new Observation("KSEA","Light Rain",14.0,170,11.0,997.6,89, "http://www.iconstore.com/", "rain.ico");
		assertEquals(170,ob.getWindDir());
		
	}
	@Test
	void testGetWindDir2of2() {
		Observation obSMP = new Observation("KSMP","Light snow",29.0,270,48,998.4,49, "http://www.iconstore.com/", "rain.ico");
		assertEquals(270,obSMP.getWindDir());
	}

	@Test
	void testGetWindSpeed1of2() {
		Observation ob = new Observation("KSEA","Light Rain",14.0,170,11.0,997.6,89, "http://www.iconstore.com/", "rain.ico");
		assertEquals(11.0,ob.getWindSpeed());
		
	}
	@Test
	void testGetWindSpeed2of2() {
		Observation obSMP = new Observation("KSMP","Light snow",29.0,270,48,998.4,49, "http://www.iconstore.com/", "rain.ico");
		assertEquals(48,obSMP.getWindSpeed());
	}

	@Test
	void testGetBeaufortNumber1of3() {
		Observation obSMP = new Observation("KSMP","Light snow",29.0,170,48,998.4,89, "http://www.iconstore.com/", "rain.ico");
		assertEquals(10,obSMP.getBeaufortNumber());
	}

	@Test
	void testGetBeaufortNumber2of3() {
		Observation obSMP = new Observation("KSMP","Light snow",29.0,170,10,998.4,89, "http://www.iconstore.com/", "rain.ico");
		assertEquals(3,obSMP.getBeaufortNumber());
	}
	@Test
	void testGetBeaufortNumber3of3() {
		Observation obSMP = new Observation("KSMP","Light snow",29.0,170,11.0,997.6,89, "http://www.iconstore.com/", "rain.ico");
		assertEquals(4,obSMP.getBeaufortNumber());
	}

	@Test
	void testGetWarning1of4() {
		//Hurricane IDA. 2021 strongest hurricane
		Observation obHUM = new Observation("KHUM","Hurricane approaching",79.0,170,129,929,89, "http://www.iconstore.com/", "rain.ico");
		assertEquals("Hurricane Warning",obHUM.getWarning());

	}

	@Test
	void testGetWarning2of4() {
		//Newport, Rhode Island
		Observation obUUU = new Observation("KUUU","Sailing Time",330,170,33.9,998.4,49, "http://www.iconstore.com/", "rain.ico");
		assertEquals("Small Craft Advisory",obUUU.getWarning());

	}
	@Test
	void testGetWarning3of4() {
		Observation obRNT = new Observation("KRNT","Light Rain",37.0,0,11.0,997.6,89, "http://www.iconstore.com/", "rain.ico");
		assertEquals("",obRNT.getWarning());
	}
	@Test
	void testGetWarning4of4() {
		Observation obSEA = new Observation("KSEA","Light Rain Fog/Mist",37.0,190,59,998.4,86, "http://www.iconstore.com/", "rain.ico");
		assertEquals("Storm Warning",obSEA.getWarning());
	}

	@Test
	void testObservationStringString1of3() {
		Observation ob = new Observation("data", "kjrb.csv");
		assertEquals("KJRB",ob.getId());
	}
	@Test
	void testObservationStringString2of3(){
		Observation ob = new Observation("data", "ksmp.csv");
		assertEquals(34.0,ob.getTemp());
	}
	@Test
	void testObservationStringString3of3() {
		Observation ob = new Observation("data", "phog.csv");
		assertEquals(10,ob.getWindSpeed());

	}
	@Test
	void testObservationString1of4() {
		String stationID = "KSMP";
		DataSource ds = DataSource.connect("http://weather.gov/xml/current_obs/" + stationID + ".xml"); 
		ds.setCacheTimeout(15 * 60);  
		ds.load();

		Observation ob = ds.fetch("Observation", "station_id", "weather", "temp_f", "wind_degrees", "wind_kt", "pressure_mb", "relative_humidity", "icon_url_base", "icon_url_name");

		Observation ob2 = new Observation(stationID);
		assertEquals(ob.getId(), ob2.getId());
	}
	@Test
	void testObservationString2of4() {
		String stationID = "KSMP";
		DataSource ds = DataSource.connect("http://weather.gov/xml/current_obs/" + stationID + ".xml"); 
		ds.setCacheTimeout(15 * 60);  
		ds.load();

		Observation ob = ds.fetch("Observation", "station_id", "weather", "temp_f", "wind_degrees", "wind_kt", "pressure_mb", "relative_humidity", "icon_url_base", "icon_url_name");

		Observation ob2 = new Observation(stationID);
		assertEquals(ob2.getTemp(), ob2.getTemp());
	}	
	@Test
	void testObservationString3of4() {
		String stationID = "KAUM";
		DataSource ds = DataSource.connect("http://weather.gov/xml/current_obs/" + stationID + ".xml"); 
		ds.setCacheTimeout(15 * 60);  
		ds.load();

		Observation ob = ds.fetch("Observation", "station_id", "weather", "temp_f", "wind_degrees", "wind_kt", "pressure_mb", "relative_humidity", "icon_url_base", "icon_url_name");

		Observation ob2 = new Observation(stationID);
		assertEquals(ob2.getWindSpeed(), ob2.getWindSpeed());
	}	
	@Test
	void testObservationString4of4() {
		String stationID = "KNYC";
		DataSource ds = DataSource.connect("http://weather.gov/xml/current_obs/" + stationID + ".xml"); 
		ds.setCacheTimeout(15 * 60);  
		ds.load();

		Observation ob = ds.fetch("Observation", "station_id", "weather", "temp_f", "wind_degrees", "wind_kt", "pressure_mb", "relative_humidity", "icon_url_base", "icon_url_name");

		Observation ob2 = new Observation(stationID);
		assertEquals(ob2.getPressure(), ob2.getPressure());
	}	

}
