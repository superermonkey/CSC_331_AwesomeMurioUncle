import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

/**
 * An InteractiveBox represents any box that can be affected by Murio.
 * These can be Bricks, Coin Boxes, or Question Mark Boxes.
 * InteractiveBox is a subclass of LevelObject, and is the superclass to Brick, QuestionMarkBox and MetalBox.
 * 
 * @author RyanPierce
 *
 */
public abstract class InteractiveBox extends LevelObject{

	/**
	 * Create a new Box for the Level.
	 * 
	 * @param l The initial location of the Box, in Point(x, y) form.
	 * @param d The size of the Box, in Dimension(w, h) form.
	 * @param v The visibility boolean of the Box.
	 * @param i The Image to display for the Box.
	 */
	public InteractiveBox(Point l, Dimension d, boolean v, Image i) {
		super(l, d, v, i);
	}
	
	/**
	 * Draw according to superclass LevelObject.
	 * @param g The Graphics object for drawing.
	 */
	public void draw(Graphics g) {
		super.draw(g);
	}
}
