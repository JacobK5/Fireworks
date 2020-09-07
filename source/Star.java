import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.Shape;
import java.awt.Color;
//import javax.swing.JPanel;
import java.util.ArrayList;

public class Star {
	double x;
	double y;
	boolean bright;
	int duration = 25;
	int time = 0;
	boolean timed = true;

	public Star(int w, int h) {
		x = Math.random() * w;
		y = Math.random() * h;
	}

	public void update() {
		if(Math.random() < 0.005) {
			bright = true;
		} else if(Math.random() < 0.01 && !timed) {
			bright = false;
		}
		if(timed && bright) {
			time++;
		}
		if(time > duration) {
			bright = false;
			time = 0;
		}
	}

	public void draw(Graphics g) {
		//g.setColor(Color.getHSBColor(0, 0, 1));
		if(bright) {
			g.setColor(makeColorTransparent(Color.getHSBColor(0, 0, 1), 255));
		} else {
			g.setColor(makeColorTransparent(Color.getHSBColor(0, 0, 1), 125));
		}
		Graphics2D g2d = (Graphics2D) g;
		Shape circle = new Arc2D.Double(x, y, 8, 8, 0, 360, Arc2D.CHORD);
		g2d.fill(circle);
	}

	public Color makeColorTransparent(Color c, int a) {
		return new Color(c.getRed(), c.getGreen(), c.getBlue(), a);
	}
}