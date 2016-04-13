
package view;
import javax.swing.JPanel;
import javax.swing.Box;
import javax.swing.JLabel;


public class SpotView extends JPanel {
	private Box panel = Box.createVerticalBox();
	
	//Variables
	private Integer carsParked;
	private Integer totalSpots;
	private Integer freeSpots;

	
	//Labels
	private JLabel lblTitleParkedCars = new JLabel("Parked Cars:");
	private JLabel lblParkedCars = new JLabel("0");
	private JLabel lblTitleFreeSpots = new JLabel("Free Spots:");
	private JLabel lblFreeSpots = new JLabel("0");

	
	public SpotView() {
		add(panel);
		panel.add(lblTitleParkedCars);
		panel.add(lblParkedCars);
		panel.add(lblTitleFreeSpots);
		panel.add(lblFreeSpots);

		
		
	}
	
	// Getters and Setters
	public int getCarsParked(){
		return carsParked;
	}
	
	public int getTotalSpots(){
		return totalSpots;
	}
	
	public int getFreeSpots(){
		return freeSpots;
	}
	
	public void setCarsParked(int x){
		carsParked = x;
		lblParkedCars.setText(carsParked.toString());
	}
	
	public void setTotalSpots(int x){
		totalSpots = x;
	}
	
	public void setFreeSpots(int x){
		freeSpots = totalSpots - carsParked;
		lblFreeSpots.setText(freeSpots.toString());
	}
	
	public void updateAllSpots(int newParkedCars, int newTotalSpots){
		carsParked = newParkedCars;
		lblParkedCars.setText(carsParked.toString());
		totalSpots = newTotalSpots;
		freeSpots = totalSpots - carsParked;
		lblFreeSpots.setText(freeSpots.toString());
	}
	
	
//	public int getEntranceQueue() {
//		return entranceQueue;
//	}
//	
//	public int getPaymentQueue() {
//		return paymentQueue;
//	} 
//	
//	public int getExitQueue() {
//		return exitQueue;
//	}
//	
//	public void setEntranceQueue(int x) {
//		entranceQueue = x;
//		lblEntranceQueue.setText(entranceQueue.toString());
//	}
//	
//	public void setPaymentQueue(int x) {
//		paymentQueue = x;
//		lblPaymentQueue.setText(paymentQueue.toString());
//	}
//	
//	public void setExitQueue(int x) {
//		exitQueue = x;
//		lblExitQueue.setText(exitQueue.toString());
//	}
//	
//	public void updateAllQueues(int newEntranceQueue, int newPaymentQueue, int newExitQueue) {
//		entranceQueue = newEntranceQueue;
//		lblEntranceQueue.setText(entranceQueue.toString());
//		paymentQueue = newPaymentQueue;
//		lblPaymentQueue.setText(paymentQueue.toString());
//		exitQueue = newExitQueue;
//		lblExitQueue.setText(exitQueue.toString());
//	}
//	
}
