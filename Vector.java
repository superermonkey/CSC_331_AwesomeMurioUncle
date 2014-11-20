/**
 * 
 */

/**
 * @author Monkey
 *
 */
public class Vector {
	private double magnitude;
	private double direction;
	// The change in the X-Component of Magnitude
	private double dX;
	// The change in the Y-Component of Magnitude
	private double dY;
	
	/**
	 * Create a 2D vector with a magnitude component and a direction component
	 * @param s Speed, in m/s
	 * @param d Direction, an angle in degrees
	 */
	public Vector(double s, double d){
		this.magnitude = s;
		this.direction = d;
		this.dX = this.magnitude * Math.cos(Math.toRadians(d));
		this.dY = this.magnitude * Math.sin(Math.toRadians(d));
		
	}

	/**
	 * @return the dX
	 */
	public double getDX() {
		return dX;
	}

	/**
	 * @param dX the dX to set
	 */
	public void setDX(double dX) {
		this.dX = dX;
		this.direction = Math.atan(this.dY / this.dX);
		this.magnitude  = this.dY / Math.sin(Math.toRadians(this.direction));
	}

	/**
	 * @return the dY
	 */
	public double getDY() {
		return dY;
	}

	/**
	 * @param dY the dY to set
	 */
	public void setDY(double dY) {
		this.dY = dY;
		this.direction = Math.atan(this.dY / this.dX);
		this.magnitude  = this.dY / Math.sin(Math.toRadians(this.direction));
	}

	/**
	 * @return the magnitude
	 */
	public double getMagnitude(double acceleration) {
		return magnitude;
	}

	/**
	 * @param magnitude the magnitude to set
	 */
	public void setMagnitude(double spd) {
		this.magnitude = spd;
		this.dY = this.magnitude * Math.sin(Math.toRadians(this.direction));
		this.dX = this.magnitude * Math.cos(Math.toRadians(this.direction));
	}
	
	/**
	 * @return the direction
	 */
	public double getDirection() {
		return direction;
	}

	/**
	 * @param direction the direction to set
	 */
	public void setDirection(double direction) {
		this.direction = direction;
		this.dX = this.magnitude * Math.cos(Math.toRadians(this.direction));
		this.dY = this.magnitude * Math.sin(Math.toRadians(this.direction));
	}


}
