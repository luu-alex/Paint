package ca.utoronto.utm.paint;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Paint extends JFrame implements ActionListener {
	private static final long serialVersionUID = -4031525251752065381L;

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Paint();
			}
		});
	}

	private PaintPanel paintPanel;
	private ShapeChooserPanel shapeChooserPanel;

	public Paint() {
		super("Paint"); // set the title and do other JFrame init
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setJMenuBar(createMenuBar());

		Container c = this.getContentPane();
		this.paintPanel = new PaintPanel();
		this.shapeChooserPanel = new ShapeChooserPanel(this);
		c.add(this.paintPanel, BorderLayout.CENTER);
		c.add(this.shapeChooserPanel, BorderLayout.WEST);
		this.pack();
		this.setVisible(true);
	}

	public PaintPanel getPaintPanel() {
		return paintPanel;
	}

	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu;
		JMenuItem menuItem;

		menu = new JMenu("File");

		// a group of JMenuItems
		menuItem = new JMenuItem("New");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new JMenuItem("Open");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new JMenuItem("Save");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menu.addSeparator();// -------------

		menuItem = new JMenuItem("Exit");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuBar.add(menu);

		return menuBar;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "Open") {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(this);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				System.out.println(file.getAbsolutePath());
				try {
					this.paintPanel.sendToParser(file);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				System.out.println("Opening: " + file.getName() + "." + "\n");
			} else {
				System.out.println("Open command cancelled by user." + "\n");
			}
		} else if (e.getActionCommand() == "Save") {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showSaveDialog(this);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				PrintWriter newFile = null;
				try {
					newFile = new PrintWriter(file);
					newFile.write("Paint Save File Version 1.0\n");
					this.paintPanel.save(newFile);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} finally {
					//
				}
				// This is where a real application would open the file.
				System.out.println("Saving: " + file.getName() + "." + "\n");
			} else {
				System.out.println("Save command cancelled by user." + "\n");
			}
		} else if (e.getActionCommand() == "New") {
			this.paintPanel.reset();
			this.shapeChooserPanel.reset();
		} else if(e.getActionCommand() == "Exit") {
			System.exit(1);
		}
		System.out.println(e.getActionCommand());
	}
}
