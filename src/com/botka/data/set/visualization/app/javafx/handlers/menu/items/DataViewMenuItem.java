/*
 * File name:  DataViewMenuItem.java
 *
 * Programmer : Jake Botka
 *
 * Date: Sep 6, 2020
 *
 */
package com.botka.data.set.visualization.app.javafx.handlers.menu.items;

import com.botka.data.set.visualization.app.ControllerTransferCallback;
import com.botka.data.set.visualization.app.javafx.handlers.FireEventListener;

import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

/**
 * Wrapper class for menu item
 *
 * @author Jake Botka
 *
 */
public class DataViewMenuItem extends MenuItemControl {

	/**
	 * @param stage
	 * @param controllerTransferCallback
	 * @param menuItem
	 * @param callback
	 */
	public DataViewMenuItem(Stage stage, ControllerTransferCallback controllerTransferCallback,
			MenuItem menuItem, FireEventListener callback) {
		super(stage, controllerTransferCallback, menuItem, callback);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param event
	 */
	@Override
	public void handle(ActionEvent event) {
		//TODO
	}

}
