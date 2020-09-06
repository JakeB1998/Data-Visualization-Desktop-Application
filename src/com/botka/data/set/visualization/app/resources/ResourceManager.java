/*
 * File name:  ResourceManager.java
 *
 * Programmer : Jake Botka
 *
 * Date: Aug 16, 2020
 *
 */
package com.botka.data.set.visualization.app.resources;

import java.net.URL;

/**
 * <insert class description here>
 *
 * @author Jake Botka
 *
 */
public class ResourceManager {
	public static URL getResourceURL(String localPath) {
		URL url = null;
		url = ResourceManager.class.getResource(localPath);
		return url;
	}
}
