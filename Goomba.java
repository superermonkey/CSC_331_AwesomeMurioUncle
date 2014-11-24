import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;

/**
 * @author RyanPierce
 *
 */
public class Goomba extends Enemy{

	/**
	 * Create a new Goomba for the Level.
	 * 
	 * @param l The initial location of the Goomba, in Point(x, y) form.
	 * @param d The size of the Goomba, in Dimension(w, h) form.
	 * @param v The visibility boolean of the Goomba.
	 * @param vector The initial speed and direction of the Goomba.
	 * @param i The Image to display for the Goomba.
	 * @param point The point value of the Goomba.
	 */
	public Goomba(Point l, Dimension d, boolean v, Vector vector, Image i, int point) {
		super(l, d, v, vector, i, point);
	}
	
	/**
	 *  The movement of the Goomba.
	 */
	public void move() {
	}
}
