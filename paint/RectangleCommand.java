package ca.utoronto.utm.paint;
import java.awt.Graphics2D;
import java.io.PrintWriter;
import java.util.ArrayList;

public class RectangleCommand implements PaintCommand {
	private Rectangle rectangle;
	public RectangleCommand(Rectangle rectangle){
		this.rectangle = rectangle;
	}
	public void execute(Graphics2D g2d){
		g2d.setColor(rectangle.getColor());
		Point topLeft = this.rectangle.getTopLeft();
		Point dimensions = this.rectangle.getDimensions();
		if(rectangle.isFill()){
			g2d.fillRect(topLeft.x, topLeft.y, dimensions.x, dimensions.y);
		} else {
			g2d.drawRect(topLeft.x, topLeft.y, dimensions.x, dimensions.y);
		}
	}
	public String getInfo() {
		String p1 = "\tp1:("+Integer.toString(this.rectangle.getP1().x)+","+Integer.toString(this.rectangle.getP1().y)+")\n";
		String p2 = "\tp2:("+Integer.toString(this.rectangle.getP2().x)+","+Integer.toString(this.rectangle.getP2().y)+")\n";
		return "Rectangle\n"+this.rectangle.toString()+p1+p2+"End Rectangle\n";
	}
}
