/*
 * File name:  ControllerTransferCallback.java
 *
 * Programmer : Jake Botka
 *
 * Date: Aug 10, 2020
 *
 */
package com.botka.data.set.visualization.app;

/**
 * Interface for callbacjs to transfer data to the controller
 *
 * @author Jake Botka
 *
 */
public interface ControllerTransferCallback {

	void onTransfer(Object data, Class type);

}
