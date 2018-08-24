package ca.utoronto.utm.paint;

import java.util.ArrayList;

public class Squiggle extends Shape {
	private ArrayList<Point> points=new ArrayList<Point>();
	
	public Squiggle(){
		
	}
	public void add(Point p){ this.points.add(p); }
	public ArrayList<Point> getPoints(){ return this.points; }
}