package ca.utoronto.utm.paint;

public class ShapeManipulatorFactory {
	public static ShapeManipulatorStrategy create(String strategyName){
		ShapeManipulatorStrategy strategy=null;
		if(strategyName=="Circle"){
			strategy=new CircleManipulatorStrategy();
		} else if(strategyName=="Squiggle"){
			strategy=new SquiggleManipulatorStrategy();
		} else if(strategyName=="Rectangle"){
			strategy=new RectangleManipulatorStrategy();
		}
		return strategy;
	}
}
