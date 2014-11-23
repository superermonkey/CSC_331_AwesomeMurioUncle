import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;


/**
 * Any object that exists in the Murio Level.  Can be static or moving(Actor).
 * Each LevelObject has a visibility variable.
 * Each LevelObject has a size, in the for of a Dimension bounding box width and height.
 * Each has a location, based on the X-Y Coordinate of the upper-left corner of the bounding box.
 * Each has an Image associate with it.
 * @author Monkey
 *
 */
public abstract class LevelObject{
	// The visibility of the object.
	protected boolean isVisible;
	// The size of the object, contained in a Dimension with width and height.
	protected Dimension size;
	// The location of the object, contained in a Point object with X and Y coordinates of the top-left corner
	protected Point originalLocation;
	protected Point location;
	// An image for the object.
	protected Image image;
	
	/**
	 * Constructor for the LevelObject.
	 * @param l the location of the object, in Point form (x and y).
	 * @param d the size of the object, in Dimension form (width and height).
	 * @param v the visibility of the object, in boolean.
	 * @param i the Image for the object.
	 */
	public LevelObject(Point l, Dimension d, boolean v, Image i){
		this.location = l;
		this.originalLocation = l;
		this.size = d;
		this.isVisible = v;
		this.image = (Image)i;
	}

	/**
	 * Check to see if a collision occurs between LevelObjects.
	 * @param other The object this object is colliding with.
	 * @return Whether or not a collision occurred.
	 */
	public String collide(LevelObject other) {
		int midX = (2*(this.getLocation().x)+ this.size.width)/2;
		int midY = (2*(this.getLocation().y)+this.getSize().height)/2;
		Point thisTop = new Point (midX, this.getLocation().y);
		Point thisBottom = new Point ((2*(this.getLocation().x) + this.size.width)/2, (this.getLocation().y+this.getSize().height));
		Point thisLeft = new Point (this.getLocation().y, midY);
		Point thisRight = new Point (midX,midY);
		
		
		Rectangle thatObject = new Rectangle(other.location.x-1, other.location.y-1, other.size.width+2, other.size.height+2);
		if (other instanceof Coin){
			if(thatObject.contains(thisBottom) || thatObject.contains(thisRight) || thatObject.contains(thisRight)|| thatObject.contains(thisRight)){
				return "COIN";
			}
		}
		else if (other instanceof Brick){
			if(thatObject.contains(thisTop)){
				return "BRICK";
			}
		}
		if(thatObject.contains(thisBottom) && thatObject.contains(thisRight)){
			return "BOTTOM_RIGHT_COLLISION";
		}
		else if (thatObject.contains(thisBottom) && thatObject.contains(thisLeft)){
			return "BOTTOM_LEFT_COLLISION";
		}
		else if (thatObject.contains(thisTop) && thatObject.contains(thisLeft)){
			return "TOP_LEFT_COLLISION";
		}
		else if (thatObject.contains(thisBottom) && thatObject.contains(thisRight)){
			return "TOP_RIGHT_COLLISION";
		}
		else if (thatObject.contains(thisBottom) && thatObject.contains(thisTop)){
			return "TOP_BOTTOM_COLLISION";
		}
		else if (thatObject.contains(thisRight) && thatObject.contains(thisLeft)){
			return "LEFT_RIGHT_COLLISION";
		}
		else if (thatObject.contains(thisLeft)){ 
			return "LEFT_COLLISION";
		}
		else if (thatObject.contains(thisRight)){ 
			return "RIGHT_COLLISION";
		}
		else if (thatObject.contains(thisBottom)){ 
			return "BOTTOM_COLLISION";
		}
		else if (thatObject.contains(thisTop)){ 
			return "TOP_COLLISION";
		}
		else{
			return "";
		}
	}
	public void draw(Graphics g) {
		this.image = this.getImage();
		g.drawImage(this.image, this.originalLocation.x+Level.GLOBAL_OFFSET, this.originalLocation.y, this.size.height, this.size.width, null);
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
	 * @return the originalLocation
	 */
	public Point getOriginalLocation() {
		return originalLocation;
	}
	/**
	 * @param originalLocation the originalLocation to set
	 */
	public void setOriginalLocation(Point originalLocation) {
		this.originalLocation = originalLocation;
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
	
}
