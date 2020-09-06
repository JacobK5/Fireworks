import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
//import java.io.File;
import java.io.*;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Graphics;

public class ShapeMaker extends JPanel implements ActionListener {
	BufferedImage image;
	int imageHeight;
	int imageWidth;
	int centerHeight;
	int centerWidth;
	ArrayList<Integer> xCoords = new ArrayList<Integer>();
	ArrayList<Integer> yCoords = new ArrayList<Integer>();

	public static void main(String[] args) {
		String imageFileName = "Images/" + args[0] + ".jpg";
		String xCoordName = args[0] + "X.ser";
		String yCoordName = args[0] + "Y.ser";
		ShapeMaker myMaker = new ShapeMaker();
		myMaker.getImage(imageFileName);
		myMaker.processImage(xCoordName, yCoordName);
		myMaker.saveLists(xCoordName, yCoordName);
	}

	public ShapeMaker() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(this);
		frame.setSize(800, 800);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		Timer timer = new Timer(20, this);
		timer.start();
	}

	public void getImage(String name) {
		//System.out.println(name);
		//File imported = new File(name);
		try {
			image = ImageIO.read(getClass().getResource(name));
		} catch(Exception e) {e.printStackTrace();}
		imageHeight = image.getHeight();
		imageWidth = image.getWidth();
		centerHeight = imageHeight / 2;
		centerWidth = imageWidth / 2;
	}

	public void processImage(String xName, String yName) {
		Color pixelColor;
		int RGB;
		double x;
		double y;
		int intX = 6;
		int intY = 6;
		double r;
		int red;
		int green;
		int blue;
		int rgbWhite = new Color(255, 255, 255).getRGB();
		int rgbBleh = new Color(105, 15, 20).getRGB();
		// for(int i = 0; i < imageWidth; i++) {
		// 	for(int j = 0; j < imageHeight; j++) {
		// 		RGB = image.getRGB(i, j);
		// 		int red= (RGB>>16)&255;
		// 		int green= (RGB>>8)&255;
		// 		int blue= (RGB)&255;
		// 		pixelColor = new Color(red, green, blue);
		// 		if(pixelColor.equals(Color.BLACK)) {
		// 			xCoords.add(i - centerWidth);
		// 			yCoords.add(j - centerHeight);
		// 			//System.out.println((i - centerWidth) + ", " + (j - centerWidth));
		// 		}
		// 	}
		// }
		System.out.println("Image width and height are: " + imageWidth +", " + imageHeight);
		int maxRadius = imageHeight * 5 / 12;
		for(double angle = 0; angle <= (2 * Math.PI); angle += 0.00001) {
			r = 0;
			intX = 6;
			intY = 6;
			while(intX > 5 && intX < (imageWidth - 5) && intY > 5 && intY < (imageHeight - 5)) {
				x = r * Math.cos(angle) + centerWidth;
				y = r * Math.sin(angle) + centerHeight;
				intX = (int) x;
				intY = (int) y;
				//System.out.println("We are checking " + intX + ", " + intY);
				RGB = image.getRGB(intX, intY);
				red = (RGB>>16)&255;
				green = (RGB>>8)&255;
				blue = (RGB)&255;
				pixelColor = new Color(red, green, blue);
				if(pixelColor.equals(Color.BLACK)) {
					//System.out.println("Black at " + (intX) + ", " + (intY));
					xCoords.add(intX - centerWidth);
		 			yCoords.add(intY - centerHeight);
		 			image.setRGB(intX, intY, rgbWhite);
				}
				// if(r > maxRadius - 5) {
				// 	image.setRGB(intX, intY, rgbBleh);
				// }
				r += 1;
			}
		}
		System.out.println("Image processed");
		System.out.println("Captured " + xCoords.size() + " pixels");
	}

	public void saveLists(String xName, String yName) {
		File xFileName = new File("Patterns/" + xName);
		File yFileName = new File("Patterns/" + yName);
		try {
			ObjectOutputStream xOutput = new ObjectOutputStream(new FileOutputStream(xFileName));
	 		xOutput.writeObject(xCoords);
	 		ObjectOutputStream yOutput = new ObjectOutputStream(new FileOutputStream(yFileName));
	 		yOutput.writeObject(yCoords);
	 		xOutput.close();
	 		yOutput.close();
		} catch(Exception e) {e.printStackTrace();}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.repaint();
	}
}