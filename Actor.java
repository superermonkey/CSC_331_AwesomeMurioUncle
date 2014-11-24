import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

/**
 * This class is meant for all of the moving objects.
 * These include but are not limited to Murio the player, Enemies, and mobile powerups (Shroom, Star).
 * 
 * @author RyanPierce
 *
 */
public abstract class Actor extends LevelObject{

	/* 
	 * Gravity constant:
	 * Lower values mean Murio can jump higher and takes longer to come down.
	 * Default value is .5
	 */
	public static final double GRAVITY = .5;
	
	/*
	 * A Vector to simulate velocity.
	 * Vector contains a speed and direction, in degrees.
	 * Vector direction is mainly effected by GRAVITY, for proper arc.
	 */
	protected Vector velocity = new Vector(0, 0);
	
	/*
	 * A Vector to simulate acceleration.
	 * Contains magnitude and direction(in that order), in degrees.
	 * Used for gravity and player acceleration.
	 */
	protected Vector acceleration = new Vector(0, 0);
	
	/**
	 * Create a new Actor for the Level.
	 * 
	 * @param l The initial location of the Actor, in Point(x, y) form.
	 * @param d The size of the Actor, in Dimension(w, h) form.
	 * @param v The visibility boolean of the Actor.
	 * @param vel The initial velocity of the Actor, in Vector(speed, direction) form.
	 * @param i The Image to display for the Actor.
	 */
	public Actor(Point l, Dimension d, boolean v, Vector vel, Image i) 
	{
		super(l, d, v, i);
		this.velocity = vel;
		
		// Set initial downward acceleration due to gravity.
		this.acceleration.setDY(GRAVITY);
	}
	
	
	//
	//
	//
	// MINDLESS GETTERS AND SETTERS AFTER THIS POINT...!!!
	//
	//
	//
	
	
	/**
	 * Returns the velocity in Vector(speed, direction) form.
	 * 
	 * @return The velocity of the Actor in Vector(speed, direction) form.
	 */
	public Vector getVelocity() 
	{
		return velocity;
	}

	/**
	 * Sets the velocity of the Actor, in Vector(speed, direction) form.
	 * 
	 * @param velocity The velocity Vector(speed, direction).
	 */
	public void setVelocity(Vector velocity) 
	{
		this.velocity = velocity;
	}

	/**
	 * Sets the acceleration of the Actor, in Vector(magnitude, direction) form.
	 * 
	 * @return The acceleration in Vector(magnitude, direction) form.
	 */
	public Vector getAcceleration() 
	{
		return acceleration;
	}
	/**
	 * Set acceleration via a new Vector(magnitude, direction).
	 * @param acceleration The acceleration to set, in Vector(magnitude,direction) form.
	 */
	public void setAcceleration(Vector acceleration) 
	{
		this.acceleration = acceleration;
	}
	
	/**
	 * Returns the gravity constant.
	 * @return The gravity constant.
	 */
	public static double getGravity() 
	{
		return GRAVITY;
	}
	
	/**
	 * Draw according to superclass LevelObject.
	 * @param g The Graphics object for drawing.
	 */
	public void draw(Graphics g) 
	{
		super.draw(g);
	}
	
	/**
	 * Since all actors have some sort of movement that differs from the basic side-scrolling,
	 * this method must be implemented by all subclasses.
	 */
	abstract public void move();
}
