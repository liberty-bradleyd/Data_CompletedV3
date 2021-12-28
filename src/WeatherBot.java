import core.data.*;

public class WeatherBot {
   private String id;
   
   /**
    * Constructs a WeatherBot object with the specified weather station id
    * @param stationID the weather station id
    */
   public WeatherBot(String stationID) {
	   id = stationID;
   }
   
   /**
    * Constructs a WeatherBot object with the SEA-TAC weather station id
    */ 
   public WeatherBot() {
	   this("KSEA");
   }
   
   /**
    * Gets an Observation object with the station id, short weather description, temperature
    * and wind direction.
    * @return an Observation object
    */
   public Observation getShortObservation() {
	      DataSource ds = DataSource.connect("http://weather.gov/xml/current_obs/" + id + ".xml"); 
	      ds.setCacheTimeout(15 * 60);  
	      ds.load();
	      Observation ob = ds.fetch("Observation", "station_id", "weather", "temp_f", "wind_degrees");

	      return ob;
   }
   /**
    * Gets an Observation object with the station id, short weather description, temperature,
    * wind direction, wind speed in knots, barometric pressure in mb, and the relative humidity.
    * @return an Observation object
    */
   public Observation getLongObservation() {
	      DataSource ds = DataSource.connect("http://weather.gov/xml/current_obs/" + id + ".xml"); 
	      ds.setCacheTimeout(15 * 60);  
	      ds.load();
	      
	      Observation ob = ds.fetch("Observation", "station_id", "weather", "temp_f", "wind_degrees", "wind_kt", "pressure_mb", "relative_humidity");
	      return ob;
  
   }
   public static void main(String[] args) {

	   WeatherBot bot = new WeatherBot("KSEA");
	  // Observation ob1 = bot.getShortObservation();
	   Observation ob2 = bot.getLongObservation();
	  // System.out.println(ob1.toString());
	   System.out.println(ob2.toString());
	   
	   WeatherBot bot2 = new WeatherBot("KRNT");
	   Observation ob3 = bot2.getLongObservation();
	   System.out.println(ob3.toString());
	   
	   if (ob2.colderThan(ob3)) {
		   System.out.println("It is colder at " +ob2.getId() + " than "  + ob3.getId());
	   }
	   else {
		   System.out.println("It is warmer at " +ob2.getId() + " than "  + ob3.getId());
	   }
	   
	   System.out.println(getWindConditions(ob2));
	   System.out.println(getWindConditions(ob3));
   }
   
   /**
    * Returns a string representation of the specified Observation. Summarize the
    * Beaufort number into 4 categories; calm; breezy; wind flags out; storm 
    * @param ob The observation
    * @return a string representation of the specified Observation
    */
   public static String getWindConditions(Observation ob) {
	   String wind = ob.getId() + ": ";
	   if (ob.getBeaufortNumber() >=10 ) {
		   wind += "Storm is coming";
	   }else if (ob.getBeaufortNumber() >= 6 ) {
		   wind += "Wind flags are out";
	   }else if (ob.getBeaufortNumber() >= 2) {
		   wind += "Nice breeze today";
	   }else {
		   wind += "Wind is calm";
	   }
	   return wind;
   }

}



