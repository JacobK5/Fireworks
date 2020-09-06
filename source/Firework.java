import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.Shape;

public class Firework {
	Particle firstParticle;
	ArrayList<Particle> explodingParticles;
	String type;
	boolean exploded;
	int lifespan;
	Color color;
	ArrayList<Integer> xExplodes;
	ArrayList<Integer> yExplodes;
	ArrayList<Particle> tail;
	ArrayList<ArrayList<Particle>> explodedTails;
	int tailLength;
	boolean stopped;

	public Firework(double w, double h) {
		firstParticle = new Particle((double) Math.random() * w, h, 0, (double) Math.random() * -15 - 20, false);
		explodingParticles = new ArrayList<Particle>();
		tail = new ArrayList<Particle>();
		explodedTails = new ArrayList<ArrayList<Particle>>();
		tailLength = 5;
		xExplodes = new ArrayList<Integer>();
		yExplodes = new ArrayList<Integer>();
		lifespan = 255;
		int r = (int) (Math.random() * 255);
		int g = (int) (Math.random() * 255);
		int b = (int) (Math.random() * 255);
		color = new Color(r, g, b);
		type = "default";
	}

	public Firework(double w, double h, ArrayList<Integer> xEx, ArrayList<Integer> yEx) {
		this(w, h);
		xExplodes = xEx;
		yExplodes = yEx;
		//System.out.println("xEx size: " + xExplodes.size());
	}

	public void importShapes() {

	}

	public void update() {
		if(!exploded) {
			firstParticle.update();
			if(firstParticle.yvel >= 0) {
				exploded = true;
				explode();
			}
			
		} else {
			for(Particle p : explodingParticles) {
				p.update();
			}
			if(lifespan > 10) {
				lifespan -= 10;
			}
			// if(lifespan < 150 && !stopped) {
			// 	for(Particle p : explodingParticles) {
			// 	p.stop();
			// 	//p.drawTail = false;
			// 	}
			// 	stopped = true;
			// }
		}
	}

	public void explode() {
		if(xExplodes.size() == 0) {
			for(int i = 0; i < 50; i++) {
			explodingParticles.add(new Particle(firstParticle.x, firstParticle.y, (double) Math.random() * 10 - 5, (double) Math.random() * 10 - 7, true));
			}
		} else {
			//System.out.println("Got into else, making " + xExplodes.size() + " particles");
			for( int i = 0; i < xExplodes.size(); i++) {
					explodingParticles.add(new Particle(firstParticle.x, firstParticle.y, xExplodes.get(i), yExplodes.get(i), true));
			}
			//System.out.println("Done making particles");
		}
		
		firstParticle = null;
	}

	// public void updateTail() {
	// 	double[] state;
	// 	if(!this.exploded) {
	// 		if(tail.size() < tailLength) {
	// 			state = firstParticle.getState();
	// 			//int loopsToDo = tailLength - tail.size();
	// 			for(int i = 0; i < tailLength; i++) {
	// 				state = firstParticle.reverseUpdate(state);
	// 				tail.add(tailLength - 1 - i ,new Particle(state[0], state[1], state[2], state[3], false));
	// 			}
	// 		} else {
	// 			tail.remove(0);
	// 			state = firstParticle.reverseUpdate(firstParticle.getState());
	// 			tail.add(new Particle(state[0], state[1], state[2], state[3], false));
	// 		}
	// 	} else {
	// 		for(Particle p : this.explodingParticles) {
				
	// 		}
	// 	}
	// }

	public void draw(Graphics g) {
		g.setColor(this.color);
		if(!this.exploded) {
			this.firstParticle.draw(g);
		} else {
			this.color = makeColorTransparent(this.color, this.lifespan);
			g.setColor(this.color);
			for(Particle p : this.explodingParticles) {
				p.draw(g);
			}
		}
	}

	public Color makeColorTransparent(Color c, int a) {
		return new Color(c.getRed(), c.getGreen(), c.getBlue(), a);
	}
}