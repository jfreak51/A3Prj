package com.mycompany.a3;

/**
 * This Interface enforces the Iterator pattern by creating a collection to store objects
 * @author Isaac
 *
 */
public interface ICollection {
	public void add(GameObject gO);
	public IIterator getIterator();
}
