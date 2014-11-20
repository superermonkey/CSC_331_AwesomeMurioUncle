import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;


/**
 * An Brick that can be destroyed by Big Murio, but not Small Murio.
 * Brick is a subclass of InteractiveBox, which is a subclass of LevelObject.
 * 
 * @author RyanPierce
 *
 */
public class Brick extends InteractiveBox{
	/*
	 *  Determines whether box is breakable.
	 *  All instances will be set to true when Murio is big.
	 */ 
	protected boolean isBreakable = false;
	
	/**
	 * Create a new Brick for the Level.
	 * 
	 * @param l The initial location of the Brick, in Point(x, y) form.
	 * @param d The size of the Brick, in Dimension(w, h) form.
	 * @param v The visibility boolean of the Brick.
	 * @param i The Image to display for the Brick.
	 */
	public Brick(Point l, Dimension d, boolean v, Image i) {
		super(l, d, v, i);
	}

	

	//
	//
	//
	// MINDLESS GETTERS AND SETTERS AFTER THIS POINT...!!!
	//
	//
	//
		
	/**
	 * Returns the breakability of the Brick.
	 * @return Whether or not the Brick can be broken.
	 */
	public boolean isBreakable() 
	{
		return isBreakable;
	}

	/**
	 * The breakability of the Brick, in boolean form.  Defaults to false.
	 * @param isBreakable The breakability of the Brick, in boolean form.
	 */
	public void setBreakable(boolean isBreakable) 
	{
		this.isBreakable = isBreakable;
	}


	/**
	 * Draw according to superclass LevelObject.
	 * @param g The Graphics object for drawing.
	 */
	public void draw(Graphics g) 
	{
		super.draw(g);
		// TODO Add breaking animation for big Murio.
	}

}
