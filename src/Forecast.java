import core.data.*;
public class Forecast {
	private String forecastURL;
	public Forecast(double lat, double lon) {
		forecastURL = "https://forecast.weather.gov/MapClick.php?lat=" + lat + "lon=" + lon + "&unit=0&lg=english&FcstType=dwml";
		
		// Need to figure out how to fetchArray for time-layout XML note AND also look at the child layout-key node
		// look here: https://github.com/berry-cs/sinbad/blob/master/tutorials/java/welcome02-arr.md
	}
	
	public static void main(String[] args) {
		double lat = 47.58;
		double lon = -122.03;
		String forecastURL = "https://forecast.weather.gov/MapClick.php?lat=" + lat + "&lon=" + lon + "&unit=0&lg=english&FcstType=dwml";
		System.out.println(forecastURL);
		//DataSource ds = DataSource.connectUsing("https://graphical.weather.gov/xml/DWMLgen/schema/DWML.xsd");
		 DataSource ds = DataSource.connectAs("XML", forecastURL);
	      ds.setCacheTimeout(15 * 60);  
	      ds.load();
	      ds.printUsageString(true);
	      
	      // Try and fetch the time periods
	      String[] times = ds.fetchStringArray("data/time-layout/start-valid-time/period-name");
	      // Try and fetch the forecast per period
	      String forecaster = ds.fetchString("data/parameters/wordedForecast/wordGenerator");
	      System.out.println(forecaster);
	      //String[] descriptions = ds.fetchStringArray("data/parameters/wordedForecast/text");
	      //String[] descriptions = ds.fetchStringArray("data/parameters/weather/weather-conditions");
	      
	      // fetch icons
	      String[] icons = ds.fetchStringArray("data/parameters/conditions-icon/icon-link");
	      System.out.println(ds.hasFields("data/parameters/conditions-icon/time-layout"));
	      System.out.println(ds.hasFields("value"));
	      printInfo(times);
	      //printInfo(descriptions);
	      printInfo(icons);
	}
	private static void printInfo(String[] arr) {
		for (String elem : arr) {
			System.out.println(elem);
		}
	}
}
