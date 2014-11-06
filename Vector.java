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
	
	public Vector(double s, double d){
		this.speed = s;
		this.direction = d;
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
	}
	
	

}
