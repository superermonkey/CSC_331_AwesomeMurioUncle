import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;

import com.sun.prism.Graphics;

/**
 * 
 */

/**
 * @author Monkey
 *
 */
public abstract class Actor extends LevelObject{

	// Gravity constant, 9.8m/s^2
	public static final double GRAVITY = 9.8;
	protected Vector velocity = new Vector(0, 0);
	
	/**
	 * @param l
	 * @param d
	 * @param v
	 */
	public Actor(Point l, Dimension d, boolean v, Vector vel, Image i) {
		super(l, d, v, i);
		this.velocity = vel;
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @return the velocity
	 */
	public Vector getVelocity() {
		return velocity;
	}

	/**
	 * @param velocity the velocity to set
	 */
	public void setVelocity(Vector velocity) {
		this.velocity = velocity;
	}

	/**
	 * @return the gravity
	 */
	public static double getGravity() {
		return GRAVITY;
	}

	public void draw(Graphics g) {
		
	}
	
	public abstract void move();

}
