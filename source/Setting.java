import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
// import javax.swing.JFrame;
// import javax.swing.JPanel;
// import javax.swing.JButton;
// import javax.swing.Timer;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.geom.Arc2D;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.Collections;
import java.io.*;

@SuppressWarnings("unchecked")
public class Setting extends JPanel {
	JFrame frame;
	Timer timer;
	ArrayList<Firework> fireworks;
	ArrayList<Star> stars;
	int numStars;
	int height;
	int width;
	ArrayList<Integer> shapeX;
	ArrayList<Integer> shapeY;
	ArrayList<ArrayList<Integer>> allShapesX;
	ArrayList<ArrayList<Integer>> allShapesY;
	ArrayList<ArrayList<Integer>> useShapesX;
	ArrayList<ArrayList<Integer>> useShapesY;
	ArrayList<String> allFileNames;
	//ArrayList<Integer> useX;
	//ArrayList<Integer> useY;
	int trailLength;
	JFrame setUpFrame;
	JPanel choosingPanel;
	JList patternOptions;
	JPanel setUpPanel;
	ArrayList<String> patternsToUse;


	public Setting() {
		fireworks = new ArrayList<Firework>();
		stars = new ArrayList<Star>();
		numStars = 30;
		//useX = new ArrayList<Integer>();
		//useY = new ArrayList<Integer>();
		patternsToUse = new ArrayList<String>();
		allShapesX = new ArrayList<ArrayList<Integer>>();
		allShapesY = new ArrayList<ArrayList<Integer>>();
		useShapesX = new ArrayList<ArrayList<Integer>>();
		useShapesY = new ArrayList<ArrayList<Integer>>();
		frame = new JFrame("Fireworks!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.setLocationRelativeTo(null);
		this.setBackground(Color.BLACK);
		frame.getContentPane().add(this);
		setUpFrame = new JFrame();
		setUpFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUpFrame.setSize(400, 300);
		setUpFrame.setLocationRelativeTo(null);
		setUpPanel = new JPanel();	

		fillStars();

		setUpPanel.setLayout(new BoxLayout(setUpPanel, BoxLayout.PAGE_AXIS));
		JButton startBtn = new JButton("Start");
		startBtn.addActionListener(new StartListener());
		JButton choosePatternButton = new JButton("Choose patterns");
		choosePatternButton.addActionListener(new ChoosePatternListener());
		JButton addPatternButton = new JButton("Add a new pattern");

		setUpPanel.add(startBtn);
		setUpPanel.add(choosePatternButton);
		setUpPanel.add(addPatternButton);
		setUpFrame.getContentPane().add(setUpPanel);
		makeChoosingPanel();

		JPanel addingPanel = new JPanel();
		
		setUpFrame.setVisible(true);
	}

	public void fillStars() {
		for(int i = 0; i < numStars; i++) {
			stars.add(new Star(frame.getWidth(), frame.getHeight()));
		}
	}

	public static void main(String[] args) {
		Setting mySetting = new Setting();
	}

	public void importShapes() {
		ArrayList<Integer> toAdd = new ArrayList<Integer>();
		for (String name : patternsToUse) {
			File xLoad = new File("Patterns/" + name + "X.ser");
			File yLoad = new File("Patterns/" + name + "Y.ser");
			try {
				ObjectInputStream xInput = new ObjectInputStream(new FileInputStream(xLoad));
		 		allShapesX.add((ArrayList<Integer>) xInput.readObject());
		 		xInput.close();
		 		ObjectInputStream yInput = new ObjectInputStream(new FileInputStream(yLoad));
		 		allShapesY.add((ArrayList<Integer>) yInput.readObject());
		 		yInput.close();
			} catch(Exception e) {e.printStackTrace();}
 		}

 		int increment;
 		int scaling;
 		for(ArrayList<Integer> list : allShapesX) {
 			increment = list.size() / 350;//how many particles per firework
			scaling = Collections.max(list) / 15;
			for(int i = 0; i < list.size(); i+= increment) {
				toAdd.add(list.get(i) / scaling);
			}
			//System.out.println(toAdd);
			useShapesX.add(new ArrayList<Integer>(toAdd));
			//System.out.println("UseShapesX(0): " + useShapesX.get(0));
			toAdd.clear();
 		}
 		for(ArrayList<Integer> list : allShapesY) {
 			increment = list.size() / 350;//how many particles per firework
			scaling = Collections.max(list) / 15;
			for(int i = 0; i < list.size(); i+= increment) {
				toAdd.add(list.get(i) / scaling);
			}
			useShapesY.add(new ArrayList<Integer>(toAdd));
			toAdd.clear();
 		}
	 		//set lists we will actually use
	 		
	 		
	 		// for(int i = 0; i < shapeX.size(); i += increment) {
	 		// 	useX.add((shapeX.get(i) / scaling));
	 		// 	useY.add((shapeY.get(i) / scaling));
	 		// }
	
		
	}

	public void makeChoosingPanel() {
		choosingPanel = new JPanel();
		//get all options for patterns
		File patternsDir = new File("Patterns/");
		File[] filesInDir = patternsDir.listFiles();
		allFileNames = new ArrayList<String>();
		for(File f : filesInDir) {
			if(f.getName().endsWith("X.ser")) {
				String[] nameParts = f.getName().split("X");
				allFileNames.add(nameParts[0]);
			}
		}
		DefaultListModel model = new DefaultListModel();
		for(String n : allFileNames) {
			model.addElement(n);
		}
		patternOptions = new JList(model);
		//load previous selections

		JScrollPane patternScrollPane = new JScrollPane(patternOptions);
		choosingPanel.add(patternScrollPane);
		JButton savePatternChoice = new JButton("Save and return");
		savePatternChoice.addActionListener(new SavePatternListener());
		choosingPanel.add(savePatternChoice);
	}


	@Override
	public void paintComponent(Graphics g) {
		//trailLength++;
		//if(trailLength > 10) {
			super.paintComponent(g);
			//trailLength = 0;
		//}//not gonna actually do it this way
		for(Star s : stars) {
			s.draw(g);
		}
		
		for(Firework f : fireworks) {
			f.draw(g);
		}
	}

	public class TimerListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			height = frame.getHeight();
			width = frame.getWidth();
			if(Math.random() < 0.1 && fireworks.size() < 5) {
				int randomChoice = (int) (Math.random() * useShapesX.size());
				//System.out.println(useShapesX.get(randomChoice));
				fireworks.add(new Firework(width, height, useShapesX.get(randomChoice), useShapesY.get(randomChoice)));
				//System.out.println("Firework count: " + fireworks.size());
			}

			for(int i = 0; i < fireworks.size(); i++) {
				fireworks.get(i).update();
				if(fireworks.get(i).lifespan < 10) {
					fireworks.remove(fireworks.get(i));
				}
			}
			for(Star s : stars) {
				s.update();
			}
			frame.repaint();
		}
	}

	public class StartListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			frame.setVisible(true);
			setUpFrame.dispose();
			importShapes();
			timer = new Timer(40, new TimerListener());
			timer.start();
		}
	}

	public class SavePatternListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			//save choices
			patternsToUse = (ArrayList<String>) patternOptions.getSelectedValuesList();
			//go back to main menu
			setUpFrame.getContentPane().remove(choosingPanel);
			setUpFrame.getContentPane().add(setUpPanel);
			setUpFrame.getContentPane().revalidate();
			setUpFrame.getContentPane().repaint();
			//setUpFrame.pack();
		}
	}

	public class ChoosePatternListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			//go to choosing panel
			setUpFrame.getContentPane().remove(setUpPanel);
			setUpFrame.getContentPane().add(choosingPanel);
			setUpFrame.getContentPane().revalidate();
			setUpFrame.getContentPane().repaint();
			//setUpFrame.pack();
		}
	}
}