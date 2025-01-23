/* Activity 2 */
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
	
	/**
	 * Constructs an Observation object with the specified parameters and sets
	 * the other instance data to their default values. toString will output a
	 * short observation, if this constructor is used.
	 * @param the weather station id
	 * @param description Short description of the current weather
	 * @param temp temperature
	 * @param windDir wind direction in degrees
	 */
//	public Observation(String id, String description, double temp, int windDir) {
//		this(id, description, temp, windDir,0,0,0);
//		shortDescription = true;
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
	 */
	public Observation(String id, String description, double temp, int windDir, double windSpeed, double pressure, int humidity) {
		this.id = id;
		this.description = description;
		this.temp = temp;
		this.windDir = windDir;
		this.windSpeed = windSpeed;
		this.pressure = pressure;
		this.humidity = humidity;
//		shortDescription = false;

	}

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
}
