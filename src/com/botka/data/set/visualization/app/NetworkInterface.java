/*
 * File name:  NetworkInterface.java
 *
 * Programmer : Jake Botka
 *
 * Date: Aug 2, 2020
 *
 */
package com.botka.data.set.visualization.app;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


/**
 * 
 * Utility class to determine if client has an internet connection/
 * @author Jake Botka
 *
 */
public class NetworkInterface {
	public static final NetworkInterface INSTANCE = new NetworkInterface();

	public static NetworkInterface getInstance() {
		return INSTANCE;
	}

	/**
	 * Default constructor
	 */
	private NetworkInterface() {

	}

	/**
	 * Connects to the url google.com to test if an internet connection is present.
	 * 
	 * @return true if is connected to internet, false otherwise
	 */
	public boolean hasInternetConnection() {

		try {
			final URL url = new URL("http://www.google.com");
			final URLConnection conn = url.openConnection();
			conn.connect();
			conn.getInputStream().close();
			return true;
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

}
