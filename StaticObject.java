import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

/**
 * @author RyanPierce
 *
 */
public class StaticObject extends LevelObject {

	/**
	 * @param l
	 * @param d
	 * @param v
	 */
	public StaticObject(Point l, Dimension d, boolean v, Image i) {
		super(l, d, v, i);
		// TODO Auto-generated constructor stub
	}
	public void draw(Graphics g) {
		super.draw(g);
	}
	public void move(){
		// Not Used.
	}
}
