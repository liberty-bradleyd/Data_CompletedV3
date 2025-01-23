import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
}
