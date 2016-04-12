package view;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JFrame;

class Slice {
	double value;
	Color color;

	public Slice(double value, Color color) {
		this.value = value;
		this.color = color;
	}
}

class MyComponent extends JComponent {

	private int carsParked;
	private int totalSpaces = 540;
	private int freeSpaces = totalSpaces - carsParked;
	private Slice[] slices;
	
	public int getCarsParked(){
		return carsParked;
	}
	
	public void setCarsParked(int x){
		carsParked = x;
		slices = { new Slice(carsParked, Color.black), new Slice(freeSpaces, Color.blue) };
	}
	
	

	MyComponent() {}

	public void paint(Graphics g) {
		drawPie((Graphics2D) g, getBounds(), slices);
	}

	void drawPie(Graphics2D g, Rectangle area, Slice[] slices) {
		double total = 0.0D;
		for (int i = 0; i < slices.length; i++) {
			total += slices[i].value;
		}
		double curValue = 0.0D;
		int startAngle = 0;
		for (int i = 0; i < slices.length; i++) {
			startAngle = (int) (curValue * 360 / total);
			int arcAngle = (int) (slices[i].value * 360 / total);
			g.setColor(slices[i].color);
			g.fillArc(area.x, area.y, area.width, area.height, startAngle, arcAngle);
			curValue += slices[i].value;
		}
	}
}

public class Graph {
	private	JFrame frame = new JFrame();
	private	MyComponent content = new MyComponent();
	public Graph() {
		
	//	content.get
		
		frame.getContentPane().add(content);
		frame.setSize(300, 200);
		frame.setVisible(true);
	}
	public int getCarsParked(){
		return content.getCarsParked();
	}
	
	public void setCarsParked(int x){
		content.setCarsParked(x);
	}
}