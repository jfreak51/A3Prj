package com.mycompany.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

/**
 * FoodStation is a Fixed object that contains a capacity. This capacity is used to increase the ants food level when they collide
 * @author Isaac
 *
 */
public class FoodStation extends Fixed implements ISelectable {
	
	
	private boolean isSelected;
	private int capacity;
	
	/**
	 * Constructor used to access parent private fields and instantiate them
	 * @param size
	 * @param x
	 * @param y
	 * @param color
	 * @param capacity
	 */
	public FoodStation(int size, int x, int y, int color, int capacity) {
		super(size, x, y, color);
		this.capacity = capacity;
	}
	
	/**
	 * returns the capacity of the food station
	 * @return
	 */
	public int getCapacity() {
		return capacity;
	}
	
	/**
	 * sets the capacity of the food station
	 * @param x: number to be the capacity of foodStation
	 */
	public void setCapacity(int x) {
		capacity = x;
	}
	
	public String toString() {
		return "Food Station: loc=" + (Math.round(this.getLocation().getX() * 10.0) / 10.0) + ", " + (Math.round((this.getLocation().getY()) *10.0) / 10.0) + 
                " color=[" + 	ColorUtil.red(this.getColor()) + "," + 
                				ColorUtil.green(this.getColor()) + "," + 
                				ColorUtil.blue(this.getColor()) + "] " + 
                " size=" + ((FoodStation) this).getSize() + 
                " capacity=" + ((FoodStation) this).getCapacity() + "\n";
	}
	
	/**
	 * Draw method to paint the Food Station object in mapview
	 */
	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
		if (!this.isSelected()) {
			g.setColor(this.getColor());
			g.fillRect(	pCmpRelPrnt.getX() + (this.getLocation().getX() - this.getSize()/2), 
						pCmpRelPrnt.getY() + (this.getLocation().getY() - this.getSize()/2), 
						this.getSize(), this.getSize());
		}
		g.setColor(ColorUtil.BLACK);
		g.drawRect(	pCmpRelPrnt.getX() + (this.getLocation().getX() - this.getSize()/2), 
					pCmpRelPrnt.getY() + (this.getLocation().getY() - this.getSize()/2), 
					this.getSize(), this.getSize());
		
		
	}
	
	/**
	 * Colliding checker for Food Station
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
	 * Food Station collision results
	 */
	@Override
	public void handleCollision(GameObject otherObject, GameWorld gw) {
		if (!this.containsCollision(otherObject) || !otherObject.containsCollision(this)) {
			this.addCollision(otherObject);
			otherObject.addCollision(this);
			gw.foodStationCollision(this);
		}
	}
	
	/**
	 * This checks to see if the User generated point is within the FoodStation objects area.
	 */
	@Override
	public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt) {
		int px = pPtrRelPrnt.getX();
		int py = pPtrRelPrnt.getY();
		int xLoc = pCmpRelPrnt.getX() + this.getLocation().getX() - (this.getSize()/2);
		int yLoc = pCmpRelPrnt.getY() + this.getLocation().getY() - (this.getSize()/2);
		
		if ((px >= xLoc) && (px <= xLoc + this.getSize()) && (py >= yLoc) && (py <= yLoc + this.getSize())) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public void setSelected(boolean b) {
		this.isSelected = b;
		
	}

	@Override
	public boolean isSelected() {
		
		return this.isSelected;
	}
}
