package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

/**
 * command for the position button
 * @author Isaac
 *
 */
public class Position extends Command {
	MapView mview;
	
	public Position(MapView mv, Game g) {
		super("Position");
		mview = mv;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		 mview.setPushed(true);
	}
}
