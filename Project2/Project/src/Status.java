import javax.swing.JPanel;
import javax.swing.BoxLayout;
import java.awt.Color;
import javax.swing.JLabel;

public class Status extends JPanel {
    private JPanel panel = new JPanel();
    private Integer numberOfParkingPlaces;
    private Integer currentNumberOfPlaces;
    private Integer estimatedIncome;
    private Integer realIncome = 0;

    //Labels
    JLabel lblCarsParked = new JLabel("Cars today:");
    JLabel lblCurrentCarsParked = new JLabel("Placeholder Current");
    JLabel lblTotalPlaces = new JLabel("|| Total parking places:");
    JLabel lblTotalParkingPlaces = new JLabel("Placeholder Total");
    JLabel lblEstimatedIncomeTitle = new JLabel("|| Estimated income:");
    JLabel lblEstimatedIncome = new JLabel("€0");
    JLabel lblRealIncomeTitle = new JLabel("|| Real income:");
    JLabel lblRealIncome = new JLabel("€0");

    /**
     * Create the panel.
     */
    public Status() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(panel);
        //panel.setLayout(null);


        lblCarsParked.setBackground(Color.LIGHT_GRAY);
        //lblCarsParked.setBounds(37, 45, 212, 14);
        panel.add(lblCarsParked);


        lblCurrentCarsParked.setBackground(Color.LIGHT_GRAY);
        //lblCurrentCarsParked.setBounds(259, 45, 46, 14);
        panel.add(lblCurrentCarsParked);


        lblTotalPlaces.setBackground(Color.LIGHT_GRAY);
        //lblTotalPlaces.setBounds(37, 70, 212, 14);
        panel.add(lblTotalPlaces);


        lblTotalParkingPlaces.setBackground(Color.LIGHT_GRAY);
        //lblTotalParkingPlaces.setBounds(259, 70, 46, 14);
        panel.add(lblTotalParkingPlaces);


        lblEstimatedIncomeTitle.setBackground(Color.LIGHT_GRAY);
        panel.add(lblEstimatedIncomeTitle);

        lblEstimatedIncome.setBackground(Color.lightGray);
        panel.add(lblEstimatedIncome);

        lblRealIncomeTitle.setBackground(Color.LIGHT_GRAY);
        panel.add(lblRealIncomeTitle);

        lblRealIncome.setBackground(Color.LIGHT_GRAY);
        panel.add(lblRealIncome);

    }

    public void setNumberOfParkingPlaces(Integer x) {
        numberOfParkingPlaces = x;
        lblTotalParkingPlaces.setText(numberOfParkingPlaces.toString());
    }

    public void setNumberOfCurrentPlaces(Integer x) {
        currentNumberOfPlaces = x;
        lblCurrentCarsParked.setText(currentNumberOfPlaces.toString());
    }

    public void updateEstimatedIncome(Integer x) {
        estimatedIncome = x;
        lblEstimatedIncome.setText("€" + estimatedIncome.toString());
    }

    public void updateRealIncome(Integer x) {
        realIncome = x;
        lblRealIncome.setText("€" + realIncome.toString());
    }

    public Integer getNumberOfParkingPlaces() {
        return numberOfParkingPlaces;
    }
    public Integer getRealIncome() { return realIncome; }

    public void updateView() {
        panel.repaint();
    }
}