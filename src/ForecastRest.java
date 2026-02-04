import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ForecastRest {

	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		HttpClient client = HttpClient.newHttpClient();

		HttpRequest request = HttpRequest.newBuilder()
		    .uri(URI.create("https://api.weather.gov/gridpoints/SEW/124,67/forecast"))
		    .header("User-Agent", "MyWeatherApp (email@example.com)")
		    .build();

		HttpResponse<String> response =
		    client.send(request, HttpResponse.BodyHandlers.ofString());

		//System.out.println(response.body());
		ForecastPeriod.getForecast(40.7826, -73.9656);
	}

}
