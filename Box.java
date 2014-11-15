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
public abstract class Box extends LevelObject{

	/**
	 * @param l
	 * @param d
	 * @param v
	 */
	public Box(Point l, Dimension d, boolean v, Image i) {
		super(l, d, v, i);
		// TODO Auto-generated constructor stub
	}
	
	
	public void draw(Graphics g) {
		super.draw(g);
	}

}
