package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.Image;
import com.codename1.ui.events.ActionEvent;

/**
 * This is a command class for the Help command
 * @author Isaac
 *
 */
public class Help extends Command {
	
	private GameWorld gw;

	public Help(GameWorld gWorld) {
		super("Help");
		gw = gWorld;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		gw.Help();
	}

}
