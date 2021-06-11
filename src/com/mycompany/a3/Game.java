package com.mycompany.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.util.UITimer;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import java.lang.String;

/**
 * The Game class contains Form in which the game will be played through, as well as sending inputs the player sends to the Game World class;
 * The Game World will be created here and instantiated when Game() is called.
 * 
 * 
 * @author Isaac
 * @
 */
public class Game extends Form implements Runnable {
	
	/**
	 * GameWorld, MapView, and ScoreView variables are made to hold objects later on
	 */
	private GameWorld gw;
	private MapView mv;
	private ScoreView sv;
	
	/**
	 * Timer used to activate the tick function and progress the game
	 */
	private UITimer timer = new UITimer(this);
	
	
	/**
	 * A Toolbar, Buttons, and a CheckBox will be instantiated to be added to the form
	 */
	private Button aButton = new Button("Accelerate");
	private Button bButton = new Button("Brake");
	private Button lButton = new Button("Left");
	private Button rButton = new Button("Right");
	
	private Button pButton = new Button("Pause/Play");
	private Button posButton = new Button("Position");
	
	private Toolbar toolbar = new Toolbar();
	private Button taButton = new Button("Accelerate");
	private Button tabButton = new Button ("About");
	private Button teButton = new Button("Exit");
	private CheckBox sCB = new CheckBox("Sound");
	
	
	
	
	/**
	 * Constructor for the Game() class. Creates the games GUI, assigns commands to components, and sets up the MVC pattern
	 * @return 
	 */
	public Game() {
		
		/**
		 * GameWorld, MapView, and ScoreView are instantiated here. ScoreView is given a GameWorld object to access its methods.
		 * GameWorld is given two observers in MapView, and ScoreView
		 */
		gw = new GameWorld();
		mv = new MapView(gw);
		sv = new ScoreView(gw);
		
		gw.addObserver(mv);
		gw.addObserver(sv);
		
		/**
		 * Create the UITimer here and bind it to the game form
		 */
		
		timer.schedule(20, true, this);
		
		/**
		 * The GUI is given the BorderLayout
		 */
		setLayout(new BorderLayout());
		
		/**
		 * Containers for all of the BorderLayout sections are created (besides the Center for MapView and North for ScoreView)
		 */
		Container westContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		Container eastContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		Container southContainer = new Container(new BoxLayout(BoxLayout.X_AXIS).xCenter());
		
		
		/**
		 * The following code establishes commands for all of the components as well as for some key binds.
		 * the commands then calls the corresponding method in GameWorld
		 */
		Accelerate 		aCommand 	= new Accelerate(gw);
		this.addKeyListener('a', aCommand);
		aButton.setCommand(aCommand);
		taButton.setCommand(aCommand);
		
		Brake			bCommand 	= new Brake(gw);
		this.addKeyListener('b', bCommand);
		bButton.setCommand(bCommand);
		
		Exit			eCommand	= new Exit(gw);
		teButton.setCommand(eCommand);
		
		Help			hCommand	= new Help(gw);
		
		Left			lCommand 	= new Left(gw);
		this.addKeyListener('l', lCommand);
		lButton.setCommand(lCommand);
		
		Right			rCommand	= new Right(gw);
		this.addKeyListener('r', rCommand);
		rButton.setCommand(rCommand);
		
		SoundOn			sndCommand	= new SoundOn(gw);
		sCB.setCommand(sndCommand);
		
		About			tabCommand	= new About(gw);
		tabButton.setCommand(tabCommand);
		
		Pause			pCommand	= new Pause(this);
		pButton.setCommand(pCommand);
		
		Position		posCommand 	= new Position(mv, this);
		posButton.setCommand(posCommand);
		
		
		
		/**
		 * The GUI components are customized and added to their corresponding containers
		 */
		aButton.getUnselectedStyle().setBgTransparency(255);
		aButton.getUnselectedStyle().setBgColor(ColorUtil.GREEN);
		aButton.getUnselectedStyle().setFgColor(ColorUtil.BLACK);
		aButton.getUnselectedStyle().setBorder(Border.createLineBorder(3, ColorUtil.rgb(0, 155, 0)));
		aButton.getAllStyles().setMargin(TOP, 100);
		westContainer.add(aButton);
		lButton.getUnselectedStyle().setBgTransparency(255);
		lButton.getUnselectedStyle().setBgColor(ColorUtil.BLACK);
		lButton.getUnselectedStyle().setFgColor(ColorUtil.WHITE);
		lButton.getUnselectedStyle().setBorder(Border.createLineBorder(3, ColorUtil.GRAY));
		lButton.getAllStyles().setMargin(TOP, 30);
		westContainer.add(lButton);
		bButton.getUnselectedStyle().setBgTransparency(255);
		bButton.getUnselectedStyle().setBgColor(ColorUtil.rgb(255, 0, 0));
		bButton.getUnselectedStyle().setFgColor(ColorUtil.BLACK);
		bButton.getUnselectedStyle().setBorder(Border.createLineBorder(3, ColorUtil.rgb(155, 0, 0)));
		bButton.getAllStyles().setMargin(TOP, 100);
		eastContainer.add(bButton);
		rButton.getUnselectedStyle().setBgTransparency(255);
		rButton.getUnselectedStyle().setBgColor(ColorUtil.BLACK);
		rButton.getUnselectedStyle().setFgColor(ColorUtil.WHITE);
		rButton.getUnselectedStyle().setBorder(Border.createLineBorder(3, ColorUtil.GRAY));
		rButton.getAllStyles().setMargin(TOP, 30);
		eastContainer.add(rButton);
		pButton.getUnselectedStyle().setBgTransparency(255);
		pButton.getUnselectedStyle().setBgColor(ColorUtil.GRAY);
		pButton.getUnselectedStyle().setFgColor(ColorUtil.BLACK);
		pButton.getUnselectedStyle().setBorder(Border.createLineBorder(3, ColorUtil.rgb(0, 155, 0)));
		pButton.getAllStyles().setMargin(TOP, 100);
		southContainer.add(pButton);
		posButton.getUnselectedStyle().setBgTransparency(255);
		posButton.getUnselectedStyle().setBgColor(ColorUtil.GRAY);
		posButton.getUnselectedStyle().setFgColor(ColorUtil.BLACK);
		posButton.getUnselectedStyle().setBorder(Border.createLineBorder(3, ColorUtil.rgb(0, 155, 0)));
		posButton.getAllStyles().setMargin(TOP, 100);
		southContainer.add(posButton);
		
		setToolbar(toolbar);
		toolbar.addCommandToRightBar(hCommand);
		taButton.getUnselectedStyle().setBgTransparency(255);
		taButton.getUnselectedStyle().setBgColor(ColorUtil.GRAY);
		taButton.getUnselectedStyle().setFgColor(ColorUtil.BLACK);
		taButton.getUnselectedStyle().setBorder(Border.createLineBorder(3, ColorUtil.LTGRAY));
		toolbar.addComponentToSideMenu(taButton);
		sCB.getUnselectedStyle().setBgTransparency(255);
		sCB.getUnselectedStyle().setBgColor(ColorUtil.GRAY);
		sCB.getUnselectedStyle().setFgColor(ColorUtil.BLACK);
		sCB.getUnselectedStyle().setBorder(Border.createLineBorder(3, ColorUtil.LTGRAY));
		sCB.getUnselectedStyle().setAlignment(CENTER);
		toolbar.addComponentToSideMenu(sCB);
		tabButton.getUnselectedStyle().setBgTransparency(255);
		tabButton.getUnselectedStyle().setBgColor(ColorUtil.GRAY);
		tabButton.getUnselectedStyle().setFgColor(ColorUtil.BLACK);
		tabButton.getUnselectedStyle().setBorder(Border.createLineBorder(3, ColorUtil.LTGRAY));
		toolbar.addComponentToSideMenu(tabButton);
		teButton.getUnselectedStyle().setBgTransparency(255);
		teButton.getUnselectedStyle().setBgColor(ColorUtil.GRAY);
		teButton.getUnselectedStyle().setFgColor(ColorUtil.BLACK);
		teButton.getUnselectedStyle().setBorder(Border.createLineBorder(3, ColorUtil.LTGRAY));
		toolbar.addComponentToSideMenu(teButton);
		
		/**
		 * The Containers are added to the main Forms BorderLayout sections.
		 */
		add(BorderLayout.NORTH, sv);
		add(BorderLayout.CENTER, mv);
		add(BorderLayout.WEST, westContainer);
		add(BorderLayout.EAST, eastContainer);
		add(BorderLayout.SOUTH, southContainer);
		
		
		
		
		/**
		 * The Title is set and the GUI is shown to the user
		 */
		this.setTitle("ThePath Game");
		this.show();
		
		
		/**
		 * The size of the game area is calculated. GameWorld is then initialized through the init call.
		 */
		gw.setGameSize(mv.getWidth(), mv.getHeight());
		gw.init();
		gw.createSounds();
		revalidate();
	}
	
	/**
	 * Function used to pause the game or unpause. It's also used to deactivate/reactivate buttons based on conditions
	 */
	public void pause() {
		if (gw.getPause()) {
			gw.pause();
			timer.schedule(20, true, this);
			pButton.setText("Pause");
			posButton.setEnabled(false);
			rButton.setEnabled(true);
			lButton.setEnabled(true);
			aButton.setEnabled(true);
			bButton.setEnabled(true);
			taButton.setEnabled(true);
			
		}
		else {
			gw.pause();
			timer.cancel();
			pButton.setText("Play");
			posButton.setEnabled(true);
			rButton.setEnabled(false);
			lButton.setEnabled(false);
			aButton.setEnabled(false);
			bButton.setEnabled(false);
			taButton.setEnabled(false);
			
		}
		revalidate();
	}
	
	
	/**
	 * This method is called whenever the timer hits the indicated time (20 msec)
	 */
	@Override
	public void run() {
		gw.tick(20);
		revalidate();
		
	}
}
