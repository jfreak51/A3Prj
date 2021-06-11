package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.Image;
import com.codename1.ui.events.ActionEvent;

/**
 * Right is a command class
 * @author Isaac
 *
 */
public class Right extends Command {
	
	private GameWorld gw;

	public Right(GameWorld gWorld) {
		super("Right");
		gw = gWorld;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		gw.right();
	}

}
