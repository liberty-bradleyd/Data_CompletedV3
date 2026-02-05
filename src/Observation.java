/* Activity 2 */

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class Observation implements Comparable{
	private String id;
	private double temp;    // in fahrenheit
	private int windDir;   // in degrees
	private String description;
	private double pressure; // in mb
	private int humidity; 
	private double windSpeed;
	//private boolean shortDescription;
	//added
	private String iconURL;
	// **2026**static fields that represent the XML notes for each
	// part of the observation
	public static final String ID = "station_id";
	public static final String TEMPF = "temp_f";
	public static final String WINDDIR = "wind_degrees";
	public static final String DESCRIPTION = "weather";
	public static final String PRESSURE_MB = "pressure_mb";
	public static final String HUMIDITY = "relative_humidity";
	public static final String WINDSPEED_KTS = "wind_kt";
	public static final String ICONURL_BASE = "icon_url_base";
	public static final String ICONURL_FILE = "icon_url_name";
	
	/**
	 * Constructs an Observation object with the specified parameters and sets
	 * the other instance data to their default values. toString will output a
	 * short observation, if this constructor is used.
	 * @param the weather station id
	 * @param description Short description of the current weather
	 * @param temp temperature
	 * @param windDir wind direction in degrees
	 */
// **2025 change - removed this overload **/
//	public Observation(String id, String description, double temp, int windDir) {
//		this(id, description, temp, windDir,0,0,0);
//		shortDescription = true;
//	}
//
//	/**
//	 * Constructs an Observation object with the specified parameters.
//	 * toString will output a full observation, if this constructor is used.
//	 * @param the weather station id
//	 * @param description Short description of the current weather
//	 * @param temp temperature
//	 * @param windDir wind direction in degrees
//	 * @param windSpeed wind speed in knots
//	 * @param pressure barometric pressure in mb
//	 * @param humidity relative humidity
//	 */
//	public Observation(String id, String description, double temp, int windDir, double windSpeed, double pressure, int humidity) {
//		this.id = id;
//		this.description = description;
//		this.temp = temp;
//		this.windDir = windDir;
//		this.windSpeed = windSpeed;
//		this.pressure = pressure;
//		this.humidity = humidity;
////		shortDescription = false;
//
//	}

	/**
	 * Constructs an Observation object with the specified parameters.
	 * toString will output a full observation, if this constructor is used.
	 * @param the weather station id
	 * @param description Short description of the current weather
	 * @param temp temperature
	 * @param windDir wind direction in degrees
	 * @param windSpeed wind speed in knots
	 * @param pressure barometric pressure in mb
	 * @param humidity relative humidity
	 * @param iconURLBase the base URL, including the domain name for an icon depicting the weather
	 * @param iconURLName the icon name depicting the weather
	 */
	public Observation(String id, String description, double temp, int windDir, double windSpeed, double pressure, int humidity, String iconURLBase, String  iconURLName) {
		this.id = id;
		this.description = description;
		this.temp = temp;
		this.windDir = windDir;
		this.windSpeed = windSpeed;
		this.pressure = pressure;
		this.humidity = humidity;
//		shortDescription = false;
		this.iconURL = iconURLBase+iconURLName;

	}
	/**
	 * determine if the temperature of this observation is colder than other.
	 * @param other the other other observation
	 * @return true if this observation's temperature is colder than other; otherwise false.
	 */
	public boolean colderThan(Observation other) {
		return this.temp < other.temp;
	}


	/**
	 * @return the temp
	 */
	public double getTemp() {
		return temp;
	}

	/**
	 * @return the wind Direction
	 */
	public int getWindDir() {
		return windDir;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the pressure
	 */
	public double getPressure() {
		return pressure;
	}

	/**
	 * @return the humidity
	 */
	public int getHumidity() {
		return humidity;
	}

	/**
	 * @return the windSpeed
	 */
	public double getWindSpeed() {
		return windSpeed;
	}

	/**
	 * Gets the weather station ID
	 * @return the weather station id
	 */
	public String getId() {
		return id;
	}
	/**
	 * Gets the URL to the icon depicting the weather. 
	 * This is a combination of the baseURL and the icon filename
	 * @return A full URL to the icon depicting the weather. 
	 */
	public String getIconURL() {
		/*
		 * Had to ensure that the protocol was https instead of http or the request would not go
		 * through when we tried to retrieve the icon and display it.
		 */
		if (!iconURL.substring(0, 4).equals("https")) {
			iconURL = iconURL.substring(0,4) + "s" + iconURL.substring(4);
		}
		return iconURL;
	}

	/**
	 * @return a String representing this observation
	 */
	public String toString() {
//		if (shortDescription)
//			return (id + ": " + temp + " degrees; " + description + " (wind: " + windDir + " degrees)");
//		else
			return (id + ": " + temp + " degrees; " + description + " (wind: " + windSpeed + " knots @ " + windDir + " degrees); barometric pressure: " + pressure + "; relativity humidity: " + humidity);
	}

	/**
	 * Compares this object with the specified object for order.
	 * @return a value > 0 if this observation has a higher temperature than other;
	 *           value < 0 if this observation has a higher temperature than other;
	 *           value = 0 if this observation's temperature is equal to other.
	 * NOTE: Polymorphism is needed for this, so I didn't include this in the project spec.
	 */
	@Override
	public int compareTo(Object other) {
		if (other instanceof Observation) {
			Observation otherOb = (Observation) other;
			if (this.getTemp() == otherOb.getTemp()) {
				return 0;
			}
			else if (this.getTemp() > otherOb.getTemp()) {
				return 1;
			}
			else {
				return -1;
			}
		}
		return -1;
	}
	
	/**
	 * Returns the Beaufort number on the Beaufort wind force scale.
	 * @return the Beaufot number for the current observation
	 * For more details, see https://en.wikipedia.org/wiki/Beaufort_scale
	 */
	public int getBeaufortNumber() {
		if (windSpeed < 1){
			return 0; 
		}else if (windSpeed <= 3) {
			return 1;
		}else if (windSpeed <= 6) {
			return 2;
		}else if (windSpeed <= 10) {
			return 3;
		}else if (windSpeed <= 16) {
			return 4;
		}else if (windSpeed <= 21) {
			return 5;
		}else if (windSpeed <= 27) {
			return 6;
		}else if (windSpeed <= 33) {
			return 7;
		}else if (windSpeed <= 40) {
			return 8;
		}else if (windSpeed <= 47) {
			return 9;
		}else if (windSpeed <= 55) {
			return 10;
		}else if (windSpeed <= 63) {
			return 11;
		}else {
			return 12;
		}
	}
	   /**
	    * Returns a string representation of the wind conditions. Summarize the
	    * Beaufort number into 4 categories; calm; breezy; wind flags out; storm 
	    * @return a string representation of the wind conditions
	    */
	   public String getWindConditions() {
		   //String wind = getId() + ": ";
		   int beaufort = getBeaufortNumber();
		   if (beaufort >=10 ) {
			   return "Storm is coming";
		   }else if (beaufort >= 6 ) {
			   return "Wind flags are out";
		   }else if (beaufort >= 2) {
			   return "Nice breeze today";
		   }else {
			   return "Wind is calm";
		   }

	   }
	   public String getWarning() {
		   int beaufort = getBeaufortNumber();
		   switch (beaufort) {
		   case 12: return "Hurricane Warning";
		   case 10:
		   case 11: return "Storm Warning"; 
		   case 6:
		   case 7:
		   case 8:
		   case 9: return "Small Craft Advisory"; 
		   default: return "";
		   }
	   }
	   
		public Observation(String stationID) {
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
				 id = ""; 
				 temp = Double.MIN_VALUE;    // in fahrenheit
				 windDir = Integer.MIN_VALUE;   // in degrees
				 description = "";
				 pressure = Double.MIN_VALUE; // in mb
				 humidity = Integer.MIN_VALUE; 
				 windSpeed = Double.MIN_VALUE;
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
				this.iconURL = iconBase+iconFile;
				xmlStream.close();
				//return new Observation(id, description, temp, windDir, windSpeed, pressure, humidity, iconBase, iconFile);

			} catch (Exception e) {
				e.printStackTrace();
			}



			//return obs;
		}
		public Observation(String dir, String filename) {
			 id = "";
			 temp = Double.MIN_VALUE;    // in fahrenheit
			 windDir = Integer.MIN_VALUE;   // in degrees
			 description = "";
			 pressure = Double.MIN_VALUE; // in mb
			 humidity = Integer.MIN_VALUE; 
			 windSpeed = Double.MIN_VALUE;
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
				iconURL= iconBase+iconFile;
				//return new Observation(id, description, temp, windDir, windSpeed, pressure, humidity, iconBase, iconFile);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			//return null;
		}
}
