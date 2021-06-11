package com.mycompany.a3;

import java.util.Random;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

/**
 * the Ant class is the main feature of the game and is controlled by the player.
 * @author Isaac
 *
 */
public class Ant extends Movable implements ISteerable {
	
	/**
	 * This section of code here defines the Singleton pattern for the Ant. There can only be one instance of ant.
	 */
	
	private static Ant singleAnt;
	
	
	public static Ant getInstance() {
		if (singleAnt == null) {
			
			singleAnt = new Ant(70, 100, 100, ColorUtil.rgb(100, 0, 0), 0, 2, 1000);
		}
		return singleAnt;
	}
	
	/**
	 * maximumSpeed: variable to limit the speed of the ant
	 * foodLevel: variable to determine how long the ant can go before needing to fill up foodLevel or lose the game
	 * foodConsumptionRate: variable that determines the rate at which foodLevel is decreased
	 * healthLevel: variable that contains the amount of times the ant can collide with a spider before death. Also limits speed
	 * lastFlagReached: variable that hold the last flag that the ant has reached. Is the win condition for the game
	 */
	private int maximumSpeed;
	private int foodLevel;
	private int foodConsumptionRate;
	private int healthLevel;
	private int lastFlagReached;
	
	//-------------------------------------------------------------------
	
	/**
	 * Constructor for the Ant class to access the parents fields
	 * @param size
	 * @param x
	 * @param y
	 * @param color
	 * @param heading
	 * @param speed
	 * @param foodLevel
	 */
	private Ant(int size, int x, int y, int color, int heading, int speed, int foodLevel) {
		super(size, x, y, color, heading, speed);
		
		maximumSpeed = 10;
		this.foodLevel = foodLevel;
		foodConsumptionRate = 2;
		healthLevel = 10;
		lastFlagReached = 1;
	}
		
	//-------------------------------------------------------------------
	//-------------------------------------------------------------------

	/**
	 * changeHeading overrides ISteerables changeHeading method so that the ants heading can be changed by the player
	 */
	@Override
	public void changeHeading(int x) {
		setHeading((getHeading() + 360 + x) % 360);
	}
	
	//-------------------------------------------------------------------
	
	/**
	 * returns the max speed the ant can go
	 * @return
	 */
	public int getMaximumSpeed() {
		return maximumSpeed;
	}
	
	/**
	 * returns the foodLevel of the ant
	 * @return
	 */
	public int getFoodLevel() {
		return foodLevel;
	}
	
	/**
	 * sets the foodLevel of the ant
	 * @param x
	 */
	public void setFoodLevel(int x) {
		foodLevel = x;
	}
	
	/**
	 * gets the rate at which foodLevel will be reduced per tick
	 * @return
	 */
	public int getFoodConsumptionRate() {
		return foodConsumptionRate;
	}
	
	/**
	 * returns the healthLevel of the Ant
	 * @return
	 */
	public int getHealthLevel() {
		return healthLevel;
	}
	
	/**
	 * sets the Health Level of the Ant
	 * @param x
	 */
	public void setHealthLevel(int x) {
		healthLevel = x;
	}
	
	/**
	 * returns the last flag the ant has reached
	 * @return
	 */
	public int getLastFlagReached() {
		return lastFlagReached;
	}
	
	/**
	 * sets the last flag the ant has reached
	 * @param x
	 */
	public void setLastFlagReached(int x) {
		lastFlagReached = x; 
	}
	
	/**
	 * This method is used to reset ant since another and object can't be made
	 * @param startingPoint
	 */
	public void resetAnt(Point startingPoint) {
		this.setLocation(startingPoint.getX(), startingPoint.getY());
		this.setFoodLevel(1000);
		this.setHealthLevel(10);
		this.setLastFlagReached(1);
		this.setHeading(0);
		this.setColor(ColorUtil.rgb(255, 0, 0));
		this.clearCollisions();
		this.setSpeed(2);
	}
	

	@Override
	public String toString() {
		return "Ant:          loc=" + (Math.round(Ant.getInstance().getLocation().getX() * 10.0) / 10.0) + ", " + (Math.round(Ant.getInstance().getLocation().getY() * 10.0) /10.0) + 
	            " color=[" + ColorUtil.red(Ant.getInstance().getColor()) + "," 
	                       + ColorUtil.green(Ant.getInstance().getColor()) + "," 
	                       + ColorUtil.blue(Ant.getInstance().getColor()) + "] " + 
	            " heading=" + ((Ant) Ant.getInstance()).getHeading() + 
	            " speed=" + ((Ant) Ant.getInstance()).getSpeed() + 
	            " size=" + ((Ant) Ant.getInstance()).getSize() + 
	            " maxSpeed=" + ((Ant) Ant.getInstance()).getMaximumSpeed() + 
	            " foodConsumptionRate=" + ((Ant) Ant.getInstance()).getFoodConsumptionRate() + "\n";
	}

	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
		g.setColor(this.getColor());
		g.fillArc(	Math.round(pCmpRelPrnt.getX() + (this.getLocation().getX() - this.getSize()/2)), 
					Math.round(pCmpRelPrnt.getY() + (this.getLocation().getY() - this.getSize()/2)),
					this.getSize(), this.getSize(), 0, 360);
		g.setColor(ColorUtil.BLACK);
		g.drawArc(	Math.round(pCmpRelPrnt.getX() + (this.getLocation().getX() - this.getSize()/2)), 
				Math.round(pCmpRelPrnt.getY() + (this.getLocation().getY() - this.getSize()/2)),
				this.getSize(), this.getSize(), 0, 360);
		
	}
	
	/**
	 * Ant Collision tester
	 */
	@Override
	public boolean collidesWith(GameObject otherObject) {
		
		boolean result = false;
		
		float r1 = this.getLocation().getX() + this.getSize();
		float l1 = this.getLocation().getX();
		float t1 = this.getLocation().getY() + this.getSize();
		float b1 = this.getLocation().getY();
		
		float r2 = otherObject.getLocation().getX() + otherObject.getSize();
		float l2 = otherObject.getLocation().getX();
		float t2 = otherObject.getLocation().getY() + otherObject.getSize();
		float b2 = otherObject.getLocation().getY();
		
		if (r1 < l2 || l1 > r2) {
			result = false;
			this.removeCollision(otherObject);
			otherObject.removeCollision(this);
		}
		else if (t2 < b1 || t1 < b2) {
			result = false;
			this.removeCollision(otherObject);
			otherObject.removeCollision(this);
		}
		else {
			result = true;
		}
		return result;
	}
	
	/**
	 * Empty body implementation since it would be redundant
	 */
	@Override
	public void handleCollision(GameObject otherObject, GameWorld gw) {}
	
	
	
}
