/*
 * File name:  Component.java
 *
 * Programmer : Jake Botka
 *
 * Date: Aug 10, 2020
 *
 */
package com.botka.data.set.visualization.app.javafx.components;

import com.botka.data.set.visualization.app.ImmutableID;

/**
 * Base class for all components
 *
 * @author Jake Botka
 *
 */
public abstract class Component implements ImmutableID {

	protected static final int UNIQUE_ID_LENGTH = 8;

	/**
	 * 
	 */
	public Component() {

	}

	public abstract int getStaticID();

	public abstract int getUniqueID();

	/**
	 * 
	 */
	public String toString() {
		return super.toString();
	}

}
