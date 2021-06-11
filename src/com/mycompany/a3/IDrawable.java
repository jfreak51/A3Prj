package com.mycompany.a3;

import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

/**
 * IDrawable enforces drawing for gameobjects so there can be visual aspects to the game.
 * @author Isaac
 *
 */
public interface IDrawable {
	
	/**
	 * 
	 * @param g: The graphics object to be modified
	 * @param pCmpRelPrnt the components position relative to the parent. For making sure the object is being drawn in the right place.
	 */
	public void draw(Graphics g, Point pCmpRelPrnt);
}
