package com.mycompany.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

/**
 * The spider class is the "enemy" of the spider. It provides the player with an obstacle that needs to be avoided in order to win.
 * It also provides the game with a "lose" condition
 * @author Isaac
 *
 */
public class Spider extends Movable {

	/**
	 * constructor that allows Spider to access its parents fields
	 * @param size
	 * @param x
	 * @param y
	 * @param color
	 * @param heading
	 * @param speed
	 */
	public Spider(int size, int x, int y, int color, int heading, int speed) {
		super(size, x, y, color, heading, speed);
	}
	
	/**
	 * Empty body implementation of setColor() since we don't want to change the color of the spider ever
	 */
	@Override
	public void setColor(int x) {}
	
	@Override
	public String toString() {
		return "Spider:       loc=" + (Math.round(this.getLocation().getX() * 10.0) / 10.0) + ", " + (Math.round(this.getLocation().getY() * 10.0) /10.0) + 
                " color=[" + 	ColorUtil.red(this.getColor()) + "," + 
                				ColorUtil.green(this.getColor()) + "," + 
                				ColorUtil.blue(this.getColor()) + "] " + 
                " heading=" + ((Spider) this).getHeading() + 
                " speed=" + ((Spider) this).getSpeed() + 
                " size=" + ((Spider) this).getSize() + "\n";
	}
	
	
	/**
	 * Overriding the draw method to add shapes to MapView
	 */
	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
		g.setColor(this.getColor());
		int xPosition = Math.round(pCmpRelPrnt.getX() + this.getLocation().getX());
		int yPosition = Math.round(pCmpRelPrnt.getY() + this.getLocation().getY());
		int[] xArray = new int[] {	xPosition + this.getSize()/2, 
									xPosition, 
									xPosition - this.getSize()/2};
		int[] yArray = new int[] {	yPosition - this.getSize()/2, 
									yPosition + this.getSize()/2, 
									yPosition - this.getSize()/2};
		g.drawPolygon(	xArray, 
						yArray, 
						3);	
		
	}
	
	/**
	 * Check to see spider collision
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
	 * Spider collision results
	 */
	@Override
	public void handleCollision(GameObject otherObject, GameWorld gw) {
		if (!this.containsCollision(otherObject) || !otherObject.containsCollision(this)) {
			this.addCollision(otherObject);
			otherObject.addCollision(this);
			gw.spiderCollision();
		}
	}

}
