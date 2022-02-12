import core.data.DataSource;

/*
 Represents information about a NWS weather station
*/

public class WeatherStation {
   private String name;
   private String id;
   private String state;
   private double lat;
   private double lng;
   
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
      this.lat = lat;
      this.lng = lng;
      this.state = state;   
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
   
   public Observation getCurrentWeather() {
	   System.out.println("Station ID: " + id);
	      DataSource ds = DataSource.connect("http://weather.gov/xml/current_obs/" + id + ".xml"); 
	      ds.setCacheTimeout(15 * 60);  
	      ds.load();
	      
	      Observation ob = ds.fetch("Observation", "station_id", "weather", "temp_f", "wind_degrees", "wind_kt", "pressure_mb", "relative_humidity","icon_url_base","icon_url_name");
	   
	      return ob;

   }
   
}