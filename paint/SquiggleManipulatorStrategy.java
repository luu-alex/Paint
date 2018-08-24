package ca.utoronto.utm.paint;

import java.awt.event.MouseEvent;

class SquiggleManipulatorStrategy extends ShapeManipulatorStrategy {
	private Squiggle squiggle;
	@Override
	public void mouseDragged(MouseEvent e) {
		this.squiggle.add(new Point(e.getX(), e.getY()));
		this.paintPanel.repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
			this.squiggle = new Squiggle();
			SquiggleCommand squiggleCommand = new SquiggleCommand(this.squiggle);
			this.paintPanel.addCommand(squiggleCommand);
	}
}
