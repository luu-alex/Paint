package ca.utoronto.utm.paint;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class ShapeManipulatorStrategy implements MouseMotionListener, MouseListener {

	protected PaintPanel paintPanel; // so our subclasses can access this

	public void install(PaintPanel paintPanel) {
		this.paintPanel=paintPanel;
		this.paintPanel.addMouseListener(this);
		this.paintPanel.addMouseMotionListener(this);
	}
	
	public void uninstall() {
		this.paintPanel.removeMouseListener(this);
		this.paintPanel.removeMouseMotionListener(this);
	}
	
	// MouseMotionListener below
	@Override
	public void mouseMoved(MouseEvent e) { }
	@Override
	public void mouseDragged(MouseEvent e) { }

	// MouseListener below
	@Override
	public void mouseClicked(MouseEvent e) { }

	@Override
	public void mousePressed(MouseEvent e) { }

	@Override
	public void mouseReleased(MouseEvent e) { }

	@Override
	public void mouseEntered(MouseEvent e) { }

	@Override
	public void mouseExited(MouseEvent e) { }
}
