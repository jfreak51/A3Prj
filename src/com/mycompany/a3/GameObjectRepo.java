package com.mycompany.a3;

import java.util.ArrayList;

/**
 * This concrete class enforces the Iterator pattern by creating an iterable collection of GameObjects 
 * @author Isaac
 *
 */
public class GameObjectRepo implements ICollection {
	
	/**
	 * An array list is used to store the GameObjects
	 */
	private ArrayList<GameObject> objectList = new ArrayList<>();

	@Override
	public void add(GameObject gO) {
		objectList.add(gO);
	}

	@Override
	public IIterator getIterator() {
		return new GameObjectIterator();
	}
	
	/**
	 * A private class for GameObjectRepo meant to implement an iterator for any GameObjectRepo classes instantiated
	 * @author Isaac
	 *
	 */
	private class GameObjectIterator implements IIterator {
		
		/**
		 * holds the current place of the iterator in the array list
		 */
		private int index;
		
		/**
		 * hasNext() checks to see if there is another element in the list
		 */
		@Override
		public boolean hasNext() {
			
			if (index < objectList.size()) {
				return true;
			}
			return false;
		}
		
		/**
		 * getNext() grabs the a reference to the object in the next position in the object list.
		 * Index is then incremented
		 */
		@Override
		public Object getNext() {
			
			if (this.hasNext()) {
				
				return objectList.get(index++);
				
			}
			return null;
			
		}
		
		
	}

}
