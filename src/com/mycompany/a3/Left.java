package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.Image;
import com.codename1.ui.events.ActionEvent;
/**
 * Left is a command class for turning left
 * @author Isaac
 *
 */
public class Left extends Command {
	
	private GameWorld gw;

	public Left(GameWorld gWorld) {
		super("Left");
		gw = gWorld;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		gw.left();
	}

}
