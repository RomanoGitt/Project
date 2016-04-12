package controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import main.Simulator;

public class ButtonController implements ActionListener{
	private ActionEvent event;
	private Simulator simulator;
	
	public ButtonController(Simulator simulator) {
		this.simulator = simulator;
	}

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
							simulator.tick();
						}
						break;
					case "100ticks":
						for (int i = 0; i < 100; i++) {
							simulator.tick();
						}
						break;
					case "1440ticks":
						for (int i = 0; i < 1440; i++) {
							simulator.tick();
						}
						break;
					case "showStatus":
						simulator.getSimulatorView().toggleStatusVisible();
					}

				}
			};
			buttonListenerThread.start();
		}
}
