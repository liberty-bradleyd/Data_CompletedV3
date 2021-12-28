import core.data.*;

public class Activity1 {
   public static void main(String[] args) {
      String id = "KSEA";
      DataSource ds = DataSource.connect("http://weather.gov/xml/current_obs/" + id + ".xml"); 
      ds.setCacheTimeout(15 * 60);  
      ds.load();
      ds.printUsageString();
      double temp = ds.fetchFloat("temp_f");
      String loc = ds.fetchString("location");
      System.out.println("The temperature at " + loc + " is " + temp + "F");
   }
   
   /**
    * Returns the current temperature in fahrenheit at the specified weather station
    * @param id the weather station id
    * @return the temperature
    */
   public static double getTempF(String id) {
	      DataSource ds = DataSource.connect("http://weather.gov/xml/current_obs/" + id + ".xml"); 
	      ds.setCacheTimeout(15 * 60);  
	      ds.load();
	      //ds.printUsageString();
	      double temp = ds.fetchFloat("temp_f");
	      return temp;
   }
   /**
    * Returns a concise forecast for the specified weather station id
    * @param id the weather station id
    * @return a concise forecast 
    */
   public static String getConciseForecast(String id) {
	      DataSource ds = DataSource.connect("http://weather.gov/xml/current_obs/" + id + ".xml"); 
	      ds.setCacheTimeout(15 * 60);  
	      ds.load();
	      ds.printUsageString();
	      double temp = ds.fetchDouble("temp_f");
	      String loc = ds.fetchString("location");
	      return "The temperature at " + loc + " is " + temp + "F";

   }
}
