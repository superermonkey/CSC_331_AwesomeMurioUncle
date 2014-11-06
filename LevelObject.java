import java.awt.Dimension;
import java.awt.Point;

import com.sun.prism.Graphics;

/**
 * 
 */

/**
 * @author Monkey
 *
 */
public abstract class LevelObject {
	protected boolean isVisible;
	protected Dimension size;
	protected Point location;
	
	public LevelObject(Point l, Dimension d, boolean v){
		super();
		this.location = l;
		this.size = d;
		this.isVisible = v;
		
	}

	/**
	 * @return the isVisible
	 */
	public boolean isVisible() {
		return isVisible;
	}

	/**
	 * @param isVisible the isVisible to set
	 */
	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	/**
	 * @return the size
	 */
	public Dimension getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(Dimension size) {
		this.size = size;
	}

	/**
	 * @return the location
	 */
	public Point getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(Point location) {
		this.location = location;
	}
	
	
	// Abstract so all subclasses must implement.
	abstract public void draw(Graphics g);
	
}