package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.Image;
import com.codename1.ui.events.ActionEvent;

/**
 * Tick is a command class that calls the tick command in GameWorld when called upon
 * @author Isaac
 *
 */
public class Tick extends Command {
	private GameWorld gw;

	public Tick(GameWorld gWorld) {
		super("Tick");
		gw = gWorld;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		gw.tick(20);
	}

}
