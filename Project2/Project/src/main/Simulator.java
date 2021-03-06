package main;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import logic.AdHocCar;
import logic.Car;
import logic.CarQueue;
import logic.Location;
import logic.ResCar;
import model.SimulatorView;


public class Simulator {

	private CarQueue entranceCarQueue;
	private CarQueue paymentCarQueue;
	private CarQueue exitCarQueue;
	private SimulatorView simulatorView;

	private int day = 0;
	private int hour = 0;
	private int minute = 0;

	private int tickPause = 100;
	
	private int parkedCars = 0;

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
		simulatorView.updateSpots(parkedCars, numberOfPlaces);

	}

	public static void main(String[] args) {
		Simulator sim = new Simulator();
		// sim.run();
	}
	
	public SimulatorView getSimulatorView() {
		return simulatorView;
	}

	/*
	 * public void run() { for (int i = 0; i < 10000; i++) { tick(); } }
	 */

	public void tick() {

		// Update queue views 
		simulatorView.updateQueues(entranceCarQueue.getQueueSize(), paymentCarQueue.getQueueSize(), exitCarQueue.getQueueSize());
		// Update spot views
		simulatorView.updateSpots(parkedCars, numberOfPlaces);

		// Advance the time by one minute.
		minute++;
	
		while (minute > 59) {
			minute -= 60;
			hour++;
		}
		while (hour > 23) {
			hour -= 24;
			day++;
			//totalCarsParked = 0;
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
			} else if (random.nextInt(10) > 8) {
				Car car = new AdHocCar();
				car.setHasParkPass(true);
				entranceCarQueue.addCar(car);
				totalCarsParked++;
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
				parkedCars++;
				simulatorView.updateSpots(parkedCars, numberOfPlaces);
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
				paymentCarQueue.addCar(car);
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
			if (car.hasParkPass()) {
				simulatorView.removeCarAt(car.getLocation());
				exitCarQueue.addCar(car);
			} else {
				int currentRealIncome = simulatorView.getRealIncome();
				int newRealIncome = currentRealIncome + priceToPay;
				simulatorView.setRealIncome(newRealIncome);
				simulatorView.removeCarAt(car.getLocation());
				exitCarQueue.addCar(car);
			}
		}

		// Let cars leave.
		for (int i = 0; i < exitSpeed; i++) {
			Car car = exitCarQueue.removeCar();
			if (car == null) {
				break;
			}
			parkedCars--;
			simulatorView.updateStatus(numberOfPlaces, totalCarsParked);
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
