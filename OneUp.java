import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
/**
 * Class for OneUp that powers up Murio not controlled by Murio.
 * OneUp has constant horizontal movement until it goes off screen.
 * 
 * @author RyanPierce
 *
 */
public class OneUp extends PowerUp{

	/**
	 * Create a new OneUp for the Level.
	 * 
	 * @param l The initial location of the OneUp, in Point(x, y) form.
	 * @param d The size of the OneUp, in Dimension(w, h) form.
	 * @param v The visibility boolean of the Brick.
	 * @param vector The initial speed and direction of the OneUp.
	 * @param i The Image to display for the OneUp.
	 */
	public OneUp(Point l, Dimension d, boolean v, Vector vector, Image i) {
		super(l, d, v, vector, i);
	}

	/**
	 * @param g The Graphics object.
	 */
	public void draw(Graphics g) {
	}
	
	/**
	 * The movement of the OneUp.  OneUp moves horizontally and bounces off of solid objects.
	 */
	public void move() {
	}
}
