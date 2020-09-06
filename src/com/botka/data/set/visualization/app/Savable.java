/*
 * File name:  Savable.java
 *
 * Programmer : Jake Botka
 *
 * Date: Aug 3, 2020
 *
 */
package com.botka.data.set.visualization.app;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

/**
 * Interface that marks an object as savable.
 *
 * @author Jake Botka
 *
 */
public interface Savable<T extends Serializable> {
	/**
	 * Default implemented method that will do a safe save by handling checks to
	 * determining if the file can safely be written to.
	 * 
	 * @param file File to be saved.
	 * @return True if the file was saved, otherwise false.
	 */
	default boolean safeSave(File file) {
		if (file != null)
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (file.canWrite())
					return this.save(file);
			}
		return false;
	}

	boolean save();

	boolean save(File file);
}
