import java.awt.Graphics;
import java.awt.Graphics2D;
//import java.awt.geom.*;
import java.awt.geom.Line2D;
import java.awt.BasicStroke;
import java.awt.Shape;
import java.awt.Color;
import javax.swing.JPanel;
import java.util.ArrayList;

public class Tail extends Particle {
	//boolean justMade;
	// double x;
	// double y;
	// double xvel;
	// double yvel;
	// double acc;
	// double gravity;
	double prevX;
	double prevY;

	public Tail(double x_, double y_, double xvel_, double yvel_) {
		super(x_, y_, xvel_, yvel_, false);
		//justMade = true;
		// x = x_;
		// y = y_;
		// xvel = xvel_;
		// yvel = yvel_;
		// gravity = 1f;
	}

	public void update() {
		//if(!justMade) {
			applyForce(gravity);
			yvel += acc;
			prevX = x;
			prevY = y;
			x += xvel;
			y += yvel;
		//} else {
		//	justMade = false;
		//}
	}

	// public void applyForce(double force) {
	// 	acc = force;
	// }

	public void draw(Graphics g, int size) {
		Graphics2D g2d = (Graphics2D) g;
		//Shape circle = new Arc2D.Double(x, y, 10, 10, 0, 360, Arc2D.CHORD);
		//g2d.fill(circle);
		g2d.setStroke(new BasicStroke(size));
		Shape line = new Line2D.Double(prevX + 5, prevY + 5, x + 5, y + 5);
		g2d.draw(line);
		//g.drawLine((int) prevX + 5,(int)  prevY + 5, (int) x + 5, (int) y + 5);
	}
}