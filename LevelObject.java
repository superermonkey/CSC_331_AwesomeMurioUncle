import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;


/**
 * 
 */

/**
 * Any object that exists in the Murio Level.  Can be static or moving(Actor).
 * Each LevelObject has a visibility variable.
 * Each LevelObject has a size, in the for of a Dimension bounding box width and height.
 * Each has a location, based on the X-Y Coordinate of the upper-left corner of the bounding box.
 * Each has an Image associate with it.
 * @author Monkey
 *
 */
public abstract class LevelObject {
	// The visibility of the object.
	protected boolean isVisible;
	// The size of the object, contained in a Dimension with width and height.
	protected Dimension size;
	// The location of the object, contained in a Point object with X and Y coordinates of the top-left corner
	protected Point location;
	// An image for the object.
	protected Image image;
	protected boolean isSolidAndStationary;
	
	/**
	 * Constructor for the LevelObject.
	 * @param l the location of the object, in Point form (x and y).
	 * @param d the size of the object, in Dimension form (width and height).
	 * @param v the visibility of the object, in boolean.
	 * @param i the Image for the object.
	 */
	public LevelObject(Point l, Dimension d, boolean v, Image i){
		super();
		this.location = l;
		this.size = d;
		this.isVisible = v;
		this.image = i;
	}


	/**
	 * @return whether the LevelObject is visible.
	 */
	public boolean isVisible() {
		return isVisible;
	}

	/**
	 * @param isVisible Set the object as visible or not.
	 */
	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	/**
	 * @return the size, in Dimension form (width and height).
	 */
	public Dimension getSize() {
		return size;
	}

	/**
	 * @param size set the size of the object, in Dimension form (width and height).
	 */
	public void setSize(Dimension size) {
		this.size = size;
	}

	/**
	 * @return the location of the object in Point form (x and y).
	 */
	public Point getLocation() {
		return location;
	}

	/**
	 * @param location set the location of the object in Point form (x and y).
	 */
	public void setLocation(Point location) {
		this.location = location;
	}
	
	
	/**
	 * @return the image used for the LevelObject.
	 */
	public Image getImage() {
		return image;
	}


	/**
	 * @param image Set the Image for the LevelObject.
	 */
	public void setImage(Image image) {
		this.image = image;
	}

	/**
	 * Check to see if a collision occurs between LevelObjects.
	 * @param other The object this object is colliding with.
	 * @return Whether or not a collision occurred.
	 */
	public boolean collide(LevelObject other) {
		// Current LevelObject.
		Rectangle thisObject = new Rectangle(this.location.x, this.location.y, this.size.width, this.size.height);
		// Another LevelObject to check.
		Rectangle thatObject = new Rectangle(other.location.x, other.location.y, other.size.width, other.size.height);
		
		if(thisObject.intersects(thatObject)){
			return true;
		}
		else{
			return false;
		}
		
	}
	
	// Abstract so all subclasses must implement.
	abstract public void draw(Graphics g);
	
	// All classes must implement.
	abstract public void move();
	
}
