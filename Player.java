import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Vector;


/**
 * 
 */

/**
 * @author Monkey
 *
 */
public class Player extends Actor{

	private double angle;
	/**
	 * @param l
	 * @param d
	 * @param v
	 * @param vector
	 */
	public Player(Point l, Dimension d, boolean v, Vector vector) {
		super(l, d, v, vector);
		angle = 0;
	}

	/**
	 * @return the angle
	 */
	public double getAngle() {
		return angle;
	}

	/**
	 * @param angle the angle to set
	 */
	public void setAngle(double angle) {
		this.angle = angle;

	}

	/* (non-Javadoc)
	 * @see LevelObject#draw(java.awt.Graphics)
	 */
	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}
}