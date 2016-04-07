import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Status extends JPanel {
	private final JPanel panel = new JPanel();
	private Integer numberOfParkingPlaces;

	//Labels
	JLabel lblCarsParked = new JLabel("Cars parked:");
	JLabel lblStatusView = new JLabel("Status View");
	JLabel lblFreeSpaces = new JLabel("Free Spaces:");
	JLabel lblCurrentCarsParked = new JLabel("test");
	JLabel lblTotalParkingPlaces = new JLabel("Test");
	
	/**
	 * Create the panel.
	 */
	public Status() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS)); 
		add(panel);
		panel.setLayout(null);
		
		
		lblCarsParked.setBackground(Color.LIGHT_GRAY);
		lblCarsParked.setBounds(37, 45, 212, 14);
		panel.add(lblCarsParked);
		
		
		lblStatusView.setBackground(Color.WHITE);
		lblStatusView.setBounds(119, 11, 74, 14);
		lblStatusView.setForeground(Color.RED);
		panel.add(lblStatusView);
		
		
		lblFreeSpaces.setBackground(Color.LIGHT_GRAY);
		lblFreeSpaces.setBounds(37, 70, 212, 14);
		panel.add(lblFreeSpaces);
		
		
		lblCurrentCarsParked.setBackground(Color.LIGHT_GRAY);
		lblCurrentCarsParked.setBounds(259, 45, 46, 14);
		panel.add(lblCurrentCarsParked);
		
		
		lblTotalParkingPlaces.setBackground(Color.LIGHT_GRAY);
		lblTotalParkingPlaces.setBounds(259, 70, 46, 14);
		panel.add(lblTotalParkingPlaces);

	}
	
	public void setNumberOfParkingPlaces(Integer x) {
		numberOfParkingPlaces = x;
		lblTotalParkingPlaces.setText(numberOfParkingPlaces.toString());
	}
	
	public Integer getNumberOfParkingPlaces() {
		return numberOfParkingPlaces;
	}
	
	public void updateView() {
		panel.repaint();
	}
}
