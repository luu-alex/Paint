package ca.utoronto.utm.paint;

import javax.swing.*;  
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

class PaintPanel extends JPanel {
	private static final long serialVersionUID = 3277442988868869424L;
	private ArrayList<PaintCommand> commands = new ArrayList<PaintCommand>();
	private PaintSaveFileParser parser = new PaintSaveFileParser();
	
	public PaintPanel(){
		this.setBackground(Color.white);
		this.setPreferredSize(new Dimension(300,300));
	}
	
	public void setCommands(ArrayList<PaintCommand> commands){
		this.commands=commands;
	}
	public void reset(){
		this.commands.clear();
		this.repaint();
	}
	
	public void addCommand(PaintCommand command){
		this.commands.add(command);
	}
	public void save(PrintWriter writer){
		//this is where the file is sent in and the writing happens
		for(int i=0;i<this.commands.size();i++) {
			writer.write(this.commands.get(i).getInfo());
		}
		writer.write("End Paint Save File");
		writer.close();
	}
	//This would call the parse function in PaintSaveFileParser
	public void sendToParser(File fileToParse) throws FileNotFoundException, IOException {
		FileReader fr = new FileReader(fileToParse);
		BufferedReader lineInput = new BufferedReader(fr);
		if(this.parser.parse(lineInput)) {
			this.setCommands(this.parser.getCommands());
			this.repaint();
		}

	}
	public void paintComponent(Graphics g) {
        super.paintComponent(g); //paint background
        Graphics2D g2d = (Graphics2D) g;		
		for(PaintCommand c: this.commands){
			c.execute(g2d);
		}
		g2d.dispose();
	}
}
