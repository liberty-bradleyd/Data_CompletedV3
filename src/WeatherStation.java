import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import core.data.DataSource;

/*
 Represents information about a NWS weather station
*/

public class WeatherStation implements Comparable{
   private String name;
   private String id;
   private String state;
   private double lat;
   private double lng;
   private Observation obs;
   
   //**2026**

   public static final String ID = "station_id";
   public static final String STATE = "state";
   public static final String STATION_NAME = "station_name";
   public static final String LATITUDE = "latitude";
   public static final String LONGITUDE = "longitude";

   /**
    * Constructor 
    * @param name The name of the station
    * @param id the id for the station
    * @param state the state in which this station is located
    * @param lat latitude of this station
    * @param lng longitude of this station
    */
   WeatherStation(String name, String id, String state, double lat, double lng) {
      this.name = name;
      this.id = id;
      this.state = state;  
      this.lat = lat;
      this.lng = lng;
 
   }
   
   /**
    * Gets the id of this station
    * @return the id of this station
    */
   public String getId() { 
      return id;
   }
   
   /**
    * Gets the name of this station
    * @return the name of this station
    */
   public String getName() { 
      return name;
   }
   /**
    * Gets latitude for this station
    * @returns the latitude for this station
    */
   public double getLatitude() {
	   return lat;
   }
   /**
    * Gets longitude for this station
    * @returns the longitude for this station
    */
   public double getLongitude() {
	   return lng;
   }
   /**
    * Gets state in which this station is located
    * @returns the state in which this station is located
    */
   public String getState() {
	   return state;
   }
    /**
    * Determines if this weather station is located in the given state
    * @param st the state
    * @return true if this weather station is located in st; otherwise false
    */
   public boolean isLocatedInState(String st) {
      return this.state.equals(st);
   }
   
   public Observation getCurrentObservation() {

	      DataSource ds = DataSource.connect("http://weather.gov/xml/current_obs/" + id + ".xml"); 
	      ds.setCacheTimeout(15 * 60);  
	      ds.load();
	      
	      obs = ds.fetch("Observation", "station_id", "weather", "temp_f", "wind_degrees", "wind_kt", "pressure_mb", "relative_humidity","icon_url_base","icon_url_name");
	   
	      return obs;

   }
   
   public ForecastPeriod[] getForecast() throws JSONException, IOException {
	   JSONObject jsonGrid = JSONHelper.readJsonFromUrl("https://api.weather.gov/points/" + lat + "," + lng);
	   JSONObject propertiesGrid = jsonGrid.getJSONObject("properties");
	   // JSONObject json = JSONHelper.readJsonFromUrl("https://api.weather.gov/gridpoints/SEW/134,66/forecast");
	   JSONObject json = JSONHelper.readJsonFromUrl(propertiesGrid.getString("forecast"));
	    //System.out.println(json.toString());
	    JSONObject properties= json.getJSONObject("properties");
	    JSONArray periods = properties.getJSONArray("periods");
	    ForecastPeriod[] forecasts = new ForecastPeriod[periods.length()];
	    for (int i = 0; i < periods.length(); i++) {
	    	JSONObject current = periods.getJSONObject(i);
	    	// precip chance can either be null or a number, which isn't easily 
	    	// mappable to a java type.
	    	int precipChancePercent =0;
	    	try {
	    		precipChancePercent = current.getJSONObject("probabilityOfPrecipitation").getInt("value");
					
	    	}catch(Exception e) {
	    		//System.out.println("Precip chance is null");
	    	}
	    	forecasts[i] = new ForecastPeriod(current.getString("name"),
	    									  current.getString("startTime"),
	    									  current.getString("endTime"),
	    									  current.getInt("temperature"), 
	    									  current.getString("temperatureUnit"),
	    									  precipChancePercent,
	    									  current.getString("windSpeed"),
	    									  current.getString("windDirection"),
	    									  current.getString("icon"), 
	    									  current.getString("shortForecast"), 
	    									  current.getString("detailedForecast"));
	    }
	    return forecasts;

   }
	/**
	 * Compares this object with the specified object for order.
	 * @return a value > 0 if this observation has a name later in the alphabet than other;
	 *           value < 0 if this observation has a name earlier in the alphabet than other;
	 *           value = 0 if this observation's name is equal to other.
	 * NOTE: Polymorphism is needed for this, so I didn't include this in the project spec.
	 */
	@Override
	public int compareTo(Object other) {
		if (other instanceof WeatherStation) {
			WeatherStation otherOb = (WeatherStation) other;
			return this.getName().compareTo(otherOb.getName());
			}
		return -1;
	}

   
}