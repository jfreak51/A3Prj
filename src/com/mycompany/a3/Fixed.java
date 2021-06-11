package com.mycompany.a3;


/**
 * abstract class used to define objects that will stay put for the duration of the game
 * @author Isaac
 *
 */
abstract class Fixed extends GameObject {
	/**
	 * constructor for the Fixed object. Used to access parent variable in the child classes
	 * @param size
	 * @param x
	 * @param y
	 * @param color
	 */
	public Fixed(int size, int x, int y, int color) {
		super(size, x, y, color);
		// TODO Auto-generated constructor stub
	}
}