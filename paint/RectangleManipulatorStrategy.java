package ca.utoronto.utm.paint;

import java.awt.event.MouseEvent;

class RectangleManipulatorStrategy extends ShapeManipulatorStrategy {
	private Rectangle rectangle;
	@Override
	public void mouseDragged(MouseEvent e) {
		Point p2=new Point(e.getX(), e.getY());
		this.rectangle.setP2(p2);
		this.paintPanel.repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
			Point p1 = new Point(e.getX(), e.getY());
			Point p2 = new Point(e.getX(), e.getY());

			Rectangle rectangle = new Rectangle(p1,p2);
			this.rectangle = rectangle;
			RectangleCommand rectangleCommand = new RectangleCommand(this.rectangle);
			this.paintPanel.addCommand(rectangleCommand);
	}
}
