package com.mycompany.a3;

import java.util.ArrayList;

import com.codename1.ui.geom.Point;


/**
 * This class is used as a base for all of the objects in the game. All objects contain the following:
 * size: the size of the object is determined by how much space in the form it takes up using a 1000x1000 grid
 * location: contains the coordinates of the centerpoint of the object in a (floatx, floaty) form
 * color: contains the values of the RGB color for the object
 * @author Isaac
 *
 */
abstract class GameObject implements IDrawable, ICollider {
	
	private int size;
	private Point location;
	private int color;
	private ArrayList<GameObject> collisionArray;
	
	/**
	 * The constructor for a GameObject. Needed so that the private variable can be referenced in a child class
	 * @param size
	 * @param x
	 * @param y
	 * @param color
	 */
	public GameObject (int size, int x, int y, int color) {
		this.size = size;
		this.location = new Point(x,y);
		this.color = color;
		this.collisionArray = new ArrayList<GameObject>();
	}
	
	/**
	 * returns the size of the object
	 * @return
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * gets the location (x,y) of the objects centerpoint
	 * @return
	 */
	public Point getLocation() {
		return location;
	}
	
	/**
	 * sets the location of the object using two float variables
	 * @param x: the x value on a 2d grid from 0 to 1000
	 * @param y: the y value on a 2d grid from 0 to 1000
	 */
	public void setLocation(int x, int y) {
		location.setX((int) x);
		location.setY((int) y);
	}
	
	/**
	 * returns the color value of the object in in form with (red, green, blue) format
	 * @return
	 */
	public int getColor() {
		return color;
	}
	
	/**
	 * sets a new color for the object
	 * @param newColor: the int value in (red, green, blue) format
	 */
	public void setColor(int newColor) {
		color = newColor;
	}
	
	public void addCollision(GameObject gO) {
		collisionArray.add(gO);
	}
	
	public void removeCollision(GameObject gO) {
		collisionArray.remove(gO);
	}
	
	public boolean containsCollision(GameObject gO) {
		if (collisionArray.contains(gO)) {
			return true;
		}
		return false;
	}
	
	public void clearCollisions() {
		collisionArray.clear();
	}
}
