
public class Forecast {
	private String forecastURL;
	public Forecast(double lat, double lon) {
		forecastURL = "https://forecast.weather.gov/MapClick.php?lat=" + lat + "lon=" + lon + "&unit=0&lg=english&FcstType=dwml";
		// Need to figure out how to fetchArray for time-layout XML note AND also look at the child layout-key node
		// look here: https://github.com/berry-cs/sinbad/blob/master/tutorials/java/welcome02-arr.md
	}
}
