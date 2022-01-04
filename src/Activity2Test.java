import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

class Activity2Test {
	static WeatherBot botSEA;
	static Observation obSEAShort;
	static WeatherBot botBOS;
	static Observation obBOSLong;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		botSEA = new WeatherBot("KSEA");
		obSEAShort = botSEA.getShortObservation();
		botBOS = new WeatherBot("KBOS");
		obBOSLong = botBOS.getLongObservation();
	}

	@Test
	void testConstructor1of4() {
		Observation ob = new Observation("KSEA","Light Rain",38.0,170);
		assertEquals(38.0,ob.getTemp());
	}
	@Test
	void testConstructor2of4() {
		Observation ob = new Observation("KSEA","Light Rain",28.0,170);
		assertEquals(0,ob.getBeaufortNumber());
	}
	@Test
	void testConstructor3of4() {
		Observation ob = new Observation("KSEA","Light Rain",28.0,170,11.0,997.6,89);
		assertEquals(28.0,ob.getTemp());
	}

	@Test
	void testConstructor4of4() {
		Observation ob = new Observation("KSEA","Light Rain",38.0,170,11.0,997.6,89);
		assertEquals(4,ob.getBeaufortNumber());
	}

	@Test
	void testToString1of4() {
		Observation ob = new Observation("KSEA","Light Rain",38.0,170);
		assertEquals("KSEA: 38.0 degrees; Light Rain (wind: 170 degrees)",ob.toString());
	}

	@Test
	void testToString2of4() {
		Observation ob = new Observation("KSMP","Light Snow",29.0,0);
		assertEquals("KSMP: 29.0 degrees; Light Snow (wind: 0 degrees)",ob.toString());
	}

	@Test
	void testToString3of4() {
		Observation ob = new Observation("KSEA","Light Rain",38.0,170,10.0,997.6,89);
		assertEquals("KSEA: 38.0 degrees; Light Rain (wind: 10.0 knots @ 170 degrees); barometric pressure: 997.6; relativity humidity: 89",ob.toString());
	}
	@Test
	void testToString4of4() {
		Observation obUUU = new Observation("KUUU","Sailing Time",74.0,170,23.9,998.4,49);
		assertEquals("KUUU: 74.0 degrees; Sailing Time (wind: 23.9 knots @ 170 degrees); barometric pressure: 998.4; relativity humidity: 49",obUUU.toString());
	}

	@Test
	void testColderThan1of2() {
		Observation obSEA = new Observation("KSEA","Light Rain Fog/Mist",39.0,190);
		Observation obRNT = new Observation("KRNT","Light Rain",37.0,0);
		assertFalse(obSEA.colderThan(obRNT));
	}
	@Test
	void testColderThan2of2() {
		Observation obSEA = new Observation("KSEA","Light Rain Fog/Mist",37.0,190,11.0,998.4,86);
		Observation obSMP = new Observation("KSMP","Light snow",29.0,0,0,998.4,89);
		assertTrue(obSMP.colderThan(obSEA));
	}

	@Test
	void testGetBeaufortNumber1of3() {
		Observation obSMP = new Observation("KSMP","Light snow",29.0,170,48,998.4,89);
		assertEquals(10,obSMP.getBeaufortNumber());
	}

	@Test
	void testGetBeaufortNumber2of3() {
		Observation obSMP = new Observation("KSMP","Light snow",29.0,170,10,998.4,89);
		assertEquals(3,obSMP.getBeaufortNumber());
	}
	@Test
	void testGetBeaufortNumber3of3() {
		Observation obSMP = new Observation("KSMP","Light snow",29.0,170);
		assertEquals(0,obSMP.getBeaufortNumber());
	}

	@Test
	void testGetWindConditions1of4() {
		//Hurricane IDA. 2021 strongest hurricane
		Observation obHUM = new Observation("KHUM","Hurricane approaching",79.0,170,129,929,89);
		assertEquals("Storm is coming",obHUM.getWindConditions());

	}

	@Test
	void testGetWindConditions2of4() {
		//Newport, Rhode Island
		Observation obUUU = new Observation("KUUU","Sailing Time",330,170,23.9,998.4,49);
		assertEquals("Wind flags are out",obUUU.getWindConditions());

	}
	@Test
	void testGetWindConditions3of4() {
		Observation obRNT = new Observation("KRNT","Light Rain",37.0,0);
		assertEquals("Wind is calm",obRNT.getWindConditions());
	}
	@Test
	void testGetWindConditions4of4() {
		Observation obSEA = new Observation("KSEA","Light Rain Fog/Mist",37.0,190,11.0,998.4,86);
		assertEquals("Nice breeze today",obSEA.getWindConditions());
	}

	@Test
	void testGetId1of2() {
		Observation ob = new Observation("KS52","Light Rain",28.0,170);
		assertEquals("KS52",ob.getId());
	}

	@Test
	void testGetId2of2() {
		Observation ob = new Observation("KSMP","Light Rain",28.0,170,11.0,997.6,89);
		assertEquals("KSMP",ob.getId());
	}
	@Test
	void testGetTemp1of2() {
		Observation ob = new Observation("KSEA","Light Rain",28.0,170,11.0,997.6,89);
		assertEquals(28.0,ob.getTemp());

	}

	@Test
	void testGetTemp2of2() {
		Observation ob = new Observation("KSEA","Light Rain",14.0,170);
		assertEquals(14.0,ob.getTemp());

	}
	@Test
	void testgetShortObservation1of4() {
		assertEquals(0,obSEAShort.getBeaufortNumber());
	}
	@Test
	void testgetShortObservation2of4() {
		assertNotEquals(Integer.MIN_VALUE,obSEAShort.getTemp());
	}
	@Test
	void testgetShortObservation3of4() {
		assertEquals("KSEA",obSEAShort.getId());
	}
	@Test
	void testgetShortObservation4of4() {
		Observation obIntFalls = new Observation("KINL","Overcast with Haze",-24.0, 330);
		assertTrue(obIntFalls.colderThan(obSEAShort));
	}
	@Test
	void testgetLongObservation1of3() {
		// Boston is the windiest big city in the US.
		assertNotEquals(0,obBOSLong.getBeaufortNumber());
	}
	@Test
	void testgetLongObservation2of3() {
		// Boston is the windiest big city in the US.
		assertEquals("KBOS",obBOSLong.getId());
	}
	@Test
	void testgetLongObservation3of3() {
		// Boston is the windiest big city in the US.
		// International Falls, MN is typically the coldest in the US in winter
		Observation obIntFalls = new Observation("KINL","Overcast with Haze",-24.0, 330);
		assertTrue(obIntFalls.colderThan(obBOSLong));

	}
}
