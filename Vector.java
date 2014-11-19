/**
 * 
 */

/**
 * @author Monkey
 *
 */
public class Vector {
	private double speed;
	private double direction;
	// The change in the X-Component of Velocity
	private double dX;
	// The change in the Y-Component of Velocity
	private double dY;
	
	/**
	 * Create a 2D vector with a speed component and a direction component
	 * @param s Speed, in m/s
	 * @param d Direction, an angle in degrees
	 */
	public Vector(double s, double d){
		this.speed = s;
		this.direction = d;
		this.dX = this.speed * Math.cos(Math.toRadians(d));
		this.dY = this.speed * Math.sin(Math.toRadians(d));
		
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
		this.speed  = this.dX / Math.sin(Math.toRadians(this.direction));
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
		this.speed  = this.dY / Math.sin(Math.toRadians(this.direction));
	}

	/**
	 * @return the speed
	 */
	public double getSpeed() {
		return speed;
	}

	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(double speed) {
		this.speed = speed;
		this.dX = this.speed * Math.cos(Math.toRadians(this.direction));
		this.dY = this.speed * Math.sin(Math.toRadians(this.direction));
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
		this.dX = this.speed * Math.cos(Math.toRadians(this.direction));
		this.dY = this.speed * Math.sin(Math.toRadians(this.direction));
	}
	
	

}
