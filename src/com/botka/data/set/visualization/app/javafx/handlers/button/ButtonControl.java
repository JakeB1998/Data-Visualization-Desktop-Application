/*
 * File name:  ButtonControl.java
 *
 * Programmer : Jake Botka
 *
 * Date: Aug 16, 2020
 *
 */
package com.botka.data.set.visualization.app.javafx.handlers.button;

import com.botka.data.set.visualization.app.ControllerTransferCallback;
import com.botka.data.set.visualization.app.javafx.components.NodeControls;
import com.botka.data.set.visualization.app.javafx.handlers.FireEventListener;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

/**
 * <insert class description here>
 *
 * @author Jake Botka
 *
 */
public class ButtonControl extends NodeControls
		implements EventHandler<ActionEvent> {

	private Button mButton;
	private FireEventListener mFireEventCallback;

	public ButtonControl(Stage stage,
			ControllerTransferCallback controllerTransferCallback,
			Button button, FireEventListener callback) {
		super(stage, controllerTransferCallback);
		this.mButton = button;
	}

	/**
	 * Shows ui
	 * 
	 * @return
	 */
	public boolean show() {
		if (this.mButton != null) {
			this.mButton.setVisible(true);
			return true;
		}
		return false;
	}

	/**
	 * hides ui
	 * 
	 * @return
	 */
	public boolean hide() {
		if (this.mButton != null) {
			this.mButton.setVisible(false);
			return true;
		}
		return false;
	}

	@Override
	public String getID() {
		if (this.mButton != null)
			return this.mButton.getId();
		return null;
	}

	/**
	 * Overriden by sub But then called by subclass. This allows the subclass to
	 * hand MORE implementation
	 */
	@Override
	public void handle(ActionEvent event) {
		if (this.mFireEventCallback != null) {
			if (this.mButton != null) {
				this.mFireEventCallback.onEventFire(this.mButton.getId());
			}
		}
	}

	/**
	 * 
	 * @return
	 */
	public Button getMenuItem() {
		return this.mButton;
	}

	/**
	 * 
	 * @return
	 */
	public FireEventListener getFileEventListener() {
		return this.mFireEventCallback;
	}

}
