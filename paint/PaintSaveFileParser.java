package ca.utoronto.utm.paint;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
/**
 * Parse a file in Version 1.0 PaintSaveFile format. An instance of this class
 * understands the paint save file format, storing information about
 * its effort to parse a file. After a successful parse, an instance
 * will have an ArrayList of PaintCommand suitable for rendering.
 * If there is an error in the parse, the instance stores information
 * about the error. For more on the format of Version 1.0 of the paint 
 * save file format, see the associated documentation.
 * 
 * @author 
 *
 */
public class PaintSaveFileParser {
	private int lineNumber = 0; // the current line being parsed
	private String errorMessage =""; // error encountered during parse
	public ArrayList<PaintCommand> commands; // created as a result of the parse
	
	/**
	 * Below are Patterns used in parsing 
	 */
	private Pattern pFileStart=Pattern.compile("^PaintSaveFileVersion1.0$");
	
	private Pattern pFileEnd=Pattern.compile("^EndPaintSaveFile$");

	private Pattern pCircleStart=Pattern.compile("^Circle$");
	private Pattern pCircleEnd=Pattern.compile("^EndCircle$");


	// ADD MORE!!
	private Pattern pRectangleStart=Pattern.compile("^Rectangle$");
	private Pattern pRectangleEnd=Pattern.compile("^EndRectangle$");
	private Pattern pSquiggleStart=Pattern.compile("^Squiggle$");
	private Pattern pSquiggleEnd=Pattern.compile("^EndSquiggle$");

	private Pattern pColor = Pattern.compile("^color:([01]?[0-9]?[0-9]|2[0-4][0-9]|25[0-5]),([01]?[0-9]?[0-9]|2[0-4][0-9]|25[0-5]),([01]?[0-9]?[0-9]|2[0-4][0-9]|25[0-5])$");
	private Pattern pFill = Pattern.compile("^filled:(true|false)$");

	private Pattern pBlankLine = Pattern.compile("^$");
	
	/**
	 * Store an appropriate error message in this, including 
	 * lineNumber where the error occurred.
	 * @param mesg
	 */
	private void error(String mesg){
		this.errorMessage = "Error in line "+lineNumber+" "+mesg;
	}
	/**
	 * 
	 * @return the PaintCommands resulting from the parse
	 */
	public ArrayList<PaintCommand> getCommands(){
		return this.commands;
	}
	/**
	 * 
	 * @return the error message resulting from an unsuccessful parse
	 */
	public String getErrorMessage(){
		return this.errorMessage;
	}
	
	/**
	 * Parse the inputStream as a Paint Save File Format file.
	 * The result of the parse is stored as an ArrayList of Paint command.
	 * If the parse was not successful, this.errorMessage is appropriately
	 * set, with a useful error message.
	 * 
	 * @param inputStream the open file to parse
	 * @return whether the complete file was successfully parsed
	 */
	public boolean parse(BufferedReader inputStream) {
		this.commands = new ArrayList<PaintCommand>();
		this.errorMessage="";
		
		// During the parse, we will be building one of the 
		// following shapes. As we parse the file, we modify 
		// the appropriate shape.
		
		Circle circle = null; 
		Rectangle rectangle = null;
		Squiggle squiggle = null;
	
		try {	
			int state=0; Matcher m; String l;
			
			this.lineNumber=0;
			while ((l = inputStream.readLine().replaceAll("\\s+", "")) != null) { // taken care of white spaces.
				this.lineNumber++;
				switch(state){
					case 0:
						if(pBlankLine.matcher(l).matches()) {
							break;
						}
						m=pFileStart.matcher(l);
						if(m.matches()){
							state=1;
							break;
						}
						error("expected Start of Paint Save File.");
						state = 911;
						break;
					case 1: // Looking for the start of a new object or end of the save file
						if(pBlankLine.matcher(l).matches()) {
							break;
						}
						if(pFileEnd.matcher(l).matches()) {
							break;
						}
						if (pCircleStart.matcher(l).matches()) {
							state = 88;
							circle = new Circle();
						} else if(pRectangleStart.matcher(l).matches()) {
							rectangle = new Rectangle();
							state=88;
						} else if(pSquiggleStart.matcher(l).matches()) {
							squiggle = new Squiggle();
							state=88;
						} else if (pFileEnd.matcher(l).matches()) {
							return true;
						} else {
							state=911;
							error("expected shape starter.");
						}
						break;
					case 2: //circle center
						if(pBlankLine.matcher(l).matches()) {
							break;
						}
						Pattern pCircleCenter = Pattern.compile("^center:\\((\\d{1,3}),(\\d{1,3})\\)$");
						m = pCircleCenter.matcher(l);
						if (m.matches()) {
							int x = Integer.parseInt(m.group(1));
							int y = Integer.parseInt(m.group(2));
							circle.setCentre(new Point(x,y));
							state = 3;
							break;
						}
						error("expected circle center.");
						state=911;
						break;
					case 3: // circle radius
						if(pBlankLine.matcher(l).matches()) {
							break;
						}
						Pattern pCircleRadius = Pattern.compile("^radius:(\\d{1,})$");
						m = pCircleRadius.matcher(l);
						if (m.matches()) {
							state = 4;
							circle.setRadius(Integer.parseInt(m.group(1)));
						} else {
							error("expected circle radius.");
							state = 911;
						}
						break;
					case 4: // End circle
						if(pBlankLine.matcher(l).matches()) {
							break;
						}
						m = pCircleEnd.matcher(l);
						if (m.matches()) {
							CircleCommand circleCommand = new CircleCommand(circle);
							this.commands.add(circleCommand);
							circle = null;
							state = 1; //return to state that allocates shape
							break;

						} error("expected end of a circle object.");
						state=911;
						break;
					case 5: //rectangle p1
						if(pBlankLine.matcher(l).matches()) {
							break;
						}
						Pattern point1=Pattern.compile("^p1:\\(([0-9]\\d*),([0-9]\\d*)\\)$");
						m=point1.matcher(l);
						if(m.matches()) {
							int p1x=Integer.parseInt(m.group(1));
							int p1y=Integer.parseInt(m.group(2));
							rectangle.setP1(new Point(p1x,p1y));
							state=6;
							break;
						}
						error("expected one of the rectangle shape point.");
						state=911;
						break;
					case 6://rectangle p2
						if(pBlankLine.matcher(l).matches()) {
							break;
						}
						Pattern point2=Pattern.compile("^p2:\\(([0-9]\\d*),([0-9]\\d*)\\)$");
						m=point2.matcher(l);
						if(m.matches()) {
							int p2x=Integer.parseInt(m.group(1));
							int p2y=Integer.parseInt(m.group(2));
							rectangle.setP2(new Point(p2x,p2y));
							state=7;
							break;
						}
						error("expected one of the rectangle shapes point.");
						state=911;
						break;
					case 7://end rectangle
						if(pBlankLine.matcher(l).matches()) {
							break;
						}
						m=pRectangleEnd.matcher(l);
						if(m.matches()) {
							RectangleCommand rectangleCommand = new RectangleCommand(rectangle);
							this.commands.add(rectangleCommand);
							rectangle=null;

							state=1;
							break;
						}

						error("expected ending of a rectanlge shapes.");
						state=911;
						break;
					case 8://squiggle points array begin
						if(pBlankLine.matcher(l).matches()) {
							break;
						}
						Pattern pointsArray =Pattern.compile("^points$");
						m=pointsArray.matcher(l);
						if(m.matches()) {
							state=9;
							break;
						}
						error("expected points of squiggle.");
						state=911;
						break;
					case 9://point add squiggle
						if(pBlankLine.matcher(l).matches()) {
							break;
						}
						Pattern point = Pattern.compile("^point:\\(([0-9]\\d*),([0-9]\\d*)\\)$");
						m=point.matcher(l);
						if(m.matches()) {
							int px=Integer.parseInt(m.group(1));
							int py=Integer.parseInt(m.group(2));
							squiggle.add(new Point(px,py));
							state=9;
							break;
						} else {
							Pattern endPoints = Pattern.compile("^endpoints");
							m=endPoints.matcher(l);
							if(m.matches()) {
								state=10;
								break;
							} 
						}
						error("expected squiggle shape.");
						state=911;
						break;
					case 10://end squiggle
						if(pBlankLine.matcher(l).matches()) {
							break;
						}
						m=pSquiggleEnd.matcher(l);
						if(m.matches()) {
							SquiggleCommand squiggleCommand = new SquiggleCommand(squiggle);
							this.commands.add(squiggleCommand);
							squiggle=null;
							state=1;
							break;
						}
						error("expected Squiggle end.");
						state=911;
						break;
					case 88://color case
						if(pBlankLine.matcher(l).matches()) {
							break;
						}
						m=pColor.matcher(l);
						if (m.matches()) {
							int r = Integer.parseInt(m.group(1));
							int g = Integer.parseInt(m.group(2));
							int b = Integer.parseInt(m.group(3));
							state = 99;
							if (circle != null) {
								circle.setColor(new Color(r,g,b));
							}else if (rectangle != null) {
								rectangle.setColor(new Color(r,g,b));
							}else if (squiggle != null) {
								squiggle.setColor(new Color(r,g,b));
							}
						} else {
							state = 911; //dead state
							error("expected color of the shape.");
						}
						break;
					case 99://fill case
						if(pBlankLine.matcher(l).matches()) {
							break;
						}
						m=pFill.matcher(l);
						if (m.matches()) {
							if (circle != null) {
								circle.setFill(Boolean.valueOf(m.group(1)));
								state = 2;
							}else if (rectangle != null) {
								rectangle.setFill(Boolean.valueOf(m.group(1)));
								state = 5;
							}else if (squiggle != null) {
								squiggle.setFill(Boolean.valueOf(m.group(1)));
								state = 8;
							}
						}else {
							state = 911;
							error("expected fill property of the shape.");
						}
						break;
					case 911: //dead state
						JOptionPane.showMessageDialog(null,
							    "The file chosen to open is invalid. Please make sure you do not modify the saved file. "+this.getErrorMessage(),
							    "File Open Error",
							    JOptionPane.ERROR_MESSAGE);
						return false;
				}
			} 
		}  catch (Exception e){
			
		}
		return true;
	}
}