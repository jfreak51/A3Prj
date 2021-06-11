package com.mycompany.a3;

/**
 * an abstract class that defines objects that will have their locations updated as the game progresses
 * @author Isaac
 *
 */
public abstract class Movable extends GameObject {
	
	/**
	 * heading determines the angle in which the Movable object will move based on a compass based degree system
	 * speed determines the distance from the objects point it will move once the object is determined to move
	 */
	private float heading;
	private int speed;
	
	/**
	 * constructor created to allow the child class to access parent fields and initialize them
	 * @param size
	 * @param x
	 * @param y
	 * @param color
	 * @param heading
	 * @param speed
	 */
	public Movable(int size, int x, int y, int color, int heading, int speed) {
		super(size, x, y, color);
		this.heading = heading;
		this.speed = speed;
	}
	
	/**
	 * returns the degree angle that the object is facing
	 * @return
	 */
	public float getHeading() {
		return heading;
	}
	
	/**
	 * changes the degree angle that the object is facing
	 * @param newHeading
	 */
	public void setHeading(float newHeading) {
		heading = (newHeading + 360) % 360;
	}
	
	/**
	 * returns the speed of the object
	 * @return
	 */
	public int getSpeed() {
		return speed;
	}
	
	/**
	 * changes the displacement of the object that will occur
	 * @param newSpeed
	 */
	public void setSpeed(int newSpeed) {
		speed = newSpeed;
	}
	
	/**
	 * move() provides the math needed to convert the heading and speed into a tangible coordinate location to provide to the object.
	 * It then sets a new location based on the calculation
	 */
	public void move(int time, int width, int height) {
		float dx = (float)Math.cos(Math.toRadians((90 - getHeading()) % 360)) * (this.getSpeed()* time/20);
		float dy = (float)Math.sin(Math.toRadians((90 - getHeading()) % 360)) * (this.getSpeed()* time/20);
		
		setLocation((int)(getLocation().getX() + dx), (int)(getLocation().getY() + dy));
		
		if (this.getLocation().getX() > (float) width) {
			this.setLocation(width, this.getLocation().getY());
			this.setHeading(270);
		}
		else if (this.getLocation().getY() > (float) height) {
			this.setLocation(this.getLocation().getX(), height);
			this.setHeading(180);
		}
		else if (this.getLocation().getX() < (float) 0) {
			this.setLocation(0, this.getLocation().getY());
			this.setHeading(90);
		}
		else if (this.getLocation().getY() < (float) 0) {
			this.setLocation(this.getLocation().getX(), 0);
			this.setHeading(0);
		}
	}
	
	
}
