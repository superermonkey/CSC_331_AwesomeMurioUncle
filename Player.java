import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;


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
	public Player(Point l, Dimension d, boolean v, Vector vector, Image i) {
		super(l, d, v, vector, i);
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
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(this.image, location.x, location.y, size.width, size.height, null);
	}
}