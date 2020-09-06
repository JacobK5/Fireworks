import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.Shape;
import java.awt.Color;
//import javax.swing.JPanel;
import java.util.ArrayList;

public class Particle {
	double x;
	double y;
	double xvel;
	double yvel;
	double acc;
	double gravity;
	ArrayList<Tail> tail;
	int tailLength;
	int transparencyFactor;
	boolean moved;
	boolean drawTail;

	public Particle(double x_, double y_, double xvel_, double yvel_, boolean boomed) {
		x = x_;
		y = y_;
		xvel = xvel_;
		yvel = yvel_;
		gravity = 1f;
		drawTail = false;
		if(drawTail) {
			tailLength = 3;
			transparencyFactor = 255 / (tailLength + 1);
			tail = new ArrayList<Tail>();
		}
	}

	public void update() {
		if(drawTail) {
			if(tail.size() < tailLength && moved) {
				tail.add(new Tail(x - xvel * tail.size(), y - yvel * tail.size(), xvel, yvel - acc * tail.size()));
			}
		}
		applyForce(gravity);
		yvel += acc;
		x += xvel;
		y += yvel;
		if(drawTail) {
			for(Tail t : tail) {
				t.update();
			}
			if(!moved) {
				moved = true;
			}
		}
	}

	public void applyForce(double force) {
		acc = force;
	}

	public void stop() {
		yvel = 0;
		xvel = 0;
		for(Tail t : tail) {
				t.stop();
			}
	}

	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		Shape circle = new Arc2D.Double(x, y, 10, 10, 0, 360, Arc2D.CHORD);
		g2d.fill(circle);
		if(drawTail) {
			drawTail(g);
		}
	}

	public void drawTail(Graphics g) {
		//if(tail.size() > 0) {
			for(int i = 0; i < tail.size() ; i++) {
				int alpha = 255 - (i + 1) * transparencyFactor;
				//System.out.println(alpha);
				g.setColor(makeColorTransparent(g.getColor(), alpha));
				tail.get(i).draw(g, (tail.size() - i) * 2);
			}
		//}
	}

	public Color makeColorTransparent(Color c, int a) {
		return new Color(c.getRed(), c.getGreen(), c.getBlue(), a);
	}

	// public double[] getState() {
	// 	double[] state = new double[4];
	// 	state[0] = x;
	// 	state[1] = y;
	// 	state[2] = xvel;
	// 	state[3] = yvel;
	// 	return state;
	// }

	// public double[] reverseUpdate(double[] state) {
	// 	applyForce(gravity);
	// 	yvel -= acc;
	// 	state[0] -= xvel;
	// 	state[1] -= yvel;
	// 	state[2] = xvel;
	// 	state[3] = yvel;
	// 	return state;
	// }
}