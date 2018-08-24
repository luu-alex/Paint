package ca.utoronto.utm.paint;
import java.awt.Graphics2D;
import java.io.PrintWriter;

public interface PaintCommand {
	public void execute(Graphics2D g2d);
	public String getInfo();
}
