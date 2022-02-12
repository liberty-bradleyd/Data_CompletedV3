import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import java.awt.BorderLayout;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListModel;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.ListSelectionModel;
import java.awt.GridLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;
import java.awt.Font;

public class WeatherForecaster2 {
	private WeatherBureau bureau;
	protected JFrame WeatherObserver;
	/**
	 * @wbp.nonvisual location=95,8
	 */
	private final JComboBox comboBox = new JComboBox();
	private JList stationList;
	private DefaultListModel listModel;
	private JPanel panel;
	private JLabel lblWeatherText;
	private JLabel lblWeatherData;
	private JLabel lblTempText;
	private JLabel lblTempData;
	private JLabel lblStationName;
	private JLabel lblIcon;
	private JLabel lblNewLabel;
	private JPanel panel_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WeatherForecaster2 window = new WeatherForecaster2();
					window.WeatherObserver.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public WeatherForecaster2() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// Initialize our WeatherBureau object
		bureau = new WeatherBureau();
		WeatherObserver = new JFrame();
		WeatherObserver.setBounds(100, 100, 1167, 647);
		WeatherObserver.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel_1 = new JPanel();
		WeatherObserver.getContentPane().add(panel_1, BorderLayout.NORTH);
		
		lblNewLabel = new JLabel("Pick a State");
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		JComboBox statesCombo = new JComboBox();
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addComponent(lblNewLabel)
					.addGap(18)
					.addComponent(statesCombo, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE)
					.addGap(849))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(5)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(statesCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
		);
		panel_1.setLayout(gl_panel_1);
		
					// Add listener for the list of states
					statesCombo.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							//updateWeatherData((String)statesCombo.getSelectedItem());
							updateStations((String)statesCombo.getSelectedItem());
		
						}
					});
		
		// Initialize our List box witha  default list model
		listModel = new DefaultListModel();
		stationList = new JList(listModel);
		stationList.setToolTipText("Select the weather station for which you want to see the most recent observation.");
		stationList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		// Add a scroll pane, so that our list box can be scrolled.
		 JScrollPane scrollPane = new JScrollPane(stationList);
		 scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		 
		 // Add a Panel to display the observation for a selected station.
			WeatherObserver.getContentPane().add(scrollPane, BorderLayout.WEST);
			
			panel = new JPanel();
			WeatherObserver.getContentPane().add(panel, BorderLayout.CENTER);
			
			lblTempText = new JLabel("Temperature");
			lblTempText.setFont(new Font("Tahoma", Font.BOLD, 21));
			lblTempText.setVerticalAlignment(SwingConstants.TOP);
			lblTempText.setHorizontalAlignment(SwingConstants.LEFT);
			
			lblWeatherText = new JLabel("Weather");
			lblWeatherText.setFont(new Font("Tahoma", Font.BOLD, 21));
			lblWeatherText.setVerticalAlignment(SwingConstants.TOP);
			lblWeatherText.setHorizontalAlignment(SwingConstants.LEFT);
			
			lblWeatherData = new JLabel("");
			lblWeatherData.setFont(new Font("Tahoma", Font.PLAIN, 21));
			lblWeatherData.setVerticalAlignment(SwingConstants.TOP);
			lblWeatherData.setHorizontalAlignment(SwingConstants.LEFT);
			
			lblTempData = new JLabel("");
			lblTempData.setFont(new Font("Tahoma", Font.PLAIN, 21));
			lblTempData.setVerticalAlignment(SwingConstants.TOP);
			lblTempData.setHorizontalAlignment(SwingConstants.LEFT);
			lblTempData.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent arg0) {
				}
			});
			
			lblStationName = new JLabel("");
			lblStationName.setHorizontalAlignment(SwingConstants.CENTER);
			lblStationName.setFont(new Font("Tahoma", Font.PLAIN, 28));
			
			lblIcon = new JLabel("");
			lblIcon.setHorizontalAlignment(SwingConstants.CENTER);
			GroupLayout gl_panel = new GroupLayout(panel);
			gl_panel.setHorizontalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel.createSequentialGroup()
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panel.createSequentialGroup()
								.addGap(90)
								.addComponent(lblIcon, GroupLayout.PREFERRED_SIZE, 561, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_panel.createSequentialGroup()
								.addContainerGap()
								.addComponent(lblTempText, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_panel.createSequentialGroup()
								.addContainerGap()
								.addComponent(lblWeatherText, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
									.addComponent(lblTempData, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblWeatherData, GroupLayout.PREFERRED_SIZE, 679, GroupLayout.PREFERRED_SIZE)))
							.addComponent(lblStationName, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 864, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
			);
			gl_panel.setVerticalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel.createSequentialGroup()
						.addComponent(lblStationName, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
						.addGap(56)
						.addComponent(lblIcon)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblWeatherText, GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
							.addComponent(lblWeatherData, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblTempText, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblTempData))
						.addGap(353))
			);
			panel.setLayout(gl_panel);

			// populate the list of states
			ArrayList<String> states = bureau.getStatesWithStations();
			for (String state: states) {
				statesCombo.addItem(state);
			}

		 //Add listener for the list of stations
		stationList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				try {
				// Get the station ID
				String selectedValue  = (String)stationList.getSelectedValue();
				System.out.println(selectedValue);
				// Get the index of the "--"
				int endOfStationPos = selectedValue.indexOf("--");
				String station = selectedValue.substring(0,endOfStationPos);
				updateWeather(station);
				}
				catch (Exception e) {
					// eat the exception.
				}
			}
		});
		
	}
	
	private void updateStations(String state) {
		WeatherStation[] stations = bureau.getStationsInStateSortedByName(state);
		//stationList.removeAll();
		listModel.removeAllElements();
		for (WeatherStation station : stations) {
			// If this gets updated, update stationList.addListSelectionListener
			listModel.addElement(station.getId() + "--" + station.getName());
		}
		stationList.setModel(listModel);
	}
	private void updateWeather(String station) {
		// Get the Weather Station for which the user has selected
		WeatherStation wb = bureau.getStation(station);
		lblStationName.setText(wb.getName() + " (" + station + ")");
		
		// Attempt to get the current observation for the weather station
		try {
			Observation ob = wb.getCurrentWeather();
			// update our UI with the current observation.
			URL iconURL = new URL(ob.getIconURL());
			BufferedImage image = ImageIO.read(iconURL.openStream());
			lblIcon.setIcon(new ImageIcon(image));
			lblWeatherData.setText(ob.getDescription());
			lblTempData.setText(ob.getTemp() + "°F");
		}
		catch(Exception e) {
			String na = "Not Available";
			lblWeatherData.setText(na);
			lblTempData.setText(na);
			lblIcon.setIcon(null);
			
		}
		
		
		
	}
	private void updateWeatherData(String state, String station) {
		// get the stations for the selected state

		WeatherStation[] stations = bureau.getStationsInStateSortedByName(state);

		// get the current set of columns
		String[] columns = {"Station ID","Description", "Temp",  "Wind"};

		// initialize array to hold the data for the table
		Object[][] data = new Object[1][columns.length];
		// traverse the stations 
		for (int row = 0; row < stations.length; row++) {
			// 				"Station ID","Description", "Temp",  "Wind",
			if (stations[row].getId().equals(station)) {
				try {
					Observation ob = stations[row].getCurrentWeather();
					data[row][0] = ob.getId();
					data[row][1] = ob.getDescription();
					data[row][2] = ob.getTemp();
					data[row][3] = ob.getWindConditions();
				}
				catch(Exception e){
					data[row][0] = "";
					data[row][1] = "";
					data[row][2] = "";
					data[row][3] = "";
				}
			}
			else {
				data[row][0] = "";
				data[row][1] = "";
				data[row][2] = "";
				data[row][3] = "";
			
			}
		}
		//observationsTable.setModel(new DefaultTableModel(data,columns));
	}
}


