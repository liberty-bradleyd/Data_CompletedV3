/*
 * Arrays and ArrayLists of objects
 */

import core.data.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

public class WeatherBureau {
	WeatherStation[] stations;
	// **2026**
	ArrayList<WeatherStation> stationsList;

	/**
	 * Constructor that initializes stations by connecting, loading
	 * and fetching the weather stations serviced by the National 
	 * Weather Service
	 */
	public WeatherBureau() {
		DataSource ds = DataSource.connect("http://weather.gov/xml/current_obs/index.xml").load();
		ds.setCacheTimeout(15*60);
		ds.load();

		stations = ds.fetchArray("WeatherStation", "station/station_name", 
				"station/station_id", "station/state",
				"station/latitude", "station/longitude");

	}
	//**2026**
	public WeatherBureau(String dir, String filename) {
		String name = "";
		String id = "";
		String state = "";
		double lat = Double.MIN_VALUE;
		double lng = Double.MIN_VALUE;
		stationsList = new ArrayList<WeatherStation>(3000);
		// open file
		try {
			File csvFile = new File(dir + "/"+ filename);
			Scanner fileReader = new Scanner(csvFile);

			// Read row by row
			// first row is column headers

			boolean first = true;
			String[] columnHeaders = null;
			String[] station = null;
			while (fileReader.hasNextLine()) {
				String line = fileReader.nextLine();
				if (first) {
					columnHeaders = line.split(",");
					first = false;
				}
				else {
					station = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);


					// Create a WeatherStation and add to ArrayList
					for (int i = 0; i < station.length; i++) {
						switch (columnHeaders[i]) {
						case WeatherStation.ID: id = station[i];break;
						case WeatherStation.STATE: state = station[i];break;
						case WeatherStation.LATITUDE:lat = Double.parseDouble(station[i]);break;
						case WeatherStation.LONGITUDE: lng = Double.parseDouble(station[i]);break;
						case WeatherStation.STATION_NAME: name = station[i]; break;
						default: // skipping this piece of data 
						}
					} 
					stationsList.add(new WeatherStation(name, id, state, lat, lng));
				}
			}
			// populate stations
			stations = new WeatherStation[stationsList.size()];
			for (int i = 0; i < stationsList.size(); i++) {
				stations[i] = stationsList.get(i);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	/**2026**/
	public WeatherBureau(String urlString) {
		stationsList = new ArrayList<>();
		Document doc = null;
		try {
			// Create a URL and open connection
			URL url = new URL(urlString);
			URLConnection connection = url.openConnection();

			// Get input stream from connection
			InputStream xmlStream = connection.getInputStream();

			// Setup XML parser
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

			// Parse XML
			doc = dBuilder.parse(xmlStream);
			doc.getDocumentElement().normalize();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		// init the variables, in case an element is not present
		String name = "";
		String id = "";
		String state = "";
		double lat = Double.MIN_VALUE;
		double lng = Double.MIN_VALUE;

		NodeList stationNodes = doc.getElementsByTagName("station");

		for (int i = 0; i < stationNodes.getLength(); i++) {
			Element stationEl = (Element) stationNodes.item(i);
			// This code verifies that the element exists and if not the default value is used.
			// get station id
			NodeList tempIDList = stationEl.getElementsByTagName(WeatherStation.ID);
			if (tempIDList.getLength() > 0) {
				id = tempIDList.item(0).getTextContent();
			}

			// get state
			NodeList tempStateList = stationEl.getElementsByTagName(WeatherStation.STATE);
			if (tempStateList.getLength() > 0) {
				state = tempStateList.item(0).getTextContent();
			}

			// get station_name
			NodeList tempStationList = stationEl.getElementsByTagName(WeatherStation.STATION_NAME);
			if (tempStationList.getLength() > 0) {
				name = tempStationList.item(0).getTextContent();
			}

			// get latitude
			NodeList tempLatList = stationEl.getElementsByTagName(WeatherStation.LATITUDE);
			if (tempLatList.getLength() > 0) {
				lat = Double.parseDouble(tempLatList.item(0).getTextContent());
			}

			// get longitude
			NodeList tempLngList = stationEl.getElementsByTagName(WeatherStation.LONGITUDE);
			if (tempLngList.getLength() > 0) {
				lng = Double.parseDouble(tempLngList.item(0).getTextContent());
			}

			stationsList.add(new WeatherStation(name,id,state,lat,lng));
		}
		// populate stations
		stations = new WeatherStation[stationsList.size()];
		for (int i = 0; i < stationsList.size(); i++) {
			stations[i] = stationsList.get(i);
		}
	}
	/**
	 * Gets all the weather stations as an array
	 * @return he weather stations as an array
	 */

	public WeatherStation[] getAllStationsArray() {
		return stations;
	}

	/**
	 * Gets all the weather stations as an ArrayList
	 * @return he weather stations as an ArrayList
	 */
	public ArrayList<WeatherStation> getAllStationsList(){
		ArrayList<WeatherStation> list = new ArrayList<WeatherStation>();
		for (WeatherStation station : stations) {
			list.add(station);
		}

		return list;
	}

	/**
	 * Filters the list of weather stations to the ones in the specified state
	 * @return the list of weather stations in the specified state
	 */

	public ArrayList<WeatherStation> getStationsInState(String state){
		ArrayList<WeatherStation> list = new ArrayList<WeatherStation>();
		for (WeatherStation station : stations) {
			if (station.isLocatedInState(state)) {
				list.add(station);
			}
		}

		return list;
	}


	public ArrayList<String> getStatesWithStations(){
		ArrayList<String> states = new ArrayList<String>();
		for (WeatherStation station : stations) {
			if (!states.contains(station.getState())) {
				states.add(station.getState());
			}
		}
		states.sort(null);
		System.out.println(states.size());
		return states;
	}

	private int getStationsInStateCount(String state) {
		int count = 0;
		for (WeatherStation station : stations) {
			if (station.isLocatedInState(state)) {
				count++;
			}
		}
		return count;

	}
	/**
	 * Finds the Weather Station in the specified state with the coldest temperature.
	 * @param state the state
	 * @return An Observation for the weather station with the coldest temperature
	 */
	public Observation getColdestInState(String state) {
		ArrayList<WeatherStation> list = getStationsInState(state);
		WeatherStation ws = list.get(0);
		//		WeatherBot bot = new WeatherBot(ws.getId());
		//		Observation ob = bot.getShortObservation();
		Observation ob = ws.getCurrentObservation();
		double coldestTemp = ob.getTemp();

		for (int i = 1; i < list.size(); i++) {
			WeatherStation ws2 = list.get(i);
			//			//WeatherBot bot2 = new WeatherBot(ws2.getId());
			// use try..catch, because sometimes the stations are offline.
			try {
				//				//Observation ob2 = bot2.getShortObservation();
				Observation ob2 = ws2.getCurrentObservation(); //**add
				if (ob2.getTemp() < coldestTemp) {
					ob = ob2;
					coldestTemp = ob.getTemp();
				}
			}catch(Exception e) {
				System.out.println("Error retrieving observation data for " + ws2.getId() + ": " + ws2.getName());
			}

		}

		return ob;
	}
	/**
	 * Returns a WeatherStation object for the specified station
	 * @param stationLookingFor the NWS code for a Weather Station
	 * @return a WeatherStation object for the specified station, if it exists; otherwise null
	 */
	public WeatherStation getStation(String stationLookingFor) {
		for (WeatherStation station : stations) {
			if (station.getId().equals(stationLookingFor)) {
				return station;
			}
		}
		return null;
	}

	/**
	 * Gets a list of all weather stations in a state sorted by their name.
	 * @param state the state
	 * @return list of all weather stations in a state sorted by their name
	 */

	public WeatherStation[] getStationsInStateSortedByName(String state) {
		int filteredSize = getStationsInStateCount(state);
		WeatherStation[] sortedStations = new WeatherStation[filteredSize];
		int count = 0;
		for (WeatherStation station : stations) {
			if (station.isLocatedInState(state)) {
				sortedStations[count] = station;
				count++;
			}
		}

		insertionSort(sortedStations);
		return sortedStations;
	}
	/**
	 * Sorts the array of WeatherStation using the Insertion Sort algorithm
	 * @param arr the array of WeatherStation
	 */
	public void insertionSort(WeatherStation[] arr) {
		int sortedIndex;
		WeatherStation newValue;

		// start at 1, because 0 is sorted with itself.
		for (int index = 1; index < arr.length; index++) {
			// get new value to insert into the correct place
			newValue = arr[index];
			sortedIndex = index;
			// Shift values to the right as long as they are larger.
			while (sortedIndex > 0 && arr[sortedIndex - 1].getName().compareTo(newValue.getName()) > 0) {
				arr[sortedIndex] = arr[sortedIndex - 1];
				sortedIndex--;
			}
			// Insert our value into its correct place.
			arr[sortedIndex] = newValue;
		} 
	}
	public static void main(String[] args) {
		//	WeatherBureau bureau = new WeatherBureau();
		//WeatherBureau bureau = new WeatherBureau("data","All_Stations.csv");
		WeatherBureau bureau = new WeatherBureau("https://weather.gov/xml/current_obs/index.xml");
		// this would print out ~2800 weather stations
//			   WeatherStation[] stations = bureau.getAllStationsArray();
//			   for (WeatherStation ws : stations) {
//				   System.out.println("  " + ws.getId() + ": " + ws.getName());
//			   }
//			   System.out.println("Total number of stations: " + stations.length);
//		  
//			   System.out.println();
		System.out.println(bureau.getStatesWithStations());
		System.out.println("****");
		System.out.println("Getting weather stations in Washington");
		ArrayList<WeatherStation> waStations = bureau.getStationsInState("WA");
		for (WeatherStation ws : waStations) {
			System.out.println("  " + ws.getId() + ": " + ws.getName());
		}
		System.out.println("Total number of stations: " + waStations.size());

		System.out.println();
		System.out.println("Sorting stations in Washington");
		WeatherStation[] filteredStations = bureau.getStationsInStateSortedByName("WA");
		for (WeatherStation ws : filteredStations) {
			System.out.println("  " + ws.getId() + ": " + ws.getName());
		}

		System.out.println();
		System.out.println("Getting weather stations in Rhode Island");
		ArrayList<WeatherStation> riStations = bureau.getStationsInState("RI");
		for (WeatherStation ws : riStations) {
			System.out.println("  " + ws.getId() + ": " + ws.getName());
		}
		System.out.println("Total number of stations: " + riStations.size());

		System.out.println("Getting coldest station in Rhode Island");
		Observation ob = bureau.getColdestInState("RI");
		System.out.println("Coldest station is - " + ob);
		//System.out.println(ob);



	}
}