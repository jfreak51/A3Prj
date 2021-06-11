package com.mycompany.a3;


import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

/**
 * Flag is a fixed object that is used to progress the game and finally complete the game successfully
 * @author Isaac
 *
 */
public class Flag extends Fixed implements ISelectable {
	
	/**
	 * sequenceNumber is used to increment the ants last flag visited. Once the greatest sequence number is collided, the game ends
	 */
	private int sequenceNumber;
	
	
	private boolean isSelected;
	/**
	 * constructor provided so that the child class may access and initialize parent fields
	 * @param size
	 * @param x
	 * @param y
	 * @param color
	 * @param sequenceNumber
	 */
	public Flag(int size, int x, int y, int color, int sequenceNumber) {
		super(size, x, y, color);
		this.sequenceNumber = sequenceNumber;
	}
	
	/**
	 * returns the sequenceNumber of the flag
	 * @return
	 */
	public int getSequenceNumber() {
		return sequenceNumber;
	}
	
	@Override
	public void setColor(int newColor) {
		
	}
	
	@Override
	public String toString() {
		return "Flag:         loc=" + (Math.round(this.getLocation().getX() * 10.0) / 10.0) + ", " + (Math.round(this.getLocation().getY() * 10.0) /10.0) + 
                " color=[" + 	ColorUtil.red(this.getColor()) + "," + 
                				ColorUtil.green(this.getColor()) + "," + 
                				ColorUtil.blue(this.getColor()) + "] " + 
                " size=" + ((Flag) this).getSize() + 
                " seqNum=" + ((Flag) this).getSequenceNumber() + "\n";
	}

	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
		g.setColor(this.getColor());
		int xPosition = pCmpRelPrnt.getX() + this.getLocation().getX();
		int yPosition = pCmpRelPrnt.getY() + this.getLocation().getY();
		int[] xArray = new int[] {	xPosition + this.getSize()/2, 
									xPosition, 
									xPosition - this.getSize()/2};
		int[] yArray = new int[] {	yPosition - this.getSize()/2, 
									yPosition + this.getSize()/2, 
									yPosition - this.getSize()/2};
		if(this.isSelected()) {
			g.drawPolygon(	xArray, 
							yArray, 
							3);
		}
		else {
			g.fillPolygon(	xArray, 
							yArray, 
							3);
		}
		
		

		g.setColor(ColorUtil.BLACK);
		g.drawString(String.valueOf(this.sequenceNumber), xPosition, yPosition);
		
	}
	
	/**
	 * Flag Collision detection method
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
	 * Flag collision result
	 */
	@Override
	public void handleCollision(GameObject otherObject, GameWorld gw) {
		if (!this.containsCollision(otherObject) || !otherObject.containsCollision(this)) {
			this.addCollision(otherObject);
			otherObject.addCollision(this);
			gw.flagCollision(getSequenceNumber());
		}
	}
	
	/**
	 * setSelected changes the value of selected to determine whether to allow the user to move a ISelectable object
	 */
	@Override
	public void setSelected(boolean b) {
		isSelected = b;
		
	}
	
	/**
	 * Returns the value of a flag objects selected status. Boolean
	 */
	@Override
	public boolean isSelected() {
		return this.isSelected;
	}
	
	/**
	 * This method checks to see if the users mouse input is within the flag's range.
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

	
}
