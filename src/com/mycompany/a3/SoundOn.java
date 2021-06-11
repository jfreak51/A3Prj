package com.mycompany.a3;

import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.Image;
import com.codename1.ui.events.ActionEvent;

/**
 * SoundOn is a Command class
 * @author Isaac
 *
 */
public class SoundOn extends Command {
	private GameWorld gw;

	public SoundOn(GameWorld gWorld) {
		super("Sound");
		gw = gWorld;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (((CheckBox) e.getComponent()).isSelected()) {
			gw.setSoundOn();
		}
		else {
			gw.setSoundOff();
		}
	}

}
