package view;
import javax.swing.JPanel;
import javax.swing.JLabel;

public class QueueView extends JPanel {
	private JPanel panel = new JPanel();
	
	//Variables
	private Integer entranceQueue;
	private Integer paymentQueue;
	private Integer exitQueue;
	
	//Labels
	private JLabel lblTitleEntranceQueue = new JLabel("Entrance Queue:");
	private JLabel lblEntranceQueue = new JLabel("0");
	private JLabel lblTitlePaymentQueue = new JLabel("Payment Queue:");
	private JLabel lblPaymentQueue = new JLabel("0");
	private JLabel lblTitleExitQueue = new JLabel("Exit Queue:");
	private JLabel lblExitQueue = new JLabel("0");
	
	public QueueView() {
		add(panel);
		panel.add(lblTitleEntranceQueue);
		panel.add(lblEntranceQueue);
		panel.add(lblTitlePaymentQueue);
		panel.add(lblPaymentQueue);
		panel.add(lblTitleExitQueue);
		panel.add(lblExitQueue);
		
		
	}
	
	// Getters and Setters
	public int getEntranceQueue() {
		return entranceQueue;
	}
	
	public int getPaymentQueue() {
		return paymentQueue;
	}
	
	public int getExitQueue() {
		return exitQueue;
	}
	
	public void setEntranceQueue(int x) {
		entranceQueue = x;
		lblEntranceQueue.setText(entranceQueue.toString());
	}
	
	public void setPaymentQueue(int x) {
		paymentQueue = x;
		lblPaymentQueue.setText(paymentQueue.toString());
	}
	
	public void setExitQueue(int x) {
		exitQueue = x;
		lblExitQueue.setText(exitQueue.toString());
	}
	
	public void updateAllQueues(int newEntranceQueue, int newPaymentQueue, int newExitQueue) {
		entranceQueue = newEntranceQueue;
		lblEntranceQueue.setText(entranceQueue.toString());
		paymentQueue = newPaymentQueue;
		lblPaymentQueue.setText(paymentQueue.toString());
		exitQueue = newExitQueue;
		lblExitQueue.setText(exitQueue.toString());
	}
	
}
