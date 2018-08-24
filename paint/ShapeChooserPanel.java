package ca.utoronto.utm.paint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ShapeChooserPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = -2308804917821899439L;
	private ShapeManipulatorStrategy strategy;
	private Paint paint;
	public ShapeChooserPanel(Paint paint) {	
		this.paint=paint;
		String[] buttonLabels = { "Circle", "Squiggle", "Rectangle" };
		this.setLayout(new GridLayout(buttonLabels.length, 1));
		for (String label : buttonLabels) {
			JButton button = new JButton(label);
			this.add(button);
			button.addActionListener(this);
		}
 		this.reset();
	}
	
	public void reset(){
		this.setStrategy("Circle");
	}
	
	private void setStrategy(String strategyName){
		if(this.strategy!=null){
			this.strategy.uninstall();
		}
		this.strategy = ShapeManipulatorFactory.create(strategyName);
		this.strategy.install(paint.getPaintPanel());
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		this.setStrategy(e.getActionCommand());
	}
}