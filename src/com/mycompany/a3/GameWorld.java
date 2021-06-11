package com.mycompany.a3;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

import com.codename1.ui.geom.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;

/**
 * This Class is used to define the Game World and manipulate the game based on what the user inputs in the Game class.
 * It initializes the main game objects and provides methods for manipulating those objects with the given user commands
 * @author Isaac
 *
 */
public class GameWorld extends Observable {
	
	/**
	 * Creating a rand variable to get random numbers for instantiation of objects
	 */
	Random rand = new Random();
	
	/**
	 * This variable holds the last flags value. Used to determine the win condition for the player
	 */
	private int objective;
	
	
	/**
	 * currentClockTime holds the amount of times the "tick" command has been entered
	 * livesRemaining starts at 3 and ends the game when it hits 0
	 * Sound indicates whether the game volume is on or not
	 * gameWidth and gameHeight determine the MapViews size
	 * gameCollection is used to store all of the GameObject's to iterate through them
	 */
	private int currentClockTime = 0;
	private int clockProgress = 1000;
	private int livesRemaining = 3;
	private boolean sound = false;
	private boolean pause = false;
	private int gameWidth = 0;
	private int gameHeight = 0;
	public GameObjectRepo gameCollection; //creating a GameObjectRepo variable to implement the Iterator pattern
	
	/**
	 * Creating Sound object references
	 */
	private Sound spiderSound;
	private Sound foodSound;
	private Sound flagSound;
	private BGSound loopSound;
	
	/**
	 * This method creates all of the objects needed for the game and adds them to the objectList
	 */
	public void init() {
		
		/**
		 * objective is instantiated here so that the win condition is created
		 */
		objective = 4 + rand.nextInt(6);
		
		/**
		 * The location of the first flag and the ant are put into a variable to be used to create those objects.
		 */
		Point startingPoint = new Point(10 + rand.nextInt(100), 10 + rand.nextInt(100));
		
		/**
		 * the iterator for GameWorldObjects is instantiated
		 */
		gameCollection = new GameObjectRepo();
		
		/**
		 * The initial flag and ant are created as the 1st and second elements of the list. 
		 * Since Ant is a Singleton based class, its data needs to be reset to the initial point since reinitializing the game won't recreate the Ant object.
		 */
		gameCollection.add(Ant.getInstance());
		Ant.getInstance().resetAnt(startingPoint);
		gameCollection.add(new Flag(30, Ant.getInstance().getLocation().getX(), Ant.getInstance().getLocation().getY(), ColorUtil.rgb(0, 0, 255), 1));
		
		/**
		 * This for loop instantiates all Flag objects and adds them to the objectList
		 */
		for (int i = 2; i <= objective; i++) {
			gameCollection.add(new Flag(30, rand.nextInt(gameWidth), rand.nextInt(gameHeight), ColorUtil.rgb(0, 0, 255), i));
		}
		
		/**
		 * This for loop instantiates all Spider objects and adds them to the objectList
		 */
		for (int i = 0; i <= (2 + rand.nextInt(7)); i++) {
			gameCollection.add(new Spider((50 + rand.nextInt(36)), rand.nextInt(gameWidth), rand.nextInt(gameHeight), ColorUtil.BLACK, rand.nextInt(360), (2 + rand.nextInt(6))));
		}
		
		/**
		 * This for loop instantiates all FoodStation objects and adds them to the objectList
		 */
		for (int i = 0; i <= (2 + rand.nextInt(4)); i++) {
			int sCap = 100 + rand.nextInt(31);
			gameCollection.add(new FoodStation(sCap, (rand.nextInt(gameWidth)), (rand.nextInt(gameHeight)), ColorUtil.rgb(0, 255, 0), sCap));
		}
		
		/**
		 * All instances of setChanged(); notifyObservers(gameCollection); are served to update all observers that a change occurred in the code and to check to see if any of
		 * the values needs to be updated. In this case, the initial values will be shown to the user.
		 */
		setChanged(); notifyObservers(gameCollection);
	}
	
	/**
	 * The method increases the speed of the ant if it meets the certain constraints.
	 * speed must be <= the ants health and can't surpass Maximum speed in order for speed to be increased
	 * 
	 */
	public void accelerate() {
		
		for (IIterator iter = gameCollection.getIterator(); iter.hasNext();) {
			GameObject ant = (GameObject) iter.getNext();
			if (ant instanceof Ant) {
				ant = (Ant) ant;
				
				if (((Ant) ant).getSpeed() == ((Ant) ant).getMaximumSpeed()|| ((Ant) ant).getHealthLevel() <= ((Ant) ant).getSpeed()) {
					System.out.println("Your speed can't surpass your Health Level or the Maximum Speed!\nCurrent speed = " + ((Ant) ant).getSpeed() + "\n");
				}
				else {
					((Ant) ant).setSpeed(((Ant) ant).getSpeed() + 1);
					System.out.println("Ant's speed has increased to " + ((Ant) ant).getSpeed() + "\n");
				}
				break;
			}
		}
		setChanged(); notifyObservers(gameCollection);
		
	}
	
	/**
	 * brake() slows down the ant if certain constraints are met. (This is player choosen brake and not a result of spider collision.
	 * speed cannot be voluntarily made 0 
	 */
	public void brake() {
		
		for (IIterator iter = gameCollection.getIterator(); iter.hasNext();) {
			GameObject ant = (GameObject) iter.getNext();
			if (ant instanceof Ant) {
				if (((Ant) ant).getSpeed() > 1) {
					((Ant) ant).setSpeed(((Ant) ant).getSpeed() - 1);
					System.out.println("Ant's speed has decreased to " + ((Ant) ant).getSpeed() + "\n");
				}
				else {
					System.out.println("Speed cannot be reduced to 0!\n");
				}
				break;
			}
		}
		setChanged(); notifyObservers(gameCollection);
	}
	
	/**
	 * left() changes the heading of ant in the negative direction (Turns to the left)
	 */
	public void left() {
		
		for (IIterator iter = gameCollection.getIterator(); iter.hasNext();) {
			GameObject ant = (GameObject) iter.getNext();
			if (ant instanceof Ant) {
				System.out.println("Ant's heading has changed from " + ((Ant) ant).getHeading());
				((Ant) ant).changeHeading(-30);
				System.out.print(" to " + ((Ant) ant).getHeading() + "\n");
				break;
			}
		}
		setChanged(); notifyObservers(gameCollection);
	}
	
	/**
	 * right() changes the heading of the ant in the positive direction (Turns to the right)
	 */
	public void right() {
		
		for (IIterator iter = gameCollection.getIterator(); iter.hasNext();) {
			GameObject ant = (GameObject) iter.getNext();
			if (ant instanceof Ant) {
				System.out.println("Ant's heading has changed from " + ((Ant) ant).getHeading());
				((Ant) ant).changeHeading(30);
				System.out.print(" to " + ((Ant) ant).getHeading() + "\n");
			}
		}
		setChanged(); notifyObservers(gameCollection);
		
		
	}
	
	/**
	 * flagCollision() is called when the ant collides with a flag. The ants lastFlagReached variable is check to see if it should be incremented or not changed.
	 * lastFlagReached only increments if the object is determined to be a flag and if said flag is 1 greater than the ants last flag reached
	 * 
	 * It also checks to see if the last flag reached was the objective. If so, the game is over and the player wins!
	 * @param x: x is the collided flags sequenceNumber
	 */
	public void flagCollision(int x) {
		int y = 0;
		for (IIterator iter = gameCollection.getIterator(); iter.hasNext();) {
			GameObject flag = (GameObject) iter.getNext();
			if (flag instanceof Flag) {
				y++;
				if (y==x) {
					if (((Flag) flag).getSequenceNumber() == ((Ant) Ant.getInstance()).getLastFlagReached() + 1) {
						((Ant) Ant.getInstance()).setLastFlagReached(((Flag) flag).getSequenceNumber());
						System.out.println("The Ant's Last flag reached has been updated!" + "\n");
						if (this.sound) {
							flagSound.play();
						}
						
						if (((Ant) Ant.getInstance()).getLastFlagReached() == objective) {
							System.out.println("Game over, you win! Total time: " + currentClockTime);
							Display.getInstance().exitApplication();
						}
					}
					else {
						System.out.println("That isn't the correct flag!");
					}
				}
			}
		}
		setChanged(); notifyObservers(gameCollection);
	}
	
	/**
	 * foodStationCollision notes that the ant has collided with a food station. Said food station gives the ant its capacity by translating it into foodLevel.
	 * For a1, the first Food Station in the objectList with a capacity > 0 is consumed.
	 * A new Food Station is created once a previous Food Stations stores have been consumed.
	 */
	public void foodStationCollision(GameObject currFoodStation) {
		
		for (IIterator iter = gameCollection.getIterator(); iter.hasNext();) {
			GameObject foodStation = (GameObject) iter.getNext();
			if (foodStation.equals(currFoodStation)) {
				if (((FoodStation) foodStation).getCapacity() != 0) {
					((Ant) Ant.getInstance()).setFoodLevel(((Ant) Ant.getInstance()).getFoodLevel() + ((FoodStation) foodStation).getCapacity());
					((FoodStation) foodStation).setCapacity(0);
					((FoodStation) foodStation).setColor(ColorUtil.rgb(0, 50, 0));
					
					System.out.println("YUM! Ant has eaten from the Food Station and has increased its Food Level to: " + ((Ant) Ant.getInstance()).getFoodLevel() + "\n");
					if (this.sound) {
						foodSound.play();
					}
					
					int sCap = 20 + rand.nextInt(31);
					gameCollection.add(new FoodStation(sCap, (50 + rand.nextInt(951)), (50 + rand.nextInt(951)), ColorUtil.rgb(0, 255, 0), sCap));
					break;
				}
			}
		}
		setChanged(); notifyObservers(gameCollection);
	}
	
	/**
	 * spiderCollision() occurs when the ant collides with a spider object. The ants healthLevel is decreased.
	 * if the ants speed is greater than its new healthLevel value, its speed is decreased
	 * antDeath() is checked to see if the game needs to be reinitialized or if the player continues
	 * the ants color also fades as it collides with the spider.
	 */
	public void spiderCollision() {
		if (this.sound) {
			spiderSound.play();
		}
		((Ant) Ant.getInstance()).setHealthLevel(((Ant) Ant.getInstance()).getHealthLevel() - 1);
		
		Ant.getInstance().setColor(ColorUtil.rgb((255 * Ant.getInstance().getHealthLevel() / 10), 0, 0));
		
		System.out.println("OUCH!!! That hurt. Ant's health is now at: " + ((Ant) Ant.getInstance()).getHealthLevel() + "\n");
		
		if (((Ant) Ant.getInstance()).getSpeed() > ((Ant) Ant.getInstance()).getHealthLevel()) {
			((Ant) Ant.getInstance()).setSpeed(((Ant) Ant.getInstance()).getHealthLevel());
			
			System.out.println("Ant's speed has also decreased to: " + ((Ant) Ant.getInstance()).getSpeed() + "\n");
		}
		
		antDeath();
		setChanged(); notifyObservers(gameCollection);
		
	}
	
	/**
	 * tick() updates the values of the following:
	 * Spiders have their heading changed slightly
	 * Movable objects are use the move() call
	 * Ant's foodLevel value is decreased and antDeath() is called.
	 * currentClockTime is incremented by 1
	 */
	public void tick(int time) {
		
		clockProgress = clockProgress - time;
		
		
		for (IIterator iter = gameCollection.getIterator(); iter.hasNext();) {
			GameObject gO = (GameObject) iter.getNext();
			if (gO instanceof Spider) {
				if (rand.nextBoolean()) {
					((Spider) gO).setHeading(((Spider) gO).getHeading() + rand.nextInt(50));
				}
				else {
					((Spider) gO).setHeading(((Spider) gO).getHeading() - rand.nextInt(50));
				}
			}
			
			if (gO instanceof Movable) {
				((Movable) gO).move(time, gameWidth, gameHeight);
			}
			
			if (gO instanceof ICollider) {
				if (gO.collidesWith(Ant.getInstance())) {
					gO.handleCollision(Ant.getInstance(), this);
				}
			}
			
			
			//clockProgress handles how fast a unit of time progresses
			if (clockProgress <= 0) {
				clockProgress = 1000;
				currentClockTime++;
				if (gO instanceof Ant) {
					((Ant) gO).setFoodLevel(((Ant) gO).getFoodLevel() - ((Ant) gO).getFoodConsumptionRate());
					System.out.println("Ant's Food Level has decreased to: " + ((Ant) gO).getFoodLevel());
					antDeath();
				}
			}
		}
		
		
		setChanged(); 
		notifyObservers(gameCollection);
		
	}
	
	/**
	 * display() shows the player the lives remaining, the elapsed clock time, ants food level, and ants health level
	 * NO LONGER NEEDED AS OF ASSIGNMENT 2
	 */
	public void display() {
		System.out.println("lives=" + getLivesRemaining() + 
        " elapsedTime=" + getClockTime() +
        " foodLevel=" + ((Ant) Ant.getInstance()).getFoodLevel() + 
        " healthLevel=" + ((Ant) Ant.getInstance()).getHealthLevel() + 
        "\n");
	}
	
	/**
	 * map () makes all objects in the objectList show their values. It checks the classes child and determines the information to show:
	 * Ant displays location, color, heading, speed, size, maxSpeed, and foodConsputionRate
	 * Flag displays location, color, size, and sequenceNumber
	 * Spider displays location, heading, speed, and size
	 * FoodStation displays location, color, size, and capacity
	 * NO LONGER NEEDED AS OF ASSIGNMENT 2
	 */
	public void map() {
		for (IIterator iter = gameCollection.getIterator(); iter.hasNext();) {
			GameObject gO = (GameObject) iter.getNext();
			 if (gO instanceof Ant) {
				 System.out.println(gO);
			 }
			 else if (gO instanceof Flag) {
				 System.out.println(gO);
			 }
			 else if (gO instanceof Spider) {
				 System.out.println(gO);
			 }
			 else if (gO instanceof FoodStation) {
				 System.out.println(gO);
			 }
			 else {
				 System.out.println("Something went wrong :Shrug: (instanceof failure or objectList.size() issue)");
			 }
		}
	}
	
	
	/**
	 * exitGame() is invoked if the user confirms they want to exit the game. System.exit is called to quit the program
	 */
	public void exitGame() {
		System.out.println("Thanks for playing!!!");
		Display.getInstance().exitApplication();
	}
	
	/**
	 * resumeGame() just tells the user that gameplay has resumed
	 */
	public void resumeGame() {
		System.out.println("Gameplay has resumed.");
	}
	
	/**
	 * getLives Remaining just shows lives remaining
	 * @return livesRemaining: indicator determining if the game will continue or if the loss condition is reached
	 */
	public int getLivesRemaining() {
		return livesRemaining;
	}
	
	/**
	 * sets livesRemaining
	 * @param x the value given to livesRemaining
	 */
	public void setLivesRemaining(int x) {
		livesRemaining = x;
		setChanged(); notifyObservers(gameCollection);
	}
	
	/**
	 * getClockTime() gets the value of currentClockTime
	 * @return currentClockTime: value indicates how many times tick() has been called
	 */
	public int getClockTime() {
		return currentClockTime;
	}
	
	/**
	 * setClockTime() sets the clock time
	 * @param x the value given to clock time in int
	 */
	public void setClockTime(int x) {
		currentClockTime = x;
		setChanged(); notifyObservers(gameCollection);
	}
	
	
	/**
	 * antDeath() checks to see if the ant is in a state to trigger the failure state or to reinitialize the Game World
	 * determined when the healthLevel of the ant reaches 0.
	 * livesRemaining is reduced by one if the Health level of the ant reaches zero or if the foodLevel of the ant reaches 0
	 */
	public void antDeath() {
		if (((Ant) Ant.getInstance()).getHealthLevel() <= 0 || ((Ant) Ant.getInstance()).getFoodLevel() <= 0) {
			
			setLivesRemaining(getLivesRemaining() - 1);
			
			if (getLivesRemaining() <= 0) {
				System.out.println("Game over, you failed!");
				Display.getInstance().exitApplication();
			}
			else {
				System.out.println("Restarting world with lives = " + getLivesRemaining() + "\n");
				init();
			}
			
		}
		setChanged(); notifyObservers(gameCollection);
	}
	
	/**
	 * gets the current boolean value of sound. True means there is sound. False means there is no sound.
	 * @return boolean sound.
	 */
	public boolean getSound() {
		return sound;
	}
	
	/**
	 * sets the sound variable to true and notifies observers of the change
	 */
	public void setSoundOn() {
		sound = true;
		if (pause == false) {
			loopSound.play();
		}
		setChanged(); notifyObservers(gameCollection);
	}
	
	/**
	 * sets the sound variable to false and notifies observers of the change
	 */
	public void setSoundOff() {
		sound = false;
		loopSound.pause();
		setChanged(); notifyObservers(gameCollection);
	}
	
	/**
	 * Displays information on who programmed the assignment, for which class, and for whom.
	 */
	public void About() {
		Command cOk = new Command("OK");
		
		Label myLabel = new Label("Programmed by Isaac Rodriguez for CSC 133 taught by Dr. Pinar Muyan-Ozcelik. Uhh... I also like kiwis?");
		
		Dialog.show("About", myLabel, cOk);
	}
	
	/**
	 * Displays all of the keybinds available for the user to trigger
	 */
	public void Help() {
		Command cOk = new Command("OK");
		
		Label myLabel = new Label("Keybinds: a = accelerate, b = brake, l = left turn, r = right turn, f = food station collision, g = spider collision, t = tick");
		
		Dialog.show("About", myLabel, cOk);
	}
	
	/**
	 * sets the size of the game area
	 * @param width int
	 * @param height int
	 */
	public void setGameSize(int width, int height) {
		gameWidth = width;
		gameHeight = height;
	}
	
	/**
	 * returns the width of the gameplay area
	 * @return gameWidth int
	 */
	public int getGameWidth() {
		return gameWidth;
	}
	
	/**
	 * returns the height of the gameplay area
	 * @return gameHeight int
	 */
	public int getGameHeight() {
		return gameHeight;
	}
	
	public void createSounds() {
		flagSound = new Sound("FlagCollision.wav");
		foodSound = new Sound("FoodStationCollision.wav");
		spiderSound = new Sound("SpiderCollision.wav");
		loopSound = new BGSound("Background.wav");
	}
	
	public void pause() {
		if(pause) {
			pause = false;
			if (sound) {
				loopSound.play();
			}
		}
		else {
			pause = true;
			if (sound) {
				loopSound.pause();
			}
		}
		
		
	}
	
	public boolean getPause() {
		return pause;
	}
	
	
}
