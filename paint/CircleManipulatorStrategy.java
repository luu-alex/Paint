package ca.utoronto.utm.paint;

import java.awt.event.MouseEvent;

class CircleManipulatorStrategy extends ShapeManipulatorStrategy {
	private Circle circle;
	@Override
	public void mouseDragged(MouseEvent e) {
		int x1=circle.getCentre().x, y1=circle.getCentre().y;
		int x2=e.getX(), y2=e.getY();
		
		int radius = (int)Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
		this.circle.setRadius(radius);
		this.paintPanel.repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
			Point centre = new Point(e.getX(), e.getY());
			this.circle=new Circle(centre, 0);
			CircleCommand circleCommand = new CircleCommand(this.circle);
			this.paintPanel.addCommand(circleCommand);
	}
}
