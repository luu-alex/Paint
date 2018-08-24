package ca.utoronto.utm.paint;

import java.awt.Color;

public class Shape {
	private Color color;
	private boolean fill;
	Shape(){
		// Pick a random color for this
		int r = (int)(Math.random()*256);
		int g = (int)(Math.random()*256);
		int b= (int)(Math.random()*256);
		this.color = new Color(r, g, b);
		
		this.fill = (1==(int)(Math.random()*2));
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public boolean isFill() {
		return fill;
	}
	public void setFill(boolean fill) {
		this.fill = fill;
	}
	public String toString(){
		int r = this.color.getRed();
		int g = this.color.getGreen();
		int b = this.color.getBlue();

		String s = "";
		s+="\tcolor:"+r+","+g+","+b+"\n";
		s+="\tfilled:"+this.fill+"\n";
		return s;
	}
}
