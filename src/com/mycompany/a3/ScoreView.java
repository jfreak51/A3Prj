package com.mycompany.a3;

import java.util.Observable;
import java.util.Observer;

import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.Layout;

/**
 * ScoreView is a Container that shows the current values of Ant and important game objective information
 * @author Isaac
 *
 */
public class ScoreView extends Container implements Observer {
	
	/**
	 * Labels names are created. A Layout (FlowLayout) is instantiated. An instance of the static ant class is brought in.
	 */
	private Label time;
	private Label lives;
	private Label lastFlag;
	private Label foodLevel;
	private Label healthLevel;
	private Label sound;
	private FlowLayout flowLayout = new FlowLayout();
	private Ant ant = Ant.getInstance();
	
	/**
	 * ScoreView(GameWorld) is a constructor to set up the Container
	 * @param gw
	 */
	public ScoreView(GameWorld gw) {
		
		/**
		 * Initial values of the container are provided
		 */
		time = new Label("Time = " + Integer.toString(gw.getClockTime()));
		lives = new Label("Lives = " + Integer.toString(gw.getLivesRemaining()));
		lastFlag = new Label("Last Flag Reached = " + ant.getLastFlagReached());
		foodLevel = new Label("Food Level = " + Integer.toString(ant.getFoodLevel()));
		healthLevel = new Label("Health Level = " + Integer.toString(ant.getHealthLevel()));
		if (gw.getSound()) {
			sound = new Label("Sound is ON");
		}
		else {
			sound = new Label("Sound is OFF");
		}
		
		/**
		 * The labels will be centered when added to the container. The containers layout is set to FlowLayout
		 */
		flowLayout.setAlign(CENTER);
		this.setLayout(flowLayout);
		
		/**
		 * The components are added to the container
		 */
		this.add(time);
		this.add(lives);
		this.add(lastFlag);
		this.add(foodLevel);
		this.add(healthLevel);
		this.add(sound);
		
		/**
		 * update is called since the Form was having trouble showing initial values.
		 */
		update(gw, null);
		
	}
	
	/**
	 * update is called when the notifyObservers() method is called in GameWorld.
	 * It updates the values of the labels in the ScoreView
	 */
	@Override
	public void update(Observable observable, Object data) {
		time.setText("Time = " + Integer.toString(((GameWorld) observable).getClockTime()));
		lives.setText(", Lives = " + Integer.toString(((GameWorld) observable).getLivesRemaining()));
		lastFlag.setText(", Last Flag Reached = " + Integer.toString(ant.getLastFlagReached()));
		foodLevel.setText(", Food Level = " + Integer.toString(ant.getFoodLevel()));
		healthLevel.setText(", Health Level = " + Integer.toString(ant.getHealthLevel()));
		if (((GameWorld) observable).getSound()) {
			sound.setText("Sound is ON");
		}
		else {
			sound.setText("Sound is OFF");
		}
		
	}

}
