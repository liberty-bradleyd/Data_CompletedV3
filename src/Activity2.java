import java.io.File;
import java.util.Scanner;

import core.data.*;

public class Activity2 {

	/**
	 * Gets an Observation object with the station id, short weather description, temperature,
	 * wind direction, wind speed in knots, barometric pressure in mb, and the relative humidity.
	 * @return an Observation object
	 */
	public static Observation getObservation(String stationID) {
		DataSource ds = DataSource.connect("http://weather.gov/xml/current_obs/" + stationID + ".xml"); 
		ds.setCacheTimeout(15 * 60);  
		ds.load();

		Observation ob = ds.fetch("Observation", "station_id", "weather", "temp_f", "wind_degrees", "wind_kt", "pressure_mb", "relative_humidity");
		return ob;


	}
	public static Observation getObservation(String filename, String stationID) {
		 String id = "";
		 double temp = Double.MIN_VALUE;    // in fahrenheit
		 int windDir = Integer.MIN_VALUE;   // in degrees
		 String description = "";
		 double pressure = Double.MIN_VALUE; // in mb
		 int humidity = Integer.MIN_VALUE; 
		 double windSpeed = Double.MIN_VALUE;
		//private boolean shortDescription;
		//added
		 String iconBase = "";
		 String iconFile = "";

		// open file
		try {
			File csvFile = new File("data/"+ filename);
			Scanner fileReader = new Scanner(csvFile);
			
			// Read row by row
			// first row is column headers
			
			boolean first = true;
			String[] columnHeaders = null;
			String[] observation = null;
			while (fileReader.hasNextLine()) {
				String line = fileReader.nextLine();
				if (first) {
					columnHeaders = line.split(",");
					first = false;
				}
				else {
					observation = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
					break;
				}
			}
//			// debug code
//			if (columnHeaders.length == observation.length) {
//				for (int i = 0; i < columnHeaders.length; i++) {
//					System.out.println(columnHeaders[i] + ":" + observation[i]);
//				}
//			}else {
//				System.out.println("Headers: " + columnHeaders.length);
//				System.out.println("Observation: " + observation.length);
//			}
			// Extract Observation details
			for (int i = 0; i < observation.length; i++) {
				switch (columnHeaders[i]) {
				case Observation.ID: id = observation[i];break;
				case Observation.TEMPF: temp = Double.parseDouble(observation[i]);break;
				case Observation.WINDDIR: windDir = Integer.parseInt(observation[i]);break;
				case Observation.DESCRIPTION: description = observation[i];break;
				case Observation.PRESSURE_MB: pressure = Double.parseDouble(observation[i]); break;
				case Observation.HUMIDITY: humidity = Integer.parseInt(observation[i]); break;
				case Observation.WINDSPEED_KTS: windSpeed = Double.parseDouble(observation[i]);break;
				case Observation.ICONURL_BASE: iconBase = observation[i];break;
				case Observation.ICONURL_FILE: iconFile = observation[i];break;
				default: // skipping this piece of data 
				}
			} 
			return new Observation(id, description, temp, windDir, windSpeed, pressure, humidity, iconBase, iconFile);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	public static void main(String[] args) {

//		Observation ob1 = getObservation("KSEA");
//		Observation ob2 = getObservation("KRNT");
//		Observation ob3 = getObservation("KSMP");
//
//		System.out.println(ob1.toString());
//		System.out.println(ob2.toString());
//		System.out.println(ob3.toString());
//
//		if (ob2.colderThan(ob3)) {
//			System.out.println("It is colder at " +ob2.getId() + " than "  + ob3.getId());
//		}
//		else {
//			System.out.println("It is warmer at " +ob2.getId() + " than "  + ob3.getId());
//		}
//
//		System.out.println(ob2.getId() +": " + ob2.getWindConditions());
//		System.out.println(ob3.getId() +": " + ob3.getWindConditions());
		Observation ob2 = getObservation("krnt.csv","KRNT");
		System.out.println(ob2);
	}



}



