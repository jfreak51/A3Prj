package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;

/**
 * Exit is a command class that exits the game through user confirmation
 * @author Isaac
 *
 */
public class Exit extends Command {
	
	private GameWorld gw;

	public Exit(GameWorld gWorld) {
		super("Exit");
		gw = gWorld;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		/**
		 * Ok and cancel commands are made and put into a command array
		 */
		Command cOk = new Command("OK");
		Command cCancel = new Command("Cancel");
		Command[] cmds = new Command[] {cOk, cCancel};
		Label myLabel = new Label("Exit?");
		
		/**
		 * A dialogue box is shown asking the user if they'd like to continue the game or exit.
		 * Pressing the OK button exits. Pressing the Cancel button continues playing.
		 */
		Command k = Dialog.show("Would you like to exit the game?", myLabel, cmds);
		
		if (k == cOk) {
			gw.exitGame();
		}
		else {
			System.out.println("The game will resume.");
		}
	}

}
