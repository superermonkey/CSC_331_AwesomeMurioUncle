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

	/* (non-Javadoc)
	 * @see LevelObject#draw(java.awt.Graphics)
	 */
	@Override
	public void draw(Graphics g) {
		super.draw(g);
	}


}
