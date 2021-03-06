/*
 * File name:  ExitButtonHandler.java
 *
 * Programmer : Jake Botka
 *
 * Date: Aug 2, 2020
 *
 */
package com.botka.data.set.visualization.app.javafx.handlers.button;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

import com.botka.data.set.visualization.app.Savable;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import main.com.botka.data.set.visualization.api.data.serialization.Deserializer;


/**
 * <insert class description here>
 *
 * @author Jake Botka
 *
 */
public class ExitButtonHandler
		implements EventHandler<ActionEvent>, Serializable, Savable<ExitButtonHandler> {

	private static final long serialVersionUID = -107077907428293204L;
	private static final File FILE = new File("exitButtonEventCount" + ".txt");
	private AtomicInteger mEventCount;

	/**
	 * Constructor
	 */
	public ExitButtonHandler() {
		this.mEventCount = new AtomicInteger();
		if (!FILE.exists())
			try {
				FILE.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		this.readFile();
		this.save();

	}

	@Override
	/**
	 * Event handler implementation
	 */
	public void handle(ActionEvent event) {
		this.mEventCount.incrementAndGet();
		this.save();
		System.exit(0);
	}

	/**
	 * Reads data from the variable: FILE
	 */
	public void readFile() {
		if (FILE.exists() && FILE.canRead()) {
			if (FILE.length() > 0) {
				byte[] arr = new byte[(int) FILE.length()];
				FileInputStream fileIn = null;
				try {
					fileIn = new FileInputStream(FILE);
					fileIn.read(arr);

					Integer data = Deserializer.getDeserializer().deserialize(arr);
					if (data != null) {
						int nData = data.intValue();

						data = null;
						fileIn = null;
						this.mEventCount.set(nData);
						
					}

				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println(e.getMessage());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}

	@Override
	public boolean save() {
		return this.save(FILE);
	}

	@Override
	public boolean save(File file) {
		if (file.canWrite()) {
			try {
				FileOutputStream fileOut = new FileOutputStream(file);
				ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
				objectOut.writeObject(new Integer(this.mEventCount.get()));
				
				return true;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.mEventCount.byteValue();
		}
		return false;
	}

}
