import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import core.data.*;

public class Activity2 {

	/**
	 * Gets an Observation object with the station id, short weather description, temperature,
	 * wind direction, wind speed in knots, barometric pressure in mb, and the relative humidity.
	 * @return an Observation object
	 */
	public static Observation getObservationOLD(String stationID) {
		DataSource ds = DataSource.connect("http://weather.gov/xml/current_obs/" + stationID + ".xml"); 
		ds.setCacheTimeout(15 * 60);  
		ds.load();

		Observation ob = ds.fetch("Observation", "station_id", "weather", "temp_f", "wind_degrees", "wind_kt", "pressure_mb", "relative_humidity");
		return ob;


	}
	public static String getObservationXMLFile(String xmlFilename, String stationID) {
		String urlString = "https://weather.gov/xml/current_obs/" + stationID + ".xml";
		String fileContents = "";
		try {
			// Create a URL object
			URL url = new URL(urlString);

			// Open an input stream from the URL and wrap it in a BufferedReader
			//            try (InputStream is = url.openStream();
			//                 InputStreamReader isr = new InputStreamReader(is);
			//                 BufferedReader br = new BufferedReader(isr)) {
			//
			//                String line;
			//                // Read the file line by line
			//                int count = 1;
			//                while ((line = br.readLine()) != null) {
			//                    fileContents += line + "\r\n";
			//                    System.out.println(count++);
			//                }
			//            }
			try (InputStream is = url.openStream();
					InputStreamReader isr = new InputStreamReader(is);
					Scanner fileIOReader = new Scanner(isr)) {
				File output = new File("data/" + xmlFilename);
				PrintStream ps = new PrintStream(output);


				String line;
				// Read the file line by line
				while (fileIOReader.hasNextLine()) {
					line = fileIOReader.nextLine();
					fileContents += line + "\n";
					ps.println(line);
				}
				fileIOReader.close();
				ps.close();
			}

		} catch (IOException e) {
			// Handle exceptions, such as a malformed URL or connection issues
			System.err.println("An error occurred while reading from the URL: " + e.getMessage());
			e.printStackTrace();
		}
		// Write out the XML file
		return fileContents;
	}
	public static Observation getObservation(String stationID) {
		Observation obs = null;
		try {
			// URL of the XML feed
			String urlString = "https://forecast.weather.gov/xml/current_obs/" + stationID + ".xml";

			// Create a URL and open connection
			URL url = new URL(urlString);
			URLConnection connection = url.openConnection();

			// Optional: set Accept header (not always needed, but can help)
			connection.addRequestProperty("Accept", "application/xml");

			// Get input stream from connection
			InputStream xmlStream = connection.getInputStream();

			// Setup XML parser
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

			// Parse XML
			Document doc = dBuilder.parse(xmlStream);
			doc.getDocumentElement().normalize();

			// Initialize the variables. It is possible that any given observation may be missing one or more
			// observation details.
			String id = ""; 
			double temp = Double.MIN_VALUE;    // in fahrenheit
			int windDir = Integer.MIN_VALUE;   // in degrees
			String description = "";
			double pressure = Double.MIN_VALUE; // in mb
			int humidity = Integer.MIN_VALUE; 
			double windSpeed = Double.MIN_VALUE;
			String iconBase = "";
			String iconFile = "";

			// get station id
			NodeList tempIDList = doc.getElementsByTagName(Observation.ID);
			if (tempIDList.getLength() > 0) {
				id = tempIDList.item(0).getTextContent();
			}
			// get temperature in Fahrenheit
			NodeList tempFList = doc.getElementsByTagName(Observation.TEMPF);
			if (tempFList.getLength() > 0) {
				temp = Double.parseDouble(tempFList.item(0).getTextContent());
			}
			// get wind direction in degrees
			NodeList tempWinDirList = doc.getElementsByTagName(Observation.WINDDIR);
			if (tempWinDirList.getLength() > 0) {
				windDir = Integer.parseInt(tempWinDirList.item(0).getTextContent());
			}

			// get general weather description
			NodeList weatherList = doc.getElementsByTagName(Observation.DESCRIPTION);
			if (weatherList.getLength() > 0) {
				description = weatherList.item(0).getTextContent();
			}
			// get barometric presssure
			NodeList pressureList = doc.getElementsByTagName(Observation.PRESSURE_MB);
			if (pressureList.getLength() > 0) {
				pressure = Double.parseDouble(pressureList.item(0).getTextContent());
			}

			// get humidity
			NodeList humidityList = doc.getElementsByTagName(Observation.HUMIDITY);
			if (humidityList.getLength() > 0) {
				humidity = Integer.parseInt(humidityList.item(0).getTextContent());
			}
			// get wind speed in kts
			NodeList tempWindSpeedList = doc.getElementsByTagName(Observation.WINDSPEED_KTS);
			if (tempWindSpeedList.getLength() > 0) {
				windSpeed = Integer.parseInt(tempWindSpeedList.item(0).getTextContent());
			}
			// get icon base URL
			NodeList iconBaseList = doc.getElementsByTagName(Observation.ICONURL_BASE);
			if (iconBaseList.getLength() > 0) {
				iconBase = iconBaseList.item(0).getTextContent();
			}
			// get icon file
			NodeList iconFileList = doc.getElementsByTagName(Observation.ICONURL_FILE);
			if (iconFileList.getLength() > 0) {
				iconFile = iconFileList.item(0).getTextContent();
			}

			xmlStream.close();
			return new Observation(id, description, temp, windDir, windSpeed, pressure, humidity, iconBase, iconFile);

		} catch (Exception e) {
			e.printStackTrace();
		}



		return obs;
	}
	public static Observation getObservation(String filename, String dir) {
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
			File csvFile = new File(dir + "/"+ filename);
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
		Observation ob2 = new Observation("data", "krnt.csv");
		System.out.println(ob2);

		Observation ob3 = new Observation("data", "kjrb.csv");
		System.out.println(ob3);
		Observation ob4 = new Observation("data", "ksea.csv");
		System.out.println(ob4);

		Observation ob5 = new Observation("data", "ksmp.csv");
		System.out.println(ob5);
		Observation ob6 = new Observation("data", "phog.csv");
		System.out.println(ob6);

		Observation ob8 = new Observation("KSEA");
		System.out.println(ob8);
	}



}



