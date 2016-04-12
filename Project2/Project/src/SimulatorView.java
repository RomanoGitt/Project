import javax.swing.*;
import java.awt.*;

public class SimulatorView extends JFrame {
    private CarParkView carParkView;
    private StatusView statusView;
    private int numberOfFloors;
    private int numberOfRows;
    private int numberOfPlaces;
    private Car[][][] cars;
    private ButtonController buttonController;

    public JButton button1;
    public JButton button100;
    public JButton button1440;
    public JButton buttonStatus;

    public SimulatorView(int numberOfFloors, int numberOfRows, int numberOfPlaces, Simulator sim) {
        this.numberOfFloors = numberOfFloors;
        this.numberOfRows = numberOfRows;
        this.numberOfPlaces = numberOfPlaces;
        cars = new Car[numberOfFloors][numberOfRows][numberOfPlaces];

        Container contentPane = getContentPane();
        carParkView = new CarParkView();
        statusView = new StatusView();
        buttonController = new ButtonController(sim);
        
        contentPane.add(statusView, BorderLayout.SOUTH);


        //contentPane.add(stepLabel, BorderLayout.NORTH);
        contentPane.add(carParkView, BorderLayout.CENTER);
        //contentPane.add(population, BorderLayout.SOUTH);

        button1 = new JButton("1 Tick");
        button1.setActionCommand("1tick");
        button1.addActionListener(buttonController);

        button100 = new JButton("100 Tick's");
        button100.setActionCommand("100ticks");
        button100.addActionListener(buttonController);

        button1440 = new JButton("1440 Ticks (1 day)");
        button1440.setActionCommand("1440ticks");
        button1440.addActionListener(buttonController);

        buttonStatus = new JButton("Hide status");
        buttonStatus.setActionCommand("showStatus");
        buttonStatus.addActionListener(buttonController);


        JToolBar menu = new JToolBar();
        menu.add(button1);
        menu.add(button100);
        menu.add(button1440);
        menu.add(buttonStatus);

        contentPane.add(menu, BorderLayout.NORTH);

        pack();
        setVisible(true);

        updateView();
    }

    public void updateStatus(int maxNumberOfPlaces, int currentNumberOfPlaces) {
        statusView.setNumberOfParkingPlaces(maxNumberOfPlaces);
        statusView.setNumberOfCurrentPlaces(currentNumberOfPlaces);
    }

    public void setEstimatedIncome(Integer x) {
        statusView.updateEstimatedIncome(x);
    }

    public void setRealIncome(Integer x) {
        statusView.updateRealIncome(x);
    }

    public Integer getRealIncome() {
        return statusView.getRealIncome();
    }

    public void toggleStatusVisible() {
        if(statusView.isVisible()) {
            statusView.setVisible(false);
            buttonStatus.setText("Show status");
        } else {
            statusView.setVisible(true);
            buttonStatus.setText("Hide status");
        }
    }

    public void updateView() {
        carParkView.updateView();
    }

    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public int getNumberOfPlaces() {
        return numberOfPlaces;
    }

    public Car getCarAt(Location location) {
        if (!locationIsValid(location)) {
            return null;
        }
        return cars[location.getFloor()][location.getRow()][location.getPlace()];
    }

    public boolean setCarAt(Location location, Car car) {
        if (!locationIsValid(location)) {
            return false;
        }
        Car oldCar = getCarAt(location);
        if (oldCar == null) {
            cars[location.getFloor()][location.getRow()][location.getPlace()] = car;
            car.setLocation(location);
            return true;
        }
        return false;
    }

    public Car removeCarAt(Location location) {
        if (!locationIsValid(location)) {
            return null;
        }
        Car car = getCarAt(location);
        if (car == null) {
            return null;
        }
        cars[location.getFloor()][location.getRow()][location.getPlace()] = null;
        car.setLocation(null);
        return car;
    }

    public Location getFirstFreeLocation(Car car) {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    if (getCarAt(location) == null) {
                    	if (car instanceof ResCar) {
                    		if ((floor == 2) && (row > 3)) {
                    			return location;
                    		}
                    	} else {
                    		return location;
                    	}
                    }
                }
            }
        }
        return null;
    }

    public Car getFirstLeavingCar() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    if (car != null && car.getMinutesLeft() <= 0 && !car.getIsPaying()) {
                        return car;
                    }
                }
            }
        }
        return null;
    }

    public void tick() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    if (car != null) {
                        car.tick();
                    }
                }
            }
        }
    }

    private boolean locationIsValid(Location location) {
        int floor = location.getFloor();
        int row = location.getRow();
        int place = location.getPlace();
        if (floor < 0 || floor >= numberOfFloors || row < 0 || row > numberOfRows || place < 0 || place > numberOfPlaces) {
            return false;
        }
        return true;
    }
    
    private class CarParkView extends JPanel {
        
        private Dimension size;
        private Image carParkImage;    
    
        /**
         * Constructor for objects of class CarPark
         */
        public CarParkView() {
            size = new Dimension(0, 0);
        }
    
        /**
         * Overridden. Tell the GUI manager how big we would like to be.
         */
        public Dimension getPreferredSize() {
            return new Dimension(800, 500);
        }
    
        /**
         * Overriden. The car park view component needs to be redisplayed. Copy the
         * internal image to screen.
         */
        public void paintComponent(Graphics g) {
            if (carParkImage == null) {
                return;
            }
    
            Dimension currentSize = getSize();
            if (size.equals(currentSize)) {
                g.drawImage(carParkImage, 0, 0, null);
            }
            else {
                // Rescale the previous image.
                g.drawImage(carParkImage, 0, 0, currentSize.width, currentSize.height, null);
            }
        }
    
       /* public void updateView() {
            // Create a new car park image if the size has changed.
            if (!size.equals(getSize())) {
                size = getSize();
                carParkImage = createImage(size.width, size.height);
            }
            Graphics graphics = carParkImage.getGraphics();
            for(int floor = 0; floor < getNumberOfFloors(); floor++) {
                for(int row = 0; row < getNumberOfRows(); row++) {
                    for(int place = 0; place < getNumberOfPlaces(); place++) {
                        Location location = new Location(floor, row, place);
                        Car car = getCarAt(location);
                        Color color = car == null ? Color.white : Color.red;
                        drawPlace(graphics, location, color);
                    }
                }
            }
            repaint();
        }*/
        
		public void updateView() {
			// Create a new car park image if the size has changed.
			if (!size.equals(getSize())) {
				size = getSize();
				carParkImage = createImage(size.width, size.height);
			}
			Graphics graphics = carParkImage.getGraphics();
			for (int floor = 0; floor < getNumberOfFloors(); floor++) {
				for (int row = 0; row < getNumberOfRows(); row++) {
					for (int place = 0; place < getNumberOfPlaces(); place++) {
						Location location = new Location(floor, row, place);
						Car car = getCarAt(location);
						if(car == null) {
							if ((floor == 2) && (row > 3)) {
								Color color = Color.green;
								drawPlace(graphics, location, color);
							} else {
								Color color = Color.white;
								drawPlace(graphics, location, color);
							}
						} else {
							if (car instanceof AdHocCar) {
								Color color = Color.red;
								drawPlace(graphics, location, color);
							} else if (car instanceof ResCar) {
								Color color = Color.blue;
								drawPlace(graphics, location, color);
							}
						}
					}
				}	
			}
			
			repaint();
		}
    
        /**
         * Paint a place on this car park view in a given color.
         */
        private void drawPlace(Graphics graphics, Location location, Color color) {
            graphics.setColor(color);
            graphics.fillRect(
                    location.getFloor() * 260 + (1 + (int)Math.floor(location.getRow() * 0.5)) * 75 + (location.getRow() % 2) * 20,
                    60 + location.getPlace() * 10,
                    20 - 1,
                    10 - 1); // TODO use dynamic size or constants
        }
    }

}
