import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class ForecastPeriod {

	private String name;
	private LocalDateTime start;
	private LocalDateTime end;
	private int temp;
	private String tempUnit;
	private int precipChancePercent;
	private int windSpeed;
	private String windSpeedStr;
	private String windDirection;
	private String icon;
	private String shortForecast;
	private String detailedForecast;

	public ForecastPeriod(String name, String start, String end, int temp, 
			String tempUnit, int precipChancePercent, 
			String windSpeed, String windDirection, String icon, 
			String shortForecast, String detailedForecast) {
		this.name = name;
		try {
			// sample - "startTime": "2025-01-20T13:00:00-08:00"
			//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ"); 
			DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
			this.start = LocalDateTime.parse(start, formatter);
			this.end = LocalDateTime.parse(end, formatter);
		}catch(DateTimeParseException e){
			System.out.println("Could not parse start or end time.");
			System.out.println(e.toString());
		}
		// temp
		this.temp = temp;
		this.tempUnit = tempUnit;

		// precip
		//		if (precipChancePercent == null) {
		//			this.precipChancePercent = 0;
		//		}
		//		else{
		//this.precipChancePercent = Integer.parseInt(precipChancePercent);
		this.precipChancePercent = precipChancePercent;
		//		}
		// wind
		this.windSpeedStr = windSpeed;
		this.windSpeed = Integer.parseInt(windSpeedStr.substring(0, windSpeedStr.indexOf(" ")));
		this.windDirection = windDirection;

		// niceities
		this.icon = icon;
		this.shortForecast = shortForecast;
		this.detailedForecast = detailedForecast;
	}
	/**2026**/
	public ForecastPeriod(String name, LocalDateTime start, LocalDateTime end, int temp, 
			String tempUnit, int precipChancePercent, 
			int windSpeed, String windDirection, String icon, 
			String shortForecast, String detailedForecast) {

		// logistics
		this.name = name;
		this.start = start;
		this.end = end;
		// temp
		this.temp = temp;
		this.tempUnit = tempUnit;

		// precip chance
		this.precipChancePercent = precipChancePercent;

		// wind
		this.windSpeed = windSpeed;
		this.windDirection = windDirection;

		// niceities
		this.icon = icon;
		this.shortForecast = shortForecast;
		this.detailedForecast = detailedForecast;
	}
	public String getName() {
		return name;
	}
	public LocalDateTime getStart() {
		return start;
	}

	public LocalDateTime getEnd() {
		return end;
	}

	public int getTemp() {
		return temp;
	}
	public String getTempUnit() {
		return tempUnit;
	}
	public int getPrecipChancePercent() {
		return precipChancePercent;
	}
	public String getWindSpeedStr() {
		return windSpeedStr;
	}
	public int getWindSpeed() {
		return windSpeed;
	}
	public String getWindDirection() {
		return windDirection;
	}
	public String getIcon() {
		return icon;
	}

	public String getShortForecast() {
		return shortForecast;
	}
	public String getDetailedForecast() {
		return detailedForecast;
	}

	/**2026**/
	public static ForecastPeriod[] getForecast(double lat, double lng) {
		String url = "https://api.weather.gov/points/" + lat + "," + lng;
		System.out.println(url);
		JSONObject jsonGrid = null;
		try {
			jsonGrid = JSONHelper.readJsonFromUrl(url);
		}
		catch(IOException e) {
			System.out.println("Could not open URL:" + url);
		}

		JSONObject propertiesGrid = jsonGrid.getJSONObject("properties");
		// JSONObject json = JSONHelper.readJsonFromUrl("https://api.weather.gov/gridpoints/SEW/134,66/forecast");
		JSONObject json = null;
		// the forecast URL is different than the URL containing the lat and lng, because the NWS
		// has distinct areas that are based on lat and lng, but are not the actual lat and lng.
		try {
			json = JSONHelper.readJsonFromUrl(propertiesGrid.getString("forecast"));
		}
		catch(IOException e) {
			System.out.println("Could not open URL for the forecast node");
		}
		System.out.println(json.toString());
		return parseJSON(json);
	}

	public static ForecastPeriod[] getForecast(String dir, String filename) {
		//TODO
		
		// open file
		File jsonFile = null;
		String fileContents = "";
		try {
			jsonFile = new File(dir + "/" + filename);
			Scanner fileReader = new Scanner(jsonFile);
			while (fileReader.hasNextLine()) {
				fileContents += fileReader.nextLine();
			}
			fileReader.close();
		}
		catch(Exception e) {
			System.out.println("Unable to read file");
		}
		// convert to JSONObject
		JSONObject json = new JSONObject(fileContents);
		// parse it
		return parseJSON(json);
	}
	private static ForecastPeriod[] parseJSON(JSONObject json) {
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


			// convert the start and end times to LocalDateTime objects using the parse method.
			LocalDateTime start = null;
			LocalDateTime end = null;
			try {
				// sample - "startTime": "2025-01-20T13:00:00-08:00"
				//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ"); 
				DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
				start = LocalDateTime.parse(current.getString("startTime"), formatter);
				end = LocalDateTime.parse(current.getString("endTime"), formatter);
			}catch(DateTimeParseException e){
				System.out.println("Could not parse start or end time.");
				System.out.println(e.toString());
			}

			//convert the wind speed from a string to an int
			String windSpeedStr = current.getString("windSpeed");
			int windSpeed = Integer.parseInt(windSpeedStr.substring(0, windSpeedStr.indexOf(" ")));

			// the rest of the elements should be able to be retrieved direct
			forecasts[i] = new ForecastPeriod(current.getString("name"),
					start,
					end,
					current.getInt("temperature"), 
					current.getString("temperatureUnit"),
					precipChancePercent,
					windSpeed,
					current.getString("windDirection"),
					current.getString("icon"), 
					current.getString("shortForecast"), 
					current.getString("detailedForecast"));
		}
		return forecasts;

	}
	public static void main(String[] args) {
		System.out.println(getForecast("data","centralPark.json").length);
		System.out.println(getForecast(40.78, -73.97).length);
	}
}
