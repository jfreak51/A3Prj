package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.Image;
import com.codename1.ui.events.ActionEvent;

/**
 * About is a command for the About action
 * @author Isaac
 *
 */
public class About extends Command {
	
	private GameWorld gw;

	public About(GameWorld gWorld) {
		super("About");
		gw = gWorld;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		gw.About();
	}

}
