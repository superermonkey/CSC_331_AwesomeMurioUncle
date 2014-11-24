import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
/**
 * Class for Mushroom that powers up Murio not controlled by Murio.
 * Mushroom has constant horizontal movement until it goes off screen.
 * 
 * @author RyanPierce
 *
 */
public class Mushroom extends PowerUp{

	/**
	 * Create a new Mushroom for the Level.
	 * 
	 * @param l The initial location of the Mushroom, in Point(x, y) form.
	 * @param d The size of the Mushroom, in Dimension(w, h) form.
	 * @param v The visibility boolean of the Brick.
	 * @param vector The initial speed and direction of the Mushroom.
	 * @param i The Image to display for the Mushroom.
	 */
	public Mushroom(Point l, Dimension d, boolean v, Vector vector, Image i) {
		super(l, d, v, vector, i);
	}

	/**
	 * @param g The Graphics object.
	 */
	public void draw(Graphics g) {
	}
	
	/**
	 * The movement of the mushroom.  Mushroom moves horizontally and bounces off of solid objects.
	 */
	public void move() {
	}
}
