package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.Image;
import com.codename1.ui.events.ActionEvent;

/**
 * Brake is a command class for the brake action
 * @author Isaac
 *
 */
public class Brake extends Command {
	
	private GameWorld gw;

	public Brake(GameWorld gWorld) {
		super("Brake");
		gw = gWorld;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		gw.brake();
	}

}

