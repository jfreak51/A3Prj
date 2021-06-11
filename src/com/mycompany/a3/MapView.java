package com.mycompany.a3;

import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import com.codename1.ui.geom.Point;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.plaf.Border;

/**
 * MapView is simply a container with a border. It will be expanded upon in the future.
 * @author Isaac
 *
 */
public class MapView extends Container implements Observer {
	
	private GameWorld gw;
	private boolean posPushed = false;

	public MapView(GameWorld gWorld) {
		gw = gWorld;
		this.getAllStyles().setBorder(Border.createLineBorder(10, ColorUtil.rgb(255, 0, 0)));
	}
	
	public void setPushed(boolean b) {
		posPushed = b;
	}
	
	@Override
	public void update(Observable observable, Object data) {
		repaint();

	}
	
	/**
	 * overridden paint to use graphics object onto MapView
	 */
	@Override
	public void paint (Graphics g) {
		super.paint(g);
		Point pCmpRelPrnt = new Point(getX(), getY());
		for (IIterator iter = gw.gameCollection.getIterator(); iter.hasNext();) {
			((GameObject) iter.getNext()).draw(g, pCmpRelPrnt);
		}
	}
	
	/**
	 * Method used to get the Point where the user clicked in the MapView container. Also used to confirm moving a ISelectable unit
	 */
	public void pointerPressed(int x, int y) {
		x = x - getParent().getAbsoluteX();
		y = y - getParent().getAbsoluteY();
		Point pPtrRelPrnt = new Point(x,y);
		Point pCmpRelPrnt = new Point(getX(), getY());
		for(IIterator iter = gw.gameCollection.getIterator(); iter.hasNext();) {
			GameObject gO = (GameObject) iter.getNext();
			if (gO instanceof ISelectable) {
				if (((ISelectable) gO).isSelected() && posPushed) {
					gO.setLocation(x-pCmpRelPrnt.getX(), y-pCmpRelPrnt.getY());
					((ISelectable) gO).setSelected(false);
					posPushed = false;
				}
				else if (((ISelectable) gO).contains(pPtrRelPrnt, pCmpRelPrnt)) {
					((ISelectable) gO).setSelected(true);
				}
				else {
					((ISelectable) gO).setSelected(false);
				}
			}
		}
		repaint();
	}
	
	
}
