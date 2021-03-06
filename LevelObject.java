import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * Any object that exists in the Murio Level.  Can be static or moving(Actor).
 * Each LevelObject has a visibility variable.
 * Each LevelObject has a size, in the for of a Dimension bounding box width and height.
 * Each has a location, based on the X-Y Coordinate of the upper-left corner of the bounding box.
 * Each has an Image associate with it.
 * @author RyanPierce
 *
 */
public abstract class LevelObject{
	// The visibility of the object.
	protected boolean isVisible;
	// The size of the object, contained in a Dimension with width and height.
	protected Dimension size;
	// The original location of the object, contained in a Point object with X and Y coordinates of the top-left corner
	protected Point originalLocation;
	// The current location of the object, contained in a Point object with X and Y coordinates of the top-left corner
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
		this.image = i;
	}

	/**
	 * Check to see if a collision occurs between LevelObjects.
	 * @param other The object this object is colliding with.
	 * @return Whether or not a collision occurred.
	 */
	public String collide(LevelObject other) {
		/*
		 * Create 6 points around the LevelObject initiating the collide,
		 * 		usually an Actor (Player, Enemy, Powerup).
		 * 
		 * These six points are located at the midpoint of each side of the bounding
		 * 		Rectangle of that object, as illustrated by the stars below.
		 * 
		 * 		_____*_____
		 * 		|		  |
		 * 		*		  *
		 * 		|		  |
		 * 		| Player  |
		 * 		|		  |
		 * 		*		  *
		 * 		|____*____|
		 * 
		 */
		int midX = (2*(this.getLocation().x)+ this.size.width)/2;
		
		// Top intersection Point.
		Point thisTop = new Point (midX, this.getLocation().y);
		// Bottom intersection Point.
		Point thisBottom = new Point (midX, (this.getLocation().y+this.getSize().height));
		// TopLeft intersection Point.
		Point thisTopLeft = new Point (this.getLocation().x, this.location.y + (this.size.height/3));
		// TopRight intersection Point.
		Point thisTopRight = new Point (this.location.x + this.size.width , this.location.y + (this.size.height/3));
		// BottomLeft intersection Point.
		Point thisBottomLeft = new Point (this.getLocation().x, this.location.y + (2*this.size.height/3));
		// BottomRight intersection Point.
		Point thisBottomRight = new Point (this.location.x + this.size.width , this.location.y + (2*this.size.height/3));
				
		// The bounding Rectangle of the LevelObject to test intersection with.
		Rectangle thatObject = new Rectangle(other.location.x-1, other.location.y-1, other.size.width+2, other.size.height+2);
		// If object is Coin.
		if (other instanceof Coin){
			if(thatObject.contains(thisTop) || thatObject.contains(thisTopLeft) || thatObject.contains(thisBottom) 
					|| thatObject.contains(thisTopRight) || thatObject.contains(thisBottomLeft) || thatObject.contains(thisBottomRight)){
			return "COIN";
			}
		}
		// If object is Brick.
		else if (other instanceof Brick && thatObject.contains(thisTop)){
			return "BRICK";
		}
		// If object is Brick.
		else if (other instanceof QuestionMarkBox && thatObject.contains(thisTop)){
			return "QUESTIONMARKBOX";
		}
		// If object is a Goomba.
		else if (other instanceof Goomba){
			if(thatObject.contains(thisBottom)){
				return "GOOMBA_TOP";
			}
			else if(thatObject.contains(thisTop) || thatObject.contains(thisTopLeft) 
					|| thatObject.contains(thisTopRight) || thatObject.contains(thisBottomLeft) || thatObject.contains(thisBottomRight)){
				return "GOOMBA_KILL";
			}
		}
		// If collision occurs at Bottom Right corner of initiating LevelObject (Player, Enemy, Powerup).
		if (thatObject.contains(thisBottomLeft) || thatObject.contains(thisTopLeft)){
			return "LEFT_COLLISION";
		}
		// If collision occurs at Top Right corner of initiating LevelObject (Player, Enemy, Powerup).
		else if (thatObject.contains(thisTopRight) || thatObject.contains(thisBottomRight)){
			return "RIGHT_COLLISION";
		}
		// If collision occurs on Bottom of initiating LevelObject (usually Player interacting with Static Object).
		else if (thatObject.contains(thisBottom)){ 
			return "BOTTOM_COLLISION";
		}
		// If collision occurs on Top of initiating LevelObject (usually Player interacting with Static Object).
		else if (thatObject.contains(thisTop)){ 
			return "TOP_COLLISION";
		}
		// Default case.
		else{
			return "";
		}
	}
	/**
	 * The draw method for LevelObjects.
	 * @param g The Graphics object.
	 */
	public void draw(Graphics g) {
		this.image = this.getImage();
		g.drawImage(this.image, this.originalLocation.x+Level.GLOBAL_OFFSET, this.originalLocation.y, this.size.height, this.size.width, null);
	}
	
	abstract public void move();
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
