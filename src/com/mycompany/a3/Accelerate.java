package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.Image;
import com.codename1.ui.events.ActionEvent;

/**
 * Accelerate is a command class for the accelerate action
 * @author Isaac
 *
 */
public class Accelerate extends Command {
	
	private GameWorld gw;
	
	public Accelerate(GameWorld gWorld) {
		super("Accelerate");
		gw = gWorld;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		gw.accelerate();
	}

}

