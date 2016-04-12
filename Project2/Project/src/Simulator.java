import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Simulator implements ActionListener {

	private CarQueue entranceCarQueue;
	private CarQueue paymentCarQueue;
	private CarQueue exitCarQueue;
	private SimulatorView simulatorView;

	private int day = 0;
	private int hour = 0;
	private int minute = 0;

	private int tickPause = 100;

	private int totalCarsParked = 0;
	private int resCarsParked = 0;
	private int numberOfPlaces = 0;
	private int priceToPay = 9;

	int weekDayArrivals = 50; // average number of arriving cars per hour
	int weekendArrivals = 90; // average number of arriving cars per hour

	int enterSpeed = 3; // number of cars that can enter per minute
	int paymentSpeed = 10; // number of cars that can pay per minute
	int exitSpeed = 9; // number of cars that can leave per minute

	public Simulator() {
		entranceCarQueue = new CarQueue();
		paymentCarQueue = new CarQueue();
		exitCarQueue = new CarQueue();
		simulatorView = new SimulatorView(3, 6, 30, this);
		numberOfPlaces = simulatorView.getNumberOfFloors() * simulatorView.getNumberOfRows()
				* simulatorView.getNumberOfPlaces();
		simulatorView.updateStatus(numberOfPlaces, totalCarsParked);
	}

	public static void main(String[] args) {
		Simulator sim = new Simulator();
		// sim.run();
	}

	/*
	 * public void run() { for (int i = 0; i < 10000; i++) { tick(); } }
	 */

	// Button events afvangen
	private ActionEvent event;

	public void setActionEvent(ActionEvent e) {
		event = e;
	}

	public ActionEvent getActionEvent() {
		return event;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		setActionEvent(e);

		Thread buttonListenerThread = new Thread() {
			public void run() {
				ActionEvent event = getActionEvent();
				String command = event.getActionCommand();

				switch (command) {
				case "1tick":
					for (int i = 0; i < 1; i++) {
						tick();
					}
					break;
				case "100ticks":
					for (int i = 0; i < 100; i++) {
						tick();
					}
					break;
				case "1440ticks":
					for (int i = 0; i < 1440; i++) {
						tick();
					}
					break;
				case "showStatus":
					simulatorView.toggleStatusVisible();
				}

			}
		};
		buttonListenerThread.start();
	}

	private void tick() {
		// Advance the time by one minute.
		minute++;
		while (minute > 59) {
			minute -= 60;
			hour++;
		}
		while (hour > 23) {
			hour -= 24;
			day++;
			totalCarsParked = 0;
		}
		while (day > 6) {
			day -= 7;
		}

		Random random = new Random();

		// Get the average number of cars that arrive per hour.
		int averageNumberOfCarsPerHour = day < 5 ? weekDayArrivals : weekendArrivals;

		// Calculate the number of cars that arrive this minute.
		double standardDeviation = averageNumberOfCarsPerHour * 0.1;
		double numberOfCarsPerHour = averageNumberOfCarsPerHour + random.nextGaussian() * standardDeviation;
		int numberOfCarsPerMinute = (int) Math.round(numberOfCarsPerHour / 60);

		// Add the cars to the back of the queue.
		for (int i = 0; i < numberOfCarsPerMinute; i++) {
			if (random.nextInt(10) < 1) {
				Car car = new ResCar();
				entranceCarQueue.addCar(car);
				totalCarsParked++;
				resCarsParked++;
				car.setIsReserved(true);
				//Hier plek reserveren??????????????????????????????????????????????????????????????????????????.........
			} else {
				Car car = new AdHocCar();
				entranceCarQueue.addCar(car);
				totalCarsParked++;
				car.setIsReserved(false);
			}
		}

		// Remove car from the front of the queue and assign to a parking space.
		for (int i = 0; i < enterSpeed; i++) {
			//if (car instanceof ReservationCar){ Geef deze gast een gereserveerde plek }.....................................
			Car car = entranceCarQueue.removeCar();
			if (car == null) {
				break;
			}
			// Find a space for this car.
			Location freeLocation = simulatorView.getFirstFreeLocation(car);
			if (freeLocation != null) {
				simulatorView.setCarAt(freeLocation, car);
				simulatorView.updateStatus(numberOfPlaces, totalCarsParked);
				simulatorView.setEstimatedIncome(priceToPay * totalCarsParked);
				int stayMinutes = (int) (15 + random.nextFloat() * 10 * 60);
				car.setMinutesLeft(stayMinutes);
			}
		}

		// Perform car park tick.
		simulatorView.tick();

		// Add leaving cars to the exit queue.
		while (true) {
			Car car = simulatorView.getFirstLeavingCar();
			if (car == null) {
				break;
			}

			else if (car instanceof AdHocCar) {
				car.setIsPaying(true);
				paymentCarQueue.addCar(car);
			}

			else if (car instanceof ResCar) {
				car.setIsPaying(true);
				simulatorView.removeCarAt(car.getLocation());
				exitCarQueue.addCar(car);
			}
		}
		
		// Add leaving cars to the exit queue.
	/*	while (true) {
			Car car = simulatorView.getFirstLeavingCar();
			if (car == null) {
				break;
			}
			// In plaats van alles naar true te zetten. Controleren of iemand
			// een kaarthouder is of niet, en aan de hand daarvan naar true of
			// false zetten. (true voor geen kaarthouder en false voor
			// kaarthouders)
			car.setIsPaying(true);

			if (car.getIsPaying()) {
				paymentCarQueue.addCar(car);
			} else {
				exitCarQueue.addCar(car);
			}
		}
*/
		// Let cars pay.
		for (int i = 0; i < paymentSpeed; i++) {
			Car car = paymentCarQueue.removeCar();
			if (car == null) {
				break;
			}
			int currentRealIncome = simulatorView.getRealIncome();
			int newRealIncome = currentRealIncome + priceToPay;
			simulatorView.setRealIncome(newRealIncome);
			simulatorView.removeCarAt(car.getLocation());
			exitCarQueue.addCar(car);
		}

		// Let cars leave.
		for (int i = 0; i < exitSpeed; i++) {
			Car car = exitCarQueue.removeCar();
			if (car == null) {
				break;
			}
			// Bye!
		}

		// Update the car park view.
		simulatorView.updateView();

		// Pause.
		try {
			Thread.sleep(tickPause);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
