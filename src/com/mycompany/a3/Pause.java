package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

/**
 * Command for the pause button
 * @author Isaac
 *
 */
public class Pause extends Command {
	Game g;
	
	Pause(Game game){
		super("Pause");
		g = game;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		g.pause();
		
	}
}
